package com.iec.makeup.ui.features.profiles.booking_history.components.finished_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iec.makeup.ui.features.profiles.booking_history.components.finished_screen.components.ItemFinishedNotReview
import com.iec.makeup.ui.features.profiles.booking_history.components.finished_screen.components.ItemFinishedWithReview
import com.iec.makeup.ui.features.profiles.booking_history.model.UserBookingUIWrapper
import com.iec.makeup.ui.features.profiles.booking_history.model.mockListTest

enum class FilterOption(val title: String) {
    ALL("Tất cả"), REVIEWED("Đã đánh giá"), NOT_REVIEWED("Chưa đánh giá")
}

@Composable
fun ScreenFinishedBooking(
    data: List<UserBookingUIWrapper> = mockListTest,
){
    var filteredContent by remember { mutableStateOf(FilterOption.ALL) }
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.FilterAlt,
                contentDescription = "Filter"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Bộ lọc: ${filteredContent.title}"
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                Modifier.clickable{
                    expanded = !expanded
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(FilterOption.ALL.title) },
                    onClick = {
                        expanded = false
                        filteredContent = FilterOption.ALL
                    }
                )
                DropdownMenuItem(
                    text = { Text(FilterOption.REVIEWED.title) },
                    onClick = {
                        expanded = false
                        filteredContent = FilterOption.REVIEWED
                    }
                )
                DropdownMenuItem(
                    text = { Text(FilterOption.NOT_REVIEWED.title) },
                    onClick = {
                        expanded = false
                        filteredContent = FilterOption.NOT_REVIEWED
                    }
                )
            }
        }
        LazyColumn {
            items(data.size) { index ->
                val item = data[index]
                // Later implement the review
                if(item.state==null){
                    ItemFinishedWithReview(item)
                }else{
                    ItemFinishedNotReview(item)
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ScreenFinishedBooking()
}