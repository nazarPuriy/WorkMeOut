package com.example.workmeout.data

import com.example.workmeout.model.Exercise

class ExerciseDataSourceDummy{

    companion object{

        fun createDataSet(): ArrayList<Exercise>{
            val list = ArrayList<Exercise>()
            list.add(
                Exercise(
                    25,
                    "aasdf"
                )
            )
            list.add(
                Exercise(
                    73,
                    "funciona"
                )
            )

            return list
        }
    }
}