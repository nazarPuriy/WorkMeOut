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
import com.example.workmeout.model.Exercise
import com.example.workmeout.model.ExerciseDescription
import org.json.JSONArray
import org.json.JSONObject
import com.example.workmeout.model.User
import com.example.workmeout.ui.me.ExerciseSearchAdapter

class ExerciseDataBase {

    //Variable que se utilitza para acceder a la base de datos.
    lateinit var requestQ : RequestQueue

    //Método que utilizaremos para guardar nuevos ejercicios en la base de datos.
    fun guardarEjercicio(context: Context, name : String, description: String){
        val URL : String = "http://192.168.1.41:8080/websercv/exercise/registrar.php"
        val stringRequest = object: StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->
                Toast.makeText(context,"Exercise registered", Toast.LENGTH_SHORT).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(context,"ERROR : " + error.toString(), Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): Map<String, String> {
                var parametros = HashMap<String,String>()
                parametros["name"] = name
                parametros["description"] = description
                return parametros
            }
        }
        requestQ= Volley.newRequestQueue(context)
        requestQ.add(stringRequest);
    }

    fun matchExercise(context: Context, partialName:String, adapter:ExerciseSearchAdapter){
        var name: String
        var description: String
        val URL : String = "http://192.168.1.41:8080/websercv/exercise/buscar_match.php?search="+partialName
        var list: ArrayList<ExerciseDescription>
        val jsonArrayRequest : JsonArrayRequest = JsonArrayRequest(URL,
            Response.Listener<JSONArray>{ response->
                var jsonObject: JSONObject
                list = ArrayList()
                for(i in 0..response.length()-1){
                    jsonObject=response.getJSONObject(i);
                    name = jsonObject.getString("name")
                    description = jsonObject.getString("description")
                    list.add(ExerciseDescription(name, description))

                    adapter.submitList(list)
                    adapter.notifyDataSetChanged()

                }
            }, Response.ErrorListener { error->
                Toast.makeText(context, "No matching exercises found.", Toast.LENGTH_SHORT).show()
                list = ArrayList()
                adapter.submitList(list)
                adapter.notifyDataSetChanged()
            })

        requestQ= Volley.newRequestQueue(context);
        requestQ.add(jsonArrayRequest)
    }

    //Método que utilizamos para buscar ejercicios en la base de datos.
    fun buscarEjercicio(context: Context, id : Int){
        var name : String
        var description : String
        val URL : String = "http://192.168.1.41:8080/websercv/exercise/buscar.php?id="+id
        val jsonArrayRequest : JsonArrayRequest = JsonArrayRequest(URL,
            Response.Listener<JSONArray>{ response->
                var jsonObject: JSONObject
                for(i in 0..response.length()-1){
                    jsonObject=response.getJSONObject(i);
                    name = jsonObject.getString("name")
                    description = jsonObject.getString("description")

                    //TODO llamar al controlador para que lo muestre
                }
            }, Response.ErrorListener { error->

            })

        requestQ= Volley.newRequestQueue(context);
        requestQ.add(jsonArrayRequest)

    }


    //TODO la función para buscar coincidencias que llamara al controlador para mostrarlas.

}