package com.example.workmeout.model

class User {
    //Todos los atributos del usuario
    var userName: String  //Utilizaremos este como identificador único de usuario.
    var name:String
    var password: String
    var email: String
    var phoneNumber: Int
    var age: Int
    var sex : Boolean
    var weight: Int
    var height: Int
    var numberOfRoutines : Int
    var tempRoutineIndex : Int
    var currentRutineIndex : Int
    lateinit var routine1 : Routine
    lateinit var routine2 : Routine
    lateinit var routine3 : Routine
    lateinit var routine4 : Routine
    lateinit var routine5 : Routine

    //Constructor principal de la clase usuario
    constructor(userName: String,name:String, password: String, email: String, phoneNumber: Int, age: Int, sex: Boolean, weight : Int, height : Int){
        this.userName = userName
        this.name = name
        this.password = password
        this.email = email
        this.phoneNumber = phoneNumber
        this.age = age
        this.sex = sex;
        this.weight = weight
        this.height = height
        numberOfRoutines = 0
        tempRoutineIndex = 0
        currentRutineIndex = 0
    }

    /* SI TODO VA BIEN VALE VERGAAA
    fun constructRoutines(nroutine1 : Routine?,nroutine2 : Routine?, nroutine3 : Routine?, nroutine4 : Routine?, nroutine5 : Routine?){
        if (nroutine1 != null) {
            routine1 = nroutine1
            if(nroutine2 != null) {
                routine2 = nroutine2
                if(nroutine3 != null) {
                    routine3 = nroutine3
                    if(nroutine4 != null) {
                        routine4 = nroutine4
                        if(nroutine5 != null) {
                            routine5 = nroutine5
                            numberOfRoutines = 5
                        }else{
                            numberOfRoutines=4
                        }
                    }else{
                        numberOfRoutines=3
                    }
                }else{
                    numberOfRoutines = 2
                }
            }else{
                numberOfRoutines = 1
            }
        }else{
            numberOfRoutines = 0
        }
    }
    */

}