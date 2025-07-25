package com.iec.makeup.ui.features.booking

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iec.makeup.core.ui.IECText
import com.iec.makeup.ui.theme.primaryColor
import androidx.compose.foundation.clickable
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    navBack: () -> Unit = {},
    navToComplete: () -> Unit = {}
) {
    // State for dropdowns and pickers
    var selectedForm by remember { mutableStateOf("At Home") }
    var showFormDropdown by remember { mutableStateOf(false) }
    val formOptions = listOf("At Home", "At Salon")

    // Date picker state
    val datePickerState = rememberDatePickerState()
    var selectedDateTime by remember { mutableStateOf("06/02/2025 20:00") }
    var showDateTimePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "BOOK APPOINTMENT",
                        color = primaryColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color.White,
                elevation = 4.dp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // User Information Section
            Text(
                "Information for John Doe:",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoCard(
                    icon = Icons.Default.LocationOn,
                    text = "123 Main Street, Central City, NY"
                )
                Spacer(modifier = Modifier.width(8.dp))
                InfoCard(
                    icon = Icons.Default.Phone,
                    text = "123-456-7890"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Appointment Details Section
            Text(
                "Your makeup appointment:",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            AppointmentDetailRow(
                icon = Icons.Default.Person,
                label = "Expert:",
                value = "Mai Linh Bui"
            )
            // Form selection (clickable)
            AppointmentDetailRow(
                icon = Icons.Default.Code, // Using Code as a placeholder icon
                label = "Form:",
                value = selectedForm,
                showDropdown = true,
                onClick = { showFormDropdown = true }
            )
            if (showFormDropdown) {
                DropdownMenu(
                    expanded = showFormDropdown,
                    onDismissRequest = { showFormDropdown = false }
                ) {
                    formOptions.forEach { option ->
                        DropdownMenuItem(onClick = {
                            selectedForm = option
                            showFormDropdown = false
                        }) {
                            Text(option)
                        }
                    }
                }
            }
            // Date/time picker (clickable)
            AppointmentDetailRow(
                icon = Icons.Default.CalendarToday,
                label = "Date & Time:",
                value = selectedDateTime,
                showDropdown = true,
                onClick = { showDateTimePicker = true }
            )
            // Material3 DatePickerDialog
            if (showDateTimePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDateTimePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            val millis = datePickerState.selectedDateMillis
                            if (millis != null) {
                                val date = Date(millis)
                                val formatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
                                selectedDateTime = "$formatted 20:00" // Keep time static for now
                            }
                            showDateTimePicker = false
                        }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDateTimePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            AppointmentDetailRow(
                icon = Icons.Default.FavoriteBorder,
                label = "Makeup type:",
                value = "Party",
                showImageIcon = true
            )
            AppointmentDetailRow(
                icon = Icons.Default.AttachMoney, // Using AttachMoney as a placeholder icon
                label = "Makeup price:",
                value = "350,000 VND"
            )
            AppointmentDetailRow(
                icon = Icons.Default.DirectionsCar, // Using DirectionsCar as a placeholder icon
                label = "Travel fee:",
                value = "0 VND"
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes the total and button to the bottom

            // Total Price Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total:", fontSize = 18.sp, color = Color.Black)
                Text(
                    "350,000 VND",
                    fontSize = 20.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirmation Button
            Button(
                onClick = {
                    navToComplete()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Confirm", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun InfoCard(icon: ImageVector, text: String) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(icon, contentDescription = null, tint = primaryColor)
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray)
            }
            IECText("123-456-7890", color = Color.Black)
            IECText(text, color = Color.Black)
        }
    }
}

@Composable
fun AppointmentDetailRow(
    icon: ImageVector,
    label: String,
    value: String,
    showDropdown: Boolean = false,
    showImageIcon: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .let { if (onClick != null) it.clickable { onClick() } else it },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = primaryColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, fontSize = 16.sp, color = Color.Black, modifier = Modifier.width(120.dp))
        Text(value, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        if (showImageIcon) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Default.Image,
                contentDescription = "Image",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            ) // Placeholder for image icon
        }
        if (showDropdown) {
            Spacer(modifier = Modifier.weight(1f)) // Pushes the dropdown icon to the end
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Dropdown",
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookingScreen() {
    BookingScreen()
}