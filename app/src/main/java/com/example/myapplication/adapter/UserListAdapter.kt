package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserListAdapter(
    private val userList: List<User>,
    private val onUserClick: (User) -> Unit
) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user, onUserClick)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.tvUsername)
        private val emailTextView: TextView = itemView.findViewById(R.id.tvEmail)
        private val telNumberTextView: TextView = itemView.findViewById(R.id.tvTelNumber)
        private val addressTextView: TextView = itemView.findViewById(R.id.tvAddress)
        private val statusTextView: TextView = itemView.findViewById(R.id.tvStatus) // Menambahkan TextView untuk status

        fun bind(user: User, onUserClick: (User) -> Unit) {
            usernameTextView.text = user.username
            emailTextView.text = user.email
            telNumberTextView.text = user.telNumber
            addressTextView.text = user.address
            statusTextView.text = user.status // Menampilkan status pada TextView

            itemView.setOnClickListener { onUserClick(user) }
        }
    }
}
