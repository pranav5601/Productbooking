package com.example.pranav.productbooking.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.example.pranav.productbooking.R
import com.example.pranav.productbooking.adapters.AdapterItemList
import com.example.pranav.productbooking.helper.Constant
import com.example.pranav.productbooking.model.Items
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.frag_display_items.*

class FragDisplayItems : FragBase() {
    var items = Items()
    var itemList= ArrayList<Items>()
    lateinit var mAdapter: AdapterItemList

    val mItemRef= FirebaseDatabase.getInstance().reference.child(Constant.ITEMS)
    override fun getRecourseLayout(): Int = R.layout.frag_display_items

    override fun setUpView() {
        showLoader()
        showBack(true)
        setBarTitle("Items")
        setupRcv()

    }

    private fun setupRcv() {
        val layoutManager = LinearLayoutManager(baseContext, LinearLayout.VERTICAL, false)
        rcvDisplayItem.layoutManager = layoutManager
        mAdapter =   AdapterItemList(baseContext, itemList,{ } )
        rcvDisplayItem.adapter = mAdapter
        mItemRef.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                closeLoader()
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                closeLoader()

            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                closeLoader()

            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, text: String?) {
                items = dataSnapshot.getValue<Items>(Items::class.java)!!
                itemList.add(items)
                mAdapter.notifyDataSetChanged()
                closeLoader()

            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                closeLoader()

            }

        })

    }


}
