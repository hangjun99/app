package com.example.booksale

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ChatItemActivity : RecyclerView.Adapter<ChatItemActivity.ViewHolder>() {
    private var items = ArrayList<Product>()
    var listener: OnProductItemClicklistener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = inflate.inflate(R.layout.item_chat, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Product = items[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Product) {
        items.add(item)
    }

    fun getItem(position: Int):Product {
        return items[position]
    }

    fun onItemClick(holder: ViewHolder?, view: View?, position: Int) {
        if (listener != null) {
            listener!!.onItemClick(holder, view, position)
        }
    }

    fun setOnItemClickListener(listener: OnProductItemClicklistener?) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.chat_textview_title)
        private val lastChat: TextView = itemView.findViewById(R.id.chat_item_textview_lastmessage)
        lateinit var detailData: Array<String>
        fun setItem(item: Product){
            titleTextView.text = item.title
            lastChat.text = item.lastMsg
            detailData = item.detailData
        }

        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "성공", Toast.LENGTH_SHORT).show()
                val chatRoom = detailData[0]
                val UserInd = detailData[4]
                val Nickname = detailData[5]
                val opponentNickname = detailData[6]
                val intent = Intent(itemView.context, ChatlistActivity::class.java)
                intent.putExtra("chatRoom", chatRoom)
                intent.putExtra("UserInd", UserInd)
                intent.putExtra("Nickname", Nickname)
                intent.putExtra("opponentNickname", opponentNickname)
                itemView.context.startActivity(intent)
            }
        }


    }
    data class Product(
        val title: String,
        val lastMsg: String,
        val detailData: Array<String>
    )
    open class OnProductItemClicklistener {
        open fun onItemClick(holder: ViewHolder?, view: View?, position: Int) {}

    }
}