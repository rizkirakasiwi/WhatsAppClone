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
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.kuhaku.whatsappclone.R
import com.kuhaku.whatsappclone.model.user
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register1.*
import kotlinx.android.synthetic.main.fragment_register2.*
import kotlinx.android.synthetic.main.fragment_register3.*
import kotlinx.android.synthetic.main.fragment_register3.view.*
import okhttp3.*
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


class RegisterActivity : AppCompatActivity(), register1.sendData{


    val TAG = "RegisterActivity"

    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        viewPagerRegister.adapter = adapter(supportFragmentManager)
    }


    override fun send(data: String) {
        Log.i(TAG, "data string $data")
        register3().displayRecieveData(data)
    }


    fun uploadImage(username:String, password: String){
        val url = getString(R.string.urlUpload)
        val MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg")

        val bitmap = (img_register.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val bitmapByteArray = baos.toByteArray()
        val file = Base64.encodeToString(bitmapByteArray,Base64.DEFAULT)

        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("upload","image", RequestBody.create(MEDIA_TYPE_JPEG, file))
            .build()

        val request = Request.Builder()
            .url(url)
            .post(multipartBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "upload image failur ${e.message}")
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val response = response.body?.string()

                Log.i(TAG, "Response : $response")
                postData(response)

            }
        })
    }



    fun postData(imageName:String?){
        val url = getString(R.string.urlUser)
        Log.i(TAG,"on postData imageName = $imageName")

        val username = edt_usernameRegister?.text.toString()
        val password = edt_passwordRegister?.text.toString()
        val nama = edt_namaRegister?.text.toString()
        val noHp = edt_PhoneNumberRegister?.text.toString()
        val email = edt_EmailRegister?.text.toString()
        val caption = edt_captionRegister?.text.toString()
        val status = "Offline"
        val waktuOnline = "Never"

        if(username == "null" && password == "null"){
            Log.i(TAG, "username null")
        }else {

            val users = arrayOf(
                user(
                    "", username, password, nama,
                    email, noHp, caption, status, waktuOnline, imageName
                )
            ).toList()

            val json = Gson().toJson(users)

            Log.i(TAG, "json $json")


            val JSON = MediaType.get("application/json; charset=utf-8")

            val body = RequestBody.create(JSON, json)
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()
            client.newCall(request).enqueue(object : Callback {
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
