package school.sptech.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import school.sptech.R
import school.sptech.Routes
import school.sptech.ui.theme.BrancoFundo
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.fontFamily
import school.sptech.ui.theme.letterSpacingPrincipal

@Composable
fun TopBarInicio(navController: NavController, modifier: Modifier = Modifier){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically,
    ) {
        Row(
            modifier = modifier,
            Arrangement.Absolute.Left,
            Alignment.CenterVertically
        ){

            IconButton(onClick = { /*TODO*/ }, modifier = modifier.size(52.dp)) {
                Image(
                    bitmap = ImageBitmap.imageResource(id = R.mipmap.foto_perfil),
                    contentDescription = "Foto de Perfil",
                    modifier = modifier.fillMaxSize()
                )
            }

            Spacer(modifier = modifier.size(16.dp))

            Column {
                Text(
                    text = "Patricia Dias",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    letterSpacing = -0.5.sp,
                    lineHeight = 15.sp,
                    color = RoxoNubank
                )

                Spacer(modifier = modifier.size(4.dp))

                Text(
                    text = "Studio Patricia Dias",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.5.sp,
                    letterSpacing = -0.5.sp,
                    lineHeight = 15.sp,
                    color = Cinza
                )
            }
        }


        Row {
            IconButton(onClick = {
                navController.navigate(Routes.Notificacoes.route)
            }) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.mipmap.notificacao),
                    "I",
                    modifier = Modifier.size(32.dp),
                    tint = Preto
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarVoltar(navController: NavController, titulo: String) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Preto
        ),
        title = {
            Text(
                text = titulo,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    letterSpacing = letterSpacingPrincipal
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    tint = Preto
                )
            }
        },
        //scrollBehavior = scrollBehavior
    )
}

@Composable
fun TopBarEstoque(navController: NavController, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 24.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = modifier
                .align(Alignment.CenterVertically)
                .fillMaxHeight(0.08f),
            onClick = {
                navController.popBackStack()
            },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Preto
            )
        ){
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = "Ícone de Voltar",
                modifier = Modifier.size(32.dp),
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.065f),
            value = text,
            onValueChange = {
                if(it.length < 20){
                    text = it
                }
            },
            leadingIcon = {
                Box(modifier = modifier) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.mipmap.pesquisar),
                        contentDescription = "Ícone de Pesquisar",
                        modifier = Modifier.size(18.dp),
                    )
                }
            },
            placeholder = {
                Text(
                    modifier = modifier,
                    text = "Pesquisar",
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily,
                    letterSpacing = letterSpacingPrincipal,
                    color = Preto,
                    lineHeight = 8.sp
                )
            },
            textStyle = TextStyle(
                textDirection = TextDirection.ContentOrLtr,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamily,
                letterSpacing = letterSpacingPrincipal,
                color = Preto,
            ),
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Preto,
                unfocusedLeadingIconColor = Preto,
                focusedTextColor = Preto,
                focusedBorderColor = RoxoNubank,
                focusedLeadingIconColor = RoxoNubank
            ),
            singleLine = true,

            )

        Row(
            modifier = modifier,
        ) {
            IconButton(
                onClick = { navController.navigate(Routes.AdicionarProduto.route) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Preto
                )
            ) {
                Icon(
                    Icons.Rounded.Add,
                    "Ícone de Adicionar",
                    modifier = modifier.size(64.dp)

                )
            }

            IconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Preto
                )
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.mipmap.filtro),
                    "Ícone de Filtro",
                    modifier = modifier.size(32.dp)
                )
            }
        }
    }
}

