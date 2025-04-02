package com.example.androidwebsocketchatlibrary.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidwebsocketchatlibrary.R

data class ChatMessage(
    val text: String,
    val isSentByUser: Boolean
)

class ChatAdapter : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(DiffCallback()) {

    private val VIEW_TYPE_USER = 1
    private val VIEW_TYPE_SERVER = 2

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isSentByUser) VIEW_TYPE_USER else VIEW_TYPE_SERVER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_USER) {
            val view = inflater.inflate(R.layout.item_user_message, parent, false)
            UserMessageViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_server_message, parent, false)
            ServerMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder) {
            is UserMessageViewHolder -> holder.bind(message)
            is ServerMessageViewHolder -> holder.bind(message)
        }
    }

    fun submitNewMessage(message: ChatMessage, recyclerView: RecyclerView) {
        val newList = currentList.toMutableList().apply { add(message) }
        submitList(newList) {
            recyclerView.scrollToPosition(newList.size - 1)
        }
    }

    inner class UserMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv: TextView = view.findViewById(R.id.tvUserMessage)
        fun bind(msg: ChatMessage) { tv.text = msg.text }
    }

    inner class ServerMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv: TextView = view.findViewById(R.id.tvServerMessage)
        fun bind(msg: ChatMessage) { tv.text = msg.text }
    }

    class DiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(old: ChatMessage, new: ChatMessage) = old === new
        override fun areContentsTheSame(old: ChatMessage, new: ChatMessage) = old == new
    }
}
