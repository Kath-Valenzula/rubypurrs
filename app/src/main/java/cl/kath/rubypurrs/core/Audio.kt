package cl.kath.rubypurrs.core

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import cl.kath.rubypurrs.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MusicLayer(volume: Float = 0.25f) {
    val ctx = LocalContext.current
    DisposableEffect(Unit) {
        val mp = MediaPlayer.create(ctx, R.raw.theme_rubypurrs_loop).apply {
            isLooping = true
            setVolume(volume, volume)
            start()
        }
        onDispose { mp.release() }
    }
}

@Composable
fun PurrLayer(play: Boolean, volume: Float = 0.6f) {
    val ctx = LocalContext.current
    // Esta key re-crea el player cuando cambia "play"
    LaunchedEffect(play) { /* no-op, solo para clave de recomposición */ }

    DisposableEffect(play) {
        val player = MediaPlayer.create(ctx, R.raw.purr_loop).apply {
            isLooping = true
            setVolume(volume, volume)
            if (play) start()
        }
        onDispose { player.release() }
    }
}

@Composable
fun rememberPurrOneShot(): (Long) -> Unit {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    return remember {
        { ms: Long ->
            scope.launch {
                val mp = MediaPlayer.create(ctx, R.raw.purr_loop).apply {
                    isLooping = false
                    setVolume(0.6f, 0.6f)
                }
                try {
                    mp.start()
                    delay(ms)
                } finally {
                    runCatching { mp.stop() }
                    mp.release()
                }
            }
        }
    }
}
