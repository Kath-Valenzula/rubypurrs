package cl.kath.rubypurrs.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cl.kath.rubypurrs.data.AuthStore
import cl.kath.rubypurrs.data.User

@Composable
fun RegisterScreen(onDone: () -> Unit) {
    val focus = LocalFocusManager.current

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }
    var showPass by rememberSaveable { mutableStateOf(false) }
    var role by rememberSaveable { mutableStateOf("Usuario") }

    var error by remember { mutableStateOf<String?>(null) }
    var ok by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Crear cuenta", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focus.clearFocus() })
        )

        Spacer(Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = role == "Usuario", onClick = { role = "Usuario" })
                Text("Usuario")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = role == "Admin", onClick = { role = "Admin" })
                Text("Admin")
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isBlank() || email.isBlank() || pass.isBlank()) {
                    error = "Completa todos los campos"
                    ok = false
                    return@Button
                }

                val u = User(email.trim(), pass) // <- User(email, pass)

                if (AuthStore.register(u)) {
                    error = null
                    ok = true
                    focus.clearFocus()
                } else {
                    error = "Email duplicado o límite de 5 usuarios"
                    ok = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Registrar") }

        TextButton(onClick = onDone, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Volver")
        }

        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error!!)
        }
        if (ok) {
            Spacer(Modifier.height(8.dp))
            Text("Usuario creado")
        }
    }
}
