package com.example.workmeout.ui.sport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.component3
import com.example.workmeout.R
import com.example.workmeout.ui.MainActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.*
import java.util.*

class ExerciseActivity : AppCompatActivity() {
    lateinit var tvName: TextView;
    lateinit var changing: DataPoint;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        tvName = findViewById(R.id.textExerciseName)
        val exName: String = getIntent().getStringExtra("exName")
        val exWeight: Double = getIntent().getDoubleExtra("exWeight",0.0)
        val calendar: Calendar = Calendar.getInstance();
        val today: Date = calendar.time;
        tvName.setText(exName);
        val graphView: GraphView = findViewById(R.id.grafic);

        val series: PointsGraphSeries<DataPoint> = PointsGraphSeries<DataPoint>(getDataPoints(exWeight,today));

        graphView.addSeries(series);
        acabargrafic(graphView,today)

        series.setOnDataPointTapListener(OnDataPointTapListener(
            (@Override
            fun(series: PointsGraphSeries<DataPoint>, dataPoint: DataPoint){
                Toast.makeText(this,dataPoint.y.toString(),Toast.LENGTH_LONG).show()
                changing = dataPoint;
            }) as (Series<DataPointInterface>, DataPointInterface) -> Unit
        ))


    }

    fun returnButton(view: View){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    fun getDataPoints(args: Double, today: Date): Array<DataPoint>{

        //De momento solo salen los ultimos 5 dias.
        //Las Y deberan de ser los pesos de la base de datos.
        val primer: DataPoint = DataPoint(sumarRestarDiasFecha(today,-4),0.0)
        val segon: DataPoint = DataPoint(sumarRestarDiasFecha(today,-3),8.0)
        val tercer: DataPoint = DataPoint(sumarRestarDiasFecha(today,-2),10.0)
        val quart: DataPoint = DataPoint(sumarRestarDiasFecha(today,-1),32.0)
        val cinc: DataPoint = DataPoint(today,args)

        val dp: Array<DataPoint> = arrayOf(primer,segon,tercer,quart,cinc)

        return dp;

    }

    fun sumarRestarDiasFecha( fecha: Date, dias: Int): Date{
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = fecha
        calendar.add(Calendar.DAY_OF_YEAR,dias)
        print(calendar.time)
        return calendar.time
    }

    fun acabargrafic(graphView: GraphView, today: Date){
        val objj: DateAsXAxisLabelFormatter = DateAsXAxisLabelFormatter(this)
        graphView.gridLabelRenderer.setLabelFormatter(objj)
        graphView.gridLabelRenderer.numHorizontalLabels = 5

        graphView.viewport.setMinX(sumarRestarDiasFecha(today,-4).time.toDouble())
        graphView.viewport.setMinY(0.0)
        graphView.viewport.setMaxX(today.time.toDouble())
        graphView.viewport.setMaxY(100.0)
        graphView.viewport.isXAxisBoundsManual = true
        graphView.viewport.isYAxisBoundsManual = true

        graphView.gridLabelRenderer.setHumanRounding(false)
    }



}
