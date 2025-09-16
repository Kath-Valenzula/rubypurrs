package cl.kath.rubypurrs.core

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun SpeakButton(
    text: String,
    rate: Float = 1.0f,
    label: String = "Leer en voz alta"
) {
    val ctx = LocalContext.current
    var tts: TextToSpeech? by remember { mutableStateOf(null) }

    DisposableEffect(Unit) {
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }

    Button(
        onClick = {
            tts = TextToSpeech(ctx) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    @Suppress("DEPRECATION")
                    tts?.language = Locale.getDefault()
                    tts?.setSpeechRate(rate)
                    tts?.speak(text.ifBlank { "Sin texto" }, TextToSpeech.QUEUE_FLUSH, null, "say")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .semantics { contentDescription = "Leer en voz alta" }
    ) {
        Text(label)
    }
}

@Composable
fun VoiceToTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Escribe o dicta",
    modifier: Modifier = Modifier
) {
    val launcher = rememberLauncherForActivityResult(StartActivityForResult()) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            val r = res.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!r.isNullOrEmpty()) onValueChange(r[0])
        }
    }

    Column(modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedButton(
            onClick = {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    .putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )
                    .putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                launcher.launch(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .semantics { contentDescription = "Dictar con voz" }
        ) {
            Text("Dictar con voz")
        }
    }
}
