package com.example.workmeout.model

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Exercise(id:Int, classId:Int, name:String, reps:Int, description: String, weight: Int, days:ArrayList<Date>, weights:ArrayList<Int>) {

    var id: Int = id
    var classId: Int = classId
    var name: String = name
    var description: String = description
    var reps: Int = reps
    var weight: Int = weight
    var days: ArrayList<Date> = days
    var weights: ArrayList<Int> = weights
    lateinit var routine: Routine
    var dataReady = false

    /**
     * Funció que afegirà un event a l'exercici, per això rep un Date i un Int que seran la data de
     * l'exercici i el pes que s'ha fet en aquesta data.
     * La funció és simple, si la data ja esta marcada, fara un update, si no, l'afegirà.
     */
    fun addEvent(date: Date, w: Int){
        if(isDate(date)){
            updateEvent(date,w)
        }else{
            newEvent(date,w)
        }
    }

    /**
     * En cas que s'hagi de crear un nouEvent, el primer que farem es descobrir quina es la nova posició
     * que li pertocarà gràcies a la funció "newEvent".
     * A continuació, en cas que la llista encara permet afegir nous events sense haver-ne de borrar altres, és a dir
     * que hi hagin menys de 7 elements en total, s'afegiran la nova data i el nou pes a la posició correponent.
     *
     * En cas que ja hi hagin 7 elements, si la posició que hem d'afegir es diferent de la primera, s'afegiran els nous
     * valors de date i pes a les posicions corresponents i a continuació es borrarà la primera posició per a que hi
     * segueixin quedan només 7 elements.
     */
    private fun newEvent(date: Date, w: Int) {
        var newPosition: Int = searchNewPosition(date)
        if(lessThan7()){
            if(newPosition == days.size){
                days.add(date)
                weights.add(w)
            }else{
                days.add(newPosition,date)
                weights.add(newPosition,w)
            }
        }else{
            if(newPosition != 0){
                if(newPosition == days.size){
                    days.add(date)
                    weights.add(w)
                }else{
                    days.add(newPosition,date)
                    weights.add(newPosition,w)
                }
                days.removeAt(0)
                weights.removeAt(0)
            }
        }
    }

    /**
     * En cas que vulguem fer un update d'un element. Busquem la posició on es troba a la llista de dies mitjantçant
     * la funció eventPosition i actualitzem el pes a la llista de pesos.
     */
    private fun updateEvent(date:Date, w: Int){
        var position:Int = eventPosition(date)
        weights[position] = w;
    }


    /**
     * Funció que ens permet comprobar si una data ja es troba a la llista.
     */
    private fun isDate(date:Date):Boolean{
        val formatter = SimpleDateFormat("dd/MM/yyyy")

        for(x in 0 until days.size){

            if(formatter.format(days.get(x)).equals(formatter.format(date))){
                return true
            }
        }
        return false
    }

    /**
     * Funcio que ens retorna l'index d'una data de la llista dies.
     */
    private fun eventPosition(date: Date):Int{
        val formatter = SimpleDateFormat("dd/MM/yyyy")

        for(x in 0 until days.size){

            if(formatter.format(days.get(x)) == formatter.format(date)){
                return x
            }
        }
        return -1
    }

    /**
     * Funció que ens diu si hi ha menys de 7 elements a la llista dies.
     */
    private fun lessThan7():Boolean{
        if(days.size<7){
            return true;
        }
        return false;
    }

    /**
     * Funció que es fa servir per trobar la nova posició en cas d'afegir un nou element. Si
     * la data que estem intentant afegir a la llista és despres que la última, directament passarem
     * la llargada de la llista, donat que s'afegirà en la última posició.
     *
     * En cas que no sigui així, recorrem tota la llista d'esquerra a dreta
     * i retornem la primera posició en que la nostra data sigui anterior a una de la
     * llista per a que ara aquesta es coloqui en aquesta posició i la de la llista
     * passi a la següent.
     *
     * Ex: Comencem a recorrer la llista i de moment la nostra data no es abans que la de la
     * posició 0 i 1, pero arribem a la posició 2 i si que es abans que aquesta. Aleshores
     * retornem la posició 2 per què és on haurà d'anar.
     */
    private fun searchNewPosition(date:Date):Int{

        if(days.size == 0){
            return 0
        }

        if(date.after(days[days.size-1])){
            return days.size
        }
        for (i in 0 until (days.size)){
            if(date.before(days[i])){
                return i;
            }
        }
        return -1; //No passarà
    }

    fun weightAtDate(date: Date): Int {
        val formatter = SimpleDateFormat("dd/MM/yyyy")

        for(x in 0 until days.size){

            if(formatter.format(days.get(x)) == formatter.format(date)){
                return weights.get(x)
            }
        }

        return 0
    }

    fun isDoneAtDate(date: Date): Boolean {
        val formatter = SimpleDateFormat("dd/MM/yyyy")

        for(d: Date in days){
            if(formatter.format(d).equals(formatter.format(date))){
                return true
            }
        }

        return false
    }

    fun removeEvent(date: Date) {

        var index = -1
        val formatter = SimpleDateFormat("dd/MM/yyyy")

        for(x in 0 until days.size){
            if(formatter.format(days.get(x)).equals(formatter.format(date))){
                index = x
            }
        }

        if(index == -1){
            return
        }

        days.removeAt(index)
        weights.removeAt(index)

    }

    fun isReady():Boolean{
        return dataReady && id != 0
    }

    fun notifyRoutine(context: Context){
        routine.notifyExerciseReadyExisting(context)
    }




}