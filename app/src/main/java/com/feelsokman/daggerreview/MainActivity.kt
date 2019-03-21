package com.feelsokman.daggerreview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gg = SampleClass(object : SampleListener {
            override fun doSomething() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })


        gg.runRandomMethod()
    }
}
