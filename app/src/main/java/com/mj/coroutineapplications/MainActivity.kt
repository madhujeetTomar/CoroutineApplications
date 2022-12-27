package com.mj.coroutineapplications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var tvResult : TextView
    lateinit var clickMe : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult = findViewById(R.id.tvResult)
        clickMe = findViewById(R.id.button)


        clickMe.setOnClickListener {

            tvResult.text = getString(R.string.loading)

            CoroutineScope(Dispatchers.IO).launch {

                fakeApiRequest()


            }

        }
    }

   private suspend fun fakeApiRequest1()
   {
       withContext(Dispatchers.IO){
       val result = async {
           result()
       }
       //setResult(result)
       val result2 = async {
           result2()
       }
       //setResult(result2)
       val deferred = awaitAll(result, result2)
       setResult(deferred)
   }}

    private suspend fun fakeApiRequest()
    {
       withContext(Dispatchers.IO){
           val job =  withTimeoutOrNull(1900){
              val result1 = result()
               setResult(result1)
               val result2 = result2()
               setResult(result2)
           }
           if(job==null)
           {
               setResult("Cancelled Job due to TimeOut")
           }
       }
    }


    private suspend fun setResult(deferred: List<String>) {
        withContext(Dispatchers.Main) {
            val newText = "${deferred[0]} ${deferred[1]}"
            tvResult.text = newText
        }
    }

    private suspend fun setResult(result : String)
{
    withContext(Dispatchers.Main){
       val newText = tvResult.text.toString()+ "\n$result"
        tvResult.text = newText
    }

}

    private suspend fun result() : String
    {

        delay(5000)

return "Result"
    }

    private suspend fun result2() : String
    {

        delay(5000)

        return "Result2"
    }
}