package school.sptech.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.R
import school.sptech.ui.theme.Azul
import school.sptech.ui.theme.AzulOpacidade15
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade35
import school.sptech.ui.theme.Laranja
import school.sptech.ui.theme.LaranjaDourado
import school.sptech.ui.theme.LaranjaOpacidade15
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.VerdeOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.VermelhoOpacidade15
import school.sptech.ui.theme.fontFamily
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.theme.letterSpacingSecundaria

@Composable
fun CardKpi(titulo:String, valor:String, cor:String, modifier: Modifier = Modifier){
    var corFundo: Color
    var corTexto: Color

    when(cor.uppercase()){
        "VERMELHO" -> {
            corFundo = VermelhoOpacidade15
            corTexto = Vermelho
        }
        "LARANJA" -> {
            corFundo = LaranjaOpacidade15
            corTexto = Laranja
        }
        "AZUL" -> {
            corFundo = AzulOpacidade15
            corTexto = Azul
        } else -> {
        corFundo = VerdeOpacidade15;
        corTexto = Verde
    }
    }

    Box(modifier = modifier.clip(RoundedCornerShape(16.dp))) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(corFundo)
                .padding(horizontal = 16.dp, vertical = 21.dp),
            Arrangement.Start,
        ) {
            Column {
                Text(
                    text = titulo,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = fontFamily,
                    letterSpacing = letterSpacingSecundaria,
                    color = Preto
                )
                Text(
                    text = valor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = fontFamily,
                    letterSpacing = letterSpacingPrincipal,
                    color = corTexto
                )
            }
        }
    }
}

@Composable
fun CardProduto(nome:String, categoria:String, qtdEstoque:Int, isTelaInicio:Boolean, modifier: Modifier = Modifier){
    Row(modifier = modifier
        .border(
            width = 1.3.dp,
            brush = Brush.linearGradient(
                colors = listOf(LaranjaDourado, RoxoNubank),
                start = Offset.Zero,
                end = Offset.Infinite,
            ),
            shape = RoundedCornerShape(20.dp)
        )
        .background(Branco, shape = RoundedCornerShape(20.dp))) {
        Box(modifier = Modifier
            .padding(18.dp)
        ){
            Column {
                Text(
                    text = nome,
                    fontSize = 12.5.sp,
                    lineHeight = 20.sp,
                    color = RoxoNubank,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = letterSpacingPrincipal,
                    fontFamily = fontFamily
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = categoria,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = letterSpacingPrincipal,
                    lineHeight = 15.sp,
                    color = Cinza,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.size(8.dp))

                ButtonEstoque(qtdEstoque = qtdEstoque)

                if(isTelaInicio){
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = CircleShape,
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Branco,
                            containerColor = RoxoNubank,
                        )
                    ) {
                        Icon(
                            Icons.Rounded.Add,
                            contentDescription = "Ícone Adicionar",
                            modifier = Modifier.size(15.dp)
                        )

                        Spacer(modifier = Modifier.size(4.dp))

                        Text(
                            text = stringResource(id = R.string.reporEstoque),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            fontFamily = fontFamily,
                            letterSpacing = letterSpacingPrincipal
                        )
                    }
                } else {
                    val buttonRetirarEnabled = qtdEstoque > 0

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        Arrangement.spacedBy(4.dp),
                        Alignment.CenterVertically,
                    ) {
                        TextButton(
                            modifier = Modifier
                                .weight(0.5f)
                                .height(36.dp),
                            shape = CircleShape,
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Cinza,
                                containerColor = Branco,
                                disabledContentColor = CinzaOpacidade35,
                                disabledContainerColor = Branco
                            ),
                            enabled = buttonRetirarEnabled,
                            border = BorderStroke(1.5.dp, if(buttonRetirarEnabled) Cinza else CinzaOpacidade35)
                        ) {
                            Text(
                                text = "Retirar",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                fontFamily = fontFamily,
                                letterSpacing = letterSpacingPrincipal
                            )
                        }

                        TextButton(
                            modifier = Modifier
                                .weight(0.5f)
                                .height(36.dp),
                            shape = CircleShape,
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Branco,
                                containerColor = RoxoNubank
                            )
                        ) {
                            Text(
                                text = "Repor",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                fontFamily = fontFamily,
                                letterSpacing = letterSpacingPrincipal
                            )
                        }
                    }
                }
            }
        }
    }
}
