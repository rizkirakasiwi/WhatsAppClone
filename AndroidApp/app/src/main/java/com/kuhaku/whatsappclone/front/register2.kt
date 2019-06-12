package com.kuhaku.whatsappclone.front

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuhaku.whatsappclone.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register1.view.*
import kotlinx.android.synthetic.main.fragment_register2.view.*


class register2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register2, container, false)
        back2(view)
        next2(view)
        return view
    }


    fun back2(view:View){
        view.txt_back2?.setOnClickListener {
            activity?.viewPagerRegister?.setCurrentItem(0, true)
        }
    }

    fun next2(view:View){
        view.txt_next2?.setOnClickListener {
            activity?.viewPagerRegister?.setCurrentItem(2, true)
        }
    }

}
