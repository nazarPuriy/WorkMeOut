package com.example.workmeout.model

import com.example.workmeout.model.Routine

class Usuari(id:Int,userName: String,password: String,email: String,phoneNumber: Int,age: Int, weight: Int, height: Int, RutineList: ArrayList<Routine>){

    var id: Int = id;
    var userName: String = userName
    var password: String = password
    var email: String = email
    var phoneNumber: Int = phoneNumber
    var age: Int = age
    var weight: Int = weight;
    var height: Int = height;
    var RutineList: ArrayList<Routine> = RutineList

    constructor(id:Int, userName: String, password: String, email: String, phoneNumber: Int, age: Int, RutineList: ArrayList<Routine>) : this(id,userName,password, email, phoneNumber, age, 0,0,ArrayList())

    fun addRutine(Rutine: Routine){
        if(!isRutine(Rutine.name)){
            RutineList.add(Rutine)
        }else{
            //NOTIFICACION QUE NO SE PUEDE AÑADIR PORQUE YA ESTA
        }
    }

    fun deleteRutine(rutineName: String){
        var positionRutine = rutinePosition(rutineName);
        if(positionRutine == -1){
            //Mensaje de que no existe la Routine i no se puede eliminar
        }else{
            RutineList.removeAt(positionRutine)
        }
    }

    fun isRutine(RutineName: String): Boolean {
        val RutineListIterator = RutineList.iterator()
        while(RutineListIterator.hasNext()){
            if(RutineListIterator.next().name == RutineName)
                return true
        }
        return false
    }

    fun rutinePosition(RutineName: String): Int{
        val RutineListIterator = RutineList.iterator()
        while(RutineListIterator.hasNext()){
            var mom : Routine = RutineListIterator.next()
            if(mom.name == RutineName){
                return RutineList.indexOf(mom)
            }
        }
        return -1;
    }
}