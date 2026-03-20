package com.example.n15_quan_ly_phong_tro.roomrental.model

data class Room(
    val roomCode: String,
    val roomName: String,
    val rentPrice: Double,
    val status: RoomStatus,
    val tenantName: String,
    val tenantPhone: String
)