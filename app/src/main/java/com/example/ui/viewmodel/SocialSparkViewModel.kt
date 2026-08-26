package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.TTSNarrator
import com.example.data.local.SocialSparkDatabase
import com.example.data.model.AvatarConfig
import com.example.data.model.CategoryMasteryStat
import com.example.data.model.DailyActivityLog
import com.example.data.model.EmotionZone
import com.example.data.model.EvidenceBasedParentTip
import com.example.data.model.ParentalSettings
import com.example.data.model.ScenarioStep
import com.example.data.model.SkillCategory
import com.example.data.model.SocialScenario
import com.example.data.model.StickerItem
import com.example.data.model.TrophyMilestone
import com.example.data.model.UserProgress
import com.example.data.repository.SocialSparkRepository
import com.example.data.scenarios.PreloadedScenarios
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class AppScreen {
    data object Home : AppScreen()
    data class ScenarioPlayer(val scenarioId: String) : AppScreen()
    data object AvatarCustomizer : AppScreen()
    data object Rewards : AppScreen()
    data object CalmZone : AppScreen()
    data object ParentDashboard : AppScreen()
}

data class ActiveScenarioState(
    val scenario: SocialScenario,
    val currentStepIndex: Int = 0,
    val selectedChoiceId: String? = null,
    val showFeedback: Boolean = false,
    val attemptsOnCurrentStep: Int = 0,
    val isStepCompleted: Boolean = false,
    val isScenarioCompleted: Boolean = false,
    val earnedStars: Int = 0,
    val startTimeMs: Long = System.currentTimeMillis()
) {
    val currentStep: ScenarioStep
        get() = scenario.steps.getOrElse(currentStepIndex) { scenario.steps.last() }

    val isLastStep: Boolean
        get() = currentStepIndex >= scenario.steps.size - 1
}

class SocialSparkViewModel(application: Application) : AndroidViewModel(application) {

    private val db = SocialSparkDatabase.getDatabase(application)
    val repository = SocialSparkRepository(db.socialSparkDao())
    val tts = TTSNarrator(application)

