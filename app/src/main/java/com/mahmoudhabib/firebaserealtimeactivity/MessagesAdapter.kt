package com.mahmoudhabib.firebaserealtimeactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class MessagesAdapter(private val context: Context, private val itemList: List<Message>) :
    RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageIdTextView: TextView = itemView.findViewById(R.id.message_id)
        val messageTextView: TextView = itemView.findViewById(R.id.message_text)
        val messageTimeTextView: TextView = itemView.findViewById(R.id.message_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = itemList[position]
        holder.messageIdTextView.text = currentMessage.id.toString()
        holder.messageTextView.text = currentMessage.content

        val messageTime = SimpleDateFormat("hh : mm aaa", Locale.getDefault()).format(Date(currentMessage.time))

        holder.messageTimeTextView.text = messageTime
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}