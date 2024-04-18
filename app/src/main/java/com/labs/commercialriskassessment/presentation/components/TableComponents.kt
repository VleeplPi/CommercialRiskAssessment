package com.labs.commercialriskassessment.presentation.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TAG: String = "TableComponents"
@Composable
fun TablePrimaryComponent(data: Array<DoubleArray>, headers: Array<String>){
    Log.d(TAG, "TablePrimaryComponent")
    LazyRow(userScrollEnabled=true){
        items(headers){header ->
            Text(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .width(50.dp)
                    .padding(5.dp),
                text = header,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp
            )
        }
    }
    LazyColumn(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = true
    ) {
        items(data.size){row ->
//            LazyRow(){
//                items(data[row].size){value ->
//                    Text(
//                        modifier = Modifier
//                            .border(1.dp, Color.Black)
//                            .width(50.dp)
//                            .padding(5.dp),
//                        text = "${value}",
//                        fontSize = 18.sp,
//                        textAlign = TextAlign.Start,
//                        letterSpacing = 2.sp
//                    )
//                }
//            }
            Text("${row}")

        }
    }
//    LazyColumn(
//        verticalArrangement = Arrangement.SpaceAround,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        userScrollEnabled = true
//    ){
//        items(data){row ->
//            LazyRow(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceAround,
//                userScrollEnabled = true
//            ){
//                items(row.size){i ->
//                    Text(text = "${row[i]}")
//                }
//            }
//        }
//    }

}