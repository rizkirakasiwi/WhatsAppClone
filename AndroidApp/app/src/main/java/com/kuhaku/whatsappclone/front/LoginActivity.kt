package com.kuhaku.whatsappclone.front

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.gson.GsonBuilder
import com.kuhaku.whatsappclone.R
import com.kuhaku.whatsappclone.model.user
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    val TAG = "LoginActivity"



    lateinit var usernameArray : ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txt_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }



        textInputLayoutUsernameLogin.editText?.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayoutUsernameLogin.isErrorEnabled = false
                Log.i(TAG,"onTextChanged username login")
            }
        })

        textInputLayoutPasswordLogin.editText?.addTextChangedListener(object:TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayoutPasswordLogin.isErrorEnabled = false
                Log.i(TAG, "onTextChanged password login")
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })


        btn_login.setOnClickListener{
            btn_login.text = "Loading.."

            Handler().postDelayed({
                val username = edt_usernameLogin.text.toString()
                val password = edt_passwordLogin.text.toString()

                getUserMethod(username, password)
            },2000)

            Log.i(TAG, "on btn_login")

        }
    }





    fun getUserMethod(username:String, password:String){
        Log.i(TAG, "on getUserMethod")
        val url = getString(R.string.urlUser)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        usernameArray = arrayListOf()

        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "LoginActivity:getUserMethod:client.newCall:onFailur : " +e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gsonBuilder = GsonBuilder().create()
                val users = gsonBuilder.fromJson(body, Array<user>::class.java).toList()

                for(i in users){
                    if(i.username == username){
                        if(i.password == password){
                            Log.i(TAG, "Login berhasil!")
                            runOnUiThread(object:Runnable{
                                override fun run() {
                                    btn_login.text = "Login"
                                }
                            })
                        }else{
                            runOnUiThread(object:Runnable{
                                override fun run() {
                                    textInputLayoutPasswordLogin.error = "Password salah!"
                                }
                            })
                            Log.i(TAG, "Password salah")
                            runOnUiThread(object:Runnable{
                                override fun run() {
                                    btn_login.text = "Login"
                                }
                            })
                        }
                    }else{
                        usernameArray.add("username salah")
                    }
                }

                if(usernameArray.count() == users.count()){
                    Log.i(TAG,"Username salah")
                    runOnUiThread(object:Runnable{
                        override fun run() {
                            btn_login.text = "Login"
                            textInputLayoutUsernameLogin.error = "Username salah!"
                        }
                    })
                }else{
                    textInputLayoutUsernameLogin.isErrorEnabled = false
                    Log.i(TAG, "username benar")
                }
            }
        })
    }







}
