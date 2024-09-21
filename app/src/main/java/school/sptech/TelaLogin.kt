package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.ui.theme.CalencareAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalencareAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passwordVisibility by remember { mutableStateOf(false) }

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
                text = "Entrar",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Preto
            )

            Spacer(modifier = Modifier.height(24.dp))
            Column{


                // Campo de Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.mipmap.icone_email),
                            contentDescription = "Email",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {stringResource(R.string.email)},
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(100.dp),

                    )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Senha
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.mipmap.icone_senha),
                            contentDescription = "senha",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { stringResource(R.string.senha) },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(100.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true

                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { /* Ação de esquecimento de senha */ }) {
                    Text(stringResource(R.string.esqueceuSenha), color = Color.Gray)
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            // Botão "Entrar"
            Button(
                onClick = { /* Ação de login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(RoxoNubank)
            ) {
                Text(stringResource(R.string.entrar), color = Branco, fontSize = 16.sp)
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
                    color = Color.Gray,
                    fontSize = 12.sp
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
                    .height(48.dp),
                shape = RoundedCornerShape(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.continuar_com_google1),
                    contentDescription = "Google",
                    modifier = modifier.fillMaxSize(),
                    alignment = Alignment.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    CalencareAppTheme {
        LoginScreen()
    }
}
