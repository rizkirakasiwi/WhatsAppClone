package com.kuhaku.whatsappclone.front


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuhaku.whatsappclone.R
import com.kuhaku.whatsappclone.model.database
import com.kuhaku.whatsappclone.model.dbHelper
import com.kuhaku.whatsappclone.model.db_user
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register1.*
import kotlinx.android.synthetic.main.fragment_register1.view.*
import kotlinx.android.synthetic.main.fragment_register3.view.*
import org.jetbrains.anko.db.insert


class register1 : Fragment() {

    lateinit var sendMessage:sendData
    val TAG = "RegisterFragment1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register1, container, false)
        view.txt_next1.setOnClickListener {
            activity?.viewPagerRegister?.currentItem = 1
            sendMessage.send(view.edt_usernameRegister?.text.toString())
        }

        view.txt_back1.setOnClickListener {
            activity?.onBackPressed()
        }
        return view
    }


    interface sendData{
        fun send(data:String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        Log.i(TAG, "on Attach")

        try {
            sendMessage = activity as sendData
        }catch (e:ClassCastException){

        }
    }


    fun saveUser(view: View){
        activity?.database?.use{
            insert(db_user.TABLE_USER,
                db_user.USERNAME to view.edt_usernameRegister.text.toString(),
                db_user.PASSWORD to view.edt_passwordRegister.text.toString()
            )
        }
    }

}

