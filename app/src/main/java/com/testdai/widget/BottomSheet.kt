package com.testdai.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.testdai.R

@Composable
fun BottomSheetWidget(
    title: String = "",
    content: @Composable ColumnScope.() -> Unit
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
                .background(MaterialTheme.colors.primaryVariant)
        )
        Text(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            text = title,
            color = MaterialTheme.colors.primary
        )
        content()
    }
}

@Composable
fun <T> ChooseBottomSheet(
    title: String = "",
    list: List<ChooseItem<T>> = emptyList(),
    contentModifier: (Int, String) -> String = { pos, text -> text },
    changed: (T) -> Unit = {}
) {
    BottomSheetWidget(
        title
    ) {
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
                ChooseItemWidget(
                    text = contentModifier(index, text),
                    selected = item.selected,
                    onClick = {
                        changed(item.value)
                    })
            })
        }
    }
}

@Composable
fun ChooseItemWidget(
    text: String, selected: Boolean, onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.surface),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colors.surface
        ),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                text = text,
                color = MaterialTheme.colors.primary
            )
            if (selected) {
                Image(
                    painter = painterResource(id = R.drawable.ic_check),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    contentDescription = ""
                )
            }
        }
    }

}

interface ChooseItem<T> {
    val value: T

    val title: String?

    val titleRes: Int?

    val selected: Boolean
}