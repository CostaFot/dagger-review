package com.feelsokman.daggerreview

class SampleClass(var sampleListener: SampleListener) {

    fun runRandomMethod() {

        sampleListener.doSomething()
    }
}