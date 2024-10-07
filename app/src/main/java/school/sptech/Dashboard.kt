package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import school.sptech.ui.theme.CalencareAppTheme

class TelaDashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Dashboard(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Dashboard(name: String, modifier: Modifier = Modifier) {
    Column {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    CalencareAppTheme {
        Dashboard("Android")
    }
}