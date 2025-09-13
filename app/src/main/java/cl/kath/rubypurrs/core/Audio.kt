package cl.kath.rubypurrs.core

import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import cl.kath.rubypurrs.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MusicLayer(enabled: Boolean = true, volume: Float = 0.25f) {
    val ctx = LocalContext.current
    val player = remember {
        MediaPlayer.create(ctx, R.raw.theme_rubypurrs_loop).apply {
            isLooping = true
            setVolume(volume, volume)
        }
    }
    LaunchedEffect(enabled) {
        if (enabled) {
            if (!player.isPlaying) player.start()
        } else {
            if (player.isPlaying) player.pause()
        }
    }
    DisposableEffect(Unit) { onDispose { player.release() } }
}

@Composable
fun PurrLayer(play: Boolean, volume: Float = 0.5f) {
    val ctx = LocalContext.current
    val player = remember {
        MediaPlayer.create(ctx, R.raw.purr_loop).apply {
            isLooping = true
            setVolume(volume, volume)
        }
    }
    LaunchedEffect(play) {
        if (play) {
            if (!player.isPlaying) player.start()
        } else {
            if (player.isPlaying) player.pause()
        }
    }
    DisposableEffect(Unit) { onDispose { player.release() } }
}

@Composable
fun rememberPurrOneShot(): (Long) -> Unit {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val player = remember {
        MediaPlayer.create(ctx, R.raw.purr_loop).apply {
            isLooping = true
            setVolume(0.6f, 0.6f)
        }
    }
    DisposableEffect(Unit) { onDispose { player.release() } }
    return remember {
        { ms: Long ->
            scope.launch {
                if (!player.isPlaying) player.start()
                delay(ms)
                if (player.isPlaying) player.pause()
            }
        }
    }
}
