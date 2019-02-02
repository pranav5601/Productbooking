package com.example.pranav.productbooking.fragments


import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.pranav.productbooking.R
import com.example.pranav.productbooking.adapters.AdapterItemList
import com.example.pranav.productbooking.adapters.AdapterUserList
import com.example.pranav.productbooking.helper.Constant
import com.example.pranav.productbooking.model.User
import com.google.firebase.database.*
import com.swyft.app.helper.Utils
import kotlinx.android.synthetic.main.frag_display_items.*
import kotlinx.android.synthetic.main.frag_user_list.*


class FragUserList : FragBase() {

    private val mDatabase = FirebaseDatabase.getInstance()
    private val userRef = mDatabase.reference.child(Constant.USER)
    private var user = User()
    private val userList = ArrayList<User>()

    lateinit var mAdapter: AdapterUserList
    override fun getRecourseLayout(): Int = R.layout.frag_user_list

    override fun setUpView() {

        setBarTitle("All Users")
        setFirebaseData()

    }

    private fun setFirebaseData() {

        val layoutManager = LinearLayoutManager(baseContext, LinearLayout.VERTICAL, false)
        rcvDisplayUser.layoutManager = layoutManager
        mAdapter =   AdapterUserList(baseContext, userList) {
            val fragUserDetails= FragUserDetails()
            fragUserDetails.getUserData(it)
            Utils.addFragmentToActivity(baseContext.supportFragmentManager,fragUserDetails,R.id.fragMainContainer)
        }
        rcvDisplayUser.adapter = mAdapter
        userRef.keepSynced(true)
        userRef.addChildEventListener(object: ChildEventListener{
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildAdded(data: DataSnapshot?, p1: String?) {
                user = data?.getValue<User>(User::class.java)!!
                if(getUserId() != user.user_id){
                    userList.add(user)
                }
                mAdapter.notifyDataSetChanged()

            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

            override fun onCancelled(error: DatabaseError?) {

            }


        })

    }


}
