package school.sptech

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import school.sptech.data.repository.DataStoreRepository
import school.sptech.ui.components.BottomBar
import school.sptech.navigation.NavigationGraph
import school.sptech.network.RetrofitService
import school.sptech.ui.theme.BrancoFundo
import school.sptech.ui.theme.CalencareAppTheme

lateinit var dataStoreRepository: DataStoreRepository
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreRepository = DataStoreRepository(this)

        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                val navController = rememberNavController()
                var buttonVisible by remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BrancoFundo),
                    bottomBar = {
                        if (buttonVisible) {
                            BottomBar(
                                navController = navController,
                                state = buttonVisible,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            Box(modifier = Modifier.fillMaxWidth())
                        }
                    },
                ) { innerPadding ->
                    Box(modifier = Modifier
                        .padding(
                                top = 0.dp,
                                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                                bottom = innerPadding.calculateBottomPadding(),
                        )) {
                        NavigationGraph(
                            navController = navController,
                            onBottomBarVisibleChanged = { isVisible ->
                                buttonVisible = isVisible
                            }
                        )
                    }
                }
            }
        }
    }
}