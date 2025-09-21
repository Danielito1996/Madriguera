package com.elitec.madriguera.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elitec.madriguera.R
import com.elitec.madriguera.presentation.navigation.MainScreens
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navigateTo: (MainScreens) -> Unit
) {
    // Control de desplazamiento
    val scrollState = rememberScrollState()

    // Estados para los campos
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    // Habilitar botón solo si los campos son válidos
    LaunchedEffect(email, password, confirmPassword) {
        isButtonEnabled = email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() && password == confirmPassword
    }

    // Degradado de imagen
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color(0x802A2A2A)
        )
    )

    Column (
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(10.dp)
    ) {
        Surface(
            shadowElevation = 8.dp,
            tonalElevation = 5.dp,
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                // Imagen superior con halo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .clip(
                            RoundedCornerShape(
                                topEnd = 20.dp,
                                topStart = 20.dp
                            )
                        )
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(
                                topEnd = 20.dp,
                                topStart = 20.dp
                            ),
                            ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), // Halo suave
                            spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.hav3m),
                        contentDescription = "Imagen de fondo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 20.dp,
                                    bottomEnd = 20.dp
                                )
                            ),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                    Box(
                        contentAlignment = Alignment.BottomStart,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 20.dp,
                                    bottomEnd = 20.dp
                                )
                            )
                            .background(brush)
                    ) {

                    }
                }
                // Títulos
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        style = MaterialTheme.typography.displaySmall,
                        text = "Madriguera"
                    )
                    Text(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyLarge,
                        text = "¡Crea tu cuenta para empezar!"
                    )
                }
                // Campos de registro
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Campo de email
                    TextField(
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        value = email,
                        onValueChange = { email = it },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        label = {
                            Text(
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                text = "Correo electrónico"
                            )
                        },
                        placeholder = {
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                text = "correo@dominio.com"
                            )
                        },
                        leadingIcon = {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Icono de email",
                                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            )
                        },
                        isError = isError && email.isBlank()
                    )
                    // Campo de contraseña
                    TextField(
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        value = password,
                        onValueChange = { password = it },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        label = {
                            Text(
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                text = "Contraseña"
                            )
                        },
                        placeholder = {
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                text = "***********"
                            )
                        },
                        leadingIcon = {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = "Icono de contraseña",
                                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = isError && password.isBlank()
                    )
                    // Campo de confirmación de contraseña
                    TextField(
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        label = {
                            Text(
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                text = "Confirmar contraseña"
                            )
                        },
                        placeholder = {
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                text = "***********"
                            )
                        },
                        leadingIcon = {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = "Icono de confirmación",
                                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = isError && (confirmPassword.isBlank() || confirmPassword != password)
                    )
                    // Mensaje de error
                    if (isError) {
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                        )
                    }
                    // Botón de registro
                    val coroutineScope = rememberCoroutineScope()
                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                                errorMessage = "Por favor, completa todos los campos"
                                isError = true
                            } else if (password != confirmPassword) {
                                errorMessage = "Las contraseñas no coinciden"
                                isError = true
                            } else {
                                /* coroutineScope.launch {
                                    try {
                                        val account = Account(client)
                                        account.create(
                                            userId = "unique()", // Generar ID único
                                            email = email,
                                            password = password
                                        )
                                        navigateTo(Mai)
                                    } catch (e: Exception) {
                                        errorMessage = "Error al registrarse: ${e.message}"
                                        isError = true
                                    }
                                }*/
                            }
                        },
                        enabled = isButtonEnabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Icono de registro",
                                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            )
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                                text = "Registrarse"
                            )
                        }
                    }
                    // Enlace para volver al login
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                    ) {
                        Text(
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            text = "¿Ya tienes cuenta? Inicia sesión",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                        Text(
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            text = "AQUÍ",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            style = MaterialTheme.typography.labelLarge,
                            color = Color(0xFF0078D4),
                            modifier = Modifier.clickable {
                                // navigateTo(MainScreens.Login)
                            }
                        )
                    }
                }
                // Divisor
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        modifier = Modifier.weight(3f).fillMaxWidth()
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(3f)
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            text = "O"
                        )
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        modifier = Modifier.weight(3f).fillMaxWidth()
                    )
                }
                // Botón de Google
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = { /* TODO: Implementar autenticación con Google */ }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(R.drawable.googleicon),
                            contentDescription = "Icono de Google"
                        )
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            text = "Registrarse con Google"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Preview(
    showBackground = true, device = "id:small_phone"
)
@Preview(
    showBackground = true, showSystemUi = false,
    device = "id:small_phone",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,

    )
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        modifier = Modifier.fillMaxSize(),
        navigateTo = {}
    )
}