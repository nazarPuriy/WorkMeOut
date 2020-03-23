package com.example.workmeout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class ChangePersonalInformationActivity : AppCompatActivity() {
    lateinit var correo : EditText
    lateinit var contrasenaVieja : EditText
    lateinit var contrasena : EditText
    lateinit var correoTxt : TextView
    lateinit var contrasenaViejaTxt : TextView
    lateinit var contrasenaTxt : TextView
    lateinit var espaciado : Space



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_personal_information)

        correo = findViewById(R.id.edt_correoElectronico)
        contrasena = findViewById(R.id.edt_nuevaContrasena)
        contrasenaVieja = findViewById(R.id.edt_contrasenaAntigua)
        correoTxt = findViewById(R.id.txtCorreoElectronico)
        contrasenaViejaTxt = findViewById(R.id.txt_contrasenaAntigua)
        contrasenaTxt = findViewById(R.id.txtNuevaContrasena)
        espaciado = findViewById(R.id.spcR)

        hide()
        val per : CheckBox = findViewById(R.id.chk_datosFragiles)
        per.setOnClickListener({
            if(per.isChecked()){
                show()
            }else{
                hide()
            }
        })
    }

    fun show(){
        correo.visibility= View.VISIBLE
        correo.height=49
        contrasena.visibility= View.VISIBLE
        contrasena.height=49
        contrasenaVieja.visibility= View.VISIBLE
        contrasenaVieja.height=49
        correoTxt.visibility= View.VISIBLE
        correoTxt.height=38
        contrasenaViejaTxt.visibility= View.VISIBLE
        contrasenaViejaTxt.height=38
        contrasenaTxt.visibility= View.VISIBLE
        contrasenaTxt.height=38
        espaciado.visibility=View.VISIBLE
    }

    fun hide(){
        correo.visibility= View.INVISIBLE
        correo.height=0
        contrasena.visibility= View.INVISIBLE
        contrasena.height=0
        contrasenaVieja.visibility= View.INVISIBLE
        contrasenaVieja.height=0
        correoTxt.visibility= View.INVISIBLE
        correoTxt.height=0
        contrasenaViejaTxt.visibility= View.INVISIBLE
        contrasenaVieja.height=0
        contrasenaTxt.visibility= View.INVISIBLE
        contrasenaTxt.height=0
        espaciado.visibility=View.INVISIBLE

    }


    //TODO Have to save the new data in the database
    fun save(view:View){
        Toast.makeText(this,"DATOS GUARDADOS",Toast.LENGTH_SHORT).show()
        finish()
    }
}
