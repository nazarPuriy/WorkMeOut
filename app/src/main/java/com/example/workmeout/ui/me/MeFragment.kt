package com.example.workmeout.ui.me

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.data.RoutineDataSourceDummy
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MeFragment : Fragment() {

    private var isImage = false
    private lateinit var uriImagePath : Uri
    private lateinit var contexto : Context
    private final var PICK_IMAGE = 1
    private lateinit var profileImage : ImageView

    override fun onResume() {
        super.onResume()
        init(this.requireView())

    }


    fun init(root: View) {
        val nombre : TextView = root.findViewById(R.id.txt_username)
        nombre.text = Controlador.currentUser!!.name
        nombre.setOnClickListener({
            val cambiarInfo : Intent = Intent(root.context,
                ChangePersonalInformationActivity::class.java)
            startActivity(cambiarInfo)

        })
        val edit : TextView = root.findViewById(R.id.txt_editar)
        edit.setOnClickListener({
            val cambiarInfo : Intent = Intent(root.context,
                ChangePersonalInformationActivity::class.java)
            startActivity(cambiarInfo)

        })

        val age:TextView = root.findViewById(R.id.txtViewAge)
        age.text = "Age: " + Controlador.currentUser!!.age.toString()

        var rv = root.findViewById<RecyclerView>(R.id.rv_1)
        val sa = RoutineAdapter()
        sa.submitList(Controlador.getRoutines())
        rv.adapter = sa
        rv.layoutManager = LinearLayoutManager(root.context)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_me, container, false)
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

        val fab:FloatingActionButton = root.findViewById(R.id.floatingActionButton2)
        fab.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "Add a new routine", Toast.LENGTH_SHORT).show()
            val intent:Intent = Intent(root.context, RoutineActivity::class.java)
            intent.putExtra("isNew", true)
            intent.putExtra("name", "New routine")
            startActivity(intent)

        })

        //Toast.makeText(context, Controlador.currentUser!!.routine1.exercises_class.get(0).name, Toast.LENGTH_SHORT).show()//todo quitar

        init(root)
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
        //saveStatus()
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
