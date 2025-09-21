package com.elitec.madriguera.presentation.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elitec.madriguera.R
import com.elitec.madriguera.presentation.navigation.Help
import com.elitec.madriguera.presentation.navigation.Home
import com.elitec.madriguera.presentation.navigation.MainScreens
import com.elitec.madriguera.presentation.navigation.SignUp

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateTo: (MainScreens) -> Unit
) {
    // Control de desplazamiento de Scroll
    val scrollState = rememberScrollState()

    // Login con email
    var isEmailLogin by remember { mutableStateOf(false) }

    // Degradado de imagen
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color(0x802A2A2A)
        )
    )

    // Seleccion de imagen
    val image = if (isSystemInDarkTheme()) {
        painterResource(R.drawable.hav2m)
    } else {
        painterResource(R.drawable.hav1m)
    }
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
                modifier = Modifier.fillMaxWidth()
                    .verticalScroll(
                        scrollState
                    )
            ) {
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
                ) {
                    Image(
                        painter = image,
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth()
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomStart = 20.dp,
                                    bottomEnd = 20.dp
                                )
                            ),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        contentAlignment = Alignment.TopEnd,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomStart = 20.dp,
                                    bottomEnd = 20.dp
                                )
                            )
                            .background(
                                brush
                            )
                    ) {
                        Button(
                            modifier = Modifier.padding(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            onClick = {
                                navigateTo(Help)
                            },
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {

                                Text(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    text = "?"
                                )
                                Text(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    text = "Ayuda"
                                )
                            }
                        }
                    }
                }
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
                        text = "¡Accede a nustros servicios ya!"
                    )
                }
                AnimatedVisibility(
                    visible = !isEmailLogin
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                start = 20.dp,
                                end = 20.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        ),
                        onClick = {
                            isEmailLogin = true
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary,
                            )
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                                text = "Iniciar con Email"
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = isEmailLogin
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = MaterialTheme.colorScheme.primary,
                                unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth()
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp
                                ),
                            value = "",
                            onValueChange = {},
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
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                            }
                        )
                        TextField(
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = MaterialTheme.colorScheme.primary,
                                unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth()
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp
                                ),
                            value = "",
                            onValueChange = {},
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
                                Icon(
                                    imageVector = Icons.Default.Key,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                            }
                        )
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            ),
                            onClick = {}
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                                Text(
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    text = "Iniciar sesión"
                                )
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp
                                )
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = "Si ha olvidado su contraseña, puede restablecerla si presionas",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            )
                            Text(
                                textDecoration = TextDecoration.Underline,
                                textAlign = TextAlign.Center,
                                text = "AQUI",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFF0078D4),
                            )
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(
                        start = 20.dp,
                        end = 20.dp
                    )
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
                Button(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    ),
                    onClick = {}
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(R.drawable.googleicon),
                            contentDescription = ""
                        )
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            text = "Iniciar con Google"
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp
                        )
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Si quieres tener acceso a nuestros servicios puede registrase con su email:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                start = 10.dp,
                                end = 10.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        ),
                        onClick = {
                            navigateTo(SignUp)
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                                text = "Registrarse"
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                ),
                onClick = {
                    navigateTo(Home("1"))
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AllInclusive,
                        contentDescription = ""
                    )
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = "Continuar como visitante"
                    )
                }
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
fun LoginScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            navigateTo = {}
        )
    }
}