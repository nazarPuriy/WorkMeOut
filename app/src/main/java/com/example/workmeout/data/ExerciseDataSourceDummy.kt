package com.example.workmeout.data

import com.example.workmeout.model.Exercise

class ExerciseDataSourceDummy{

    companion object{

        fun createDataSet(): ArrayList<Exercise>{
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