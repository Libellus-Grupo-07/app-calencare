package school.sptech.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import formatarDataDatePicker
import school.sptech.R
import school.sptech.data.model.Empresa
import school.sptech.data.model.Funcionario
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.RoxoNubank

@Composable
fun FormFieldWithLabel(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isMultiline: Boolean = false,
    readOnly: Boolean = false,
    isDateInput: Boolean = false,
    isNumericInput: Boolean = false
) {
    var enabledDatePicker by remember { mutableStateOf(false) }
    val dateValue: Long = if(isDateInput && value.isNotEmpty()) value.toLong() else 0L

    Column(modifier = Modifier.fillMaxWidth()) {
        LabelInput(label = label)
        InputMedium(
            value = if(isDateInput && value.isNotBlank()) formatarDataDatePicker(inputFormat = true, data = dateValue) else value,
            onValueChange = onValueChange,
            label = label,
            readOnly = readOnly,
            isNumericInput = isNumericInput,
            isMultiline = isMultiline,
            trailingIcon = {
                if (isDateInput) {
                    IconButton(onClick = { enabledDatePicker = true }) {
                        Icon(
                            painter = painterResource(id = R.mipmap.calendario),
                            contentDescription = "Ícone de calendário",
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }
            },
            modifier = Modifier.clickable(enabled = isDateInput) {
                enabledDatePicker = true
            }
        )
    }

    if (isDateInput) {
        if (enabledDatePicker) {
            DatePickerModal(
                dateSelected = dateValue,
                onDismiss = { enabledDatePicker = false },
                onDateSelected = { date ->
                    onValueChange(date.toString())
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownFieldWithLabel(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        LabelInput(label = label)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            InputMedium(
                value = value,
                readOnly = true,
                onValueChange = {},
                label = label,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Branco)
            ) {
                options.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(options[index])
                            expanded = false
                        },
                        text = {
                            TextoButtonLarge(texto = text)
                        },
                        colors = MenuItemColors(
                            textColor = if (text == value) RoxoNubank else Cinza,
                            disabledTextColor = Cinza,
                            leadingIconColor = Cinza,
                            trailingIconColor = Cinza,
                            disabledLeadingIconColor = Cinza,
                            disabledTrailingIconColor = Cinza
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun FormButtons(
    onCancelClick: () -> Unit = { /* TODO: Handle cancel */ },
    onAddClick: () -> Unit = { /* TODO: Handle add */ }
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        ButtonCancelar(onClick = {})

        Spacer(modifier = Modifier.size(4.dp))

        ButtonBackground(
            titulo = stringResource(R.string.adicionar),
            cor = RoxoNubank,
            onClick = {})
    }
}

@Composable
fun FormDadosPessoais(usuario: Funcionario) {
    // Nome
    FormFieldWithLabel(
        value = usuario.nome ?: "",
        onValueChange = { },
        label = stringResource(R.string.nome)
    )

    // Telefone
    FormFieldWithLabel(
        value = usuario.telefone ?: "",
        onValueChange = {},
        label = stringResource(R.string.telefone)
    )

    // Email
    FormFieldWithLabel(
        value = usuario.email ?: "",
        onValueChange = {},
        label = stringResource(R.string.email)
    )

    // Perfil
    FormFieldWithLabel(
        value = usuario.perfilAcesso ?: "",
        readOnly = true,
        onValueChange = {},
        label = stringResource(R.string.perfil)
    )
}


@Composable
fun FormDadosEmpresa(empresa: Empresa) {
    FormFieldWithLabel(
        value = empresa.razaoSocial ?: "" ,  // Valor alterado
        onValueChange = {},
        label = stringResource(id = R.string.razaoSocial)
    )

    FormFieldWithLabel(
        value = empresa.cnpj ?: "" ,  // Valor alterado
        onValueChange = {},
        label = stringResource(id = R.string.cnpj)
    )

    // Telefone

    FormFieldWithLabel(
        value = empresa.telefonePrincipal ?: "",  // Valor alterado
        onValueChange = {},
        label = stringResource(id = R.string.telefone)
    )

    // CEP
    FormFieldWithLabel(
        value = "01454-000",
        onValueChange = {},
        label = stringResource(id = R.string.cep)
    )

    FormFieldWithLabel(
        value = "Rua da Beleza Eterna",  // Valor alterado
        onValueChange = {},
        label = stringResource(id = R.string.logradouro)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(0.3f)) {
            FormFieldWithLabel(
                value = "456",  // Valor alterado
                onValueChange = {},
                label = stringResource(id = R.string.numero)
            )
        }

        Spacer(modifier = Modifier.size(12.dp))

        Column(modifier = Modifier.weight(0.5f)) {
            FormFieldWithLabel(
                value = " ",  // Valor alterado
                onValueChange = {},
                label = stringResource(id = R.string.complemento)
            )
        }
    }
}