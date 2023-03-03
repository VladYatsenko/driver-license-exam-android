package com.testdai.ui.screen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testdai.R
import com.testdai.ui.screen.home.HomeViewModel

@Composable
fun CategorySheet(
    viewModel: HomeViewModel
) {

    val categorySelector by viewModel.categorySelector.observeAsState(CategorySelectorState())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
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
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontFamily = com.testdai.compose.Fonts.medium,
            text = stringResource(id = R.string.choose_category),
            color = colorResource(id = R.color.white)
        )
        Text(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontFamily = com.testdai.compose.Fonts.regular,
            text = stringResource(id = R.string.choose_category_description),
            color = colorResource(id = R.color.gray)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),

            // content padding
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(categorySelector.categories.size) { index ->

                    Card(
                        backgroundColor = Color.White,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .clickable {
                                val item = categorySelector.categories[index]
                                if (item is CategoryItem.Item)
                                    viewModel.onCategoryClicked(item)
                            },
                        elevation = 8.dp,
                    ) {
                        val text = when (val item = categorySelector.categories[index]) {
                            is CategoryItem.Item -> item.category.name
                            CategoryItem.Empty -> ""
                        }

                        val color = when (val item = categorySelector.categories[index]) {
                            is CategoryItem.Item -> when (item.state) {
                                CategoryItem.SelectionState.Common -> Color.Black
                                CategoryItem.SelectionState.Selected -> Color.Green
                                CategoryItem.SelectionState.Disabled -> Color.Gray
                            }
                            CategoryItem.Empty -> Color.White
                        }
                        Text(
                            text = text,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = color,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        )

    }
}

@Preview
@Composable
fun CategorySheetPreview() {
    CategorySheet(viewModel = viewModel(factory = HomeViewModel.Factory))
}