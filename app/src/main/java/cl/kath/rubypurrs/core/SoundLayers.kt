package cl.kath.rubypurrs.core

import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import cl.kath.rubypurrs.R

@Composable
fun MusicLayer() {
    val ctx = LocalContext.current
    DisposableEffect(Unit) {
        val mp = MediaPlayer.create(ctx, R.raw.theme_rubypurrs_loop).apply {
            isLooping = true
            setVolume(0.25f, 0.25f)
            start()
        }
        onDispose { mp.release() }
    }
}

@Composable
fun PurrLayer(play: Boolean) {
    val ctx = LocalContext.current
    DisposableEffect(play) {
        val mp = MediaPlayer.create(ctx, R.raw.purr_loop).apply { isLooping = true }
        if (play) mp.start()
        onDispose { mp.release() }
    }
}

@Composable
fun rememberPurrOneShot(): (Long) -> Unit {
    val ctx = LocalContext.current
    return remember {
        { _: Long ->
            MediaPlayer.create(ctx, R.raw.purr_loop).apply {
                isLooping = false
                setOnCompletionListener { it.release() }
                start()
            }
        }
    }
}
