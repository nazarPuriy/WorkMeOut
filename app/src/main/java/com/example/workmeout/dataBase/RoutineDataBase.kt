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
import com.example.workmeout.ui.me.ExerciseSearchAdapter

class RoutineDataBase {

    //Variable que se utilitza para acceder a la base de datos.
    lateinit var requestQ: RequestQueue

    //Método que utilizaremos para guardar la descripcion de la rutina en una base de datos.
    fun guardarRutina(
        context: Context,
        name: String,
        description: String,
        exercise1: Int,
        exercise2: Int,
        exercise3: Int,
        exercise4: Int,
        exercise5: Int,
        exercise6: Int,
        exercise7: Int,
        exercise8: Int,
        exercise9: Int,
        exercise10: Int,
        exercise11: Int,
        exercise12: Int,
        exercise13: Int,
        exercise14: Int,
        exercise15: Int,
        days: Int
    ) {
        val URL: String = "http://192.168.1.41:8080/websercv/routine/registrar.php"
        val stringRequest = object : StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->
                Toast.makeText(context, "Routine registered id: " + response , Toast.LENGTH_SHORT).show()
                    guardarRutinaUsuario(context,response.toInt(),days,exercise1,exercise2,exercise3,exercise4,exercise5,exercise6,exercise7,exercise8,exercise9,exercise10,exercise11,exercise12,exercise13,exercise14,exercise15)
            }, Response.ErrorListener { error ->
                Toast.makeText(context, "ERROR : " + error.toString(), Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): Map<String, String> {
                var parametros = HashMap<String, String>()
                parametros["name"] = name
                parametros["description"] = description
                parametros["exercise1"]=exercise1.toString()
                parametros["exercise2"]=exercise2.toString()
                parametros["exercise3"]=exercise3.toString()
                parametros["exercise4"]=exercise4.toString()
                parametros["exercise5"]=exercise5.toString()
                parametros["exercise6"]=exercise6.toString()
                parametros["exercise7"]=exercise7.toString()
                parametros["exercise8"]=exercise8.toString()
                parametros["exercise9"]=exercise9.toString()
                parametros["exercise10"]=exercise10.toString()
                parametros["exercise11"]=exercise11.toString()
                parametros["exercise12"]=exercise12.toString()
                parametros["exercise13"]=exercise13.toString()
                parametros["exercise14"]=exercise14.toString()
                parametros["exercise15"]=exercise15.toString()
                return parametros
            }
        }
        requestQ = Volley.newRequestQueue(context)
        requestQ.add(stringRequest);
    }

    //Método que utilizaremos para guardas las rutinas correspndietnes a los usuarios
    fun guardarRutinaUsuario(
        context: Context,
        classid: Int,
        days: Int,
        exercise1: Int,
        exercise2: Int,
        exercise3: Int,
        exercise4: Int,
        exercise5: Int,
        exercise6: Int,
        exercise7: Int,
        exercise8: Int,
        exercise9: Int,
        exercise10: Int,
        exercise11: Int,
        exercise12: Int,
        exercise13: Int,
        exercise14: Int,
        exercise15: Int
    ) {
        val URL: String = "http://192.168.1.41:8080/websercv/routine/registrarUsuario.php"
        val stringRequest = object : StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->
                Toast.makeText(context, "Routine registered id: " + response , Toast.LENGTH_SHORT).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(context, "ERROR : " + error.toString(), Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): Map<String, String> {
                var parametros = HashMap<String, String>()
                parametros["classid"] = classid.toString()
                parametros["days"] = days.toString()
                parametros["exercise1"]=exercise1.toString()
                parametros["exercise2"]=exercise2.toString()
                parametros["exercise3"]=exercise3.toString()
                parametros["exercise4"]=exercise4.toString()
                parametros["exercise5"]=exercise5.toString()
                parametros["exercise6"]=exercise6.toString()
                parametros["exercise7"]=exercise7.toString()
                parametros["exercise8"]=exercise8.toString()
                parametros["exercise9"]=exercise9.toString()
                parametros["exercise10"]=exercise10.toString()
                parametros["exercise11"]=exercise11.toString()
                parametros["exercise12"]=exercise12.toString()
                parametros["exercise13"]=exercise13.toString()
                parametros["exercise14"]=exercise14.toString()
                parametros["exercise15"]=exercise15.toString()
                return parametros
            }
        }
        requestQ = Volley.newRequestQueue(context)
        requestQ.add(stringRequest);
    }

    fun matchExercise(context: Context, partialName: String, adapter: ExerciseSearchAdapter) {
        var name: String
        var description: String
        val URL: String =
            "http://192.168.1.41:8080/websercv/exercise/buscar_match.php?search=" + partialName
        var list: ArrayList<ExerciseDescription>
        val jsonArrayRequest: JsonArrayRequest = JsonArrayRequest(URL,
            Response.Listener<JSONArray> { response ->
                var jsonObject: JSONObject
                list = ArrayList()
                for (i in 0..response.length() - 1) {
                    jsonObject = response.getJSONObject(i);
                    name = jsonObject.getString("name")
                    description = jsonObject.getString("description")
                    list.add(ExerciseDescription(name, description))

                    adapter.submitList(list)
                    adapter.notifyDataSetChanged()

                }
            }, Response.ErrorListener { error ->
                Toast.makeText(context, "No matching exercises found.", Toast.LENGTH_SHORT).show()
                list = ArrayList()
                adapter.submitList(list)
                adapter.notifyDataSetChanged()
            })

        requestQ = Volley.newRequestQueue(context);
        requestQ.add(jsonArrayRequest)
    }

    //Método que utilizamos para buscar ejercicios en la base de datos.
    fun buscarEjercicio(context: Context, id: Int) {
        var name: String
        var description: String
        val URL: String = "http://192.168.1.41:8080/websercv/exercise/buscar.php?id=" + id
        val jsonArrayRequest: JsonArrayRequest = JsonArrayRequest(URL,
            Response.Listener<JSONArray> { response ->
                var jsonObject: JSONObject
                for (i in 0..response.length() - 1) {
                    jsonObject = response.getJSONObject(i);
                    name = jsonObject.getString("name")
                    description = jsonObject.getString("description")

                    //TODO llamar al controlador para que lo muestre
                }
            }, Response.ErrorListener { error ->

            })

        requestQ = Volley.newRequestQueue(context);
        requestQ.add(jsonArrayRequest)

    }
}