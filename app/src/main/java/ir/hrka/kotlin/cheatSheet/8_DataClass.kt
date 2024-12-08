package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * Data classes in Kotlin are primarily used to hold data.
 * * The primary constructor must have at least one parameter.
 * * All primary constructor parameters must be marked as val or var:
 * ```
 * data class DataClass(val value: Int, var variable: String, ...)
 * ```
 * * Data classes can't be abstract, open, sealed, or inner.
 * * Compiler generates following members for data class:
 *    * `equals()`.
 *    * `hashCode()`.
 *    * `toString()`.
 *    * `componentN()`.
 *    * `copy()`.
 * * If there are explicit implementations of `equals()`, `hashCode()`, or `toString()`
 * in the data class body or final implementations in a superclass,
 * then these functions are not generated, and the existing implementations are used.
 * * Providing explicit implementations for the `componentN()` and `copy()` functions is not allowed:
 *    * We can not override `copy()` and `componentN()`.
 * * The compiler only uses the properties defined inside the primary constructor for the automatically generated functions.
 * * Data classes and destructuring declarations:
 * ```
 * val data = DataClass(1, "a")
 * val (int, string) = data
 * ```
 * * The standard library provides the `Pair` and `Triple` classes as data class.
 */


data class DataClass(val int: Int, var string: String) {

    companion object {

        private const val CONSTANT = "hamidreza"

        fun companionFun() {
            printYellow("DataClass companion function")
        }
    }

    fun fun1() {
        printYellow("fun1")
    }
}
