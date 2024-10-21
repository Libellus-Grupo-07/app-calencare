package school.sptech.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.RoxoNubank


@Composable
fun DropdownBox(
    selectedText: String,
    expanded: Boolean,
    onClick: () -> Unit,
    onItemClick: (String) -> Unit,
    items: List<String>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Branco)
            .padding(16.dp)
    ) {
        Text(text = selectedText)
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onClick() },
        modifier = Modifier.fillMaxWidth().background(Branco),
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = { TextoButtonMedium(texto = item ) },
                onClick = { onItemClick(item) },
                colors = MenuItemColors(
                    textColor = if(item.equals(selectedText)) RoxoNubank else Cinza,
                    leadingIconColor = Cinza,
                    trailingIconColor = Cinza,
                    disabledTextColor = Cinza,
                    disabledLeadingIconColor = Cinza,
                    disabledTrailingIconColor = Cinza,
                ),
            )
        }
    }
}