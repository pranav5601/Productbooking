package com.example.pranav.productbooking.fragments

import com.example.pranav.productbooking.R
import com.example.pranav.productbooking.helper.Constant
import com.example.pranav.productbooking.model.Items
import com.example.pranav.productbooking.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.swyft.app.helper.Utils
import kotlinx.android.synthetic.main.frag_item_details.*

class FragItemDetails : FragBase() {

    private var items= Items()
    private var user = User()
    private var mUserRef = FirebaseDatabase.getInstance().reference.child(Constant.USER)

    override fun getRecourseLayout(): Int = R.layout.frag_item_details

    override fun setUpView() {
        setBarTitle("Item Details")
        getUserInfo(items.from_id)
        showLoader()

    }

    private fun setData(items: Items, user: User) {
        txtShowItemName.text = items.item_name
        txtShowItemPrice.text = items.item_mrp
        txtShowItemSize.text = items.item_size
        txtShowItemQty.text = items.item_qty
        txtSenderName.text = user.user_name

    }

    private fun getUserInfo(from_id: String) {

        mUserRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError?) {
                closeLoader()
                Utils.showToast(baseContext,error?.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.child(from_id).exists()){
                 val userData = dataSnapshot.child(from_id).getValue(User::class.java)
                userData?.let { setData(items, it) }
                closeLoader()
            }

            }

        })

    }

    fun itemInfo(items: Items) {
        this.items = items
    }

    override fun onDetach() {
        super.onDetach()
        setBarTitle("Items")
    }
}
