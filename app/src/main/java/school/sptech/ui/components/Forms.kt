package school.sptech.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import formatarData
import formatarDataDatePicker
import getLongDate
import isDataValida
import school.sptech.R
import school.sptech.data.model.Empresa
import school.sptech.data.model.Endereco
import school.sptech.data.model.Funcionario
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.theme.letterSpacingSecundaria
import school.sptech.ui.viewModel.EmpresaViewModel
import school.sptech.ui.viewModel.UsuarioViewModel
import transformarEmLocalDate
import transformarEmLocalDateTime
import java.time.LocalDate

@Composable
fun FormFieldWithLabel(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: Boolean = false,
    readOnly: Boolean = false,
    isMultiline: Boolean = false,
    isDateInput: Boolean = false,
    enabledClearDate: Boolean = false,
    isNumericInput: Boolean = false,
    isSmallInput: Boolean = false,
    clickableInput: Boolean = false,
    onClickInput: () -> Unit = {},
    isObrigatorio: Boolean = true,
    isDatePastOrPresent: Boolean = false,
    minSize: Int? = null
) {
    var enabledDatePicker by remember { mutableStateOf(false) }
    val dateValue: Long =
        if(!isDateInput || value.isEmpty()) 0L
        else if (isDateInput && value.length > 10) getLongDate(value)
        else value.toLong()
    val dateValueFormat =
        if(!isDateInput || value.isEmpty()) label
        else if (isDateInput && value.length > 10) formatarData(value)
        else formatarDataDatePicker(
            inputFormat = true,
            data = dateValue
        )

    Column(modifier = Modifier.fillMaxWidth()) {
        val corDestaque = if (error && (isDateInput && dateValueFormat.isNotEmpty())) Vermelho else Cinza

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabelInput(label = label, isSmallInput = isSmallInput)

            if (isDateInput && enabledClearDate) {
                Text(
                    text = stringResource(id = R.string.limpar_data),
                    color = Preto,
                    fontSize = 9.5.sp,
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingSecundaria,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        onValueChange("")
                    }
                )

            }
        }

        Spacer(modifier = Modifier.height(if (isObrigatorio) 4.dp else 8.dp))

        if (clickableInput || isDateInput) {
            TextButton(
                onClick = {
                    if (isDateInput) {
                        enabledDatePicker = true
                    } else onClickInput()
                },
                border = BorderStroke(1.dp, corDestaque),
                shape = RoundedCornerShape(if (isMultiline) 20.dp else 100.dp),
                colors = ButtonColors(
                    contentColor = corDestaque,
                    containerColor = Color.Transparent,
                    disabledContentColor = corDestaque,
                    disabledContainerColor = Color.Transparent,
                ),
                contentPadding = PaddingValues(
                    horizontal = 24.dp,
                    vertical = if (isSmallInput) 14.dp else if (isDateInput) 17.5.dp else 20.dp
                ),
            ) {
                Text(
                    text = if (isDateInput) {
                        dateValueFormat
                    } else value,
                    fontSize = if (isSmallInput) 11.5.sp else 13.5.sp,
                    fontFamily = fontFamilyPoppins,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = letterSpacingPrincipal,
                    color = if (error) Vermelho else if (value.isEmpty()) Cinza else Preto
                )

                if (isDateInput) {
                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = painterResource(id = R.mipmap.calendario),
                        contentDescription = "Ícone de Calendário",
                        modifier = Modifier.let {
                            if (isSmallInput) it.size(16.dp) else it.size(20.dp)
                        },
                        tint = corDestaque
                    )
                }
            }

            if (error) {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = if (isDatePastOrPresent && value.isNotEmpty())
                        stringResource(id = R.string.data_invalida, label)
                    else stringResource(id = R.string.campo_obrigatorio),
                    color = Vermelho,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingSecundaria
                )
            }

        } else {
            InputMedium(
                value = value,
                onValueChange = onValueChange,
                label = label,
                error = error,
                readOnly = readOnly,
                campoObrigatorio = isObrigatorio,
                isNumericInput = isNumericInput,
                isSmallInput = isSmallInput,
                isMultiline = isMultiline,
                tamanhoMinimo = minSize
            )
        }
    }

    if (isDateInput) {
        if (enabledDatePicker) {
            DatePickerModal(
                dateSelected = dateValue,
                onDismiss = { enabledDatePicker = false },
                onDateSelected = { date ->
                    onValueChange(date.toString())
                },
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
    error: Boolean = false,
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
                modifier = Modifier.menuAnchor(),
                error = error,
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
    onAddClick: () -> Unit = { /* TODO: Handle add */ },
    addButtonText: String = stringResource(R.string.adicionar)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        ButtonCancelar(onClick = onCancelClick)

        Spacer(modifier = Modifier.size(4.dp))

        ButtonBackground(
            titulo = addButtonText,
            cor = RoxoNubank,
            onClick = onAddClick
        )
    }
}

@Composable
fun FormDadosPessoais(
    usuario: Funcionario,
    usuarioViewModel: UsuarioViewModel
) {
    // Nome
    FormFieldWithLabel(
        value = usuario.nome ?: "",
        onValueChange = {
            usuarioViewModel.usuario = usuarioViewModel.usuario.copy(nome = it)
        },
        label = stringResource(R.string.nome)
    )

    // Telefone
    FormFieldWithLabel(
        value = usuario.telefone ?: "",
        onValueChange = {
            usuarioViewModel.usuario =
                usuarioViewModel.usuario.copy(telefone = it)
        },
        label = stringResource(R.string.telefone)
    )

    // Email
    FormFieldWithLabel(
        value = usuario.email ?: "",
        onValueChange = {
            usuarioViewModel.usuario = usuarioViewModel.usuario.copy(email = it)
        },
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
fun FormDadosEmpresa(
    empresaViewModel: EmpresaViewModel,
    empresa: Empresa,
    endereco: Endereco,
    onClickInputEndereco: () -> Unit
) {
    FormFieldWithLabel(
        value = empresa.razaoSocial ?: "",  // Valor alterado
        onValueChange = {
            empresaViewModel.empresa =
                empresaViewModel.empresa.copy(razaoSocial = it)
        },
        label = stringResource(id = R.string.razaoSocial)
    )

    FormFieldWithLabel(
        value = empresa.cnpj ?: "",  // Valor alterado
        onValueChange = {
            empresaViewModel.empresa = empresaViewModel.empresa.copy(cnpj = it)
        },
        label = stringResource(id = R.string.cnpj)
    )

    // Telefone

    FormFieldWithLabel(
        value = empresa.telefonePrincipal ?: "",  // Valor alterado
        onValueChange = {
            empresaViewModel.empresa =
                empresaViewModel.empresa.copy(telefonePrincipal = it)
        },
        label = stringResource(id = R.string.telefone)
    )

    FormFieldWithLabel(
        value = empresa.intervaloAtendimento.toString() ?: "0",
        onValueChange = {
            empresaViewModel.empresa =
                empresaViewModel.empresa.copy(intervaloAtendimento = it.toInt())
        },
        label = stringResource(id = R.string.intervaloAtendimento)
    )

    // Endereço
    FormFieldWithLabel(
        value = endereco.descricaoEndereco ?: "",  // Valor alterado
        readOnly = true,
        onValueChange = {},
        isMultiline = true,
        label = stringResource(id = R.string.endereco),
        onClickInput = onClickInputEndereco,
        clickableInput = true
    )
}