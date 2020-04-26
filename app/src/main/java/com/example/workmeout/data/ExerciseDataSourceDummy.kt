package com.example.workmeout.data

import com.example.workmeout.model.Exercise
import com.example.workmeout.model.Routine
import java.util.Date

class ExerciseDataSourceDummy{

    companion object{

        fun createDataSet(): Routine {
            val list = ArrayList<Exercise>()
            list.add(
                Exercise(
                    0,
                    10,
                    "Bench Press",
                    15,
                    true,
                    Date()
                )
            )
            list.add(
                Exercise(
                    1,
                    12,
                    "Squats",
                    8,
                    false,
                    Date()
                )
                    )
            list.add(
                Exercise(
                    2,
                    22,
                    "Pullover",
                    8,
                    false,
                    Date()
                )
            )

            var routine: Routine =
                Routine("Routine 1", "Mon, Thu, Sat")
            routine.exerciseList = list

            return routine
        }
    }
}