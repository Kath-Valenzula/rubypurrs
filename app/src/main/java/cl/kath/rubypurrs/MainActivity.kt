package cl.kath.rubypurrs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.kath.rubypurrs.core.MusicLayer
import cl.kath.rubypurrs.ui.theme.RubyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RubyTheme {
                // Música de fondo global
                MusicLayer()
                // Navegación
                NavGraph()
            }
        }
    }
}
