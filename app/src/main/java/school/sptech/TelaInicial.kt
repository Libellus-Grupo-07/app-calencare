package school.sptech

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.entity.Produto
import school.sptech.ui.theme.Amarelo
import school.sptech.ui.theme.AmareloOpacidade10
import school.sptech.ui.theme.Azul
import school.sptech.ui.theme.AzulOpacidade15
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Laranja
import school.sptech.ui.theme.LaranjaOpacidade15
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.VerdeOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.VermelhoOpacidade15
import school.sptech.ui.theme.fontFamily

@Composable
fun CabecalhoInicio(modifier: Modifier = Modifier){
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
            Icon(
                bitmap = ImageBitmap.imageResource(id = R.mipmap.notificacao),
                "I",
                modifier = Modifier.size(32.dp)
            )
        }
    }

}

@Composable
fun CardKpi(titulo:String, valor:String, cor:String, modifier: Modifier = Modifier ){
    var corFundo:Color
    var corTexto:Color

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

    Box(modifier = modifier.clip(RoundedCornerShape(16.dp))){
        Row(modifier = modifier
            .fillMaxWidth()
            .background(corFundo)
            .padding(horizontal = 16.dp, vertical = 21.dp),
                Arrangement.Start,){
                Column {
                    Text(
                        text = titulo,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = -0.3.sp,
                        color = Preto
                    )
                    Text(
                        text = valor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = -0.5.sp,
                        color = corTexto
                    )
                }
        }
    }
}

@Composable
fun BoxKpis(modifier: Modifier = Modifier){
    Row(modifier = modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
        Column {
            Row(modifier = modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Column(modifier = modifier.weight(0.4f)) {
                    CardKpi(
                        titulo = "Produtos com Estoque Alto",
                        valor = "25 produtos",
                        cor = "Verde"
                    )
                }

                Spacer(modifier = modifier.size(10.dp))

                Column(modifier = modifier.weight(0.4f)) {
                    CardKpi(
                        titulo = "Produtos sem Estoque",
                        valor = "1 produto",
                        cor = "Vermelho"
                    )
                }
            }

            Spacer(modifier = modifier.size(8.dp))

            Row {
                Column(modifier = modifier.weight(0.5f)) {
                    CardKpi(
                        titulo = "Produtos Repostos no Dia",
                        valor = "25 produtos",
                        cor = "Azul"
                    )
                }

                Spacer(modifier = modifier.size(10.dp))

                Column(modifier = modifier.weight(0.5f)) {
                    CardKpi(
                        titulo = "Produtos com Estoque Baixo",
                        valor = "5 produtos",
                        cor = "Laranja"
                    )
                }
            }
        }

    }
}

@Composable
fun CardProduto(nome:String, categoria:String, qtdEstoque:Int, isTelaInicio:Boolean, modifier: Modifier = Modifier){
    Row(modifier = modifier
        .border(
            width = 1.dp,
            brush = Brush.linearGradient(
                colors = listOf(Laranja, RoxoNubank),
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
                    letterSpacing = -0.5.sp,
                    fontFamily = fontFamily
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = categoria,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = -0.5.sp,
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
                        colors = ButtonColors(
                            contentColor = Branco,
                            containerColor = RoxoNubank,
                            disabledContentColor = Cinza,
                            disabledContainerColor = Preto
                        )
                    ) {
                        Icon(
                            Icons.Rounded.Add,
                            contentDescription = "Ícone Adicionar",
                            modifier = Modifier.size(15.dp)
                        )

                        Spacer(modifier = Modifier.size(4.dp))

                        Text(
                            text = "Repor Estoque",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.5.sp,
                            fontFamily = fontFamily,
                            letterSpacing = -0.5.sp
                        )



                    }
                }

            }
        }
    }
}

@Composable
fun ButtonEstoque(qtdEstoque:Int){
    var descricao:String
    var corFundo:Color
    var corTexto:Color
    var icone:Int

    when(qtdEstoque){
        0 -> {
            descricao = "Sem Estoque"
            corFundo = VermelhoOpacidade15
            corTexto = Vermelho
            icone = R.mipmap.sem_estoque
        }
        in 1..4 -> {
            descricao = "Quase Sem Estoque"
            corFundo = LaranjaOpacidade15
            corTexto = Laranja
            icone = R.mipmap.baixo_estoque
        }
        in 5 .. 14 -> {
            descricao = "Estoque Abaixo de 15 Produtos"
            corFundo = AmareloOpacidade10
            corTexto = Amarelo
            icone = R.mipmap.estoque_abaixo_15
        } else -> {
            descricao = "Estoque Alto"
            corFundo = VerdeOpacidade15;
            corTexto = Verde
            icone = R.mipmap.estoque_alto
        }
    }

    TextButton(
        modifier = Modifier
            .fillMaxWidth(),
        shape = CircleShape,
        onClick = { /*TODO*/ },
        colors = ButtonColors(
            contentColor = corTexto,
            containerColor = corFundo,
            disabledContentColor = Cinza,
            disabledContainerColor = Branco)
        ) {
        Icon(
            bitmap = ImageBitmap.imageResource(icone),
            contentDescription = descricao,
            tint = corTexto,
            modifier = Modifier.size(14.dp)
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = "$qtdEstoque em Estoque",
            color = corTexto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.5.sp,
            fontFamily = fontFamily,
            letterSpacing = -0.5.sp
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContainerCardProduto(){
    val produtos by remember {
        mutableStateOf(value = listOf<Produto>(
            Produto(
                "Esmalte Azul Metálico Risqué 8ml",
                "Unha",
                0
            ),
            Produto(
                "Shampoo Mais Lisos Wella 350ml",
                "Cabelo",
                1
            ),
            Produto(
                "Condicionador Wella 350ml",
                "Cabelo",
                1
            ),
            Produto(
                "Máscara de Cílios Volume Up Vult 8g",
                "Maquiagem",
                3
            ),
            Produto(
                "Spray Keune Style Dry Texturizer 300ml",
                "Cabelo",
                4
            ),
            Produto(
                "Base Líquida Estée Lauder  SFS-10",
                "Maquiagem",
                6
            ),
        ))
    }

    Box(modifier = Modifier.fillMaxWidth()){
        Column {
            Row {
                Text(
                    text = "Produtos com Quantidade Baixa",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = -0.5.sp,
                )
            }
            
            Spacer(modifier = Modifier.size(21.dp))
                    FlowRow(modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        maxItemsInEachRow = 2) {
                            repeat(2 * (produtos.size / 2)){ i ->
                                CardProduto(
                                    nome = produtos[i].nome,
                                    categoria = produtos[i].categoria,
                                    qtdEstoque = produtos[i].qtdEstoque,
                                    isTelaInicio = true,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

}

@Composable
fun TelaInicial(modifier:Modifier = Modifier){
    Column(modifier = modifier
        .padding(horizontal = 24.dp, vertical = 12.dp)
        .verticalScroll(state = ScrollState(1))) {
        CabecalhoInicio()
        Spacer(modifier = Modifier.size(21.dp))
        BoxKpis()
        Spacer(modifier = Modifier.size(21.dp))
        ContainerCardProduto()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InicioPreview(){
    CalencareAppTheme {
        TelaInicial()
    }
}
