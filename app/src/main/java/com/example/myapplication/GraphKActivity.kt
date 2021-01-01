package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.View
import com.yabu.livechart.model.DataPoint
import com.yabu.livechart.model.Dataset
import com.yabu.livechart.view.LiveChart
import com.yabu.livechart.view.LiveChartStyle
import java.io.Serializable
import java.lang.Thread.sleep
import java.util.*

class GraphKActivity : AppCompatActivity() {
    var datasetResp = Dataset(mutableListOf())
    var datasetResq = Dataset(mutableListOf())
    var datasetSnir = Dataset(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_k)
        val liveChart = findViewById<LiveChart>(R.id.live_chart)
        val liveChart1 = findViewById<LiveChart>(R.id.live_chart1)
        val liveChart2 = findViewById<LiveChart>(R.id.live_chart2)

        var intent = intent
        var data = intent.getSerializableExtra("data") as List<Result>
        for (i in 0 .. data.size-1){
            datasetResp.points.add(DataPoint(i.toFloat(), data.get(i).rsrp.toFloat()))
            datasetResq.points.add(DataPoint(i.toFloat(), data.get(i).rsrq.toFloat()))
            datasetSnir.points.add(DataPoint(i.toFloat(), data.get(i).sinr.toFloat()))
            Log.v("aaaaaaaaaaaaaaaaaa","rsrp : "+data.get(i).rsrp.toFloat()+
                    "  rsrq : "+data.get(i).rsrq.toFloat()+" sinr "+data.get(i).sinr.toFloat())
        }
        liveChart.setDataset(datasetResp)
                .drawYBounds()
                .drawBaseline()
                .setBaselineManually(1f)
                .drawFill(withGradient = true)
                .drawBaselineConditionalColor()
                .drawVerticalGuidelines(steps = 4)
                .drawHorizontalGuidelines(steps = 4)
                .drawLastPointLabel()
                .drawDataset()
        liveChart1.setDataset(datasetResq)
                .drawYBounds()
                .drawBaseline()
                .setBaselineManually(1f)
                .drawFill(withGradient = true)
                .drawBaselineConditionalColor()
                .drawVerticalGuidelines(steps = 4)
                .drawHorizontalGuidelines(steps = 4)
                .drawLastPointLabel()
                .drawDataset()
        liveChart2.setDataset(datasetSnir)
                .drawYBounds()
                .drawBaseline()
                .setBaselineManually(1f)
                .drawFill(withGradient = true)
                .drawBaselineConditionalColor()
                .drawVerticalGuidelines(steps = 4)
                .drawHorizontalGuidelines(steps = 4)
                .drawLastPointLabel()
                .drawDataset()
        //////////////////
        val style = LiveChartStyle().apply {
            textColor = Color.BLUE
            textHeight = 30f
            mainColor = Color.GREEN
            mainFillColor = Color.MAGENTA
            baselineColor = Color.BLUE
            pathStrokeWidth = 6f
            baselineStrokeWidth = 3f
        }
        val style1 = LiveChartStyle().apply {
            textColor = Color.BLUE
            textHeight = 30f
            mainColor = Color.GREEN
            mainFillColor = Color.MAGENTA
            baselineColor = Color.BLUE
            pathStrokeWidth = 6f
            baselineStrokeWidth = 3f
        }
        val style2 = LiveChartStyle().apply {
            textColor = Color.BLUE
            textHeight = 30f
            mainColor = Color.GREEN
            mainFillColor = Color.MAGENTA
            baselineColor = Color.BLUE
            pathStrokeWidth = 6f
            baselineStrokeWidth = 3f
        }
//        liveChart.setDataset(datasetResp)
//                .setLiveChartStyle(style)
//                .drawBaseline()
//                .drawFill(withGradient = true)
//                .drawYBounds()
//                .drawDataset()
//        liveChart1.setDataset(datasetResq)
//                .setLiveChartStyle(style1)
//                .drawBaseline()
//                .drawFill(withGradient = true)
//                .drawYBounds()
//                .drawDataset()
//        liveChart2.setDataset(datasetSnir)
//                .setLiveChartStyle(style2)
//                .drawBaseline()
//                .drawFill(withGradient = true)
//                .drawYBounds()
//                .drawDataset()

//        val timer = Timer()
//        timer.schedule(
//                object : TimerTask() {
//                    override fun run() {
//                        runOnUiThread {
//                            while (true) {
//                                liveChart.setDataset(dataset)
//                                        .setLiveChartStyle(style)
//                                        .drawBaseline()
//                                        .drawFill(withGradient = true)
//                                        .drawYBounds()
//                                        .drawDataset()
//                            }
//                        }
//                    }
//                }, 0, 1000)
    }

}

