package com.example.workmeout.data

import com.example.workmeout.model.ExerciseOLD
import com.example.workmeout.model.RoutineOLD
import java.util.Date

class ExerciseDataSourceDummy{

    companion object{

        fun createDataSet(): RoutineOLD {
            val list = ArrayList<ExerciseOLD>()
            list.add(
                ExerciseOLD(
                    0,
                    10,
                    "Bench Press",
                    15,
                    true,
                    Date()
                )
            )
            list.add(
                ExerciseOLD(
                    1,
                    12,
                    "Squats",
                    8,
                    false,
                    Date()
                )
                    )
            list.add(
                ExerciseOLD(
                    2,
                    22,
                    "Pullover",
                    8,
                    false,
                    Date()
                )
            )

            var routineOLD: RoutineOLD =
                RoutineOLD("Routine 1", "Mon, Thu, Sat")
            routineOLD.exerciseOLDList = list

            return routineOLD
        }
    }
}