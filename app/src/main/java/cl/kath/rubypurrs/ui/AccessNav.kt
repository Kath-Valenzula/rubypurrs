package cl.kath.rubypurrs.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cl.kath.rubypurrs.core.SpeakButton
import cl.kath.rubypurrs.core.VoiceToTextField

const val ACCESS_HUB_ROUTE = "access_hub"

fun NavGraphBuilder.accessGraph(
    @Suppress("UNUSED_PARAMETER") navController: NavController
) {
    composable(route = ACCESS_HUB_ROUTE) { AccessHubScreen() }
}

@Composable
fun AccessHubScreen() {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Modo Accesibilidad", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        VoiceToTextField(value = text, onValueChange = { text = it })
        Spacer(Modifier.height(12.dp))
        SpeakButton(text = text, rate = 1.0f, label = "Leer en voz alta")
    }
}
