package com.example.workmeout.Controlador

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.workmeout.model.User
import com.example.workmeout.dataBase.BaseDatos
import com.example.workmeout.intentoDeChat.FireHelper
import com.example.workmeout.model.Exercise
import com.example.workmeout.model.Routine
import com.example.workmeout.ui.MainActivity
import com.example.workmeout.ui.identification.LoginActivity
import com.example.workmeout.ui.identification.RegisterActivity
import com.example.workmeout.ui.me.ExerciseSearchAdapter
import com.example.workmeout.ui.me.RoutineSearchAdapter
import com.example.workmeout.ui.me.SearchRoutines
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max


object Controlador{

    var baseDatos : BaseDatos = BaseDatos()
    var currentUser : User? = null
    var currentUserRoutineIds: Int = 0
    var readyRoutines: Int = 0
    var passwordInput = ""
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    //Guarda un usuario con los datos introducidos en la base de datos.
    fun register(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        baseDatos.guardarUsuario(context,username,name,password,email,phone,age,gender,weight,height)
    }

    //Hace una petición de loguear el usuario. La base de datos llamará la función loguear una vez haya respuesta o un Timeout.
    fun loginRequest(context : Context,username:String,password:String){
        currentUser = null //Nos asseguramos de que no haya ningún usuario activo.
        readyRoutines = 0
        currentUserRoutineIds = 0
        baseDatos.buscarUsuario(context,username,password)
        passwordInput = password
        if(context is LoginActivity){context.btn_login.isEnabled = false}
    }

