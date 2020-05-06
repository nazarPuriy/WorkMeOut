package com.example.workmeout.data

import com.example.workmeout.model.RoutineOLD

class RoutineDataSourceDummy{

    companion object{

        fun createDataSet(): ArrayList<RoutineOLD>{
            val list = ArrayList<RoutineOLD>()
            list.add(
                RoutineOLD(
                    "Routine 1",
                    "Mon, Thu, Sat"
                )
            )
            list.add(
                RoutineOLD(
                    "Routine 2",
                    "Tue, Fri"
                )
            )

            return list
        }
    }
}