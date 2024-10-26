import android.icu.text.DecimalFormat
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

fun transformarEmLocalDate(data: String): LocalDate {
    val ano = data.substring(6, 10).toInt()
    val mes = data.substring(3, 5).toInt()
    val dia = data.substring(0, 2).toInt()

    val dataParse = LocalDate.of(
        ano, mes, dia
    )

    return dataParse
}

fun transformarEmLocalDateTime(data: String): LocalDateTime {
    val ano = data.substring(6, 10).toInt()
    val mes = data.substring(3, 5).toInt()
    val dia = data.substring(0, 2).toInt()

    val dateTimeParse = LocalDateTime.of(
        ano, mes, dia, 0, 0, 0
    )

    return dateTimeParse
}


fun formatarDecimal(valor: Number, isValueInput:Boolean = false): String {
    val format = DecimalFormat("#,##0.00")
    val formatted = format.format(valor.toDouble() / if(isValueInput) 100 else 1)
    return formatted
}

fun formatarValorMonetario(valor: Float): String {
    val format = DecimalFormat("R$ #,##0.00")
    val formatted = format.format(valor)
    return formatted
}

fun formatarData(data: LocalDate): String {
    val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formatted = LocalDate.parse(data.toString()).format(format)
    return formatted
}

fun formatarData(data: String): String {
    val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formatted = LocalDateTime.parse(data).format(format)
    return formatted
}

fun formatarDataBd(data: LocalDate): String {
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatted = LocalDate.parse(data.toString()).format(format)
    return formatted
}

fun formatarDoubleBd(valor: String): String {
    val format = valor.replace(".", "").replace(",", ".")
    return format
}

@OptIn(ExperimentalMaterial3Api::class)
fun formatarDataDatePicker(inputFormat: Boolean = false, data: Long?): String {
    val dateFormatter = DatePickerDefaults.dateFormatter(
        selectedDateSkeleton = if (inputFormat) "dd MM yyyy" else "dd MMMM yyyy",
    )

    return dateFormatter
        .formatDate(
            dateMillis = data ?: Date().time,
            locale = CalendarLocale.getDefault()
        ).toString()
}

fun getEnabledButtonRetirarEstoque(qtdEstoque: Int): Boolean {
    return qtdEstoque > 0
}

