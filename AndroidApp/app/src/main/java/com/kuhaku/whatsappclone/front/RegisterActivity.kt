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
import android.support.v4.view.ViewPager
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
import okhttp3.*
import java.io.IOException


class RegisterActivity : AppCompatActivity(){


    val TAG = "RegisterActivity"

    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        txt_next.text = "Next"
        txt_back.text = "Login"

        viewPagerRegister.setOnTouchListener { v, event ->
            true
        }

        viewPagerRegister.adapter = adapter(supportFragmentManager)
        txt_next.setOnClickListener {
            count+=1
            if(count == 1) {
                txt_next.text = "Finish"
                txt_back.text = "Back"
            }else if (count > 1) {
                postData()
                count = 1
            }else{
                Log.i(TAG, "count $count")
            }

            viewPagerRegister.currentItem = count

        }

        txt_back.setOnClickListener {
            count -= 1
            if(count == 0){
                txt_back.text = "Login"
                txt_next.text = "Next"
            }else if(count < 0){
                onBackPressed()
            }else{
                Log.i(TAG, "count $count")
            }

            viewPagerRegister.currentItem = count
        }
    }


    fun postData(){
        val url = getString(R.string.urlUser)

        val username = edt_usernameRegister?.text.toString()
        val password = edt_passwordRegister?.text.toString()
        val nama = edt_namaRegister?.text.toString()
        val noHp = edt_PhoneNumberRegister?.text.toString()
        val email = edt_EmailRegister?.text.toString()
        val caption = "Nothing"
        val status = "Offline"
        val waktuOnline = "Never"
        val imageName = "Nothing"

        if(username == "null" && password == "null"){
            Log.i(TAG, "username null")
        }else {

            val users = user(
                    "Nothing", username, password, nama,
                    email, noHp, caption, status, waktuOnline, imageName
                )


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




class adapter(fragmenManager:FragmentManager):FragmentPagerAdapter(fragmenManager) {
    val fragment = listOf(register1(), register2())

    override fun getCount(): Int {
        return fragment.size
    }

    override fun getItem(p0: Int): Fragment {
        return fragment[p0]
    }


}










//    fun uploadImage(username:String, password: String){
//        val url = getString(R.string.urlUpload)
//        val MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg")
//
//        val bitmap = (img_register.drawable as BitmapDrawable).bitmap
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val bitmapByteArray = baos.toByteArray()
//        val file = Base64.encodeToString(bitmapByteArray,Base64.DEFAULT)
//
//        val multipartBody = MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart("upload","image", RequestBody.create(MEDIA_TYPE_JPEG, file))
//            .build()
//
//        val request = Request.Builder()
//            .url(url)
//            .post(multipartBody)
//            .build()
//
//        OkHttpClient().newCall(request).enqueue(object:Callback{
//            override fun onFailure(call: Call, e: IOException) {
//                Log.i(TAG, "upload image failur ${e.message}")
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val response = response.body?.string()
//
//                Log.i(TAG, "Response : $response")
//                postData(response)
//
//            }
//        })
//    }