    fun editarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        var id1 : Int = 0
        var id2 : Int = 0
        var id3 : Int = 0
        var id4 : Int = 0
        var id5 : Int = 0
        if(currentUser != null){
            if(currentUser!!.routine1 != null) {
                id1 = currentUser!!.routine1!!.id
            }
            if(currentUser!!.routine2 != null){
                id2 = currentUser!!.routine2!!.id
            }
            if(currentUser!!.routine3 != null){
                id3 = currentUser!!.routine3!!.id
            }
            if(currentUser!!.routine4 != null){
                id4 = currentUser!!.routine4!!.id
            }
            if(currentUser!!.routine5 != null){
                id5 = currentUser!!.routine5!!.id
            }
            baseDatos.editarUsuario(context,username,name,password,email,phone,age,gender,weight,height,id1,id2,id3,id4,id5)
        }
    }


    //Loguea el usuario. Es llamada des de la base de datos. //TODO que se llame mas tarde
    fun login(context:Context,password:String){
        if(context is LoginActivity){context.btn_login.isEnabled = true}
        if (currentUser == null) {
            Toast.makeText(context, "Incorrect username", Toast.LENGTH_SHORT).show()
        } else {
            if (currentUser!!.password != password) {
                Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show()
            } else {

                /*Firebase----------------------------*/

                /*------------------------------------*/
                //Accedemos ya dentro de la aplicación.
                val logInt= Intent(context, MainActivity::class.java)
                context.startActivity(logInt)
            }
        }
    }

    fun temporalPrint(view: View){
        Toast.makeText(view.context,"Tenemos las rutinas " +currentUser!!.numberOfRoutines + " PRIMERA "+ currentUser!!.routine1!!.id+" con nombre "+ currentUser!!.routine1!!.name+" SEGUNDA "+ currentUser!!.routine2!!.id+" con nombre "+ currentUser!!.routine2!!.name+ " TERCERA "+ currentUser!!.routine3!!.id+" con nombre "+ currentUser!!.routine3!!.name, Toast.LENGTH_SHORT).show()
    }

    //Comprueba si los datos introducidos són correctos.
    fun checkData(username : String, name : String, password: String, email: String, phone : String, age : String) : Boolean{
        //TODO crear objecte per comprovar les dades i comprovar aquí.
        //En el nombre de usuario no importan mayúsculas i minúsculas para loguearse. Eso si si se imprime se imprimirá con estas.
        return true
    }

    //Guarda la descripción de un nuevo ejercicio la base de datos
    fun registerExercise(
        context: Context,
        name: String,
        description: String,
        reps: Int,
        weight: Int,
        routineIndex: Int
    ){
        baseDatos.guardarEjercicio(context,name,description,reps,weight, routineIndex)
    }

    fun saveExerciseIdOnRutine(
        context: Context,
        id: Int,
        classid: Int,
        routineIndex: Int
    ){
        //baseDatos.guardarRutina(context,currentUser!!.routine1.name,currentUser!!.routine1.description,)
        var routine : Routine = currentUser!!.routine1!! //Para assignar algo
        when(routineIndex){
            1 -> {routine = currentUser!!.routine1!!}
            2 -> {routine = currentUser!!.routine2!!}
            3 -> {routine = currentUser!!.routine3!!}
            4 -> {routine = currentUser!!.routine4!!}
            5 -> {routine = currentUser!!.routine5!!}
        }

        var count = routine.idCount()

        routine.exercises[count] = id
        routine.exercisesDesc[count] = classid
        baseDatos.editarRutinaUsuario(context,routine.id,routine.classid,routine.exercises[0],routine.exercises[1],routine.exercises[2],routine.exercises[3],routine.exercises[4],routine.exercises[5],routine.exercises[6],routine.exercises[7],routine.exercises[8],routine.exercises[9],routine.exercises[10],routine.exercises[11],routine.exercises[12],routine.exercises[13],routine.exercises[14],routine.days)
        baseDatos.editarRutina(context,routine.classid,routine.name,routine.description,routine.exercisesDesc[0],routine.exercisesDesc[1],routine.exercisesDesc[2],routine.exercisesDesc[3],routine.exercisesDesc[4],routine.exercisesDesc[5],routine.exercisesDesc[6],routine.exercisesDesc[7],routine.exercisesDesc[8],routine.exercisesDesc[9],routine.exercisesDesc[10],routine.exercisesDesc[11],routine.exercisesDesc[12],routine.exercisesDesc[13],routine.exercisesDesc[14])
        routine.numberOfExercises++

    }

    fun guardarEjercioRutina(exercise:Exercise, index:Int, context: Context){

        baseDatos.guardarEjercicioUsuario(exercise, index, context)

    }

    //Luego enviara la rutina al usuario
    fun registerRoutine(context: Context, name: String,description: String,exercise1: Int, exercise2: Int,exercise3: Int,exercise4: Int,exercise5: Int,exercise6: Int,exercise7: Int,exercise8: Int,exercise9: Int,exercise10: Int,exercise11: Int, exercise12: Int, exercise13: Int,exercise14: Int,exercise15: Int,days : Int){
        var routine : Routine = Routine(0,0,name,description,days)

        for(x in 0 until 15){routine.exercises.add(0)}
        for(x in 0 until 15){routine.exercisesDesc.add(0)}

        postRoutine(context,routine, currentUser!!.numberOfRoutines)
        baseDatos.guardarRutina(context,name,description,exercise1,exercise2,exercise3,exercise4,exercise5,exercise6,exercise7,exercise8,exercise9,exercise10,exercise11,exercise12,exercise13,exercise14,exercise15,days)
    }

    //Pide a la base de datos del usuario que vaya cargando las diferentes rutinas.
    fun addRoutinesToUserRequest(context : Context,id1 : Int, id2 : Int, id3 : Int, id4 : Int, id5 : Int){
        if(id1 != 0){
            baseDatos.buscarRutinaUsuario(context,id1, 0)
            currentUserRoutineIds++
        }
        if(id2 != 0){
            baseDatos.buscarRutinaUsuario(context,id2, 1)
            currentUserRoutineIds++
        }
        if(id3 != 0){
            baseDatos.buscarRutinaUsuario(context,id3, 2)
            currentUserRoutineIds++
        }
        if(id4 != 0){
            baseDatos.buscarRutinaUsuario(context,id4, 3)
            currentUserRoutineIds++
        }
        if(id5 != 0) {
            baseDatos.buscarRutinaUsuario(context,id5, 4)
            currentUserRoutineIds++
        }

    }

    //Postea la rutina ( se llama des de la base de datos ) y pide rellenar los demás campos.
    fun postRoutine(context : Context, routine : Routine, index:Int){
        when(index){
            0-> {
                currentUser!!.numberOfRoutines = max(1, currentUser!!.numberOfRoutines)
                currentUser!!.routine1 = routine
                }
            1-> {
                currentUser!!.numberOfRoutines = max(2, currentUser!!.numberOfRoutines)
                currentUser!!.routine2 = routine }
            2-> {
                currentUser!!.numberOfRoutines = max(3, currentUser!!.numberOfRoutines)
                currentUser!!.routine3 = routine }
            3-> {
                currentUser!!.numberOfRoutines = max(4, currentUser!!.numberOfRoutines)
                currentUser!!.routine4 = routine}
            4-> {
                currentUser!!.numberOfRoutines = max(5, currentUser!!.numberOfRoutines)
                currentUser!!.routine5 = routine
            }
        }
    }

    //Acaba de meter en las rutinas que se estan cargando los datos.
    fun fillRoutine(name : String, description : String, idx:Int, context: Context){

        var routine:Routine = Routine(0, 0, "", "", 0)

        when(idx){
            0-> {
                routine = currentUser!!.routine1!!
            }
            1-> {
                routine = currentUser!!.routine2!!
            }
            2-> {
                routine = currentUser!!.routine3!!
            }
            3-> {
                routine = currentUser!!.routine4!!
            }
            4-> {
                routine = currentUser!!.routine5!!
            }
        }

        routine!!.name = name
        routine.description = description
        routine.notifyExerciseReady(context)

        for(i in routine.exercises){
            if(i != 0){
                baseDatos.buscarEjercicioUsuario(context, i, routine)
            }
        }
    }



    //Acaba de meter la id procediente de la base de datos. También guardaremos en la base de datos del usuario esta id de rutina.
    fun fillNewRoutineId(context:Context,id:Int){
        if(currentUser!=null){
            when(currentUser!!.numberOfRoutines){
                1-> {
                    currentUser!!.routine1!!.id = id
                    baseDatos.editarUsuario(context, currentUser!!.userName, currentUser!!.name,currentUser!!.password,currentUser!!.email,
                        currentUser!!.phoneNumber.toString(),
                        currentUser!!.age.toString(),
                        currentUser!!.sex.toString(),
                        currentUser!!.weight.toString(),
                        currentUser!!.height.toString(),id,0,0,0,0)
                }
                2-> {
                    currentUser!!.routine2!!.id = id
                    baseDatos.editarUsuario(context, currentUser!!.userName, currentUser!!.name,currentUser!!.password,currentUser!!.email,
                        currentUser!!.phoneNumber.toString(),
                        currentUser!!.age.toString(),
                        currentUser!!.sex.toString(),
                        currentUser!!.weight.toString(),
                        currentUser!!.height.toString(),
                        currentUser!!.routine1!!.id,id,0,0,0)
                }
                3-> {
                    currentUser!!.routine3!!.id = id
                    baseDatos.editarUsuario(context, currentUser!!.userName, currentUser!!.name,currentUser!!.password,currentUser!!.email,
                        currentUser!!.phoneNumber.toString(),
                        currentUser!!.age.toString(),
                        currentUser!!.sex.toString(),
                        currentUser!!.weight.toString(),
                        currentUser!!.height.toString(),currentUser!!.routine1!!.id,currentUser!!.routine2!!.id,id,0,0)
                }
                4-> {
                    currentUser!!.routine4!!.id = id
                    baseDatos.editarUsuario(context, currentUser!!.userName, currentUser!!.name,currentUser!!.password,currentUser!!.email,
                        currentUser!!.phoneNumber.toString(),
                        currentUser!!.age.toString(),
                        currentUser!!.sex.toString(),
                        currentUser!!.weight.toString(),
                        currentUser!!.height.toString(),currentUser!!.routine1!!.id,currentUser!!.routine2!!.id,currentUser!!.routine3!!.id,id,0)
                }
                5-> {
                    currentUser!!.routine5!!.id = id
                    baseDatos.editarUsuario(context, currentUser!!.userName, currentUser!!.name,currentUser!!.password,currentUser!!.email,
                        currentUser!!.phoneNumber.toString(),
                        currentUser!!.age.toString(),
                        currentUser!!.sex.toString(),
                        currentUser!!.weight.toString(),
                        currentUser!!.height.toString(),currentUser!!.routine1!!.id,currentUser!!.routine2!!.id,currentUser!!.routine3!!.id,currentUser!!.routine4!!.id,id)
                }
            }
        }

        if(context is SearchRoutines){
            context.finish()
        }


    }

    //Acaba de meter la id procediente de la base de datos. También guardaremos en la base de datos del usuario esta id de rutina.
    fun fillNewRoutineClassId(id:Int){
        when(currentUser!!.numberOfRoutines){
            1-> {
                currentUser!!.routine1!!.classid = id
            }
            2-> {
                currentUser!!.routine2!!.classid = id
            }
            3-> {
                currentUser!!.routine3!!.classid = id
            }
            4-> {
                currentUser!!.routine4!!.classid = id
            }
            5-> {
                currentUser!!.routine5!!.classid = id
            }
        }
    }


    fun matchExercise(context: Context, name:String, adapter:ExerciseSearchAdapter){
        baseDatos.matchExercise(context, name, adapter)
    }

    fun getRoutines(): List<Routine>{

        var lista: ArrayList<Routine> = ArrayList()

        if(currentUser!!.routine1 != null){lista.add(currentUser!!.routine1!!)}
        if(currentUser!!.routine2 != null){lista.add(currentUser!!.routine2!!)}
        if(currentUser!!.routine3 != null){lista.add(currentUser!!.routine3!!)}
        if(currentUser!!.routine4 != null){lista.add(currentUser!!.routine4!!)}
        if(currentUser!!.routine5 != null){lista.add(currentUser!!.routine5!!)}


        return lista
    }

    fun deleteRoutine(context : Context, indice : Int){
        //Eliminamos la rutina de la base de datos de rutinas
        when(indice){
            0->  baseDatos.eliminarRutinaUsuario(context, currentUser!!.routine1!!.id)
            1->  baseDatos.eliminarRutinaUsuario(context, currentUser!!.routine2!!.id)
            2->  baseDatos.eliminarRutinaUsuario(context, currentUser!!.routine3!!.id)
            3->  baseDatos.eliminarRutinaUsuario(context, currentUser!!.routine4!!.id)
            4->  baseDatos.eliminarRutinaUsuario(context, currentUser!!.routine5!!.id)
        }

        var indiceS = 0
        //primero movemos todas las rutinas que estem de indices superior a la eliminar un indice más abajo.
        while(indiceS < currentUser!!.numberOfRoutines-1){
            when(indiceS){
                0 -> {
                    if(indiceS >= indice){
                        currentUser!!.routine1 = currentUser!!.routine2
                    }

                }
                1 -> {
                    if(indiceS >= indice){
                        currentUser!!.routine2 = currentUser!!.routine3
                    }

                }
                2 -> {
                    if(indiceS >= indice){
                        currentUser!!.routine3 = currentUser!!.routine4
                    }

                }
                3 -> {
                    if(indiceS >= indice){
                        currentUser!!.routine4= currentUser!!.routine5
                    }

                }

            }
            indiceS++
        }

        //Eliminamos la última (que ya está añadida en otro sitio)
        when(currentUser!!.numberOfRoutines - 1){
            0 -> currentUser!!.routine1 = null
            1 -> currentUser!!.routine2 = null
            2 -> currentUser!!.routine3 = null
            3 -> currentUser!!.routine4 = null
            4 -> currentUser!!.routine5 = null
        }


        currentUser!!.numberOfRoutines-- //Reducimos el número de rutinas en 1.
        delateRoutineDataBase(context)

    }

    fun delateRoutineDataBase(context : Context){
        var rid1 : Int = 0
        var rid2 : Int = 0
        var rid3 : Int = 0
        var rid4 : Int = 0
        var rid5 : Int = 0
        var indice = 0
        while(indice<currentUser!!.numberOfRoutines){
            when(indice){
                0-> rid1 = currentUser!!.routine1!!.id
                1-> rid2 = currentUser!!.routine2!!.id
                2-> rid3 = currentUser!!.routine3!!.id
                3-> rid4 = currentUser!!.routine4!!.id
                4-> rid5 = currentUser!!.routine5!!.id //AUnque nunca deberia de tener este valor
            }
            indice++
        }

        baseDatos.editarUsuario(context, currentUser!!.userName, currentUser!!.name,currentUser!!.password,currentUser!!.email,
            currentUser!!.phoneNumber.toString(),
            currentUser!!.age.toString(),
            currentUser!!.sex.toString(),
            currentUser!!.weight.toString(),
            currentUser!!.height.toString(),rid1,rid2,rid3,rid4,rid5)
    }

    fun editRoutineDays(indexRoutine: Int, days: Int, context: Context){

        var routine: Routine = currentUser!!.routine1!!
        when(indexRoutine){

            0 -> routine = currentUser!!.routine1!!
            1 -> routine = currentUser!!.routine2!!
            2 -> routine = currentUser!!.routine3!!
            3 -> routine = currentUser!!.routine4!!
            4 -> routine = currentUser!!.routine5!!

        }

        routine.days = days

        baseDatos.editarRutinaUsuario(context, routine.id, routine.classid, routine.exercises[0], routine.exercises[1], routine.exercises[2], routine.exercises[3],
        routine.exercises[4], routine.exercises[5], routine.exercises[6], routine.exercises[7], routine.exercises[8], routine.exercises[9], routine.exercises[10],
        routine.exercises[11], routine.exercises[12], routine.exercises[13], routine.exercises[14], days)

    }

    fun notifyRoutineReady(context: Context) {


        if(readyRoutines == currentUserRoutineIds){
            loginFireBase(context as Activity)
        }
        readyRoutines++
    }

    fun findExerciseById(id: Int): Exercise{

        for(routine in getRoutines()){
            for (exercise in routine.exercises_class){
                if(exercise.id == id){
                    return exercise
                }
            }
        }

        return Exercise(0, 0, "", 0, "", 0, ArrayList(), ArrayList())

    }

    fun editUserExercise(exercise: Exercise, context: Context){
        baseDatos.editUserExercise(exercise, context)
    }

    fun getRoutinesOnDate(d: Date): List<Routine>{

        var cal:Calendar = Calendar.getInstance()
        cal.time = d
        var day = cal.get(Calendar.DAY_OF_WEEK)
        day = day - 2
        if(day == -1){day = 6}

        var routines: ArrayList<Routine> = ArrayList()
        for(routine in getRoutines()){

            var days = routine.days

            for(x in 0 until day){
                days /= 2
            }

            if(days % 2 == 1){
                routines.add(routine)
            }

        }

        return routines

    }

    fun getRoutineIndexById(id: Int): Int{

        var index:Int = 0

        for (routine in getRoutines()){
            if(routine.id == id){
                return index
            }
            index += 1
        }

        return -1

    }

    fun loginFireBase(a: Activity){

        mAuth = FireHelper.AuthInit()
        db = FireHelper.FirebaseInit()

        mAuth.signInWithEmailAndPassword(
            currentUser!!.email,
            "123456"
        )
            .addOnCompleteListener(a) { task ->
                if (task.isSuccessful) {
                    //to update a name
                    val crrntUser = FirebaseAuth.getInstance().currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(currentUser!!.userName)
                        .build()

                    crrntUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("TAG", "User profile updated.")
                            }
                        }
                    login(a, passwordInput)
                } else {
                    // If sign in fails, display a message to the user.
                    if(a is LoginActivity){a.btn_login.isEnabled = true}
                    Toast.makeText(
                        a, "Firebase Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


    }

    fun matchRoutine(baseContext: Context, toString: String, adapter: RoutineSearchAdapter) {
        baseDatos.matchRoutine(baseContext, toString, adapter)
    }

    fun addExistingRoutine(routine: Routine, context: Context) {

        var l = getRoutines().size

        if(l >= 5) {
            Toast.makeText(context, "You can have a maximum of 5 routines.", Toast.LENGTH_SHORT)
            return
        }

        when(l){
            0 -> currentUser!!.routine1 = routine
            1 -> currentUser!!.routine2 = routine
            2 -> currentUser!!.routine3 = routine
            3 -> currentUser!!.routine4 = routine
            4 -> currentUser!!.routine5 = routine
        }

        currentUser!!.numberOfRoutines++

        for((i, x) in routine.exercisesDesc.withIndex()){
            if(x != 0){

                var ex = Exercise(0, x, "", 0, "", 0, ArrayList(), ArrayList())
                routine.exercises_class.add(ex)
                ex.routine = routine

                getExerciseDescription(ex, context)
                registerExistingExercise(ex, context, i)

            }
        }

        routine.notifyExerciseReadyExisting(context)

    }

    private fun registerExistingExercise(ex: Exercise, context: Context, idx: Int) {
        baseDatos.registerExistingExercise(ex, context, idx)
    }

    private fun getExerciseDescription(ex: Exercise, context: Context) {
        baseDatos.getExerciseDescription(ex, context)
    }

    fun uploadRoutine(routine: Routine, context: SearchRoutines) {

        baseDatos.guardarRutinaUsuario(context, routine.classid, routine.days, routine.exercises[0], routine.exercises[1], routine.exercises[2], routine.exercises[3], routine.exercises[4], routine.exercises[5],
            routine.exercises[6], routine.exercises[7], routine.exercises[8], routine.exercises[9], routine.exercises[10], routine.exercises[11], routine.exercises[12], routine.exercises[13], routine.exercises[14])

    }


}