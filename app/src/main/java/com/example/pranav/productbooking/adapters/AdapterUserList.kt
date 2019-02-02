package com.example.pranav.productbooking.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pranav.productbooking.R
import com.example.pranav.productbooking.model.Items
import com.example.pranav.productbooking.model.User
import kotlinx.android.synthetic.main.cell_item_list.view.*
import kotlinx.android.synthetic.main.cell_user_list.view.*


class AdapterUserList(val context: Activity, val userList: ArrayList<User>, val listener: (User) -> Unit): RecyclerView.Adapter<AdapterUserList.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.cell_user_list, parent, false)


        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList.get(position),context,listener)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(users: User, activity: Activity, listener: (User) -> Unit) = with(itemView) {

            itemView.setOnClickListener { listener(users) }

            txtUserName.text = users.user_name
            txtUserEmail.text = users.email_id
            txtUserNumber.text = users.mobile

        }
    }
}