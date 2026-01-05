package com.flacinc.todok_mp.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flacinc.todok_mp.ui.theme.TodoKMPTheme
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import todok_mp.composeapp.generated.resources.Res
import todok_mp.composeapp.generated.resources.app_name
import todok_mp.composeapp.generated.resources.splash_subtitle

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/business_meeting_animation.json").decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = Compottie.IterateForever
    )

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            stringResource(Res.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(20.dp),
            textAlign = TextAlign.Center
        )

        Text(
            stringResource(Res.string.splash_subtitle),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(20.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = rememberLottiePainter(
                composition = composition,
                progress = { progress }
            ),
            contentDescription = "Animation",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))

        CircularProgressIndicator(
            modifier = Modifier,
            strokeWidth = 4.dp,
            trackColor = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreviewLight() {
    TodoKMPTheme {
        SplashScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreviewNight() {
    TodoKMPTheme(
        darkTheme = true
    ) {
        SplashScreen()
    }
}