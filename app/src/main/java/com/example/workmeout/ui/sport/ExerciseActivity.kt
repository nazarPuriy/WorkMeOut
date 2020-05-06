package com.example.workmeout.ui.sport

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
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
    lateinit var cambiador: NumberPicker;
    lateinit var botoChange: Button;
    lateinit var tvReps: TextView;
    lateinit var graphView: GraphView;
    lateinit var punts: Array<DataPoint>;
    lateinit var series: PointsGraphSeries<DataPoint>
    lateinit var exName: String
    var exWeight: Double = 0.toDouble()
    var exReps: Int = 0
    val calendar: Calendar = Calendar.getInstance();
    val today: Date = calendar.time;

    override fun onCreate(savedInstanceState: Bundle?) {
        exName = getIntent().getStringExtra("exName")
        exWeight = getIntent().getDoubleExtra("exWeight",0.0)
        exReps = getIntent().getIntExtra("exReps", 0)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        tvName = findViewById(R.id.textExerciseName)
        botoChange = findViewById(R.id.buttonChange)
        cambiador = findViewById(R.id.numpicker_1)
        tvReps = findViewById(R.id.textReps)

        tvName.setText(exName);
        tvReps.setText("Reps: " + exReps.toString())
        cambiador.maxValue = 100
        cambiador.minValue = 0
        cambiador.visibility = View.INVISIBLE;
        botoChange.isEnabled = false
        graphView = findViewById(R.id.grafic);
        getDataPoints(exWeight,today)
        series = PointsGraphSeries<DataPoint>(punts);

        graphView.addSeries(series);
        acabargrafic(graphView,today)

        tapeoGraf();


    }

    fun tapeoGraf(){
        series.setOnDataPointTapListener(OnDataPointTapListener(
            (@Override
            fun(series: PointsGraphSeries<DataPoint>, dataPoint: DataPoint){

                Toast.makeText(this,dataPoint.y.toString(),Toast.LENGTH_LONG).show()
                changing = dataPoint
                activarCambiarPeso()
            }) as (Series<DataPointInterface>, DataPointInterface) -> Unit
        ))
    }

    fun activarCambiarPeso(){
        cambiador.value = changing.y.toInt()
        cambiador.visibility = View.VISIBLE
        botoChange.isEnabled = true
    }

    fun cambiarPeso(view: View){
        for (punto in punts.indices){
            if(punts.get(punto) == changing){
                val nouData = DataPoint(changing.x,cambiador.value.toDouble())
                punts.set(punto,nouData)
            }
        }
        graphView.removeAllSeries()
        series = PointsGraphSeries<DataPoint>(punts);
        graphView.addSeries(series);
        cambiador.visibility = View.INVISIBLE
        botoChange.isEnabled = false
        tapeoGraf()

    }

    fun returnButton(view: View){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    fun getDataPoints(args: Double, today: Date){

        //De momento solo salen los ultimos 5 dias.
        //Las Y deberan de ser los pesos de la base de datos.
        val primer: DataPoint = DataPoint(sumarRestarDiasFecha(today,-4),0.0)
        val segon: DataPoint = DataPoint(sumarRestarDiasFecha(today,-3),8.0)
        val tercer: DataPoint = DataPoint(sumarRestarDiasFecha(today,-2),10.0)
        val quart: DataPoint = DataPoint(sumarRestarDiasFecha(today,-1),32.0)
        val cinc: DataPoint = DataPoint(today,args)

        punts = arrayOf(primer,segon,tercer,quart,cinc)

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

    fun openDescription(view:View){
        val descriptionIntent = Intent(this,ExerciseDescriptionActivity::class.java)
        descriptionIntent.putExtra("title", exName)
        descriptionIntent.putExtra("reps", exReps)
        descriptionIntent.putExtra("MODE","0")
        startActivity(descriptionIntent)
    }


}
