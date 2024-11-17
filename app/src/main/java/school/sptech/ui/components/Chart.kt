package school.sptech.ui.components

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import formatarValorMonetario
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties
import school.sptech.R
import school.sptech.ui.theme.Azul
import school.sptech.ui.theme.AzulOpacidade15
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.PretoOpacidade15
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.VerdeOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.VermelhoOpacidade15
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal

@Composable
fun Chart() {
    ChartLine()
    //Spacer(modifier = Modifier.size(16.dp))
    //GroupButtons()
}

@Composable
fun ChartLine(){
    Box(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(0.45f)
        .shadow(
            4.dp, shape = RoundedCornerShape(20.dp)
        )
//        .border(
//            BorderStroke(
//                width = 1.dp,
//                color = PretoOpacidade15
//            ), RoundedCornerShape(20.dp)
//        )
        .clip(RoundedCornerShape(20.dp))
    ) {
        Column(modifier = Modifier
            .background(Branco)
            .padding(top = 18.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.tituloDashboard),
                fontFamily = fontFamilyPoppins,
                color = Cinza,
                fontWeight = FontWeight.Bold,
                fontSize = 10.5.sp
            )
            LineChart(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 16.dp,
                        end = 32.dp,
                        top = 8.dp,
                        bottom = 16.dp
                    ),
                data = listOf(
                    Line(
                        label = stringResource(id = R.string.receita),
                        values = listOf(28.0,41.0,5.0,10.0,35.0),
                        color = SolidColor(Azul),
                        //firstGradientFillColor = Azul.copy(alpha = .5f),
                        //secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(1000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 500,
                        drawStyle = DrawStyle.Stroke(width = 3.5.dp),
                        dotProperties = DotProperties(
                            enabled = true,
                            color = SolidColor(Branco),
                            radius = 4.dp,
                            strokeWidth = 3.dp,
                            strokeColor = SolidColor(Azul)
                        )
                    ),
                    Line(
                        label = stringResource(id = R.string.lucro),
                        values = listOf(8.0,21.0,9.0,15.0,32.0),
                        color = SolidColor(Verde),
                        //firstGradientFillColor = Verde.copy(alpha = .5f),
                        //secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(1000, easing = EaseInOutCubic),
                        //gradientAnimationDelay = 500,
                        drawStyle = DrawStyle.Stroke(width = 3.dp),
                        dotProperties = DotProperties(
                            enabled = true,
                            color = SolidColor(Branco),
                            radius = 4.dp,
                            strokeWidth = 3.dp,
                            strokeColor = SolidColor(Verde)
                        )
                    ),
                    Line(
                        label = stringResource(id = R.string.despesa),
                        values = listOf(4.0,12.0,8.0,1.0,27.0),
                        color = SolidColor(Vermelho),
                        //firstGradientFillColor = Vermelho.copy(alpha = .5f),
                        //secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(1000, easing = EaseInOutCubic),
                        //gradientAnimationDelay = 500,
                        drawStyle = DrawStyle.Stroke(width = 3.dp),
                        dotProperties = DotProperties(
                            enabled = true,
                            color = SolidColor(Branco),
                            radius = 4.dp,
                            strokeWidth = 3.dp,
                            strokeColor = SolidColor(Vermelho)
                        )
                    )
                ),
                animationMode = AnimationMode.Together(delayBuilder = { it -> it * 200L }),
                indicatorProperties = HorizontalIndicatorProperties(
                    enabled = true,
                    textStyle = TextStyle(
                        fontFamily = fontFamilyPoppins,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = letterSpacingPrincipal,
                        color = Cinza
                    ),
                    contentBuilder = { it ->
                        formatarValorMonetario(it.toFloat())
                    }
                ),
                labelProperties = LabelProperties(
                    enabled = true,
                    textStyle = TextStyle(
                        fontFamily = fontFamilyPoppins,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = letterSpacingPrincipal,
                        color = Cinza
                    ),
                    labels = listOf(
                        "Semana 1",
                        "Semana 2",
                        "Semana 3",
                        "Semana 4",
                    )
                ),
                labelHelperProperties = LabelHelperProperties(
                    enabled = false
                ),
                gridProperties = GridProperties(
                    enabled = true,
                    yAxisProperties = GridProperties.AxisProperties(
                        enabled = false,
                    ),
                    xAxisProperties = GridProperties.AxisProperties(
                        enabled = false
                    )
                ),
                popupProperties = PopupProperties(
                    enabled = true,
                    containerColor = Preto.copy(.56f),
                    textStyle = TextStyle(
                        fontFamily = fontFamilyPoppins,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = letterSpacingPrincipal,
                        color = Branco
                    ),
                    contentBuilder = { it ->
                        formatarValorMonetario(it.toFloat())
                    }
                )
            )
        }
    }
}
@Composable
fun GroupButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        ButtonChart(
            titulo = stringResource(id = R.string.receita),
            cor = AzulOpacidade15,
            onClick = {},
        )

        ButtonChart(
            titulo = stringResource(id = R.string.lucro),
            cor = VerdeOpacidade15,
            onClick = { /*TODO*/ }
        )

        ButtonChart(
            titulo = stringResource(id = R.string.despesa),
            cor = VermelhoOpacidade15,
            onClick = { /*TODO*/ }
        )
    }
}