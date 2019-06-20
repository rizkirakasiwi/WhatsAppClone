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
import org.jetbrains.anko.db.insert


class register1 : Fragment() {

    val TAG = "RegisterFragment1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register1, container, false)
        val password = view.edt_passwordRegister.text.toString()
        val confirmPassword = view.edt_passwordConfirmedRegister.text.toString()

        return view
    }


}

