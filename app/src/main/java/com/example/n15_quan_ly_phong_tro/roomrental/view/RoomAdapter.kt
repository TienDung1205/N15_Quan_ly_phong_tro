package com.example.n15_quan_ly_phong_tro.roomrental.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class RoomAdapter(
    private val onEditClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
    private val onItemClick: (Int) -> Unit
) : ListAdapter<Room, RoomAdapter.RoomViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view, onEditClick, onDeleteClick, onItemClick)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RoomViewHolder(
        itemView: View,
        private val onEditClick: (Int) -> Unit,
        private val onDeleteClick: (Int) -> Unit,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvRoomCode: TextView = itemView.findViewById(R.id.tvRoomCode)
        private val tvRoomName: TextView = itemView.findViewById(R.id.tvRoomName)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val tvTenant: TextView = itemView.findViewById(R.id.tvTenant)
        private val ivEdit: ImageView = itemView.findViewById(R.id.ivEdit)
        private val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)

        fun bind(room: Room) {
            tvRoomCode.text = room.roomCode
            tvRoomName.text = room.roomName

            val money = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(room.rentPrice)
            tvPrice.text = "Giá thuê: $money"

            tvStatus.text = room.status.label
            if (room.status == RoomStatus.AVAILABLE) {
                tvStatus.setBackgroundResource(R.drawable.bg_status_available)
                tvTenant.text = "Người thuê: Chưa có"
            } else {
                tvStatus.setBackgroundResource(R.drawable.bg_status_rented)
                tvTenant.text = "Người thuê: ${room.tenantName} - ${room.tenantPhone}"
            }

            itemView.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) onItemClick(pos)
            }

            ivEdit.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) onEditClick(pos)
            }
            ivDelete.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) onDeleteClick(pos)
            }

            itemView.setOnLongClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onDeleteClick(pos)
                }
                true
            }
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Room>() {
            override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
                return oldItem.roomCode == newItem.roomCode
            }

            override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
                return oldItem == newItem
            }
        }
    }
}