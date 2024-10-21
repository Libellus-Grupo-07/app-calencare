package school.sptech.ui.viewModel

import androidx.lifecycle.ViewModel
import school.sptech.data.model.ChartData

class DashboardViewModel : ViewModel() {
    val chartData = listOf(
        ChartData("Semana 1", 2_429.03),
        ChartData("Semana 2", 1_429.03),
        ChartData("Semana 3", 1_600.0),
        ChartData("Semana 4", 1_600.0))
}