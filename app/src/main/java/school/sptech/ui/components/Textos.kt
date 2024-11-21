package school.sptech.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.theme.letterSpacingSecundaria

@Composable
fun TituloLarge(titulo: String, defaultColor: Boolean = true) {
    Text(
        text = titulo,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingPrincipal,
        color = if (defaultColor) Preto else Color.Unspecified
    )
}

@Composable
fun TituloMedium(titulo: String) {
    Text(
        text = titulo,
        color = Preto,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingPrincipal
    )
}

@Composable
fun LabelInput(label: String, color: Color = Cinza, isSmallInput: Boolean = false) {
    Text(
        text = label,
        color = color,
        fontWeight = FontWeight.Bold,
        letterSpacing = letterSpacingPrincipal,
        fontFamily = fontFamilyPoppins,
        fontSize = if (isSmallInput) 11.5.sp else 14.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun TextoInput(texto: String) {
    Text(
        text = texto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingPrincipal
    )
}

@Composable
fun TextoKpi(valor: String, cor: Color) {
    Text(
        text = valor,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingPrincipal,
        color = cor
    )
}

@Composable
fun LabelKpi(label: String) {
    Text(
        text = label,
        fontSize = 9.5.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingSecundaria,
        color = Preto
    )
}

@Composable
fun TextoButtonExtraLarge(texto: String) {
    Text(
        text = texto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingPrincipal
    )
}

@Composable
fun TextoButtonLarge(texto: String) {
    Text(
        text = texto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingPrincipal
    )
}

@Composable
fun TextoButtonMedium(texto: String) {
    Text(
        text = texto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingPrincipal
    )
}

@Composable
fun TextoButtonSmall(texto: String) {
    Text(
        text = texto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingPrincipal
    )
}

@Composable
fun LabelChart(label: String) {
    Text(
        text = label,
        fontSize = 8.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingPrincipal,
        color = Cinza
    )
}

@Composable
fun TextoNenhumItemCadastrado(texto: String) {
    Text(
        text = texto,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = fontFamilyPoppins,
        letterSpacing = letterSpacingPrincipal,
        color = Cinza,
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
fun TextoValorColorido(
    texto: String,
    cor: Color,
) {
    Text(
        text = texto,
        fontSize = 15.sp,
        fontFamily = fontFamilyPoppins,
        letterSpacing = -0.7.sp,
        color = cor,
        fontWeight = FontWeight.ExtraBold,
        overflow = TextOverflow.Ellipsis,
    )
}


@Composable
fun MultiStyleText(
    text1: String, color1: Color,
    text2: String, color2: Color,
    text3: String, color3: Color,
    text4: String, color4: Color
) {
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(color = color1)) {
            append(text1)
        }
        withStyle(style = SpanStyle(color = color2, fontWeight = FontWeight.SemiBold)) {
            append(text2)
        }
        withStyle(style = SpanStyle(color = color3)) {
            append(text3)
        }
        withStyle(style = SpanStyle(color = color4, fontWeight = FontWeight.SemiBold)) {
            append(text4)
        }
    })
}