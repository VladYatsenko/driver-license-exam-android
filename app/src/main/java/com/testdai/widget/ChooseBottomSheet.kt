package com.testdai.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testdai.R
import com.testdai.compose.Fonts

@Composable
fun <T> ChooseBottomSheet(
    title: String = "",
    list: List<ChooseItem<T>> = emptyList(),
    contentModifier: (Int, String) -> String = { pos, text -> text },
    changed: (T) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp, 5.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.gray))
        )
        Text(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.medium,
            text = title,
            color = colorResource(id = R.color.white)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            items(count = list.size, itemContent = { index ->
                val item = list[index]
                val text = item.titleRes?.let { stringResource(id = it) } ?: item.title.orEmpty()
                Item(text = contentModifier(index, text), selected = item.selected, onClick = {
                    changed(item.value)
                })
            })
        }
    }
}

@Composable
fun Item(
    text: String, selected: Boolean, onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, colorResource(id = R.color.shark)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = colorResource(id = R.color.shark)
        ),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontFamily = Fonts.regular,
                text = text,
                color = Color.White
            )
            if (selected) {
                Image(
                    painter = painterResource(id = R.drawable.ic_check), contentDescription = ""
                )
            }
        }
    }

}

interface ChooseItem <T> {
    val value: T

    val title: String?

    val titleRes: Int?

    val selected: Boolean
}