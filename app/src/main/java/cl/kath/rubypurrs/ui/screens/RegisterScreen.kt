package cl.kath.rubypurrs.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.kath.rubypurrs.data.UserRepository

@Composable
fun RegisterScreen(onDone:()->Unit){
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf<String?>(null) }
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center){
        Text("Registro", style=MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(email,{email=it}, label={Text("Email")}, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(pass,{pass=it}, label={Text("Contraseña")}, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Button(onClick={
            val ok = UserRepository.register(email.trim(),pass)
            msg = if(ok) "Cuenta creada" else "Ya existe"
            if(ok) onDone()
        }, modifier=Modifier.fillMaxWidth().height(56.dp)){ Text("Crear cuenta") }
        msg?.let { Text(it) }
    }
}
