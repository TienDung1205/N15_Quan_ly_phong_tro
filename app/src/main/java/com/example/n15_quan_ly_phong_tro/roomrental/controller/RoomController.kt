package com.example.n15_quan_ly_phong_tro.roomrental.controller

import com.example.n15_quan_ly_phong_tro.roomrental.model.Room
import com.example.n15_quan_ly_phong_tro.roomrental.model.RoomStatus


class RoomController(private val rooms: MutableList<Room>) {

    data class RoomInput(
        val roomCode: String,
        val roomName: String,
        val rentPrice: Double,
        val status: RoomStatus,
        val tenantName: String,
        val tenantPhone: String
    )

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }

    fun getRooms(): List<Room> = rooms.toList()

    fun getRoom(index: Int): Room? = rooms.getOrNull(index)

    fun addRoom(input: RoomInput): Result {
        val validation = validate(input, editingIndex = -1)
        if (validation is Result.Error) return validation

        rooms.add(
            Room(
                roomCode = input.roomCode.trim(),
                roomName = input.roomName.trim(),
                rentPrice = input.rentPrice,
                status = input.status,
                tenantName = input.tenantName.trim(),
                tenantPhone = input.tenantPhone.trim()
            )
        )
        return Result.Success
    }

    fun updateRoom(index: Int, input: RoomInput): Result {
        if (index !in rooms.indices) return Result.Error("Phòng không tồn tại")

        val validation = validate(input, editingIndex = index)
        if (validation is Result.Error) return validation

        rooms[index] = Room(
            roomCode = input.roomCode.trim(),
            roomName = input.roomName.trim(),
            rentPrice = input.rentPrice,
            status = input.status,
            tenantName = input.tenantName.trim(),
            tenantPhone = input.tenantPhone.trim()
        )
        return Result.Success
    }

    fun deleteRoom(index: Int): Result {
        if (index !in rooms.indices) return Result.Error("Phòng không tồn tại")
        rooms.removeAt(index)
        return Result.Success
    }

    private fun validate(input: RoomInput, editingIndex: Int): Result {
        if (input.roomCode.isBlank()) return Result.Error("Mã phòng không được để trống")
        if (input.roomName.isBlank()) return Result.Error("Tên phòng không được để trống")
        if (input.rentPrice <= 0) return Result.Error("Giá thuê phải lớn hơn 0")

        val duplicatedCode = rooms.withIndex().any { (i, room) ->
            i != editingIndex && room.roomCode.equals(input.roomCode.trim(), ignoreCase = true)
        }
        if (duplicatedCode) return Result.Error("Mã phòng đã tồn tại")

        if (input.status == RoomStatus.RENTED) {
            if (input.tenantName.isBlank()) return Result.Error("Đã thuê thì cần tên người thuê")
            val phone = input.tenantPhone.trim()
            val isValidPhone = phone.matches(Regex("^0[0-9]{9,10}$"))
            if (!isValidPhone) return Result.Error("Số điện thoại không hợp lệ")
        }

        return Result.Success
    }
}