package com.example.data.model

enum class SkillCategory(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val colorHex: Long,
    val badgeName: String
) {
    PLAYGROUND(
        id = "playground",
        title = "Playground & Sharing",
        description = "Taking turns, asking to play, and sharing toys with friends.",
        iconEmoji = "🛝",
        colorHex = 0xFF10B981,
        badgeName = "Turn-Taking Champion"
    ),
    CLASSROOM(
        id = "classroom",
        title = "Classroom & Group Time",
        description = "Circle time, raising hand, asking for help, and routine changes.",
        iconEmoji = "🏫",
        colorHex = 0xFF3B82F6,
        badgeName = "Classroom Star"
    ),
    EMOTIONS_CALM(
        id = "emotions_calm",
        title = "Feelings & Calm Corner",
        description = "Recognizing emotions, sensory breaks, and taking deep breaths.",
        iconEmoji = "🧘",
        colorHex = 0xFF8B5CF6,
        badgeName = "Calm Superhero"
    ),
    CONVERSATION(
        id = "conversation",
        title = "Friendly Conversations",
        description = "Saying hello, personal space bubbles, and active listening.",
        iconEmoji = "💬",
        colorHex = 0xFFF59E0B,
        badgeName = "Friendship Wizard"
    )
}

enum class EmotionZone(
    val id: String,
    val zoneName: String,
    val emoji: String,
    val colorHex: Long,
    val description: String,
    val calmAction: String
) {
    GREEN_ZONE("green", "Green Zone (Ready & Calm)", "😊", 0xFF22C55E, "Feeling happy, focused, calm, and ready to learn.", "I am feeling great! Keep going."),
    BLUE_ZONE("blue", "Blue Zone (Rest & Recharge)", "😴", 0xFF3B82F6, "Feeling sad, tired, sick, or moving slowly.", "I can ask for a soft blanket or a quiet moment."),
    YELLOW_ZONE("yellow", "Yellow Zone (Cautious & Wobbly)", "⚡", 0xFFEAB308, "Feeling frustrated, excited, silly, or wiggly.", "I can do 3 deep balloon breaths or use a fidget!"),
    RED_ZONE("red", "Red Zone (Stop & Safe Break)", "🛑", 0xFFEF4444, "Feeling overwhelmed, very angry, or out of control.", "Stop, sit in the calm corner, and ask a grown-up for help.")
}

data class VisualCue(
    val title: String,
    val symbolEmoji: String,
    val audioCueText: String,
    val colorHex: Long,
    val pecsLabel: String
)

data class ChoiceOption(
    val id: String,
    val text: String,
    val iconEmoji: String,
    val isPositive: Boolean,
    val feedbackTitle: String,
    val feedbackDescription: String,
    val explanationAudio: String,
    val socialTip: String
)

data class ScenarioStep(
    val stepIndex: Int,
    val title: String,
    val storyPrompt: String,
    val narrationText: String,
    val visualCue: VisualCue,
    val companionSpeech: String,
    val choices: List<ChoiceOption>,
    val peerExpression: String = "😊",
    val peerName: String = "Friend",
    val sensoryTip: String? = null
)

data class SocialScenario(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: SkillCategory,
    val imageResName: String,
    val estimatedMinutes: Int,
    val steps: List<ScenarioStep>,
    val starsReward: Int = 30,
    val unlockStickerId: String,
    val learningObjective: String,
    val evidenceBaseNote: String
)
