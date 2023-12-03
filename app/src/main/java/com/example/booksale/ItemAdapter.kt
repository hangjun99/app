package com.example.booksale

import android.R
import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import javax.xml.validation.ValidatorHandler


abstract class ItemAdapter(var context: Context, var items: ArrayList<ClipData.Item>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.activity_list_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as VH
        val item = items[position]
        //vh.TitleView.setText(item.BookName())
        //vh.tvDate.setText(item.getDate())
        //vh.tvMsg.setText(item.getMsg())
        //vh.

    }
    override fun getItemCount(): Int {
        return items.size
    }

    internal inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var TitleView: TextView
        //var tvDate: TextView
        //var tvMsg: TextView
        //var iv: TextView

        init {
            //TitleView = itemView.findViewById<TextView>(R.id.titleTextView)
            //tvDate = itemView.findViewById<TextView>(R.id.tv_date)
            //tvMsg = itemView.findViewById<TextView>(R.id.tv_msg)
            //iv = itemView.findViewById<TextView>(R.id.iv)
        }
    }
}



