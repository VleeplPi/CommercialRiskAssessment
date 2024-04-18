package com.labs.commercialriskassessment.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.labs.commercialriskassessment.presentation.theme.GreyColor
import com.labs.commercialriskassessment.presentation.theme.LightGreyColor
import com.labs.commercialriskassessment.presentation.theme.componentShapes
import com.labs.commercialriskassessment.presentation.theme.lowPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryOutlinedTextField(
    textValue: String,
    labelValue: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    icon: Painter = rememberVectorPainter(image = Icons.Filled.KeyboardArrowRight),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier.fillMaxWidth()
){
    OutlinedTextField(
        modifier = modifier
            .padding(vertical = lowPadding)
            .clip(componentShapes.small),
        value = textValue,
        onValueChange = { onValueChange(it)},
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.run {
            outlinedTextFieldColors(
                focusedBorderColor = GreyColor,
                focusedLabelColor = Color.Black,
                containerColor = LightGreyColor,
                unfocusedBorderColor = LightGreyColor
            )
        },
        shape = componentShapes.large,
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(painter =icon, contentDescription = "", modifier = Modifier.height(32.dp))},
        enabled=enabled,
    )
}
