package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * An interface with only one abstract method is called a functional interface, or a Single Abstract Method (SAM) interface:
 *    * The functional interface can have several non-abstract members.
 * ```
 * interface SingleAbstractInterface {
 *     fun singleAbstractFunction()
 * }
 * ```
 * ```
 * fun interface SAM1 {
 *     fun singleAbstractFun()
 * }
 *
 * fun interface SAM2 {
 *     fun singleAbstractFun()
 *     fun concreteFunction() {
 *         // Do something
 *     }
 * }
 * ```
 * * We can instantiate from functional interface like below:
 * ```
 * val sam1 = SAM1 { // singleAbstractFun implementation }
 * val sam2 = SAM2 { // singleAbstractFun implementation }
 * ```
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