package cl.kath.rubypurrs.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack:()->Unit){
    var expanded by remember { mutableStateOf(false) }
    var lang by remember { mutableStateOf("Español") }
    val langs = listOf("Español","Inglés","Portugués")
    var vibStrong by remember { mutableStateOf(true) }
    var ttsOn by remember { mutableStateOf(true) }
    var theme by remember { mutableStateOf("Claro") }
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)){
        Text("Ajustes y Accesibilidad", style=MaterialTheme.typography.titleMedium)
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(value = lang, onValueChange = {}, readOnly = true, label = { Text("Idioma de narración") }, modifier = Modifier.menuAnchor().fillMaxWidth())
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                langs.forEach { L -> DropdownMenuItem(text = { Text(L) }, onClick = { lang=L; expanded=false }) }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){
            FilterChip(selected = vibStrong, onClick = { vibStrong=!vibStrong }, label = { Text("Vibración fuerte") })
            FilterChip(selected = ttsOn, onClick = { ttsOn=!ttsOn }, label = { Text("Narración TTS") })
        }
        Text("Tamaño de interfaz")
        Row {
            RadioButton(selected = theme=="Claro", onClick = { theme="Claro" }); Text("Claro", modifier=Modifier.padding(top=12.dp, end=16.dp))
            RadioButton(selected = theme=="Oscuro", onClick = { theme="Oscuro" }); Text("Oscuro", modifier=Modifier.padding(top=12.dp))
        }
        Row {
            Checkbox(checked = true, onCheckedChange = { }); Text("Sonidos de confirmación", modifier=Modifier.padding(top=12.dp))
        }
        TextButton(onClick = onBack){ Text("Volver") }
        Text(AnnotatedString("Ayuda y accesibilidad en Android Developers"), textDecoration = TextDecoration.Underline)
    }
}
