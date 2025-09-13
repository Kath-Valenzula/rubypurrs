package cl.kath.rubypurrs.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Scheme = lightColorScheme(
    primary = Color(0xFF6BA4C1),
    background = Color(0xFFE7F0F8),
    surface = Color(0xFFF2EDE4),
    onSurface = Color(0xFF5E4638)
)
@Composable
fun RubyTheme(content: @Composable ()->Unit) {
    MaterialTheme(colorScheme = Scheme, content = content)
}
