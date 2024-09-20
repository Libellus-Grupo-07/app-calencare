package school.sptech.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.R
import school.sptech.ui.theme.Amarelo
import school.sptech.ui.theme.AmareloOpacidade10
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Laranja
import school.sptech.ui.theme.LaranjaOpacidade15
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.VerdeOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.VermelhoOpacidade15
import school.sptech.ui.theme.fontFamily

@Composable
fun ButtonEstoque(qtdEstoque:Int){
    var descricao:String
    var corFundo: Color
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
        modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.size(15.dp)
        )

        Spacer(modifier = Modifier.size(4.dp))

        TextoButtonMedium(texto = "$qtdEstoque em Estoque")
    }
}


