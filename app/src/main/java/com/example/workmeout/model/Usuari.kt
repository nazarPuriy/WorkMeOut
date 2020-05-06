package com.example.workmeout.model

class Usuari(id:Int, userName: String, password: String, email: String, phoneNumber: Int, age: Int, weight: Int, height: Int, rutineList: ArrayList<RoutineOLD>){


    var userName: String = userName
    var password: String = password
    var email: String = email
    var phoneNumber: Int = phoneNumber
    var age: Int = age
    var weight: Int = weight;
    var height: Int = height;
    var rutineList: ArrayList<RoutineOLD> = rutineList

    constructor(id:Int, userName: String, password: String, email: String, phoneNumber: Int, age: Int, rutineList: ArrayList<RoutineOLD>) : this(id,userName,password, email, phoneNumber, age, 0,0,
        rutineList
    )
    constructor(email:String, password:String):this(0,"a",password,email,0,0,ArrayList())
    fun addRutine(rutine: RoutineOLD){
        if(!isRutine(rutine.name)){
            rutineList.add(rutine)
        }else{
            //NOTIFICACION QUE NO SE PUEDE AÑADIR PORQUE YA ESTA
        }
    }

    fun deleteRutine(rutineName: String){
        var positionRutine = rutinePosition(rutineName);
        if(positionRutine == -1){
            //Mensaje de que no existe la Routine i no se puede eliminar
        }else{
            rutineList.removeAt(positionRutine)
        }
    }

    fun isRutine(RutineName: String): Boolean {
        val RutineListIterator = rutineList.iterator()
        while(RutineListIterator.hasNext()){
            if(RutineListIterator.next().name == RutineName)
                return true
        }
        return false
    }

    fun rutinePosition(RutineName: String): Int{
        val RutineListIterator = rutineList.iterator()
        while(RutineListIterator.hasNext()){
            var mom : RoutineOLD = RutineListIterator.next()
            if(mom.name == RutineName){
                return rutineList.indexOf(mom)
            }
        }
        return -1;
    }
}