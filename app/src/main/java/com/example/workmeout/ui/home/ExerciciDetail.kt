package com.example.workmeout.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView
import com.example.workmeout.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.PointsGraphSeries
import java.util.*

class ExerciciDetail : AppCompatActivity() {
    lateinit var tvName: TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercici_detail)
        tvName = findViewById(R.id.textExerciseName)
        var exName: String = getIntent().getStringExtra("exName")
        var exWeight: Double = getIntent().getDoubleExtra("exWeight",0.0)

        tvName.setText(exName);
        val graphView: GraphView = findViewById(R.id.grafic);

        val series: PointsGraphSeries<DataPoint> = PointsGraphSeries<DataPoint>(getDataPoints(exWeight));

        graphView.addSeries(series);
    }

    fun getDataPoints(args: Double): Array<DataPoint>{
        val calendar: Calendar = Calendar.getInstance();
        val today: Date = calendar.time;


        val primer: DataPoint = DataPoint(0.0,1.0)
        val segon: DataPoint = DataPoint(1.0,2.0)
        val tercer: DataPoint = DataPoint(2.0,3.0)
        val quart: DataPoint = DataPoint(3.0,5.0)
        val cinc: DataPoint = DataPoint(4.0,args)

        val dp: Array<DataPoint> = arrayOf(primer,segon,tercer,quart,cinc)

        return dp;

    }


}
