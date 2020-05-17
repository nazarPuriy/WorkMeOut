package com.example.workmeout.ui.sport

import com.jjoe64.graphview.DefaultLabelFormatter
import java.text.SimpleDateFormat
import java.util.*

class LabelFormatterMio : DefaultLabelFormatter() {
    var sdf: SimpleDateFormat = SimpleDateFormat("MM/dd")
    var data : Date = Date()
    override fun formatLabel(value: Double, isValueX: Boolean): String {
        if(isValueX){
            data = Date(value.toLong())
            return sdf.format(data)
        }else{
            return super.formatLabel(value, isValueX)
        }

    }
}