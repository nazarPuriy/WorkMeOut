/**package com.example.workmeout.ui.sport

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
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.model.Exercise
import com.example.workmeout.ui.MainActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() {
    lateinit var tvName: TextView; //Text view que s'assigna més endavant al nom de l'exercici
    lateinit var changing: DataPoint; //Data Point per permetre fer el change
    lateinit var cambiador: NumberPicker; //S'assigna al number picker del pes..
    lateinit var botoChange: Button; //S'assigna al botò que es clica per fer el change de pes.
    lateinit var graphView: GraphView; //Grafic
    lateinit var punts: Array<DataPoint>; //Array de data points que necessita el grafic
    lateinit var series: PointsGraphSeries<DataPoint> //Estructura de data Points que necessita el grafic.
    lateinit var npReps: NumberPicker //S'assigna al number picker de les repeticions
    lateinit var exName: String //Nom de l'exercici.
    lateinit var exercise: Exercise //EXERCICI AL QUAL ENS PASSEN PER CONTROLADOR.
    lateinit var days: ArrayList<Date> //Llista dels dies de l'exercici
    lateinit var weights: ArrayList<Int> //Llista de pesos de l'exercici
    var exReps: Int = 0 //Nombre de repeticions de l'exercici
    val calendar: Calendar = Calendar.getInstance(); //Calendari necessari per fer operacions entre dates.
    val today: Date = calendar.time;

    override fun onCreate(savedInstanceState: Bundle?) {
        exercise = Controlador.findExerciseById(getIntent().getIntExtra("exerciseId", 0))
        exName = exercise.name
        days = exercise.days
        weights = exercise.weights
        exReps = exercise.reps

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        tvName = findViewById(R.id.textExerciseName)
        botoChange = findViewById(R.id.buttonChange)
        cambiador = findViewById(R.id.numpicker_1)
        npReps = findViewById(R.id.np_reps)

        tvName.setText(exName);
        npReps.maxValue = 100
        npReps.minValue = 0
        npReps.value = exercise.reps
        cambiador.maxValue = 100
        cambiador.minValue = 0
        cambiador.visibility = View.INVISIBLE;
        botoChange.isEnabled = false
        graphView = findViewById(R.id.grafic);
        getDataPoints(weights,days)
        series = PointsGraphSeries<DataPoint>(punts);

        graphView.addSeries(series);
        acabargrafic(graphView,today)

        tapeoGraf()

        npReps.setOnValueChangedListener{ numberPicker: NumberPicker, i: Int, i1: Int ->
            editExercise()
        }


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

    //todo gerard cridar a la funció editExercise
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


    //todo gerard omplir amb les dades de la variable exercise (dos arraylists amb dates i pesos)
    fun getDataPoints(weights: ArrayList<Int>, days: ArrayList<Date>){

        var prova: ArrayList<DataPoint> = ArrayList()
        for (i in 0 until days.size-1){
            prova.add(DataPoint(days[i],weights[i].toDouble()))
        }
        punts = prova.toArray() as Array<DataPoint>
        //De momento solo salen los ultimos 5 dias.
        //Las Y deberan de ser los pesos de la base de datos.
        /*val primer: DataPoint = DataPoint(sumarRestarDiasFecha(today,-4),0.0)
        val segon: DataPoint = DataPoint(sumarRestarDiasFecha(today,-3),8.0)
        val tercer: DataPoint = DataPoint(sumarRestarDiasFecha(today,-2),10.0)
        val quart: DataPoint = DataPoint(sumarRestarDiasFecha(today,-1),32.0)
        val cinc: DataPoint = DataPoint(today,args)

        punts = arrayOf(primer,segon,tercer,quart,cinc)*/

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

        graphView.viewport.setMinX(days[0].time.toDouble())
        graphView.viewport.setMinY(0.0)
        graphView.viewport.setMaxX(days[days.size-1].time.toDouble())
        graphView.viewport.setMaxY(100.0) //TODO CAMBIAR AL MAXIMO DE LOS PESOS
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

    fun editExercise(){

        //todo gerard quan editis el gràfic has de modificar la variable exercise amb els pesos i dies corresponents
        exercise.reps = npReps.value

        //demanem a la base de dades que editi
        Controlador.editUserExercise(exercise, this)



    }


}**/
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
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.model.Exercise
import com.example.workmeout.ui.MainActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() {
    lateinit var tvName: TextView;
    lateinit var changing: DataPoint;
    lateinit var cambiador: NumberPicker;
    lateinit var botoChange: Button;
    lateinit var graphView: GraphView;
    lateinit var punts: Array<DataPoint>;
    lateinit var series: PointsGraphSeries<DataPoint>
    lateinit var npReps: NumberPicker
    lateinit var exName: String
    lateinit var exercise: Exercise
    var dayList: ArrayList<Date> = ArrayList<Date>() //Llista dels dies de l'exercici
    var weightsList: ArrayList<Int> = ArrayList<Int>() //Llista de pesos de l'exercici
    var exWeight: Double = 0.toDouble()
    var exReps: Int = 0
    val calendar: Calendar = Calendar.getInstance();
    val today: Date = calendar.time;

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

        tvName.setText(exName);
        npReps.maxValue = 100
        npReps.minValue = 0
        npReps.value = exercise.reps
        cambiador.maxValue = 100
        cambiador.minValue = 0
        cambiador.visibility = View.INVISIBLE;
        botoChange.isEnabled = false
        graphView = findViewById(R.id.grafic);
        getDataPoints(weightsList,dayList)
        series = PointsGraphSeries<DataPoint>(punts);

        graphView.addSeries(series);
        acabargrafic(graphView,today)

        tapeoGraf()

        npReps.setOnValueChangedListener{ numberPicker: NumberPicker, i: Int, i1: Int ->
            editExercise()
        }


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

    //todo gerard cridar a la funció editExercise
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


    //todo gerard omplir amb les dades de la variable exercise (dos arraylists amb dates i pesos)
    fun getDataPoints(weights: ArrayList<Int>, days: ArrayList<Date>){
        /*
        //De momento solo salen los ultimos 5 dias.
        //Las Y deberan de ser los pesos de la base de datos.
        val primer: DataPoint = DataPoint(sumarRestarDiasFecha(today,-4),0.0)
        val segon: DataPoint = DataPoint(sumarRestarDiasFecha(today,-3),8.0)
        val tercer: DataPoint = DataPoint(sumarRestarDiasFecha(today,-2),10.0)
        val quart: DataPoint = DataPoint(sumarRestarDiasFecha(today,-1),32.0)
        val cinc: DataPoint = DataPoint(today,args)*/
        var prova: ArrayList<DataPoint> = ArrayList()
        for (i in 0 until days.size-1){
            var primer: DataPoint = DataPoint(days[i],weights[i].toDouble())
            prova.add(primer)
        }
        punts = arrayOf(for(value in prova))


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

    fun editExercise(){

        //todo gerard quan editis el gràfic has de modificar la variable exercise amb els pesos i dies corresponents
        exercise.reps = npReps.value

        //demanem a la base de dades que editi
        Controlador.editUserExercise(exercise, this)



    }


}
