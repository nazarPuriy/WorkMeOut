package com.example.workmeout.ui.sport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.model.Exercise
import com.example.workmeout.ui.MainActivity
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() {
    lateinit var tvName: TextView;
    lateinit var changing: DataPoint;
    lateinit var cambiador: NumberPicker;
    lateinit var botoChange: Button;
    lateinit var botoReps : Button;
    lateinit var changeTXT : TextView;
    lateinit var graphView: GraphView;
    lateinit var punts: Array<DataPoint>;
    lateinit var series: LineGraphSeries<DataPoint>
    lateinit var npReps: NumberPicker
    lateinit var exName: String
    lateinit var exercise: Exercise
    var form : LabelFormatterMio = LabelFormatterMio()
    var dayList: ArrayList<Date> = ArrayList<Date>() //Llista dels dies de l'exercici
    var weightsList: ArrayList<Int> = ArrayList<Int>() //Llista de pesos de l'exercici
    var exWeight: Double = 0.toDouble()
    var exReps: Int = 0
    val calendar: Calendar = Calendar.getInstance();
    val today: Date = calendar.time;
    val sdf: SimpleDateFormat = SimpleDateFormat("dd:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        exercise = Controlador.findExerciseById(getIntent().getIntExtra("exerciseId", 0))
        exName = exercise.name
        dayList = exercise.days;
        weightsList = exercise.weights
        exWeight = exercise.weight.toDouble()
        exReps = exercise.reps

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        tvName = findViewById(R.id.textExerciseName)
        botoChange = findViewById(R.id.buttonChange)
        cambiador = findViewById(R.id.numpicker_1)
        npReps = findViewById(R.id.np_reps)
        botoReps = findViewById(R.id.buttonChangeReps)
        changeTXT = findViewById(R.id.text_weight)

        tvName.setText(exName);
        npReps.maxValue = 100
        npReps.minValue = 0
        npReps.value = exercise.reps
        cambiador.maxValue = 100
        cambiador.minValue = 0
        cambiador.visibility = View.INVISIBLE;
        changeTXT.visibility = View.INVISIBLE;
        botoChange.isEnabled = false
        botoReps.isEnabled = false
        graphView = findViewById(R.id.grafic);
        getDataPoints(weightsList,dayList)
        series = LineGraphSeries<DataPoint>(punts);
        series.isDrawDataPoints = true
        series.dataPointsRadius = 15.0F
        series.thickness = 8
        graphView.addSeries(series);
        acabargrafic(graphView,dayList)

        tapeoGraf()

        npReps.setOnValueChangedListener{ numberPicker: NumberPicker, i: Int, i1: Int ->
            botoReps.isEnabled = true
        }


    }

    fun tapeoGraf(){
        series.setOnDataPointTapListener(OnDataPointTapListener(
            (@Override
            fun(series: LineGraphSeries<DataPoint>, dataPoint: DataPoint){
                Toast.makeText(this,dataPoint.y.toString(),Toast.LENGTH_LONG).show()
                changing = dataPoint
                activarCambiarPeso()
            }) as (Series<DataPointInterface>, DataPointInterface) -> Unit
        ))
    }

    fun activarCambiarPeso(){
        cambiador.value = changing.y.toInt()
        cambiador.visibility = View.VISIBLE
        changeTXT.visibility = View.VISIBLE
        botoChange.isEnabled = true
    }

    fun cambiarPeso(view: View){
        for (punto in punts.indices){
            if(punts.get(punto) == changing){
                val nouData = DataPoint(changing.x,cambiador.value.toDouble())
                punts.set(punto,nouData)
                editExercise(changing.x,cambiador.value)
            }
        }
        graphView.removeAllSeries()
        series = LineGraphSeries<DataPoint>(punts);
        series.isDrawDataPoints = true
        series.dataPointsRadius = 15.0F
        series.thickness = 8
        graphView.addSeries(series);
        cambiador.visibility = View.INVISIBLE
        changeTXT.visibility = View.INVISIBLE
        botoChange.isEnabled = false
        tapeoGraf()

    }

    fun returnButton(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



    fun getDataPoints(weights: ArrayList<Int>, dies: ArrayList<Date>){


        var prova: ArrayList<DataPoint> = ArrayList<DataPoint>()
        if(dayList.size==0){
            punts = prova.toTypedArray()
        }else{
            for (i in 0 until dies.size){

                var primer: DataPoint = DataPoint(dies[i],weights[i].toDouble())
                prova.add(primer)
            }


            punts = prova.toTypedArray()
        }



    }

    fun acabargrafic(graphView: GraphView, dies: ArrayList<Date>){
        //val objj: DateAsXAxisLabelFormatter = DateAsXAxisLabelFormatter(this)
        if(dayList.size == 0){
            graphView.gridLabelRenderer.labelFormatter = form
            graphView.gridLabelRenderer.numHorizontalLabels = dies.size
            graphView.viewport.isXAxisBoundsManual = true
        }else{
            graphView.gridLabelRenderer.labelFormatter = form
            var last: Long = today.time
            for (i in dies){
                if(dies.indexOf(i)==dies.size-1){
                    last = i.time
                }
            }
            //graphView.gridLabelRenderer.numHorizontalLabels = ((last-dies[0].time)/86400000).toInt()+1;
            //graphView.viewport.setMinX(dies[0].time.toDouble())
            //graphView.viewport.setMaxX(last.toDouble())
            graphView.gridLabelRenderer.numHorizontalLabels = setHoritzontalLabels(last,dies)
            setMAXMIN(last, dies)
            graphView.gridLabelRenderer.textSize = 20f
            graphView.viewport.isXAxisBoundsManual = true
            graphView.viewport.isScrollable = true
        }

        graphView.viewport.setMinY(0.0)
        graphView.viewport.setMaxY(100.0)
        graphView.viewport.isYAxisBoundsManual = true

        graphView.gridLabelRenderer.setHumanRounding(false)
    }

    fun setHoritzontalLabels(last:Long,dies: ArrayList<Date>):Int{
        var a : Int = (((last-dies[0].time)/86400000).toInt()+1)
        if(a<=7){
            return a
        }else{
            return 8
        }
    }

    fun setMAXMIN(last:Long,dies:ArrayList<Date>){
        var a : Int = (((last-dies[0].time)/86400000).toInt()+1)
        if(a<=7){
            graphView.viewport.setMinX(dayList[0].time.toDouble())
            graphView.viewport.setMaxX(last.toDouble())
        }else{
            graphView.viewport.setMinX(dayList[0].time.toDouble())
            graphView.viewport.setMaxX(sumarRestarDiasFecha(dayList[0],7).time.toDouble())
        }

    }

    fun openDescription(view:View){
        val descriptionIntent = Intent(this,ExerciseDescriptionActivity::class.java)
        descriptionIntent.putExtra("title", exName)
        descriptionIntent.putExtra("reps", exReps)
        descriptionIntent.putExtra("MODE","0")
        startActivity(descriptionIntent)
    }

    fun sumarRestarDiasFecha( fecha: Date, dias: Int): Date{
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = fecha
        calendar.add(Calendar.DAY_OF_YEAR,dias)
        print(calendar.time)
        return calendar.time
    }

    fun editExercise(dataDouble: Double, newValue: Int) {

        var posicio : Int = 0
        for(i in dayList){
            if(i.time.toDouble()==dataDouble){
                posicio = dayList.indexOf(i)
            }
        }
        weightsList[posicio] = newValue
        exercise.weights = weightsList

        //demanem a la base de dades que editi
        Controlador.editUserExercise(exercise, this)
    }

    fun editExercise2(view: View){
        exercise.reps = npReps.value
        Controlador.editUserExercise(exercise,this)
        botoReps.isEnabled = false;
    }


}
