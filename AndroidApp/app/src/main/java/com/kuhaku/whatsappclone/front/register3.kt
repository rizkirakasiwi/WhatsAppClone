package com.kuhaku.whatsappclone.front

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.text.Html
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuhaku.whatsappclone.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register3.*
import kotlinx.android.synthetic.main.fragment_register3.view.*
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.net.URI

class register3 : Fragment() {


    val TAG = "RegisterFragment3"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register3, container, false)

        view.txt_next3?.setOnClickListener {
            upload(view)
        }


        view.img_register?.setOnClickListener {
            Log.i(TAG, "on img register clicked")
            ambilGambar()
        }



        back(view)

        return view
    }



    val CAMERA_REQUEST_CODE = 1
    var path:Uri?=null

    fun ambilGambar(){
        Log.i(TAG, "on ambilGambar function")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==CAMERA_REQUEST_CODE){


            val imageBitmap = data?.extras?.get("data") as Bitmap

            img_register.setImageBitmap(imageBitmap)


        }
    }


    fun upload(view:View){
        Log.i(TAG, "onUpload: $path")

        val url = getString(R.string.urlUpload)
        val bitmap = (view.img_register.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
        val bitmapArray = byteArrayOutputStream.toByteArray()

        val file = Base64.encodeToString(bitmapArray, Base64.DEFAULT)

        val mediaType = MediaType.parse("image/png")

        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("upload", "image", RequestBody.create(mediaType, file))
            .build()

        val request = Request.Builder()
            .url(url)
            .post(multipartBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object :Callback{
            override fun onResponse(call: Call, response: Response) {
                Log.i(TAG, response.body?.string())
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "${e.message}")
                e.stackTrace
            }
        })
    }

    fun back(view: View){
        view.txt_back3.setOnClickListener {
            activity?.viewPagerRegister?.setCurrentItem(1, true)
        }

    }
}
