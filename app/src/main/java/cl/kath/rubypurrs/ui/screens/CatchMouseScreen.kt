package cl.kath.rubypurrs.ui.screens
import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import cl.kath.rubypurrs.core.Haptics
import cl.kath.rubypurrs.core.Narrator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MouseVM(app: Application): AndroidViewModel(app){
    private val tts = Narrator(app)
    private val h = Haptics(app)
    var ready by mutableStateOf(false)
    var score by mutableStateOf(0)
    fun start(){ ready=false; viewModelScope.launch {
        tts.speak("Prepárate"); delay(1000); ready=true; tts.speak("Ahora")
    } }
    fun tap(){ if(ready){ score+=1; h.ok(); tts.speak("¡Atrápalo!", false); ready=false; start() } else { h.error(); tts.speak("Aún no", false) } }
}
@Composable
fun CatchMouseScreen(onBack:()->Unit, vm: MouseVM = viewModel()){
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.SpaceBetween){
        Text("Toca justo cuando oigas la señal", style=MaterialTheme.typography.titleMedium)
        Text(if(vm.ready) "¡Ahora!" else "Espera…")
        Text("Puntaje: ${vm.score}")
        Button(onClick=vm::start, modifier=Modifier.fillMaxWidth().height(56.dp)){ Text("Iniciar") }
        Button(onClick=vm::tap, modifier=Modifier.fillMaxWidth().height(56.dp)){ Text("Tocar") }
        OutlinedButton(onClick=onBack, modifier=Modifier.fillMaxWidth().height(56.dp)){ Text("Volver") }
    }
}
