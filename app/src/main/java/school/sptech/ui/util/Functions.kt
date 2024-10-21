import android.icu.text.DecimalFormat
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

fun formatarDecimal(valor: Float): String {
    val format = DecimalFormat("#,##0.00")
    val formatted = format.format(valor)
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

@OptIn(ExperimentalMaterial3Api::class)
fun formatarDataDatePicker(inputFormat:Boolean = false, data:Long?): String{
    val dateFormatter = DatePickerDefaults.dateFormatter(
        selectedDateSkeleton = if(inputFormat) "dd MM yyyy" else "dd MMMM yyyy",
    )

    return dateFormatter
        .formatDate(
            dateMillis = data ?: Date().time,
            locale = CalendarLocale.getDefault()).toString()
}
