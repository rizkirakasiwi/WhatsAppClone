package com.kuhaku.whatsappclone.front

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.kuhaku.whatsappclone.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register1.*

class RegisterActivity : AppCompatActivity() {

    val TAG = "RegisterActivity"
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewPagerRegister.adapter = adapter(supportFragmentManager)

        txt_next.setOnClickListener {
            count++
            Log.i(TAG, "count = $count")
            if(count<3){
                Log.i(TAG, "count = $count")
                viewPagerRegister.setCurrentItem(count, true)
            }else{
                Log.i(TAG, "Register Finish")
                count=2
            }

        }

        txt_back.setOnClickListener {
            Log.i(TAG, "count back = $count")
            count--
            if(count>=0){
                viewPagerRegister.setCurrentItem(count, true)
                Log.i(TAG, "count back = $count")
            }else{
                onBackPressed()
                Log.i(TAG, "count back = $count")
            }
        }

    }
}


class adapter(fragmenManager:FragmentManager):FragmentPagerAdapter(fragmenManager){
    val fragment = listOf(register1(), register2(), register3())

    override fun getCount(): Int {
        return fragment.size
    }

    override fun getItem(p0: Int): Fragment {
        return fragment[p0]
    }
}
