package com.example.n15_quan_ly_phong_tro.roomrental.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quan_ly_phong_tro.R
import com.example.roomrental.controller.RoomController
import com.example.roomrental.model.RoomStore
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private lateinit var roomController: RoomController
    private lateinit var roomAdapter: RoomAdapter

    private lateinit var rvRooms: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        enableEdgeToEdge()
        roomController = RoomController(RoomStore.rooms)

        rvRooms = findViewById(R.id.rvRooms)
        tvEmpty = findViewById(R.id.tvEmpty)
        fabAdd = findViewById(R.id.fabAdd)

        setupRecyclerView()
        setupActions()
        renderRooms()
    }

    override fun onResume() {
        super.onResume()
        renderRooms()
    }

    private fun setupRecyclerView() {
        roomAdapter = RoomAdapter(
            onEditClick = { index -> openAddEdit(index) },
            onDeleteClick = { index -> confirmDelete(index) },
            onItemClick = { index -> openAddEdit(index) }
        )
        rvRooms.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = roomAdapter
        }
    }

    private fun setupActions() {
        fabAdd.setOnClickListener {
            openAddEdit(-1)
        }
    }

    private fun openAddEdit(editIndex: Int) {
        val intent = Intent(this, AddEditRoomActivity::class.java)
        intent.putExtra(AddEditRoomActivity.EXTRA_EDIT_INDEX, editIndex)
        startActivity(intent)
    }

    private fun confirmDelete(index: Int) {
        val room = roomController.getRoom(index) ?: return

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete_room, null)
        val tvContent = dialogView.findViewById<TextView>(R.id.tvContent)
        val btnCancel = dialogView.findViewById<TextView>(R.id.btnCancel)
        val btnSave = dialogView.findViewById<TextView>(R.id.btnSave)

        tvContent.text = "Bạn có chắc muốn xóa ${room.roomCode} - ${room.roomName}?"

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        btnCancel.setOnClickListener { dialog.dismiss() }
        btnSave.setOnClickListener {
            roomController.deleteRoom(index)
            renderRooms()
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun renderRooms() {
        val rooms = roomController.getRooms()
        roomAdapter.submitList(rooms)

        tvEmpty.visibility = if (rooms.isEmpty()) View.VISIBLE else View.GONE
        rvRooms.visibility = if (rooms.isEmpty()) View.GONE else View.VISIBLE
    }
}