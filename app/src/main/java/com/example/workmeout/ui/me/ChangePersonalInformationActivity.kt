package com.example.workmeout.ui.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.model.User
import kotlinx.android.synthetic.main.activity_change_personal_information.*
import java.util.*

class ChangePersonalInformationActivity : AppCompatActivity() {
    lateinit var correo : EditText
    lateinit var contrasenaVieja : EditText
    lateinit var contrasena : EditText
    lateinit var correoTxt : TextView
    lateinit var contrasenaViejaTxt : TextView
    lateinit var contrasenaTxt : TextView
    lateinit var espaciado : Space
    lateinit var weightnp: NumberPicker
    lateinit var heightnp: NumberPicker
    lateinit var name:EditText
    lateinit var telephone:EditText
    lateinit var gender:RadioGroup
    lateinit var email: EditText
    lateinit var per : CheckBox
    lateinit var age:EditText
    lateinit var male:RadioButton
    var height = 0
    var heightTxt=0




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
        weightnp = findViewById(R.id.weight)
        heightnp = findViewById(R.id.height)
        name = findViewById(R.id.edttxt_nombre)
        telephone = findViewById(R.id.edttxt_telefono)
        gender = findViewById(R.id.gender)
        email = findViewById(R.id.edt_correoElectronico)
        age = findViewById(R.id.edttxt_edad)
        male = findViewById(R.id.rb_hombre)
        per = findViewById(R.id.chk_datosFragiles)


        hide()

        per.setOnClickListener {
            if(per.isChecked()){
                show()
            }else{
                hide()
            }
        }
        weightnp.maxValue = 300


        weightnp.value = Controlador.currentUser!!.weight


        heightnp.maxValue = 300
        heightnp.value = Controlador.currentUser!!.height

        name.setText(Controlador.currentUser!!.name)


        telephone.setText(Controlador.currentUser!!.phoneNumber.toString())
        age.setText(Controlador.currentUser!!.age.toString())



        if(Controlador.currentUser!!.sex){
            Toast.makeText(this, "hombre", Toast.LENGTH_SHORT)
            gender.check(R.id.rb_hombre)
        }else{
            gender.check(R.id.rb_mujer)
        }


        email.setText(Controlador.currentUser!!.email)

    }


    fun show(){
        correo.visibility= View.VISIBLE
        correo.height=height
        contrasena.visibility= View.VISIBLE
        contrasena.height=height
        contrasenaVieja.visibility= View.VISIBLE
        contrasenaVieja.height=height
        correoTxt.visibility= View.VISIBLE
        correoTxt.height=heightTxt
        contrasenaViejaTxt.visibility= View.VISIBLE
        contrasenaViejaTxt.height=heightTxt
        contrasenaTxt.visibility= View.VISIBLE
        contrasenaTxt.height=heightTxt
        espaciado.visibility=View.VISIBLE
    }

    fun hide(){
        height = correo.height
        correo.visibility= View.INVISIBLE
        correo.height=0
        contrasena.visibility= View.INVISIBLE
        contrasena.height=0
        contrasenaVieja.visibility= View.INVISIBLE
        contrasenaVieja.height=0

        heightTxt=correoTxt.height
        correoTxt.visibility= View.INVISIBLE
        correoTxt.height=0
        contrasenaViejaTxt.visibility= View.INVISIBLE
        contrasenaVieja.height=0
        contrasenaTxt.visibility= View.INVISIBLE
        contrasenaTxt.height=0
        espaciado.visibility=View.INVISIBLE
    }


    fun save(view:View){

        var userNameString: String = Controlador.currentUser!!.userName

        var nameString:String
        var passwordString: String
        var emailString: String
        var phoneNumberString: String
        var ageString: String
        var sexString : String
        var weightString: String
        var heightString: String

        if(name.text.isEmpty() || telephone.text.isEmpty() || age.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields.",Toast.LENGTH_SHORT).show()
            return
        }

        nameString = name.text.toString()
        passwordString = Controlador.currentUser!!.password
        emailString = Controlador.currentUser!!.email
        phoneNumberString = telephone.text.toString()
        ageString = age.text.toString()
        sexString = male.isChecked.toString()
        weightString = weightnp.value.toString()
        heightString = heightnp.value.toString()

        if(per.isChecked){

            if(email.text.isEmpty() || contrasenaVieja.text.isEmpty() || contrasena.text.isEmpty()){
                Toast.makeText(this,"Please fill all fields.",Toast.LENGTH_SHORT).show()
                return
            }

            if(contrasenaVieja.text.toString() != Controlador.currentUser!!.password){
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
                return
            }

            passwordString = contrasena.text.toString()
            emailString = email.text.toString()
        }

        if(Controlador.checkData(userNameString, nameString, passwordString, emailString,phoneNumberString, ageString)){
            Controlador.editarUsuario(this, userNameString, nameString, passwordString, emailString, phoneNumberString, ageString, sexString, weightString, heightString)
            Controlador.currentUser!!.name = nameString
            Controlador.currentUser!!.password = passwordString
            Controlador.currentUser!!.email = emailString
            Controlador.currentUser!!.age = ageString.toInt()
            Controlador.currentUser!!.weight = weightString.toInt()
            Controlador.currentUser!!.height = heightString.toInt()
            Controlador.currentUser!!.phoneNumber = phoneNumberString.toInt()
            Controlador.currentUser!!.sex = rb_hombre.isChecked
            finish()
        }else{
            Toast.makeText(this, "Some fields are wrong", Toast.LENGTH_SHORT).show()
        }
    }

}
