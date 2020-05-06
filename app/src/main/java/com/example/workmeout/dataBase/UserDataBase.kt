package com.example.workmeout.dataBase

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.workmeout.Controlador.Controlador
import org.json.JSONArray
import org.json.JSONObject
import com.example.workmeout.model.User

class UserDataBase {

    //Variable que se utilitza para acceder a la base de datos.
    lateinit var requestQ : RequestQueue

    //Método que utilizaremos para guardar nuevos usuarios en la base de datos.
    fun guardarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        val URL : String = "http://192.168.1.41:8080/websercv/user/registrar.php"
        val stringRequest = object: StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->
                if(response.length>4){ //Nos informa la base de datos que el usuario ya existe
                    Toast.makeText(context,"There is already a user with the given username", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"User registered", Toast.LENGTH_SHORT).show()
                }


            }, Response.ErrorListener { error ->
                Toast.makeText(context,"ERROR : " + error.toString(), Toast.LENGTH_LONG).show()
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


        val URL : String = "http://192.168.1.41:8080/websercv/user/buscar.php?username="+username
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

                    Controlador.currentUser = User(username,name,password,email,phoneNumber,age,gender,weight,height);
                    Controlador.login(context,oldPassword);
                }
            }, Response.ErrorListener { error->
                Controlador.login(context,oldPassword);
            })

        requestQ= Volley.newRequestQueue(context);
        requestQ.add(jsonArrayRequest)
    }

    //Método que utilizamos para sobreescribir información.
    fun editarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        val URL : String = "http://192.168.1.41:8080/websercv/user/editar.php"
        val stringRequest = object: StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->
                Toast.makeText(context,"OPERACION EXITOSA DE EDIT", Toast.LENGTH_SHORT).show()

            }, Response.ErrorListener { error ->
                Toast.makeText(context,"ERROR : " + error.toString(), Toast.LENGTH_LONG).show()
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
                return parametros
            }
        }

        requestQ= Volley.newRequestQueue(context)
        requestQ.add(stringRequest);
    }

}