package com.labs.commercialriskassessment.presentation.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private val TAG = "InitValueFormComponent"

@Composable
fun InitValueFormComponent(onClickApplyBtn: (p:Double,n:Double,i:Double,k1:Double,k2:Double)-> Unit) {
    Log.d(TAG, "InitFormComponent")
    val isLoading = remember {
        mutableStateOf(false)
    }
    val p = remember{ mutableStateOf("") }
    val n = remember{ mutableStateOf("") }
    val i = remember{ mutableStateOf("") }
    val k1 = remember{ mutableStateOf("") }
    val k2 = remember{ mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .heightIn(450.dp)
            .padding(horizontal = 12.dp)
            .animateContentSize()){
        PrimaryOutlinedTextField(
            textValue = p.value,
            labelValue = "сумма кредита",
            onValueChange = {p.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = n.value,
            labelValue = "срок кредита",
            onValueChange = {n.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = i.value,
            labelValue = "процентная ставка",
            onValueChange = {i.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = k1.value,
            labelValue = "курс валюты на начало операции",
            onValueChange = {k1.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = k2.value,
            labelValue = "курс валюты на момент окончания операции",
            onValueChange = {k2.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        Spacer(modifier = Modifier.heightIn(20.dp))
        PrimaryGradientButtonComponent(
            value="рассчитать",
            onClickButton = {
                isLoading.value = true
                onClickApplyBtn(
                    p.value.toDouble(),
                    n.value.toDouble(),
                    i.value.toDouble()/100,
                    k1.value.toDouble(),
                    k2.value.toDouble()
                )
                isLoading.value = false
            },
            isLoading = isLoading.value,
            modifier = Modifier.width(250.dp))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormModalBottomSheet(
    openBottomSheet: Boolean,
    setStateBottomSheet: (Boolean) -> Unit,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit,

    ){
    Log.wtf(TAG, "FormModalBottomSheet")
    val skipPartiallyExpanded by remember { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
        confirmValueChange = {true},

        )
    val scope = rememberCoroutineScope()

    if (openBottomSheet) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = {
                scope.launch{
                    setStateBottomSheet(false)
                    onDismissRequest()
                }

            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets,

            ) {
            content()
        }

    }
}