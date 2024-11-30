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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun Chart(
    labels: List<String>,
    receitas: List<Double> = listOf(),
    lucro: List<Double> = listOf(),
    despesas: List<Double> = listOf()
) {
    ChartLine(
        labels = labels,
        receitas = receitas,
        lucro = lucro,
        despesas = despesas
    )
    //Spacer(modifier = Modifier.size(16.dp))
    //GroupButtons()
}

@Composable
fun ChartLine(
    labels: List<String>,
    receitas: List<Double>,
    lucro: List<Double>,
    despesas: List<Double>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(0.5f)
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
        Column(
            modifier = Modifier
                .background(Branco)
                .padding(top = 14.dp),
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

            if (receitas.map { it.toFloat() }.sum() == 0f && lucro.map { it.toFloat() }
                    .sum() == 0f && despesas.map { it.toFloat() }.sum() == 0f) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(bottom = 24.dp),
                    Arrangement.Center,
                    Alignment.CenterVertically
                ) {
                    Text(
                        text = "Não há dados para exibir",
                        fontFamily = fontFamilyPoppins,
                        color = Cinza,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = letterSpacingPrincipal,
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp
                    )
                }
            } else {
                LineChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 12.dp,
                            end = 24.dp,
                            top = 8.dp,
                            bottom = 12.dp
                        ),
                    data = listOf(
                        Line(
                            label = stringResource(id = R.string.receita),
                            values = receitas,
                            color = SolidColor(Azul),
                            //firstGradientFillColor = Azul.copy(alpha = .5f),
                            //secondGradientFillColor = Color.Transparent,
                            strokeAnimationSpec = tween(750, easing = EaseInOutCubic),
                            //gradientAnimationDelay = 750,
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
                            values = lucro,
                            color = SolidColor(Verde),
                            //firstGradientFillColor = Verde.copy(alpha = .5f),
                            //secondGradientFillColor = Color.Transparent,
                            strokeAnimationSpec = tween(750, easing = EaseInOutCubic),
                            //gradientAnimationDelay = 750,
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
                            values = despesas,
                            color = SolidColor(Vermelho),
                            //firstGradientFillColor = Vermelho.copy(alpha = .5f),
                            //secondGradientFillColor = Color.Transparent,
                            strokeAnimationSpec = tween(750, easing = EaseInOutCubic),
                            //gradientAnimationDelay = 750,
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
                        padding = 4.dp,
                        textStyle = TextStyle(
                            fontFamily = fontFamilyPoppins,
                            fontSize = 8.5.sp,
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
                        padding = 12.dp,
                        textStyle = TextStyle(
                            fontFamily = fontFamilyPoppins,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = letterSpacingPrincipal,
                            color = Cinza
                        ),
                        labels = labels
                    ),
                    labelHelperProperties = LabelHelperProperties(
                        enabled = false,
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
                        contentBuilder = {
                            formatarValorMonetario(it.toFloat())
                        }
                    )
                )
            }
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