package cl.kath.rubypurrs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.Box
import cl.kath.rubypurrs.core.MusicLayer
import cl.kath.rubypurrs.ui.theme.RubyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= 31) installSplashScreen()
        setContent {
            RubyTheme {
                Box {
                    MusicLayer()
                    NavGraph()
                }
            }
        }
    }
}
