package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printRed
import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * `Unit`:
 *    * It is an object that oly have a method (toString).
 * ```
 * public object Unit {
 *     override fun toString(): String = "kotlin.Unit"
 * }
 * ```
 * * `Any`:
 *    * It is parent of all classes in kotlin.
 *    * directly when the class is not derived from any class and indirect when the class is derived from a class.
 * ```
 * public open class Any {
 *     public open operator fun equals(other: Any?): Boolean
 *     public open fun hashCode(): Int
 *     public open fun toString(): String
 * }
 * ```
 * * `Nothing`:
 *    * It is a regular class that has not any instance because its constructor is private.
 *    * If a function has the return type of Nothing, it means that it never returns (always throws an exception).
 * ```
 * public class Nothing private constructor()
 * ```
 * * In the following cases if person.name = null s is Nothing:
 * ```
 * val s = person.name ?: return
 * val s = person.name ?: throw NullPointerException()
 * ```
 * * `TODO()`:
 *    * It returns Nothing and throws exception
 *    * it is a standard function of kotlin that is used in the body of functions that is not implemented yet.
 * ```
 * public inline fun TODO(): Nothing = throw NotImplementedError()
 * ```
 * ```
 * public inline fun TODO(reason: String): Nothing =
 *     throw NotImplementedError("$reason")
 * ```
 */

class SampleCLass2 {
    fun fun1(): Unit {
        printRed("fun1")
    }

    fun fun2(): Nothing {
        throw Exception()
    }
}