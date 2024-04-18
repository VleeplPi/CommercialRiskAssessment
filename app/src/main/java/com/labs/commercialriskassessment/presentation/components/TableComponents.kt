package com.labs.commercialriskassessment.presentation.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TAG: String = "TableComponents"

@Composable
fun SimpleTable(data: Array<DoubleArray>, headers: Array<String>) {
    Log.d(TAG,"SimpleTable")

        LazyRow(
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = true
        )
        {
            item {
                LazyColumn(
                    userScrollEnabled = true
                ) {
                    item {
                        Log.d(TAG, "HEDERS")
                        TableHeader(headers)
                    }
                    Log.d(TAG, "ROWS")
//                    items(data.size) { rowDataId ->
////                        TableRow(data[rowDataId])
//                        Text(
//                            modifier = Modifier
//                                .border(1.dp, Color.Black)
//                                .width(200.dp)
//                                .padding(5.dp),
//                            text = "${data[rowDataId]}",
//                            fontSize = 18.sp,
//                            textAlign = TextAlign.Start,
//                            letterSpacing = 2.sp
//                        )
//                    }
                }
            }
        }

}

@Composable
fun TableHeader(headers: Array<String>) {
    Log.d(TAG, "TableHeader")
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(headers.size){idx ->
            Text(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .width(200.dp)
                    .padding(5.dp),
                text = headers[idx],
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp
            )

        }
    }
}

@Composable
fun TableRow(rowData: DoubleArray) {
    Log.d(TAG, "TableRow")
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(rowData.size){idxValue ->
            Text(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .width(200.dp)
                    .padding(5.dp),
                text = "${rowData[idxValue]}",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp
            )
        }

    }
}
