package cl.kath.rubypurrs.ui.screens

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import cl.kath.rubypurrs.R
import cl.kath.rubypurrs.core.PurrLayer
import cl.kath.rubypurrs.core.rememberPurrOneShot
import cl.kath.rubypurrs.core.Haptics
import cl.kath.rubypurrs.core.Narrator
import cl.kath.rubypurrs.data.*
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeVM(app: Application) : AndroidViewModel(app) {
    val ctx = app
    var narrator = Narrator(app)
    var haptics = Haptics(app)
    var pet by mutableStateOf(PetState(70, 70, 70))

    init {
        viewModelScope.launch { initPet(ctx); petFlow(ctx).collect { pet = it } }
        viewModelScope.launch {
            while (true) {
                delay(60_000)
                updatePet(
                    ctx,
                    hunger = pet.hunger - 2,
                    energy = pet.energy - 1,
                    joy = pet.joy - 1
                )
            }
        }
    }

    fun feed()   { viewModelScope.launch { updatePet(ctx, hunger = pet.hunger + 15); narrator.speak("Ñam ñam"); haptics.ok() } }
    fun play()   { viewModelScope.launch { updatePet(ctx, joy = pet.joy + 12, energy = pet.energy - 6); narrator.speak("¡A jugar!"); haptics.ok() } }
    fun rest()   { viewModelScope.launch { updatePet(ctx, energy = pet.energy + 15); narrator.speak("A dormir un rato"); haptics.ok() } }
    fun petting(){ viewModelScope.launch { updatePet(ctx, joy = pet.joy + 10); narrator.speak("Purrr"); haptics.ok() } }
}

@Composable
fun HomeScreen(
    goFollow: () -> Unit,
    goMouse:  () -> Unit,
    goPet:    () -> Unit,
    vm: HomeVM = viewModel()
) {
    // Audio
    var purring by remember { mutableStateOf(false) }
    val purrOnce = rememberPurrOneShot()

    // Lottie decorativa (ahora sí se dibuja)
    val comp by rememberLottieComposition(LottieCompositionSpec.Asset("lottie/heart_pulse.json"))
    val anim by animateLottieCompositionAsState(comp, iterations = LottieConstants.IterateForever)

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.bg_watercolor_blue),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(12.dp))
            Image(
                painter = painterResource(R.drawable.ruby_hero),
                contentDescription = "Ruby, toca para escuchar su ronroneo",
                modifier = Modifier
                    .size(200.dp)
                    .semantics { contentDescription = "Ruby, toca para ronroneo" }
                    .clickable {
                        purring = true
                        vm.haptics.ok()
                        purrOnce(900L)
                    },
                contentScale = ContentScale.Fit
            )

            // Animación visible (elimina el warning de 'anim is never used')
            LottieAnimation(
                composition = comp,
                progress = { anim },
                modifier = Modifier.size(48.dp)
            )

            // Capa de purr continuo mientras 'purring' sea true
            PurrLayer(play = purring)
            // Apaga el loop tras ~1s
            LaunchedEffect(purring) { if (purring) { delay(950); purring = false } }

            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatChip("Hambre",  vm.pet.hunger)
                StatChip("Energía", vm.pet.energy)
                StatChip("Ánimo",   vm.pet.joy)
            }
        }

        Column {
            Button(
                onClick = vm::feed,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .semantics { contentDescription = "Dar de comer" }
            ) { Text("Dar de comer") }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = vm::petting,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .semantics { contentDescription = "Caricias" }
            ) { Text("Caricias") }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = vm::play,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .semantics { contentDescription = "Jugar" }
            ) { Text("Jugar") }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = vm::rest,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .semantics { contentDescription = "Descansar" }
            ) { Text("Descansar") }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = goFollow,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .semantics { contentDescription = "Mini-juego Seguir a Ruby" }
            ) { Text("Seguir a Ruby") }

            OutlinedButton(
                onClick = goMouse,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .semantics { contentDescription = "Mini-juego Ratón" }
            ) { Text("Atrapar ratón") }

            OutlinedButton(
                onClick = goPet,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .semantics { contentDescription = "Mini-juego Caricias" }
            ) { Text("Caricias") }
        }
    }
}

@Composable
private fun StatChip(label: String, value: Int) {
    Surface(tonalElevation = 2.dp, color = MaterialTheme.colorScheme.surface) {
        Column(
            Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, color = MaterialTheme.colorScheme.onSurface)
            LinearProgressIndicator(
                progress = { value.coerceIn(0, 100) / 100f },
                modifier = Modifier.width(140.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.White
            )
            Text("$value")
        }
    }
}
