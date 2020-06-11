package com.example.workmeout.dataBase

import android.app.Activity
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.model.Exercise
import com.example.workmeout.model.Routine
import com.example.workmeout.ui.me.ExerciseSearchAdapter
import com.example.workmeout.ui.me.SearchExercises
import com.example.workmeout.ui.me.SearchRoutines
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
class ExerciseDataBase {

    val domain = "http://83.46.142.41:7070"
    val dateFormatter = SimpleDateFormat("ddMMyyyy")

    //Variable que se utilitza para acceder a la base de datos.
    lateinit var requestQ: RequestQueue

    //Método que utilizaremos para guardar nuevos ejercicios en la base de datos.
    fun guardarEjercicio(
        context: Context,
        name: String,
        description: String,
        reps: Int,
        weight: Int,
        routineIndex: Int
    ) {
        val URL: String = domain + "/websercv/exercise/registrar.php"
        val stringRequest = object : StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->

                guardarEjercicioUsuario(
                    Exercise(
                        0,
                        response.toInt(),
                        name,
                        reps,
                        description,
                        weight,
                        ArrayList(),
                        ArrayList()
                    ), routineIndex, context
                ) //Guardamos la rutina del usuario.
            }, Response.ErrorListener { error ->
            }) {
            override fun getParams(): Map<String, String> {
                var parametros = HashMap<String, String>()
                parametros["name"] = name
                parametros["description"] = description
                return parametros
            }
        }
        requestQ = Volley.newRequestQueue(context)
        requestQ.add(stringRequest);
    }

    fun guardarEjercicioUsuario(exercise: Exercise, indexRoutine: Int, context: Context) {

        var day1 = "0"
        var day2 = "0"
        var day3 = "0"
        var day4 = "0"
        var day5 = "0"
        var day6 = "0"
        var day7 = "0"

        var weight1 = "0"
        var weight2 = "0"
        var weight3 = "0"
        var weight4 = "0"
        var weight5 = "0"
        var weight6 = "0"
        var weight7 = "0"


        if (exercise.days.size >= 1) {
            day1 = exercise.days.get(0).toString()
        }
        if (exercise.days.size >= 2) {
            day2 = exercise.days.get(1).toString()
        }
        if (exercise.days.size >= 3) {
            day3 = exercise.days.get(2).toString()
        }
        if (exercise.days.size >= 4) {
            day4 = exercise.days.get(3).toString()
        }
        if (exercise.days.size >= 5) {
            day5 = exercise.days.get(4).toString()
        }
        if (exercise.days.size >= 6) {
            day6 = exercise.days.get(5).toString()
        }
        if (exercise.days.size >= 7) {
            day7 = exercise.days.get(6).toString()
        }

        if (exercise.weights.size >= 1) {
            day1 = exercise.weights.get(0).toString()
        }
        if (exercise.weights.size >= 2) {
            day2 = exercise.weights.get(1).toString()
        }
        if (exercise.weights.size >= 3) {
            day3 = exercise.weights.get(2).toString()
        }
        if (exercise.weights.size >= 4) {
            day4 = exercise.weights.get(3).toString()
        }
        if (exercise.weights.size >= 5) {
            day5 = exercise.weights.get(4).toString()
        }
        if (exercise.weights.size >= 6) {
            day6 = exercise.weights.get(5).toString()
        }
        if (exercise.weights.size >= 7) {
            day7 = exercise.weights.get(6).toString()
        }

        Controlador.getRoutines().get(indexRoutine - 1).exercises_class.add(exercise)

        if (context is SearchExercises) {
            context.finish()
        }

        val URL: String = domain + "/websercv/exercise/registrarUsuario.php"
        val stringRequest = object : StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->
                exercise.id = response.toInt()

                //Controlador.fillNewExerciseId(response.toInt()) TODO
                //Faltaria meter el objeto ejercicio aqui dentro del objeto rutina correspondiente. Mirar el método de la id.
                Controlador.saveExerciseIdOnRutine(
                    context,
                    response.toInt(),
                    exercise.classId,
                    indexRoutine
                )
            }, Response.ErrorListener { error ->
            }) {

            override fun getParams(): Map<String, String> {
                var parametros = HashMap<String, String>()
                parametros["classid"] = exercise.classId.toString()
                parametros["reps"] = exercise.reps.toString()
                parametros["weight"] = exercise.weight.toString()
                parametros["day1"] = day1
                parametros["weight1"] = weight1
                parametros["day2"] = day2
                parametros["weight2"] = weight2
                parametros["day3"] = day3
                parametros["weight3"] = weight3
                parametros["day4"] = day4
                parametros["weight4"] = weight4
                parametros["day5"] = day5
                parametros["weight5"] = weight5
                parametros["day6"] = day6
                parametros["weight6"] = weight6
                parametros["day7"] = day7
                parametros["weight7"] = weight7
                return parametros
            }
        }
        requestQ = Volley.newRequestQueue(context)
        requestQ.add(stringRequest);


    }


    fun matchExercise(context: Context, partialName: String, adapter: ExerciseSearchAdapter) {
        var name: String
        var description: String
        var classId: String
        val URL: String = domain + "/websercv/exercise/buscar_match.php?search=" + partialName
        var list: ArrayList<Exercise>
        val jsonArrayRequest: JsonArrayRequest = JsonArrayRequest(URL,
            Response.Listener<JSONArray> { response ->
                var jsonObject: JSONObject
                list = ArrayList()
                for (i in 0..response.length() - 1) {
                    jsonObject = response.getJSONObject(i)
                    name = jsonObject.getString("name")
                    description = jsonObject.getString("description")
                    classId = jsonObject.getString("id")

                    list.add(
                        Exercise(
                            0,
                            classId.toInt(),
                            name,
                            0,
                            description,
                            0,
                            ArrayList<Date>(),
                            ArrayList<Int>()
                        )
                    )


                }

                adapter.submitList(list)
                adapter.notifyDataSetChanged()

            }, Response.ErrorListener { error ->
                list = ArrayList()
                adapter.submitList(list)
                adapter.notifyDataSetChanged()
            })

        requestQ = Volley.newRequestQueue(context);
        requestQ.add(jsonArrayRequest)
    }

    //Método que utilizamos para buscar ejercicios en la base de datos.
    fun buscarEjercicio(
        context: Context,
        ejercicioUsuaro: Exercise
    ) {
        var name: String
        var description: String
        val URL: String = domain + "/websercv/exercise/buscar.php?id=" + ejercicioUsuaro.classId
        val jsonArrayRequest: JsonArrayRequest = JsonArrayRequest(URL,
            Response.Listener<JSONArray> { response ->
                var jsonObject: JSONObject
                for (i in 0..response.length() - 1) {
                    jsonObject = response.getJSONObject(i)
                    name = jsonObject.getString("name")
                    description = jsonObject.getString("description")
                    ejercicioUsuaro.name = name
                    ejercicioUsuaro.description = description

                    if(context is SearchRoutines){
                        ejercicioUsuaro.dataReady = true
                        ejercicioUsuaro.notifyRoutine(context)
                    }else{
                        ejercicioUsuaro.routine.notifyExerciseReady(context)
                    }


                }
            }, Response.ErrorListener { error ->

            })

        requestQ = Volley.newRequestQueue(context);
        requestQ.add(jsonArrayRequest)

    }

    fun buscarEjercicioUsuario(context: Context, id: Int, routine: Routine) {

        var classId: Int
        var reps: Int
        var weight: Int
        var days: ArrayList<Date> = ArrayList()
        var weights: ArrayList<Int> = ArrayList()
        var tmp: String

        val URL: String = domain + "/websercv/exercise/buscarUsuario.php?id=" + id
        val jsonArrayRequest: JsonArrayRequest = JsonArrayRequest(URL,
            Response.Listener<JSONArray> { response ->
                var jsonObject: JSONObject
                for (i in 0..response.length() - 1) {

                    jsonObject = response.getJSONObject(i)

                    classId = jsonObject.getString("classid").toInt()
                    reps = jsonObject.getString("reps").toInt()
                    weight = jsonObject.getString("weight").toInt()

                    for (x in 1..7) {

                        tmp = jsonObject.getString("day$x")
                        if (tmp != "0") {

                            if (tmp.length < 8) {
                                tmp = "0$tmp"
                            }

                            days.add(dateFormatter.parse(tmp))
                            weights.add(jsonObject.getString("weight$x").toInt())
                        }
                    }

                    val ejercicioUsuario: Exercise =
                        Exercise(id, classId, "", reps, "", weight, days, weights)
                    ejercicioUsuario.routine = routine
                    buscarEjercicio(context, ejercicioUsuario)
                    routine.exercises_class.add(ejercicioUsuario)
                }


            }, Response.ErrorListener { error ->

            })

        requestQ = Volley.newRequestQueue(context);
        requestQ.add(jsonArrayRequest)

    }

    fun editUserExercise(exercise: Exercise, context: Context) {

        val URL: String = domain + "/websercv/exercise/editarUsuario.php"
        val stringRequest = object : StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->
            }, Response.ErrorListener { error ->
            }) {
            override fun getParams(): Map<String, String> {

                var parametros = HashMap<String, String>()
                parametros["id"] = exercise.id.toString()
                parametros["classid"] = exercise.classId.toString()
                parametros["reps"] = exercise.reps.toString()
                parametros["weight"] = exercise.weight.toString()

                for (x in 0 until 7) {

                    if (x < exercise.days.size) {

                        var s = dateFormatter.format(exercise.days.get(x))
                        if (s.length < 8) {
                            s = "0$s"
                        }

                        parametros["day" + (x + 1).toString()] = s
                        parametros["weight" + (x + 1).toString()] =
                            exercise.weights.get(x).toString()
                    } else {
                        parametros["day" + (x + 1).toString()] = "0"
                        parametros["weight" + (x + 1).toString()] = "0"
                    }
                }


                return parametros
            }
        }
        requestQ = Volley.newRequestQueue(context)
        requestQ.add(stringRequest);

    }

    fun registerExistingExercise(ex: Exercise, context: Context, idx: Int) {

        val URL: String = domain + "/websercv/exercise/registrarUsuario.php"
        val stringRequest = object : StringRequest(Request.Method.POST, URL,
            Response.Listener<String> { response ->
                ex.id = response.toInt()
                ex.routine.exercises[idx] = response.toInt()
                ex.notifyRoutine(context)
            }, Response.ErrorListener { error ->
            }) {

            override fun getParams(): Map<String, String> {
                var parametros = HashMap<String, String>()
                parametros["classid"] = ex.classId.toString()
                parametros["reps"] = ex.reps.toString()
                parametros["weight"] = ex.weight.toString()

                for(x in 0 until 7){

                    var tmp = "0"
                    var tmp2 = "0"

                    if (ex.days.size > x){
                        tmp = dateFormatter.format(ex.days[x])
                        tmp2 = ex.weights[x].toString()
                        if(tmp.length < 8){tmp = "0$tmp"}
                    }
                    parametros["day" + (x + 1).toString()] = tmp
                    parametros["weight" + (x + 1).toString()] = tmp2
                }

                return parametros
            }
        }
        requestQ = Volley.newRequestQueue(context)
        requestQ.add(stringRequest);
    }


}