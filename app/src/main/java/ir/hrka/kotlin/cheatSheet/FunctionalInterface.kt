package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.helpers.Log.printYellow

/**
 * * An interface with only one abstract method (can have several non-abstract members)
 * is called a functional interface, or a Single Abstract Method (SAM) interface.
 * * Fun interfaces cannot have abstract properties.
 */

interface SingleAbstractInterface {
    fun singleAbstractFunction()
}


fun interface SAM1 {
    fun singleAbstractFun()
}

fun interface SAM2 {
    fun singleAbstractFun()
    fun concreteFunction() {
        printYellow("concreteFunction")
    }
}

fun useSingleAbstractInterface1(singleAbstractInterface: SingleAbstractInterface) {
    singleAbstractInterface.singleAbstractFunction()
}

fun useSingleAbstractInterface2(sam1: SAM1) {
    sam1.singleAbstractFun()
}

typealias sam1 = () -> Unit