package school.sptech.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.fontFamily
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.theme.letterSpacingSecundaria

@Composable
fun TituloLarge(titulo:String) {
    Text(
        text = titulo,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        letterSpacing = letterSpacingPrincipal,
        color = Preto
    )
}

@Composable
fun LabelInput(label:String){
    Text(
        text = label,
        color = Cinza,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun TextoInput(texto:String){
    Text(
        text = texto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = fontFamily,
        letterSpacing = letterSpacingPrincipal
    )
}

@Composable
fun TextoKpi(valor:String, cor: Color){
    Text(
        text = valor,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = fontFamily,
        letterSpacing = letterSpacingPrincipal,
        color = cor
    )
}

@Composable
fun LabelKpi(label:String){
    Text(
        text = label,
        fontSize = 9.5.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = fontFamily,
        letterSpacing = letterSpacingSecundaria,
        color = Preto
    )
}

@Composable
fun TextoButtonLarge(texto:String){
    Text(
        text = texto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        fontFamily = fontFamily,
        letterSpacing = letterSpacingPrincipal
    )
}

@Composable
fun TextoButtonMedium(texto:String){
    Text(
        text = texto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        fontFamily = fontFamily,
        letterSpacing = letterSpacingPrincipal
    )
}

@Composable
fun TextoButtonSmall(texto:String){
    Text(
        text = texto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        fontFamily = fontFamily,
        letterSpacing = letterSpacingPrincipal
    )
}