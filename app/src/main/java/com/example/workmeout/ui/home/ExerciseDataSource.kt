package com.example.workmeout.ui.home

class ExerciseDataSource{

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