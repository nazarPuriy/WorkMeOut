package com.example.workmeout.model

import com.example.workmeout.model.Message
import com.example.workmeout.model.MessageType
import java.util.*

data class TextMessage(
    val text: String,
    override val time: Date,
    override val senderId: String,
    override val type: String = MessageType.TEXT):
    Message {
    constructor():this("", Date(), "")

}