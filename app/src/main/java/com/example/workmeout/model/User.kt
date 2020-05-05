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
    }

}