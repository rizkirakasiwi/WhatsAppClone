package com.kuhaku.whatsappclone.front

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import com.kuhaku.whatsappclone.R
import com.kuhaku.whatsappclone.model.user
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register1.*
import kotlinx.android.synthetic.main.fragment_register2.*
import kotlinx.android.synthetic.main.fragment_register3.*
import okhttp3.*
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    val TAG = "RegisterActivity"
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewPagerRegister.adapter = adapter(supportFragmentManager)

    }

    fun next(){
        txt_next1.setOnClickListener {
            Log.i(TAG,"txt_Next was clicked")

//
//            val email = edt_EmailRegister?.text.toString()
//            val name = edt_namaRegister.text.toString()
//            val noHp = edt_PhoneNumberRegister.text.toString()
//            val username = edt_usernameRegister.text.toString()
//            val confirmationPass = edt_passwordConfirmedRegister.text.toString()
//            val caption = edt_captionRegister?.text.toString()
//            val pass = edt_passwordRegister?.text.toString()


            count++
            Log.i(TAG, "count = $count")
            if(count==1){
                Log.i(TAG, "count = $count")
                viewPagerRegister.setCurrentItem(count, true)
            }else{
                val bitmap = (img_register.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val file = baos.toByteArray()
                val file2 = Base64.encodeToString(file, Base64.DEFAULT)

                uploadImage(file2)
                count = 2
                Log.i(TAG, "Register Finish")
            }

        }
    }



    fun uploadImage(image:String){
        val MEDIA_TYPE_PNG = MediaType.parse("image/png")
        val client = OkHttpClient()
        val url = getString(R.string.urlUpload)
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("upload", "upload", RequestBody.create(MEDIA_TYPE_PNG, image))
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, e.message)
                e.stackTrace
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(TAG,"response ${response.body?.string()}")
            }
        })
    }



    fun postData(url:String){
        Log.i(TAG,"on postData")
        val username = edt_usernameRegister.text.toString()
        val password = edt_passwordRegister.text.toString()
        val nama = edt_namaRegister.text.toString()
        val noHp = edt_PhoneNumberRegister.text.toString()
        val email = edt_EmailRegister.text.toString()
        val caption = edt_captionRegister.text.toString()
        val status = "Offline"
        val waktuOnline = "Never"

        val users = arrayOf(user("",username, password, nama, email, noHp, caption, status, waktuOnline, ""))
        val json = Gson().toJson(users)


        val JSON = MediaType.parse("application/json; charset=utf-8")
        val body = RequestBody.create(JSON, json)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "On RegisterActivity onFun postData onFailur ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val message = response.body?.string()
                Log.i(TAG, message)
            }
        })



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
