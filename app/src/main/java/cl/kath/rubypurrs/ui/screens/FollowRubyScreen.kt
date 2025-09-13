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
import cl.kath.rubypurrs.core.rememberPurrOneShot
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FollowVM(app: Application): AndroidViewModel(app){
    private val tts = Narrator(app)
    private val h = Haptics(app)
    var status by mutableStateOf("Pulsa Iniciar")
    var score by mutableStateOf(0)
    fun start(){ viewModelScope.launch {
        status = "Escucha y toca cuando Ruby esté al frente"
        while(true){
            val dir = listOf("izquierda","derecha","frente").random()
            tts.speak("Ruby a $dir")
            repeat(10){ if(dir=="frente") h.ramp(it); delay(100) }
            delay(600)
        }
    } }
    fun ok(){ score+=1; h.ok(); tts.speak("Bien", false) }
}

@Composable
fun FollowRubyScreen(onBack:()->Unit, vm: FollowVM = viewModel()){
    val comp by rememberLottieComposition(LottieCompositionSpec.Asset("lottie/sonar_pulse.json"))
    val anim by animateLottieCompositionAsState(comp, iterations = LottieConstants.IterateForever)
    val purr = rememberPurrOneShot()

    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.SpaceBetween){
        LottieAnimation(composition = comp, progress = { anim }, modifier=Modifier.fillMaxWidth().height(220.dp))
        Text(vm.status, style=MaterialTheme.typography.titleMedium)
        Text("Puntaje: ${vm.score}")
        Row {
            Button(onClick=vm::start, modifier=Modifier.weight(1f).height(56.dp)){ Text("Iniciar") }
            Spacer(Modifier.width(8.dp))
            Button(
                onClick={
                    vm.ok()
                    purr(700L)
                },
                modifier=Modifier.weight(1f).height(56.dp)
            ){ Text("Tocar") }
        }
        OutlinedButton(onClick=onBack, modifier=Modifier.fillMaxWidth().height(56.dp)){ Text("Volver") }
    }
}
