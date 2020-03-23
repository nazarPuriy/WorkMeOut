package com.example.workmeout.ui.dashboard

class RoutineDataSource{

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