package school.sptech.ui.components

import androidx.compose.runtime.Composable

@Composable
fun SeletorData(
    mesSelecionado: String,
    anoSelecionado: Int,
    onDismissRequest: () -> Unit,
    onConfirm: (String, Int) -> Unit,
) {
    CustomMonthYearPickerDialog(
        mesSelecionado = mesSelecionado,
        anoSelecionado = anoSelecionado,
        onDismissRequest = onDismissRequest,
        onConfirm = { mes, ano ->
            onConfirm(mes, ano)
        }
    )
}