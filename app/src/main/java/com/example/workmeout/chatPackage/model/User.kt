package com.example.workmeout.chatPackage.model

data class User(val name: String?,//stos interrogantes es porque si no no iba
                val bio: String?,//o mismo este interrogrante
                val profilePicturePath: String?,
                val registrationTokens: MutableList<String>
) {
    constructor() : this("", "", null, mutableListOf())

}