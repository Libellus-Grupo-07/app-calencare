package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank

class DadosEmpresa : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    //containerColor = Color.White // Cor de fundo do Scaffold
                ) { innerPadding ->
                    TelaPerfil(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TelaPerfil(modifier: Modifier = Modifier) {
    Scaffold { contentPadding ->
        Background()
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp),
                //.background(Color.White), // Cor de fundo da coluna
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Linha para os botões "Voltar", "Sair da Conta" e "Salvar"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botão Voltar à esquerda
                IconButton(
                    onClick = { /* Ação para Voltar */ }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.voltar),
                        modifier = Modifier.size(30.dp),
                        contentDescription = "Voltar"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botão "Sair da Conta"
                Button(
                    onClick = { /* Ação Dados da Empresa */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.border(1.dp, Color(0xFF888888), RoundedCornerShape(50.dp))
                ) {
                    Text(
                        text = "Sair da conta",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color(0xFF888888)
                        )
                    )
                }
                Spacer(modifier = Modifier.width(9.dp))

                // Botão "Salvar"
                Button(
                    onClick = { /* Ação para Salvar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9F35F0)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Salvar", color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    )
                }
            }

            // Imagem de perfil centralizada
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .padding(vertical = 0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ft),
                    contentDescription = "Imagem de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp)
                )
            }

            // Botões para alternar dados da empresa e pessoais
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* Ação Dados da Empresa */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.border(1.dp, Color.Transparent, RoundedCornerShape(50.dp))
                ) {
                    Text(
                        text = "Dados da Empresa",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color(0xFF888888)
                        )
                    )
                }
                Button(
                    onClick = { /* Ação Dados Pessoais */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9F35F0)),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(text = "Dados Pessoais", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Nome
            Text(text = "Nome", color = Color(0xFF888888), fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = "Patricia Dias",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RoxoNubank,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Preto,
                    unfocusedTextColor = Preto,
                    focusedLabelColor = Color.Gray
                ),
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Telefone
            Text(text = "Telefone", color = Color(0xFF888888), fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = "(11) 973682-3933",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RoxoNubank,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Preto,
                    unfocusedTextColor = Preto,
                    focusedLabelColor = Color.Gray
                ),
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email
            Text(text = "Email", fontSize = 16.sp, color = Color(0xFF888888), fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = "patricia@email.com",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RoxoNubank,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Preto,
                    unfocusedTextColor = Preto,
                    focusedLabelColor = Color.Gray
                ),
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Perfil
            Text(text = "Perfil", fontSize = 16.sp, color = Color(0xFF888888), fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = "Administrador",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RoxoNubank,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Preto,
                    unfocusedTextColor = Preto,
                    focusedLabelColor = Color.Gray
                ),
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewTelaPerfil() {
    CalencareAppTheme {
        TelaPerfil()
    }
}