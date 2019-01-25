package com.example.pranav.productbooking.adapters

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pranav.productbooking.R
import com.example.pranav.productbooking.model.Items
import kotlinx.android.synthetic.main.cell_item_list.view.*

class AdapterItemList(val context: Activity,val itemList: ArrayList<Items>,val listener: (Items) -> Unit): RecyclerView.Adapter<AdapterItemList.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.cell_item_list, parent, false)


        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(itemList.get(position),context,listener)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(items: Items, activity: Activity, listener: (Items) -> Unit) = with(itemView) {

            itemView.setOnClickListener { listener(items) }

            txtItemName.text = items.item_name
            txtItemPrice.text = items.item_mrp

        }
    }
}