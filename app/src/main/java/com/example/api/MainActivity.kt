package com.example.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.api.Retrofit.ApiInterface
import com.example.api.model.MyData
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val arrayTimeList = ArrayList<String>()
    val arrayVitalList = ArrayList<Entry>()

    private val BASE_URL="https://23.101.121.63/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val xAxis=lineChart.xAxis
       xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)


               // getMyData()
        linedata()

    }

    private fun linedata() {
        //x-axis
        arrayTimeList.add("10AM")
        arrayTimeList.add("11AM")
        arrayTimeList.add("12AM")
        arrayTimeList.add("1AM")
        arrayTimeList.add("2AM")

        //y-axis
        arrayVitalList.add(Entry(40f,0))
        arrayVitalList.add(Entry(70f,1))
        arrayVitalList.add(Entry(30f,2))
        arrayVitalList.add(Entry(60f,3))
        arrayVitalList.add(Entry(40f,4))

        val dataSet=LineDataSet(arrayVitalList,"Vitals")

             val data=LineData(arrayTimeList,dataSet)
             lineChart.data=data
    }

    private fun getMyData()
    {

        //retrofit
        val retrofitBuilder=Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiInterface::class.java)

        val retrofitData=retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyData>?> {

            override fun onResponse(call: Call<List<MyData>?>, response: Response<List<MyData>?>) {
                val responseBody=response.body()!!
                val myStringBuilder=StringBuilder()

                var i:Int=0

                for (myData in responseBody){

                    myStringBuilder.append(myData.id)
                    //myStringBuilder.append("\n")

                    arrayTimeList.add(myStringBuilder.toString())
                    arrayVitalList.add(Entry(myStringBuilder.toString().toFloat(),myData.id))


                }


                val dataSet=LineDataSet(arrayVitalList,"Vitals")

                val data=LineData(arrayTimeList,dataSet)
               lineChart.data=data
            }

            override fun onFailure(call: Call<List<MyData>?>, t: Throwable) {
                Log.d("MainActivity","onFailure:" +t.message)
            }
        })
    }

}



