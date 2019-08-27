package com.example.pranav.productbooking.fragments


import android.util.Log
import com.example.pranav.productbooking.R
import com.example.pranav.productbooking.helper.Constant
import com.example.pranav.productbooking.model.Items
import com.example.pranav.productbooking.model.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.frag_user_details.*

class FragUserDetails : FragBase() {

    private var user = User()
    private var items = Items()
    private var itemList = ArrayList<Items>()
    override fun getRecourseLayout(): Int = R.layout.frag_user_details
    override fun setUpView() {
        showBack(true)
        setItemData()
        setData()
    }

    private fun setItemData() {
        val itemRef = FirebaseDatabase.getInstance().reference.child(Constant.USER).child(user.user_id)

        itemRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {

            }

            override fun onDataChange(data: DataSnapshot?) {
                if (data?.child(Constant.ITEMS)?.exists()!!) {
                    itemRef.child(Constant.ITEMS).addChildEventListener(object : ChildEventListener {
                        override fun onCancelled(p0: DatabaseError?) {

                        }

                        override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

                        }

                        override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

                        }

                        override fun onChildAdded(data: DataSnapshot?, title: String?) {
                            items = data?.getValue<Items>(Items::class.java)!!
                            itemList.add(items)
                            Log.e("itemList", itemList.toString())
                            txtUserOrder.text = "" + itemList.size
                        }
                        override fun onChildRemoved(p0: DataSnapshot?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                    })
                }

            }

        })

    }

    private fun setData() {
        txtShowUserName.text = user.user_name
        txtShowUserEmail.text = user.email_id
        txtShowUserAddress.text = "${user.address_1},${user.address_2}"
        txtShowUserCity.text = user.city
        txtShowUserNumber.text = user.mobile

    }


    fun getUserData(user: User) {
        this.user = user
    }


}
