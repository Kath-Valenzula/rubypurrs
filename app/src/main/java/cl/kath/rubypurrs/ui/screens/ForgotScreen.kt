package cl.kath.rubypurrs.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import cl.kath.rubypurrs.data.AuthStore

@Composable
fun ForgotScreen(onDone: () -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
    var newPass by rememberSaveable { mutableStateOf("") }
    var show by rememberSaveable { mutableStateOf(false) }
    var msg by remember { mutableStateOf<String?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Recuperar contraseña")
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email registrado") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = newPass,
            onValueChange = { newPass = it },
            label = { Text("Nueva contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (show) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val ok = AuthStore.resetPass(email.trim(), newPass)
                msg = if (ok) "Contraseña actualizada" else "Email no encontrado"
                if (ok) onDone()
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Actualizar") }

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Volver") }

        if (msg != null) {
            Spacer(Modifier.height(8.dp))
            Text(msg!!)
        }
    }
}
