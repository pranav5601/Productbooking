package com.example.pranav.productbooking.fragments

import com.example.pranav.productbooking.R
import com.example.pranav.productbooking.helper.Constant
import com.example.pranav.productbooking.helper.PrefKeys
import com.example.pranav.productbooking.model.Items
import com.google.firebase.database.FirebaseDatabase
import com.pixplicity.easyprefs.library.Prefs
import com.swyft.app.helper.Utils
import kotlinx.android.synthetic.main.frag_create_items.*

class FragCreateItems : FragBase() {

    private val mDatabase = FirebaseDatabase.getInstance()
    private val itemRef = mDatabase.reference.child(Constant.ITEMS)
    private val userRef = mDatabase.reference.child(Constant.USER)
    override fun getRecourseLayout(): Int = R.layout.frag_create_items

    override fun setUpView() {
        initClick()
        setBarTitle("Add Items")
        showBack(true)

    }

    private fun initClick() {
        btnAddItem.setOnClickListener {
            if (validateData()) {
                showLoader()
                val items = Items()
                items.from_id = Prefs.getString(PrefKeys.USER_ID,"")
                items.item_name = edtItemName.text.toString()
                items.item_qty = edtItemQty.text.toString()
                items.item_size = edtItemSize.text.toString()
                items.item_mrp = edtItemMrp.text.toString()+"/-"

                itemRef.push().setValue(items).addOnCompleteListener(baseContext){task ->
                   if (task.isSuccessful) {

                       if(getUserData().status == "1"){
                           addData(items)
                       }else{
                           clearData()
                           closeLoader()
                           Utils.showToast(baseContext,"Item Added")

                       }
                   }else {
                       closeLoader()
                       Utils.showToast(baseContext,task.exception?.message)
                   }

                }
            }
        }
    }

    private fun addData(items: Items) {
        userRef.child(getUserId()).child(Constant.ITEMS).push().setValue(items).addOnCompleteListener (baseContext){task ->
            if (task.isSuccessful){
                clearData()
                closeLoader()
                Utils.showToast(baseContext,"Item Added")
            }else{
                Utils.showToast(baseContext,task.exception?.message)
                closeLoader()
            }
        }

    }

    private fun clearData() {
        edtItemName.setText("")
        edtItemQty.setText("")
        edtItemSize.setText("")
        edtItemMrp.setText("")

    }

    private fun validateData(): Boolean {

        if (!Utils.isStringValid(edtItemName.text.toString())) {
            Utils.yoyoAnimate(edtItemName)
            return false
        }
        if (!Utils.isStringValid(edtItemQty.text.toString())) {
            Utils.yoyoAnimate(edtItemQty)
            return false
        }
        if (!Utils.isStringValid(edtItemSize.text.toString())) {
            Utils.yoyoAnimate(edtItemSize)
            return false
        }
        if (!Utils.isStringValid(edtItemMrp.text.toString())) {
            Utils.yoyoAnimate(edtItemMrp)
            return false
        }

        return true
    }

}
