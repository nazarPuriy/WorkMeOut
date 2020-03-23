package com.example.workmeout.ui.dashboard

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.workmeout.ChangePersonalInformationActivity
import com.example.workmeout.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class DashboardFragment : Fragment() {

    private var isImage = false
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var uriImagePath : Uri
    private lateinit var contexto : Context
    private final var PICK_IMAGE = 1
    private lateinit var profileImage : ImageView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        contexto = root.context
        //Vamos a cargar la pequeña información guardada sobre la app
        chargeStatus()
        uploadProfileImage(root)
        profileImage.setOnClickListener(View.OnClickListener {
            val galeria = Intent()
            galeria.setType("image/*")
            galeria.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(galeria,"Selecciona una imagen"), PICK_IMAGE)
        })

        val nombre : TextView = root.findViewById(R.id.txt_username)
        nombre.setOnClickListener({
            val cambiarInfo : Intent = Intent(root.context,ChangePersonalInformationActivity::class.java)
            startActivity(cambiarInfo)

        })
        val edit : TextView = root.findViewById(R.id.txt_editar)
        edit.setOnClickListener({
            val cambiarInfo : Intent = Intent(root.context,ChangePersonalInformationActivity::class.java)
            startActivity(cambiarInfo)

        })
        return root
    }

    fun uploadProfileImage(view:View){
        profileImage=view.findViewById(R.id.img_profile)
        if(isImage){
            profileImage.setImageURI(uriImagePath)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            var path = data?.data
            if (path != null) {
                profileImage.setImageURI(path)
                isImage = true
                uriImagePath=path
            }

        }
    }

    override fun onPause() {
        saveStatus()
        super.onPause()
    }

    fun chargeStatus() {
        val files= contexto.fileList()
        if(existFile(files,"datosPerfil.txt"))
        try {
            val archivo: InputStreamReader = InputStreamReader(contexto.openFileInput("datosPerfil.txt"))
            val br : BufferedReader = BufferedReader(archivo)
            val hayImagen : String = br.readLine()
            val uriString : String = br.readLine()
            isImage = hayImagen.toBoolean()
            uriImagePath = Uri.parse(uriString)
            br.close()
            archivo.close()
        }catch(e:Exception){}
    }

    private fun existFile(files: Array<String>, s: String): Boolean {
        for(item in files){
            if(s.equals(item)){
                return true
            }
        }
        return false
    }

    fun saveStatus(){
        try{
            val archivo : OutputStreamWriter = OutputStreamWriter(contexto.openFileOutput("datosPerfil.txt",Activity.MODE_PRIVATE))
            val save : String = isImage.toString() + "\n" + uriImagePath.toString()
            archivo.write(save)
            archivo.flush()
            archivo.close()
        }catch(e:Exception){}

    }

}
