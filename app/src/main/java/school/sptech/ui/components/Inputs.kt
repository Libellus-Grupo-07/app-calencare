package school.sptech.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.R
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade15
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.theme.letterSpacingSecundaria

@Composable
fun InputIcon(
    value: String,
    onValueChange: (String) -> Unit,
    error: Boolean = false,
    leadingIcon: Int,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = fontFamilyPoppins,
            letterSpacing = letterSpacingPrincipal
        ),
        onValueChange = onValueChange,
        leadingIcon = {
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = "Email",
                modifier = Modifier.size(18.dp)
            )
        },
        trailingIcon = trailingIcon,
        label = {
            Text(
                text = label,
                fontFamily = fontFamilyPoppins,
                letterSpacing = letterSpacingPrincipal
            )
        },
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(100.dp),
        isError = error,
        supportingText = {
            if (error) {
                Text(
                    text = stringResource(id = R.string.campo_obrigatorio),
                    color = Vermelho,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingSecundaria
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = RoxoNubank,
            focusedTextColor = Preto,
            focusedLabelColor = RoxoNubank,
            focusedLeadingIconColor = RoxoNubank,
            focusedTrailingIconColor = RoxoNubank,

            unfocusedLabelColor = Cinza,
            unfocusedLeadingIconColor = Cinza,
            unfocusedTrailingIconColor = Cinza,
            unfocusedBorderColor = Cinza,
            unfocusedTextColor = Preto,

            cursorColor = RoxoNubank,

            errorCursorColor = Vermelho,
            errorLeadingIconColor = Vermelho,
            errorBorderColor = Vermelho,
            errorLabelColor = Vermelho,
            errorTextColor = Vermelho,
            errorTrailingIconColor = Vermelho,

            selectionColors = TextSelectionColors(
                handleColor = RoxoNubank,
                backgroundColor = CinzaOpacidade15,
            )
        )
    )
}


@Composable
fun InputMedium(
    value: String,
    readOnly: Boolean = false,
    trailingIcon: @Composable() (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    label: String,
    error: Boolean = false,
    isMultiline: Boolean = false,
    isNumericInput: Boolean = false,
    isSmallInput: Boolean = false,
    tamanhoMinimo: Int? = null,
    campoObrigatorio: Boolean = true,
    modifier: Modifier = Modifier
) {
    var inputNaoPreenchida by remember { mutableStateOf(error) }
    var inputPreenchidaComQuantidadeMinima by remember { mutableStateOf(true) }
    var inputNumericInvalida by remember { mutableStateOf(false) }

    val shapeSize = if (isMultiline) 20.dp else 40.dp
    val textStyle = TextStyle(
        fontFamily = fontFamilyPoppins,
        fontSize = if (isSmallInput) 11.5.sp else 13.5.sp,
        letterSpacing = letterSpacingPrincipal,
        fontWeight = FontWeight.Medium,
        baselineShift = BaselineShift(0f),
        textIndent = TextIndent(
            firstLine = if (isSmallInput) 2.sp else 8.sp,
            restLine = if (isSmallInput) 2.sp else 8.sp
        )
    )

    OutlinedTextField(
        value = value,
        textStyle = textStyle,
        readOnly = readOnly,
        onValueChange = {
            onValueChange(it)
            inputNaoPreenchida =
                it.isEmpty() && campoObrigatorio || isNumericInput && it.toDoubleOrNull() == 0.0
            inputPreenchidaComQuantidadeMinima =
                tamanhoMinimo?.let { tamanhoMinimo -> it.length >= tamanhoMinimo } ?: true
        },
        keyboardOptions = if (isNumericInput) KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal) else KeyboardOptions.Default,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Preto,
            focusedTextColor = Preto,
            focusedPlaceholderColor = Cinza,
            focusedTrailingIconColor = Preto,
            unfocusedTextColor = Preto,
            unfocusedBorderColor = Cinza,
            unfocusedPlaceholderColor = Cinza,
            unfocusedTrailingIconColor = Cinza,
            cursorColor = Preto,
            errorCursorColor = Vermelho,
            errorLeadingIconColor = Vermelho,
            errorBorderColor = Vermelho,
            errorPlaceholderColor = Vermelho,
            errorLabelColor = Vermelho,
            errorTextColor = Vermelho,
            errorTrailingIconColor = Vermelho,
            selectionColors = TextSelectionColors(
                handleColor = RoxoNubank,
                backgroundColor = CinzaOpacidade15,
            )
        ),
        trailingIcon = trailingIcon?.let { { trailingIcon() } },
        placeholder = {
            Text(
                text = if (isNumericInput) "" else label,
                style = textStyle,
                fontWeight = FontWeight.Normal
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .let {
                if (isMultiline) it.height(124.dp)
                else if (!campoObrigatorio && isSmallInput) it.height(64.dp)
                else if (isSmallInput) it.height(48.dp)
                else it.height(70.dp)
            },
        shape = RoundedCornerShape(shapeSize),
        singleLine = !isMultiline,
        maxLines = if (isMultiline) Int.MAX_VALUE else 1,
        isError = (error && value.isEmpty())
                || inputNaoPreenchida
                || !inputPreenchidaComQuantidadeMinima
                || (isNumericInput && (error && value == "0,00")),
        supportingText = {
            if ((error && value.isEmpty()) || inputNaoPreenchida || !inputPreenchidaComQuantidadeMinima || (isNumericInput && (error && value == "0,00"))) {
                Text(
                    text = if (!inputNaoPreenchida && !inputPreenchidaComQuantidadeMinima)
                        "$label deve ter no mínimo $tamanhoMinimo caracteres"
                    else if (
                        isNumericInput && (isNumericInput && (error && value == "0,00"))) stringResource(
                        id = R.string.valor_invalido,
                        label
                    )
                    else stringResource(id = R.string.campo_obrigatorio),
                    color = Vermelho,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingSecundaria
                )
            }
        }
    )
}