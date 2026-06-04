// app/src/main/java/com/firstbank/app/presentation/screens/logo/LogoScreen.kt
package com.firstbank.app.presentation.screens.logo

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstbank.app.R
import com.firstbank.app.presentation.screens.home.FirstBankDeepBlue
import com.firstbank.app.presentation.screens.home.FirstBankGold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LogoScreen(
    onNavigateToSplash: () -> Unit
) {
    // Rotation animation: 0 to 360 degrees over 2 seconds
    val rotation = remember { Animatable(0f) }

    // Scale animation: start small, grow to normal
    val scale = remember { Animatable(0.5f) }

    // Fade animation for text
    val textAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Start animations together
        launch {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = LinearEasing
                )
            )
        }
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = FastOutSlowInEasing
                )
            )
        }
        launch {
            delay(500)
            textAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            )
        }

        // Wait for rotation to finish, then hold briefly, then navigate
        delay(2500)
        onNavigateToSplash()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FirstBankDeepBlue),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Rotating Logo Container
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .rotate(rotation.value)
                    .scale(scale.value)
                    .clip(CircleShape)
                    .background(FirstBankGold),
                contentAlignment = Alignment.Center
            ) {
                // FirstBank Logo - replace with your actual logo drawable
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "FirstBank Logo",
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // App Name with fade
            Text(
                text = "FirstBank",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.alpha(textAlpha.value)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline with fade
            Text(
                text = "Since 1894",
                fontSize = 14.sp,
                color = FirstBankGold.copy(alpha = 0.8f),
                modifier = Modifier.alpha(textAlpha.value)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Loading indicator
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(FirstBankGold.copy(alpha = textAlpha.value * 0.6f))
            )
        }
    }
}