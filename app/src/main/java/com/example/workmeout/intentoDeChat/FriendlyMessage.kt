package com.example.workmeout.intentoDeChat

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class FriendlyMessage(
    var text: String? = "",
    var type: String? = "",
    var timeStamp : String? = "",
    var fromUserId : String? = ""
)