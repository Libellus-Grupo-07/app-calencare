package school.sptech.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import school.sptech.R
import school.sptech.ui.theme.Amarelo
import school.sptech.ui.theme.AmareloOpacidade10
import school.sptech.ui.theme.Azul
import school.sptech.ui.theme.AzulOpacidade15
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade35
import school.sptech.ui.theme.CinzaOpacidade50
import school.sptech.ui.theme.CinzaOpacidade7
import school.sptech.ui.theme.Laranja
import school.sptech.ui.theme.LaranjaOpacidade15
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.VerdeOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.VermelhoOpacidade15
import java.util.Locale

@Composable
fun ButtonEstoque(qtdEstoque:Int, nivelEstoque: String) {
    val descricao: String
    val corFundo: Color
    val corTexto: Color
    val icone: Int

    when (nivelEstoque) {
        "Sem estoque" -> {
            descricao = stringResource(id = R.string.semEstoque).replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            corFundo = VermelhoOpacidade15
            corTexto = Vermelho
            icone = R.mipmap.sem_estoque
        }

        "Estoque muito baixo" -> {
            descricao = stringResource(id = R.string.quaseSemEstoque).replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            corFundo = LaranjaOpacidade15
            corTexto = Laranja
            icone = R.mipmap.baixo_estoque
        }

        "Estoque baixo" -> {
            descricao = "Estoque Abaixo de 15 Produtos"
            corFundo = AmareloOpacidade10
            corTexto = Amarelo
            icone = R.mipmap.estoque_abaixo_15
        }

        else -> {
            descricao = "Estoque Alto"
            corFundo = VerdeOpacidade15;
            corTexto = Verde
            icone = R.mipmap.estoque_alto
        }
    }

    TextButton(
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        onClick = { /*TODO*/ },
        colors = ButtonColors(
            contentColor = corTexto,
            containerColor = corFundo,
            disabledContentColor = Cinza,
            disabledContainerColor = Branco
        )
    ) {
        Icon(
            bitmap = ImageBitmap.imageResource(icone),
            contentDescription = descricao,
            modifier = Modifier.size(15.dp)
        )

        Spacer(modifier = Modifier.size(4.dp))

        TextoButtonMedium(texto = "$qtdEstoque em Estoque")
    }
}

@Composable
fun ButtonChart(titulo: String, cor: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val corTexto = when (cor) {
        VermelhoOpacidade15 -> Vermelho
        VerdeOpacidade15 -> Verde
        AzulOpacidade15 -> Azul
        else -> Branco
    }

    TextButton(
        modifier = modifier.width(108.dp),
        shape = CircleShape,
        onClick = onClick,
        colors = ButtonColors(
            contentColor = corTexto,
            containerColor = cor,
            disabledContentColor = CinzaOpacidade35,
            disabledContainerColor = Cinza
        )
    ) {
        TextoButtonMedium(texto = titulo)
    }
}

@Composable
fun ButtonOutline(
    titulo: String,
    enabledButton: Boolean = true,
    enabledIcon: Boolean = true,
    iconId: Int = R.mipmap.cancelar,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isMediumButton: Boolean = false
) {
    Button(
        modifier = modifier.height(if (isMediumButton) 38.dp else 44.dp),
        enabled = enabledButton,
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonColors(
            contentColor = Cinza,
            containerColor = Branco,
            disabledContentColor = CinzaOpacidade35,
            disabledContainerColor = Branco
        ),
        border = BorderStroke(1.3.dp, if (enabledButton) Cinza else CinzaOpacidade35),
        contentPadding = PaddingValues(
            horizontal = 18.dp,
        )
    ) {
        if (enabledIcon) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Ícone de $titulo",
                modifier = modifier.let {
                    if (titulo.equals("Limpar", ignoreCase = true)) it.size(14.dp)
                    else if (isMediumButton) it.size(11.dp)
                    else it.size(13.dp)
                }

            )
            Spacer(modifier = modifier.size(if (isMediumButton) 6.dp else 8.dp))
        }

        if (isMediumButton) TextoButtonSmall(texto = titulo) else TextoButtonLarge(texto = titulo)
    }
}

@Composable
fun ButtonBackground(
    titulo: String,
    cor: Color,
    enabled: Boolean = true,
    enabledIcon: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconPainter: ImageVector? = null,
    iconId: Int? = null,
    isMediumButton: Boolean = false,
    isSmallButton: Boolean = false
) {
    val corTexto = when (cor) {
        VermelhoOpacidade15 -> Vermelho
        VerdeOpacidade15 -> Verde
        AzulOpacidade15 -> Azul
        CinzaOpacidade7 -> Cinza
        else -> Branco
    }

    Button(
        modifier = modifier.height(
            if (isSmallButton) 34.dp else if (isMediumButton) 38.dp else 44.dp
        ),
        shape = CircleShape,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = corTexto,
            containerColor = cor,
            disabledContentColor = Branco,
            disabledContainerColor = CinzaOpacidade50
        ),
        contentPadding = PaddingValues(
            horizontal = if (isSmallButton) 14.dp else 18.dp,
        )
    ) {
        if (enabledIcon) {
            if (iconId != null) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = "Ícone de $titulo",
                    modifier = modifier.width(if (isMediumButton) 16.dp else 18.dp)
                )
            } else {
                Icon(
                    imageVector = iconPainter ?: Icons.Rounded.Check,
                    contentDescription = "Ícone de $titulo",
                    modifier = modifier.let {
                        if (isMediumButton) it.width(16.dp)
                        else it.width(18.dp)
                    }
                )
            }

            Spacer(modifier = modifier.size(if (isMediumButton) 4.dp else 6.dp))
        }

        if (isSmallButton || isMediumButton) TextoButtonSmall(texto = titulo)
        else TextoButtonLarge(texto = titulo)

    }
}

@Composable
fun ButtonCancelar(onClick: () -> Unit) {
    ButtonOutline(
        titulo = stringResource(id = R.string.cancelar),
        iconId = R.mipmap.cancelar,
        onClick = onClick
    )
}

@Composable
fun ButtonIconVoltar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier
            .fillMaxHeight(0.08f),
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = Preto
        )
    ) {
        Icon(
            Icons.Rounded.ArrowBack,
            contentDescription = "Ícone de Voltar",
            modifier = Modifier.size(32.dp),
        )
    }
}

@Composable
fun ButtonIconExcluir(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = modifier
                .size(
                    60.dp
                )
                .shadow(
                    elevation = 4.dp,
                    shape = CircleShape,
                    clip = true,
                    ambientColor = Color.Transparent,
                    spotColor = Preto.copy(0.5f)
                )
                .align(Alignment.End)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0xFFC995F2)),
                        end = Offset(240f, 160f),
                    ),
                    shape = CircleShape,
                ),
        ) {
            FloatingActionButton(
                onClick = onClick,
                shape = CircleShape,
                modifier = modifier.fillMaxSize(),
                contentColor = RoxoNubank,
                containerColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp
                )
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.delete),
                    contentDescription = "Delete",
                    modifier = modifier.size(20.dp)
                )
            }
        }
    }
}
