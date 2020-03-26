package com.example.workmeout.data

import com.example.workmeout.model.Exercise

class ExerciseDataSourceDummy{

    //Para tal de que sea un método de clase
    companion object {

        fun createDataSet(): ArrayList<Exercise> {
            val list = ArrayList<Exercise>()
            list.add(
                Exercise(
                    10,
                    "Bench Press",
                    15
                )
            )
            list.add(
                Exercise(
                    12,
                    "Squats",
                    8
                )
            )
            list.add(
                Exercise(
                    22,
                    "Pullover",
                    8
                )
            )

            return list
        }
    }
}