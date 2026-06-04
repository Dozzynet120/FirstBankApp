// app/src/main/java/com/firstbank/app/presentation/screens/splash/SplashScreen.kt
package com.firstbank.app.presentation.screens.splash

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.firstbank.app.R
import kotlinx.coroutines.delay

val FirstBankDeepBlue = Color(0xFF003B5C)
val FirstBankGold = Color(0xFFB4975A)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val slides = listOf(
        SlideData(
            imageRes = R.drawable.splashr4,
            title = "Secure & Reliable",
            subtitle = "Your money is safe with FirstBank"
        ),
        SlideData(
            imageRes = R.drawable.splashr5,
            title = "Simple & Smart",
            subtitle = "Bank anytime, anywhere"
        )
    )

    var currentSlide by remember { mutableIntStateOf(0) }
    var isAutoScrolling by remember { mutableStateOf(true) }
    var userInteracted by remember { mutableStateOf(false) }
    var videoFinished by remember { mutableStateOf(false) }

    // Check auth state after splash delay
    LaunchedEffect(Unit) {
        delay(3000) // Show splash for 3 seconds minimum
        if (viewModel.isUserLoggedIn()) {
            onNavigateToHome()
        }
    }

    LaunchedEffect(currentSlide, isAutoScrolling, userInteracted) {
        if (isAutoScrolling && !userInteracted && currentSlide > 0) {
            delay(4000)
            currentSlide = if (currentSlide < slides.size) {
                currentSlide + 1
            } else {
                1
            }
        }
    }

    LaunchedEffect(userInteracted) {
        if (userInteracted) {
            delay(6000)
            userInteracted = false
            isAutoScrolling = true
        }
    }

    LaunchedEffect(videoFinished) {
        if (videoFinished && currentSlide == 0) {
            currentSlide = 1
            videoFinished = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        isAutoScrolling = false
                        userInteracted = true

                        val threshold = 50f
                        when {
                            dragAmount < -threshold -> {
                                if (currentSlide < slides.size) {
                                    currentSlide++
                                } else {
                                    currentSlide = 0
                                }
                            }
                            dragAmount > threshold -> {
                                if (currentSlide > 0) {
                                    currentSlide--
                                } else {
                                    currentSlide = slides.size
                                }
                            }
                        }
                    }
                }
        ) {
            when (currentSlide) {
                0 -> {
                    SplashVideoPlayer(
                        onVideoEnd = { videoFinished = true }
                    )
                }
                else -> {
                    val imageIndex = currentSlide - 1
                    if (imageIndex in slides.indices) {
                        Image(
                            painter = painterResource(id = slides[imageIndex].imageRes),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            FirstBankDeepBlue.copy(alpha = 0.5f),
                            FirstBankDeepBlue.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            val titleText = when (currentSlide) {
                0 -> "Banking for Everyone"
                else -> slides.getOrNull(currentSlide - 1)?.title ?: ""
            }
            val subtitleText = when (currentSlide) {
                0 -> "Trusted by millions across Nigeria"
                else -> slides.getOrNull(currentSlide - 1)?.subtitle ?: ""
            }

            AnimatedVisibility(visible = true) {
                Text(
                    text = titleText,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitleText,
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val totalSlides = slides.size + 1
                repeat(totalSlides) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == currentSlide) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == currentSlide) FirstBankGold
                                else Color.White.copy(alpha = 0.5f)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "FirstBank",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Since 1894",
                fontSize = 12.sp,
                color = FirstBankGold.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNavigateToSignUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FirstBankGold
                )
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FirstBankDeepBlue
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.White.copy(alpha = 0.6f), Color.White.copy(alpha = 0.6f))
                    )
                )
            ) {
                Text(
                    text = "Log In",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Licensed by the Central Bank of Nigeria",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextButton(onClick = { }) {
                Text(
                    text = "Get Help",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@SuppressLint("UnsafeOptInUsageError")
@Composable
private fun SplashVideoPlayer(
    onVideoEnd: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoUri = "android.resource://${context.packageName}/${R.raw.splash_video}".toUri()
            setMediaItem(MediaItem.fromUri(videoUri))
            repeatMode = Player.REPEAT_MODE_OFF
            playWhenReady = true
            prepare()
        }
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    onVideoEnd()
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

data class SlideData(
    val imageRes: Int,
    val title: String,
    val subtitle: String
)