package com.example.workmeout.data

import com.example.workmeout.chatPackage.model.Exercise
import com.example.workmeout.chatPackage.model.Routine

class ExerciseDataSourceDummy{

    companion object{

        fun createDataSet(): Routine {
            val list = ArrayList<Exercise>()
            list.add(
                Exercise(
                    10,
                    "Bench Press",
                    15,
                    true
                )
            )
            list.add(
                    Exercise(
                        12,
                        "Squats",
                        8,
                        false
                    )
                    )
            list.add(
                Exercise(
                    22,
                    "Pullover",
                    8,
                    false
                )
            )

            var routine:Routine = Routine("Routine 1", "Mon, Thu, Sat")
            routine.exercises = list

            return routine
        }
    }
}