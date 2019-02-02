package com.example.pranav.productbooking.fragments

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout

import com.example.pranav.productbooking.R
import com.example.pranav.productbooking.adapters.AdapterItemList
import com.example.pranav.productbooking.helper.Constant
import com.example.pranav.productbooking.model.Items
import com.google.firebase.database.*
import com.swyft.app.helper.Utils
import kotlinx.android.synthetic.main.frag_display_items.*

class FragDisplayItems : FragBase() {
    var items = Items()
    val itemList= ArrayList<Items>()
    private var mDatabase = FirebaseDatabase.getInstance()
    lateinit var mAdapter: AdapterItemList
    var fragHome: String? = null

    private val mItemRef= mDatabase.reference
    private val mUserItemRef = mDatabase.reference.child(Constant.USER).child(getUserId())

    override fun getRecourseLayout(): Int = R.layout.frag_display_items

    override fun setUpView() {
        showBack(true)
        setBarTitle("Items")
//        showLoader()
        if(getUserData().status == "1"){
            setRcvForUserItem()
        }else{
            setupRcvForAllItems()
        }




        initClicks()
    }

    private fun setRcvForUserItem() {
        val layoutManager = LinearLayoutManager(baseContext, LinearLayout.VERTICAL, false)
        rcvDisplayItem.layoutManager = layoutManager
        mAdapter =   AdapterItemList(baseContext, itemList) {
            val fragItemDetails= FragItemDetails()
            fragItemDetails.itemInfo(it)
            Utils.addFragmentToActivity(baseContext.supportFragmentManager,fragItemDetails,R.id.fragMainContainer)
        }
        rcvDisplayItem.adapter = mAdapter
        mUserItemRef.keepSynced(true)
        mUserItemRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                closeLoader()

            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if(dataSnapshot?.child(Constant.ITEMS)?.exists()!!){
//                    txtItemIndicator.visibility = View.GONE
                    mUserItemRef.child(Constant.ITEMS).addChildEventListener(object: ChildEventListener{
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
                }else{
                    closeLoader()
                    txtItemIndicator.visibility = View.VISIBLE
                }

            }

        })

    }



    private fun initClicks() {

    }

    private fun setupRcvForAllItems() {
        val layoutManager = LinearLayoutManager(baseContext, LinearLayout.VERTICAL, false)
        rcvDisplayItem.layoutManager = layoutManager
        mAdapter =   AdapterItemList(baseContext, itemList) {
            val fragItemDetails= FragItemDetails()
            fragItemDetails.itemInfo(it)
            Utils.addFragmentToActivity(baseContext.supportFragmentManager,fragItemDetails,R.id.fragMainContainer)
        }
        rcvDisplayItem.adapter = mAdapter
        mItemRef.keepSynced(true)
        mItemRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

                closeLoader()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
            if(dataSnapshot?.child(Constant.ITEMS)?.exists()!!){
//                txtItemIndicator.visibility = View.GONE
                mItemRef.child(Constant.ITEMS).addChildEventListener(object: ChildEventListener{
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
            }else{
                closeLoader()
                txtItemIndicator.visibility = View.VISIBLE
            }
            }

        })

    }


}