    // Current Screen State
    private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.Home)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Active Scenario State
    private val _activeScenarioState = MutableStateFlow<ActiveScenarioState?>(null)
    val activeScenarioState: StateFlow<ActiveScenarioState?> = _activeScenarioState.asStateFlow()

    // Selected Emotion Zone in Calm Center
    private val _selectedEmotionZone = MutableStateFlow<EmotionZone>(EmotionZone.GREEN_ZONE)
    val selectedEmotionZone: StateFlow<EmotionZone> = _selectedEmotionZone.asStateFlow()

    // Notification / Toast popup message
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Data Flows from Repository
    val avatarConfig: StateFlow<AvatarConfig> = repository.avatarConfig
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AvatarConfig())

    val parentalSettings: StateFlow<ParentalSettings> = repository.parentalSettings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ParentalSettings())

    val userProgress: StateFlow<UserProgress> = repository.userProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProgress())

    val stickers: StateFlow<List<StickerItem>> = repository.stickers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PreloadedScenarios.allStickers)

    val activityLogs: StateFlow<List<DailyActivityLog>> = repository.activityLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val scenarioProgress = repository.scenarioProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val categoryMastery: StateFlow<List<CategoryMasteryStat>> = combine(
        scenarioProgress,
        activityLogs
    ) { progress, logs ->
        repository.computeCategoryMastery(progress, logs)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SkillCategory.entries.map { CategoryMasteryStat(it, 0, 2, 100) }
    )

    init {
        // Sync voice parameters with settings
        viewModelScope.launch {
            parentalSettings.collect { settings ->
                tts.setVoiceParameters(settings.narrationPitch, settings.narrationSpeed)
            }
        }
    }

    // Navigation Methods
    fun navigateTo(screen: AppScreen) {
        tts.stop()
        _currentScreen.value = screen

        // Auto narration triggers on screen arrival
        when (screen) {
            is AppScreen.Home -> {
                speakText("Welcome to Social Spark! Choose an adventure or visit your calm zone!")
            }
            is AppScreen.AvatarCustomizer -> {
                speakText("Welcome to the avatar dressing room! Customize your buddy, outfit, and sensory gear!")
            }
            is AppScreen.Rewards -> {
                speakText("Here is your sticker album and trophies! You are doing awesome!")
            }
            is AppScreen.CalmZone -> {
                speakText("Welcome to the Calm Corner. Take deep balloon breaths or check your feelings.")
            }
            is AppScreen.ParentDashboard -> {
                // Parent zone
            }
            is AppScreen.ScenarioPlayer -> {
                startScenario(screen.scenarioId)
            }
        }
    }

    // Scenario Operations
    fun startScenario(scenarioId: String) {
        val scenario = repository.getScenarioById(scenarioId) ?: return
        _activeScenarioState.value = ActiveScenarioState(
            scenario = scenario,
            currentStepIndex = 0,
            selectedChoiceId = null,
            showFeedback = false,
            attemptsOnCurrentStep = 0,
            isStepCompleted = false,
            isScenarioCompleted = false,
            earnedStars = 0,
            startTimeMs = System.currentTimeMillis()
        )
        _currentScreen.value = AppScreen.ScenarioPlayer(scenarioId)

        val firstStep = scenario.steps.firstOrNull()
        if (firstStep != null && parentalSettings.value.autoPlayNarration) {
            speakText("${firstStep.title}. ${firstStep.narrationText}")
        }
    }

    fun selectScenarioChoice(choiceId: String) {
        val state = _activeScenarioState.value ?: return
        val currentStep = state.currentStep
        val choice = currentStep.choices.find { it.id == choiceId } ?: return

        val attempts = state.attemptsOnCurrentStep + 1
        _activeScenarioState.value = state.copy(
            selectedChoiceId = choiceId,
            showFeedback = true,
            attemptsOnCurrentStep = attempts
        )

        // Read choice explanation aloud
        speakText("${choice.feedbackTitle}. ${choice.explanationAudio}")

        if (choice.isPositive) {
            _activeScenarioState.value = _activeScenarioState.value?.copy(
                isStepCompleted = true
            )
        }
    }

    fun nextScenarioStep() {
        val state = _activeScenarioState.value ?: return
        if (state.isLastStep) {
            // Complete Scenario
            val totalStarsToAdd = state.scenario.starsReward
            val durationSec = ((System.currentTimeMillis() - state.startTimeMs) / 1000).toInt().coerceAtLeast(10)
            val firstAttempt = state.attemptsOnCurrentStep <= 1

            viewModelScope.launch {
                repository.recordScenarioCompletion(
                    scenario = state.scenario,
                    starsEarned = totalStarsToAdd,
                    attemptsCount = state.attemptsOnCurrentStep,
                    firstAttemptSuccess = firstAttempt,
                    durationSeconds = durationSec
                )

                // Update total stars
                val current = userProgress.value
                val newProgress = current.copy(
                    totalStars = current.totalStars + totalStarsToAdd,
                    completedScenariosCount = current.completedScenariosCount + 1
                )
                repository.saveUserProgress(newProgress)
            }

            _activeScenarioState.value = state.copy(
                isScenarioCompleted = true,
                earnedStars = totalStarsToAdd
            )

            speakText("Woohoo! You completed ${state.scenario.title} and earned $totalStarsToAdd shining stars!")
        } else {
            val nextIndex = state.currentStepIndex + 1
            _activeScenarioState.value = state.copy(
                currentStepIndex = nextIndex,
                selectedChoiceId = null,
                showFeedback = false,
                attemptsOnCurrentStep = 0,
                isStepCompleted = false
            )
            val nextStep = state.scenario.steps[nextIndex]
            if (parentalSettings.value.autoPlayNarration) {
                speakText("${nextStep.title}. ${nextStep.narrationText}")
            }
        }
    }

    // Avatar Configuration
    fun updateAvatar(newConfig: AvatarConfig) {
        viewModelScope.launch {
            repository.saveAvatarConfig(newConfig)
        }
    }

    // Parental Settings
    fun updateParentalSettings(newSettings: ParentalSettings) {
        viewModelScope.launch {
            repository.saveParentalSettings(newSettings)
        }
    }

    // Emotion Zone selection
    fun selectEmotionZone(zone: EmotionZone) {
        _selectedEmotionZone.value = zone
        speakText("${zone.zoneName}. ${zone.description} Helpful tip: ${zone.calmAction}")
    }

    // Audio Narration Helper
    fun speakText(text: String) {
        tts.speak(text)
    }

    fun stopAudio() {
        tts.stop()
    }

    fun toggleMuteAudio(): Boolean {
        return tts.toggleMute()
    }

    override fun onCleared() {
        super.onCleared()
        tts.shutdown()
    }
}
