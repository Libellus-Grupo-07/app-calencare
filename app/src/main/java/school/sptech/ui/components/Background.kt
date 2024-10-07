package school.sptech.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import school.sptech.R
import school.sptech.ui.theme.BrancoFundo

@Composable
fun Background(){
    Image(painter = painterResource(id = R.drawable.bg),
        contentDescription = "background",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxSize()
            .background(BrancoFundo)
    )
}