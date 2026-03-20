package com.example.n15_quan_ly_phong_tro.roomrental.model


object RoomStore {
    val rooms = mutableListOf(
        Room("P101", "Phòng ban công", 2500000.0, RoomStatus.AVAILABLE, "", ""),
        Room("P102", "Phòng tầng 2 - View phố", 3200000.0, RoomStatus.RENTED, "Nguyễn Văn A", "0912345678"),
        Room("P103", "Phòng đơn tiện nghi", 2000000.0, RoomStatus.AVAILABLE, "", ""),
        Room("P201", "Phòng đôi cao cấp", 4500000.0, RoomStatus.RENTED, "Trần Thị B", "0988777666"),
        Room("P202", "Phòng studio", 3800000.0, RoomStatus.AVAILABLE, "", ""),
        Room("P301", "Phòng áp mái", 1800000.0, RoomStatus.RENTED, "Lê Văn C", "0905111222"),
        Room("P302", "Phòng nhỏ giá rẻ", 1500000.0, RoomStatus.AVAILABLE, "", ""),
        Room("P401", "Phòng Full nội thất", 5000000.0, RoomStatus.RENTED, "Phạm Minh D", "0333444555"),
        Room("P402", "Phòng tầng trệt", 2200000.0, RoomStatus.AVAILABLE, "", ""),
        Room("P501", "Penthouse mini", 6500000.0, RoomStatus.RENTED, "Hoàng Anh E", "0977888999")
    )
}