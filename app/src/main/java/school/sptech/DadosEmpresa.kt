package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.ui.theme.CalencareAppTheme

class DadosEmpresa : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // Agora estamos passando o 'innerPadding' para a TelaPerfil
                    TelaPerfil(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TelaPerfil(modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding) // Usando o padding do Scaffold
                .padding(16.dp),  // Padding adicional para espaçamento
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagem de perfil
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ft), // Placeholder da imagem
                    contentDescription = "Imagem de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(300.dp) // Define a largura fixo
                        .height(300.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)) // Cor hexadecimal direto
                ) {
                    Text(text = "Dados da Empresa")
                }
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(159,53,240)) // Cor hexadecimal direto
                ) {
                    Text(text = "Dados Pessoais")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Campos de entrada de dados
            TextField(
                value = "Patricia Dias",
                onValueChange = {},
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(100.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = "(11) 973682-3933",
                onValueChange = {},
                label = { Text("Telefone") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(100.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = "patricia@email.com",
                onValueChange = {},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(100.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = "Administrador",
                onValueChange = {},
                label = { Text("Perfil") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA)) // Cor hexadecimal direto
            ) {
                Text(text = "Salvar", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}




@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        containerColor = Color.White,  // Cor do fundo da barra de navegação
        contentColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBar {
            // Ícone de Início com cor personalizada
            NavigationBarItem(
                selected = true,
                onClick = { /* Ação para Início */ },
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.home), // Substitua pelo nome da sua imagem
                        contentDescription = "Início",modifier = Modifier
                            .width(20.dp) // Define a largura fixo
                            .height(20.dp)
                    )
                },
                label = { Text("Início") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6200EA), // Cor para ícone selecionado (roxo)
                    selectedTextColor = Color(0xFF6200EA), // Cor para texto selecionado (roxo)
                    unselectedIconColor = Color.Gray,      // Cor para ícone não selecionado
                    unselectedTextColor = Color.Gray       // Cor para texto não selecionado
                )
            )

            // Ícone de Estoque com cor personalizada
            NavigationBarItem(
                selected = false,
                onClick = { /* Ação para Estoque */ },
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.estoque), // Substitua pelo nome da sua imagem
                        contentDescription = "Estoque",
                        modifier = Modifier
                            .width(20.dp) // Define a largura fixo
                            .height(20.dp)
                    )
                },
                label = { Text("Estoque") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF4CAF50), // Cor para ícone selecionado (verde)
                    selectedTextColor = Color(0xFF4CAF50), // Cor para texto selecionado (verde)
                    unselectedIconColor = Color.Gray,      // Cor para ícone não selecionado
                    unselectedTextColor = Color.Gray       // Cor para texto não selecionado
                )
            )

            // Ícone de Finanças com cor personalizada
            NavigationBarItem(
                selected = false,
                onClick = { /* Ação para Finanças */ },
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.financas), // Substitua pelo nome da sua imagem
                        contentDescription = "Finanças",
                                modifier = Modifier
                                .width(20.dp) // Define a largura fixo
                            .height(20.dp)
                    )
                },
                label = { Text("Finanças") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFFC107), // Cor para ícone selecionado (amarelo)
                    selectedTextColor = Color(0xFFFFC107), // Cor para texto selecionado (amarelo)
                    unselectedIconColor = Color.Gray,      // Cor para ícone não selecionado
                    unselectedTextColor = Color.Gray,

                )
            )

            // Ícone de Dashboard com cor personalizada
            NavigationBarItem(
                selected = false,
                onClick = { /* Ação para Dashboard */ },
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.dashboard), // Substitua pelo nome da sua imagem
                        contentDescription = "Dashboard",
                        modifier = Modifier
                            .width(20.dp) // Define a largura fixo
                            .height(20.dp)
                    )
                },
                label = { Text("Dashboard") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(254254254), // Cor para ícone selecionado (laranja)
                    selectedTextColor = Color(254254254), // Cor para texto selecionado (laranja)
                    unselectedIconColor = Color.Gray,      // Cor para ícone não selecionado
                    unselectedTextColor = Color.Gray       // Cor para texto não selecionado
                )
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
