package cl.kath.rubypurrs.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cl.kath.rubypurrs.data.UserRepository

@Composable
fun LoginScreen(onLogin:()->Unit,onRegister:()->Unit,onForgot:()->Unit){
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    Column(Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text("RubyPurrs", style=MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(email,{email=it}, label={Text("Email")},
            modifier=Modifier.fillMaxWidth().semantics{contentDescription="Correo electrónico"})
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(pass,{pass=it}, label={Text("Contraseña")},
            visualTransformation=PasswordVisualTransformation(),
            modifier=Modifier.fillMaxWidth().semantics{contentDescription="Contraseña"})
        Spacer(Modifier.height(16.dp))
        Button(onClick={
            if(UserRepository.login(email.trim(),pass)) onLogin() else error="Credenciales inválidas"
        }, modifier=Modifier.fillMaxWidth().height(56.dp).semantics{contentDescription="Entrar"}){ Text("Entrar") }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick=onRegister, modifier=Modifier.semantics{contentDescription="Ir a registro"}){ Text("Crear cuenta") }
        TextButton(onClick=onForgot, modifier=Modifier.semantics{contentDescription="Recuperar contraseña"}){ Text("Olvidé mi contraseña") }
        error?.let { Text(it, color=MaterialTheme.colorScheme.error) }
    }
}
