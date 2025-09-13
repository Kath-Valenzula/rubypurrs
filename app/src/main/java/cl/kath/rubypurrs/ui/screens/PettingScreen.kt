package cl.kath.rubypurrs.ui.screens
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.kath.rubypurrs.core.Haptics
import cl.kath.rubypurrs.core.Narrator

class PetVM(app: Application): AndroidViewModel(app){
    private val tts = Narrator(app)
    private val h = Haptics(app)
    var strokes by mutableStateOf(0)
    fun pet(){ strokes+=1; h.ok(); if(strokes%3==0) tts.speak("Prrrr", false) }
}
@Composable
fun PettingScreen(onBack:()->Unit, vm: PetVM = viewModel()){
    val grad = Brush.linearGradient(listOf(Color(0xFFE7F0F8), Color(0xFFF2EDE4)))
    Column(Modifier.fillMaxSize().background(grad).padding(24.dp), verticalArrangement = Arrangement.SpaceBetween){
        Text("Desliza suavemente para acariciar a Ruby", style=MaterialTheme.typography.titleMedium)
        Box(Modifier.fillMaxWidth().weight(1f).pointerInput(Unit){ detectDragGestures { _, _ -> vm.pet() }})
        Text("Caricias: ${vm.strokes}")
        Button(onClick=onBack, modifier=Modifier.fillMaxWidth().height(56.dp)){ Text("Volver") }
    }
}
