package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import school.sptech.ui.components.Background
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.R
import school.sptech.navigation.NavBar
import school.sptech.ui.components.InputIcon
import school.sptech.ui.components.TextoButtonExtraLarge
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.viewModel.UsuarioViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import school.sptech.data.model.Funcionario
import school.sptech.preferencesHelper
import school.sptech.ui.components.AlertError

class TelaLogin : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalencareAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        navController = rememberNavController(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: UsuarioViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var emailPreenchido by remember { mutableStateOf(true) }
    var senhaPreenchida by remember { mutableStateOf(true) }
    var passwordVisibility by remember { mutableStateOf(false) }
    var deuRuim by remember { mutableStateOf(viewModel.deuErro) }
    var msg by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
        //.background(Color(0xFFF5F5F5))
        //.then(modifier)
    ) {
        Background()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp)
        ) {

            // Logo
            Image(
                painter = painterResource(id = R.mipmap.imagem_logo_calencare),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(180.dp)
                    .padding(16.dp)
            )

            // Imagem de login
            Image(
                painter = painterResource(id = R.mipmap.imagem_login),
                contentDescription = "Imagem",
                modifier = Modifier.size(230.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Título "Entrar"
            Text(
                text = stringResource(R.string.entrar),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Preto
            )

            Spacer(modifier = Modifier.height(24.dp))
            Column {
                // Campo de Email
                InputIcon(
                    value = viewModel.usuario.email ?: "",
                    onValueChange = {
                        viewModel.usuario = viewModel.usuario.copy(email = it)
                        emailPreenchido = viewModel.usuario?.email?.isNotEmpty() ?: false
//                        email = it
                    },
                    error = !emailPreenchido,
                    leadingIcon = R.mipmap.icone_email,
                    label = stringResource(R.string.email),
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Campo de Senha
                InputIcon(
                    value = viewModel.usuario.senha ?: "",
                    onValueChange = {
                        viewModel.usuario = viewModel.usuario.copy(senha = it)
                        senhaPreenchida = viewModel.usuario?.senha?.isNotEmpty() ?: false
//                        senha = it
                    },
                    error = !senhaPreenchida,
                    leadingIcon = R.mipmap.icone_senha,
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisibility = !passwordVisibility }
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (passwordVisibility)
                                        R.mipmap.olho_aberto
                                    else R.mipmap.olho_fechado
                                ),
                                contentDescription = "Visibilidade",
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    },
                    label = stringResource(R.string.senha),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { /* Ação de esquecimento de senha */ }) {
                    Text(
                        stringResource(R.string.esqueceuSenha),
                        color = Cinza,
                        fontFamily = fontFamilyPoppins,
                        letterSpacing = letterSpacingPrincipal,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botão "Entrar"
            Button(
                onClick = {
                    emailPreenchido = viewModel.usuario.email?.isNotBlank() == true
                    senhaPreenchida = viewModel.usuario.senha?.isNotBlank() == true

                    if (emailPreenchido && senhaPreenchida) {
                        viewModel.logar()
                        deuRuim = viewModel.deuErro
                        msg = viewModel.erro

                        if (!deuRuim && msg.isNotBlank()) {
                            preferencesHelper.saveIdUsuario(
                                viewModel.usuario.id ?: 0
                            )
                            navController.navigate(NavBar.Inicio.route)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RoxoNubank,
                    contentColor = Branco
                )
            ) {
                TextoButtonExtraLarge(texto = stringResource(R.string.entrar))
            }


            if (deuRuim || msg.isNotBlank()) {
                AlertError(
                    msg = msg
                )
            }

            LaunchedEffect("login"){
                delay(5000)
                deuRuim = false
            }

            Spacer(modifier = Modifier.height(16.dp))

            // "OU"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.ou),
                    color = Cinza,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingPrincipal
                )
                Spacer(modifier = Modifier.width(8.dp))
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botão "Continuar com Google"
            OutlinedButton(
                onClick = { /* Ação de login com Google */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Cinza,

                    ),
                shape = RoundedCornerShape(100.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = Cinza
                )
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.continuar_com_google1),
                    contentDescription = "Google",
                    modifier = modifier.fillMaxSize(0.75f),
                    alignment = Alignment.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, locale = "en")
@Composable
fun LoginScreenPreview() {
    CalencareAppTheme {
        LoginScreen(navController = rememberNavController())
    }
}
