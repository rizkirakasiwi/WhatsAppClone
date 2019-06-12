package com.kuhaku.whatsappclone.front


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuhaku.whatsappclone.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.android.synthetic.main.fragment_register1.*
import kotlinx.android.synthetic.main.fragment_register1.view.*


class register1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register1, container, false)
        back1(view)
        next1(view)
        return view
    }


    fun back1(view:View){
        view.txt_back1?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    fun next1(view:View){
        view.txt_next1?.setOnClickListener {
            activity?.viewPagerRegister?.setCurrentItem(1, true)
        }
    }






}
