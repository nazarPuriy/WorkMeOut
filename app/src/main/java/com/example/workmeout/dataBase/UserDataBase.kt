package com.example.workmeout.dataBase

import android.content.Context
import android.content.res.Resources
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.model.Routine
import org.json.JSONArray
import org.json.JSONObject
import com.example.workmeout.model.User
import com.example.workmeout.ui.me.ChangePersonalInformationActivity

class UserDataBase {

    val domain = "http://83.46.142.41:7070"

    //Variable que se utilitza para acceder a la base de datos.
    lateinit var requestQ : RequestQueue

    //Método que utilizaremos para guardar nuevos usuarios en la base de datos.
    fun guardarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        val URL : String = domain + "/websercv/user/registrar.php"
        val stringRequest = object: StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->
                if(response.length>4){ //Nos informa la base de datos que el usuario ya existe
                }else{
                }


            }, Response.ErrorListener { error ->
            }) {
            override fun getParams(): Map<String, String> {
                var parametros = HashMap<String,String>()
                parametros["username"] = username
                parametros["name"] = name
                parametros["password"] = password
                parametros["email"] = email
                parametros["phone"] = phone
                parametros["age"] = age
                parametros["gender"] = gender
                parametros["weight"]= weight
                parametros["height"]= height
                parametros["routine1"]="0"
                parametros["routine2"]="0"
                parametros["routine3"]="0"
                parametros["routine4"]="0"
                parametros["routine5"]="0"

                return parametros
            }
        }
        requestQ= Volley.newRequestQueue(context)
        requestQ.add(stringRequest);
    }

    //Método que utilizamos para buscar usuarios en la base de datos.
    fun buscarUsuario(context: Context, username : String, oldPassword : String){
        var user : User? = null
        var name : String
        var password : String
        var email : String
        var phoneNumber : Int
        var age : Int
        var gender : Boolean
        var weight : Int
        var height : Int
        var rid1 : Int
        var rid2 : Int
        var rid3 : Int
        var rid4 : Int
        var rid5 : Int

        val URL : String = domain + "/websercv/user/buscar.php?username="+username
        val jsonArrayRequest : JsonArrayRequest = JsonArrayRequest(URL,
            Response.Listener<JSONArray>{ response->
                var jsonObject: JSONObject
                for(i in 0..response.length()-1){
                    jsonObject=response.getJSONObject(i);
                    name = jsonObject.getString("name")
                    password = jsonObject.getString("password")
                    email = jsonObject.getString("email")
                    phoneNumber = jsonObject.getString("phone").toInt()
                    age = jsonObject.getString("age").toInt()
                    gender = jsonObject.getString("gender") == "true"
                    weight = jsonObject.getString("weight").toInt()
                    height = jsonObject.getString("height").toInt()
                    rid1= jsonObject.getString("routine1").toInt()
                    rid2= jsonObject.getString("routine2").toInt()
                    rid3= jsonObject.getString("routine3").toInt()
                    rid4= jsonObject.getString("routine4").toInt()
                    rid5= jsonObject.getString("routine5").toInt()

                    //Vamos a obtener primero las rutinas del usuario de la base de datos corresspondiente.


                    Controlador.currentUser = User(username,name,password,email,phoneNumber,age,gender,weight,height)
                    Controlador.addRoutinesToUserRequest(context,rid1,rid2,rid3,rid4,rid5)
                    Controlador.notifyRoutineReady(context)
                }
            }, Response.ErrorListener { error->
                Controlador.login(context,oldPassword);
            })

        requestQ= Volley.newRequestQueue(context);
        requestQ.add(jsonArrayRequest)
    }

    //Método que utilizamos para sobreescribir información.
    fun editarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String, routine1 : Int, routine2 : Int, routine3 : Int, routine4 : Int, routine5 : Int){
        val URL : String = domain + "/websercv/user/editar.php"
        val stringRequest = object: StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->

            }, Response.ErrorListener { error ->
            }) {
            override fun getParams(): Map<String, String> {
                var parametros = HashMap<String,String>()
                parametros["username"] = username
                parametros["name"] = name
                parametros["password"] = password
                parametros["email"] = email
                parametros["phone"] = phone
                parametros["age"] = age
                parametros["gender"] = gender
                parametros["weight"]= weight
                parametros["height"]= height
                parametros["routine1"]=routine1.toString()
                parametros["routine2"]=routine2.toString()
                parametros["routine3"]=routine3.toString()
                parametros["routine4"]=routine4.toString()
                parametros["routine5"]=routine5.toString()
                return parametros
            }
        }

        requestQ= Volley.newRequestQueue(context)
        requestQ.add(stringRequest);
    }

}