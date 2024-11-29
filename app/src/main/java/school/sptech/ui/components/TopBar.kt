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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import school.sptech.R
import school.sptech.Routes
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.data.model.Funcionario

@Composable
fun TopBarInicio(
    usuario: Funcionario,
    navController: NavController,
    modifier: Modifier = Modifier
) {
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
        ) {

            IconButton(
                onClick = { navController.navigate(Routes.DadosPessoais.route) },
                modifier = modifier.size(52.dp)
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(id = R.mipmap.foto_perfil),
                    contentDescription = "Foto de Perfil",
                    modifier = modifier.fillMaxSize()
                )
            }

            Spacer(modifier = modifier.size(16.dp))

            Column {
                Text(
                    text = usuario.nome ?: "",
                    //text = "Nome do User",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    letterSpacing = -0.5.sp,
                    lineHeight = 15.sp,
                    color = RoxoNubank
                )

                Spacer(modifier = modifier.size(4.dp))

                Text(
                    text = usuario.empresa?.razaoSocial ?: "",
//                    text = "Nome da Empresa",
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
            TituloMedium(
                titulo = titulo
            )
        },
        navigationIcon = {
            ButtonIconVoltar(onClick = { navController.popBackStack() })
        },
        modifier = Modifier.padding(horizontal = 8.dp),

        //scrollBehavior = scrollBehavior
    )
}

@Composable
fun TopBarSearch(onClickBack: () -> Unit, onClickAdd: () -> Unit, onClickFiltro: () -> Unit, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 24.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ButtonIconVoltar(onClick = onClickBack)

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.069f),
            value = text,
            onValueChange = {
                if (it.length < 20) {
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
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingPrincipal,
                    color = Preto,
                    lineHeight = 8.sp
                )
            },
            textStyle = TextStyle(
                textDirection = TextDirection.ContentOrLtr,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamilyPoppins,
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
                onClick =  onClickAdd,
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
                onClick = onClickFiltro,
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

@Composable
fun TopBarComSelecaoData(
    titulo: String,
    mesSelecionado: String,
    anoSelecionado: Int,
    aoClicarSeletorData: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TituloLarge(titulo = titulo)
        TextButton(onClick = aoClicarSeletorData) {
            Text(
                text = "$mesSelecionado $anoSelecionado",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = fontFamilyPoppins,
                letterSpacing = letterSpacingPrincipal,
                color = Cinza
            )
            val density = LocalDensity.current
            Image(
                painter = painterResource(id = R.mipmap.flecha_para_baixo),
                contentDescription = "Flecha para Baixo",
                modifier = Modifier
                    .size(with(density) { 16.sp.toDp() })
                    .padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun TopBarInformacoesProduto(onClickVoltar: () -> Unit, onClickSalvar: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 36.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        ButtonIconVoltar(onClick = onClickVoltar)

        TituloMedium(
            titulo = stringResource(R.string.informacoesProduto)
        )


        IconButton(onClick = onClickSalvar) {
            Icon(
                painter = painterResource(id = R.mipmap.check),
                contentDescription = "Check",
                modifier = Modifier.size(24.dp),
                tint = Preto
            )
        }

    }
}

@Composable
fun TopBarConta(
    enabledButtonSalvar: Boolean,
    onClickSalvar: () -> Unit,
    onClickSair: () -> Unit,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonIconVoltar(onClick = { navController.popBackStack() })

        Spacer(modifier = Modifier.weight(1f))

        ButtonOutline(
            titulo = stringResource(id = R.string.sairconta),
            iconId = R.mipmap.logout,
            isMediumButton = true,
            onClick = onClickSair
        )

        Spacer(modifier = Modifier.width(4.dp))

        ButtonBackground(
            titulo = stringResource(R.string.salvar),
            cor = RoxoNubank,
            isMediumButton = true,
            onClick = onClickSalvar,
            enabled = enabledButtonSalvar
        )
    }
}
