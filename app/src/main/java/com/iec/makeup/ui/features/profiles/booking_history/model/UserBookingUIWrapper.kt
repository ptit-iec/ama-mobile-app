package com.iec.makeup.ui.features.profiles.booking_history.model

data class UserBookingUIWrapper(
    val id: String = "",
    val image: String = "https://www.ogleschool.edu/wp-content/uploads/2014/04/best-traits-of-makeup-artist.jpg",
    val name: String = "",
    val type: String = "",
    val totalBill: String = "",
    val state: HistoryCategory = HistoryCategory.PENDING,
    val bookedDate: String = "",
    val address: String = "",
    val phone: String = "",
    val note: String = "",
    val cancelReason: String = ""

)

val mockListTest = List(4) {
    UserBookingUIWrapper(
        id = "asdf",
        name = "Bùi Mai Linh",
        type ="Make up dự tiệc tại nhà",
        totalBill =  "350.000",
        state = HistoryCategory.PENDING,
        bookedDate =  "10/10/2023 20:00 - 21:00"
    )
}