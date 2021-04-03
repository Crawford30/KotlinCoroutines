package com.example.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val RESULT_1 = "Result #1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {


            //coroutine scope organises coroutines into groupings,, if one fails it cancels both

            //Group jobs into categories
            //The scopes can be Main==for doing something on the mainthread, Default--doing heavy computation task, IO for input and output(for network and local database)


            //IO, Main, Default
            CoroutineScope(IO).launch {
                fakeApiRequest()

            }


        }
    }


    //========================
    private fun setNewText(input: String) {
        val newText = text.text.toString() + "\n$input"
        text.text = newText
    }

    private suspend fun setTextOnMainThread(input:String){

        //do the work on the main thread

        //withContext in a coroutine will change the context or the coroutine to the one specified
        withContext(Main){

            setNewText(input)

        }

        //Or, the launch will build the new one.

//        CoroutineScope(Main).launch {
//            setNewText(input)
//
//        }


    }


    private suspend fun fakeApiRequest() {
        val result_1 = getResult1FromApi()

       // text.setText(result_1) //will cause the app to crash because we are doing work on a background thread

        //The correct way is
        setTextOnMainThread(result_1)

        println("Debug: ${result_1}")
    }

    private suspend fun getResult1FromApi(): String {
        //suspend when with coroutine will mark the function as asynchronous task

        //https://youtu.be/F63mhZk-1-Y

        logThread("getSystemServiceName")

        delay(1000) //will only delay this coroutine(delay each coroutine)

        //Thread.sleep(1000) //will sleep the entire threads

        return RESULT_1
    }


    private fun logThread(methodName: String) {
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}