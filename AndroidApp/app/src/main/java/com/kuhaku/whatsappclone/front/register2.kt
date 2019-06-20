package com.kuhaku.whatsappclone.front

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.kuhaku.whatsappclone.R
import com.kuhaku.whatsappclone.model.database
import com.kuhaku.whatsappclone.model.db_user
import com.kuhaku.whatsappclone.model.db_user2
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register1.*
import kotlinx.android.synthetic.main.fragment_register1.view.*
import kotlinx.android.synthetic.main.fragment_register2.*
import kotlinx.android.synthetic.main.fragment_register2.view.*
import okhttp3.internal.addHeaderLenient
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.update
import java.lang.ClassCastException


class register2 : Fragment()  {

    val TAG = "RegisterFragment2"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register2, container, false)

        return view
    }

}
