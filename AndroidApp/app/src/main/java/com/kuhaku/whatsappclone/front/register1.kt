package com.kuhaku.whatsappclone.front


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.kuhaku.whatsappclone.R
import com.kuhaku.whatsappclone.model.Username
import kotlinx.android.synthetic.main.fragment_register1.*
import kotlinx.android.synthetic.main.fragment_register1.view.*
import kotlinx.android.synthetic.main.fragment_register1.view.edt_passwordRegister
import okhttp3.*
import org.jetbrains.anko.support.v4.runOnUiThread
import java.io.IOException


class register1 : Fragment() {

    val TAG = "RegisterFragment1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register1, container, false)

        view.edt_usernameRegister.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                runOnUiThread {
                    view.textInputLayouUsernametRegister.isErrorEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        view.edt_passwordConfirmedRegister.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordCheck(view)
            }
        })


        view.edt_passwordRegister.addTextChangedListener(
            object:TextWatcher{
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    runOnUiThread {
                        textInputLayoutPasswordRegister.isErrorEnabled = false
                    }
                }
            }
        )
        return view
    }


    fun passwordCheck(view: View){
        val passwordA = edt_passwordRegister.text.toString()
        val passwordB = edt_passwordConfirmedRegister.text.toString()

        if(passwordA != passwordB){
            Log.i(TAG,"Password not valid")
            runOnUiThread {
                view.textInputLayoutConfirmedPasswordRegister.error = "Password is not valid"
            }

        }else{
            runOnUiThread {
                view.textInputLayoutConfirmedPasswordRegister.isErrorEnabled = false
            }
        }
    }


}

