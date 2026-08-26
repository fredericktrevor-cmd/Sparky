package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.AvatarCustomizerScreen
import com.example.ui.screens.CalmZoneScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ParentDashboardScreen
import com.example.ui.screens.RewardsScreen
import com.example.ui.screens.ScenarioPlayerScreen
import com.example.ui.theme.SocialSparkTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SocialSparkViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SocialSparkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val parentalSettings by viewModel.parentalSettings.collectAsStateWithLifecycle()
            val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()

            SocialSparkTheme(sensoryComfortMode = parentalSettings.sensoryComfortMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        // Show bottom nav bar only on top-level kid exploration screens
                        val showBottomBar = currentScreen is AppScreen.Home ||
                                currentScreen is AppScreen.AvatarCustomizer ||
                                currentScreen is AppScreen.Rewards ||
                                currentScreen is AppScreen.CalmZone

                        if (showBottomBar) {
                            KidBottomNavigationBar(
                                currentScreen = currentScreen,
                                onNavigate = { screen -> viewModel.navigateTo(screen) }
                            )
                        }
                    }
                ) { innerPadding ->
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "screen_transition",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) { targetScreen ->
                        when (targetScreen) {
                            is AppScreen.Home -> HomeScreen(viewModel = viewModel)
                            is AppScreen.ScenarioPlayer -> ScenarioPlayerScreen(viewModel = viewModel)
                            is AppScreen.AvatarCustomizer -> AvatarCustomizerScreen(viewModel = viewModel)
                            is AppScreen.Rewards -> RewardsScreen(viewModel = viewModel)
                            is AppScreen.CalmZone -> CalmZoneScreen(viewModel = viewModel)
                            is AppScreen.ParentDashboard -> ParentDashboardScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}

data class BottomNavItem(
    val screen: AppScreen,
    val label: String,
    val iconEmoji: String,
    val activeColor: Color,
    val testTag: String
)

@Composable
fun KidBottomNavigationBar(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit
) {
    val items = listOf(
        BottomNavItem(
            screen = AppScreen.Home,
            label = "Stories",
            iconEmoji = "🏠",
            activeColor = Color(0xFF6750A4),
            testTag = "nav_tab_home"
        ),
        BottomNavItem(
            screen = AppScreen.AvatarCustomizer,
            label = "Avatar",
            iconEmoji = "🎨",
            activeColor = Color(0xFF6750A4),
            testTag = "nav_tab_avatar"
        ),
        BottomNavItem(
            screen = AppScreen.Rewards,
            label = "Rewards",
            iconEmoji = "🏆",
            activeColor = Color(0xFF6750A4),
            testTag = "nav_tab_rewards"
        ),
        BottomNavItem(
            screen = AppScreen.CalmZone,
            label = "Calm",
            iconEmoji = "🧘",
            activeColor = Color(0xFF6750A4),
            testTag = "nav_tab_calm"
        )
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("kid_bottom_nav_bar"),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFCAC4D0))
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            items.forEach { item ->
                val isSelected = currentScreen::class == item.screen::class
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onNavigate(item.screen) },
                    icon = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = if (isSelected) {
                                Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFFE8DEF8))
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            } else {
                                Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                            }
                        ) {
                            Text(
                                text = item.iconEmoji,
                                fontSize = 22.sp,
                                modifier = if (!isSelected) Modifier.alpha(0.6f) else Modifier
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.label.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                letterSpacing = 0.8.sp,
                                color = if (isSelected) Color(0xFF1D192B) else Color(0xFF49454F).copy(alpha = 0.65f)
                            )
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF21005D),
                        unselectedIconColor = Color(0xFF49454F),
                        selectedTextColor = Color(0xFF1D192B),
                        unselectedTextColor = Color(0xFF49454F),
                        indicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.testTag(item.testTag)
                )
            }
        }
    }
}
