import android.icu.text.DecimalFormat
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Color
import school.sptech.ui.theme.Amarelo
import school.sptech.ui.theme.Laranja
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.Vermelho
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

fun transformarEmLocalDate(data: String): LocalDate {
    val ano = data.substring(6, 10).toInt()
    val mes = data.substring(3, 5).toInt()
    val dia = data.substring(0, 2).toInt()

    val dataParse = LocalDate.of(
        ano, mes, dia
    )

    return dataParse
}

fun transformarEmLocalDateTime(data: String, isDateBd:Boolean = false, isFinalDay:Boolean = false): LocalDateTime {
    // 2024-01-12
    val dateTimeParse: LocalDateTime
    val dateTimeIsFormatadaBd = data.length > 10

    if(dateTimeIsFormatadaBd){
        dateTimeParse = LocalDateTime.parse(data)
        return dateTimeParse
    }

    if(isDateBd){
        val ano = data.substring(0, 4).toInt()
        val mes = data.substring(5, 7).toInt()
        val dia = data.substring(8, 10).toInt()
        val hora = if(isFinalDay) 23 else 0
        val minuto = if(isFinalDay) 59 else 0
        val segundo = if(isFinalDay) 59 else 0

        dateTimeParse = LocalDateTime.of(
            ano, mes, dia, hora, minuto, segundo
        )

        return dateTimeParse
    }

    val ano = data.substring(6, 10).toInt()
    val mes = data.substring(3, 5).toInt()
    val dia = data.substring(0, 2).toInt()

    dateTimeParse = LocalDateTime.of(
        ano, mes, dia, 0, 0, 0
    )

    return dateTimeParse
}

fun getLongDate(data: String): Long {
    val dataParse = transformarEmLocalDateTime(data)
    val zonedDateTime = dataParse.atZone(ZoneId.of("UTC"));
    return zonedDateTime.toInstant().toEpochMilli()
}

fun formatarDecimal(valor: Number, isValueInput: Boolean = false): String {
    val format = DecimalFormat("#,##0.00")
    val formatted = format.format(valor.toDouble() / if (isValueInput) 100 else 1)
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

    if (data.length == 10) {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(data, inputFormatter)
        return date.format(format)
    }

    if(data.isEmpty()){
        return ""
    }

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
    if(data == null || data == 0L){
        return ""
    }

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

fun getEnabledInputDataValidade(descricaoValidade: String): Boolean {
    return !descricaoValidade.equals("Indefinido")
}

fun getStringProduto(qtdEstoque: Int): String {
    return if (qtdEstoque == 1) "produto" else "produtos"
}

fun getColorTextEstoque(qtdEstoque: Int): Color {
    return when {
        qtdEstoque == 0 -> Vermelho
        qtdEstoque in 1..5 -> Laranja
        qtdEstoque in 6 until 15 -> Amarelo
        else -> Verde
    }
}

fun getMonthInt(month: String): Month? {
    Month.values().forEach { mes ->
        val nomeEmLocale = mes.getDisplayName(TextStyle.FULL, Locale("pt", "BR"));
        if (nomeEmLocale.equals(month, ignoreCase = true)) {
            return mes;
        }
    }

    return null
}

fun isDataValida(data: String): Boolean {
    if(data.isEmpty()){
        return false
    }
   return data.isNotEmpty() && transformarEmLocalDate(data).isBefore(LocalDate.now().plusDays(1))
}