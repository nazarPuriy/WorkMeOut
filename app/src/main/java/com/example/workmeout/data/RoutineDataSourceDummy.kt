package com.example.workmeout.data

import com.example.workmeout.ChatPackage.Model.Routine

class RoutineDataSourceDummy{

    companion object{

        fun createDataSet(): ArrayList<Routine>{
            val list = ArrayList<Routine>()
            list.add(
                Routine(
                    "Routine 1",
                    "Mon, Thu, Sat"
                )
            )
            list.add(
                Routine(
                    "Routine 2",
                    "Tue, Fri"
                )
            )

            return list
        }
    }
}