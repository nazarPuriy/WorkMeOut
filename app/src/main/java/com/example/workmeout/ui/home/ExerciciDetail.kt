package com.example.workmeout.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.example.workmeout.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.PointsGraphSeries

class ExerciciDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercici_detail)

        val graphView: GraphView = findViewById(R.id.grafic);

        val series: PointsGraphSeries<DataPoint> = PointsGraphSeries<DataPoint>(getDataPoints());

        graphView.addSeries(series);
    }

    fun getDataPoints(): Array<DataPoint>{
        val primer: DataPoint = DataPoint(0.0,1.0)
        val segon: DataPoint = DataPoint(1.0,2.0)
        val tercer: DataPoint = DataPoint(2.0,3.0)
        val quart: DataPoint = DataPoint(3.0,5.0)
        val cinc: DataPoint = DataPoint(4.0,1.0)

        val dp: Array<DataPoint> = arrayOf(primer,segon,tercer,quart,cinc)

        return dp;

    }
}
