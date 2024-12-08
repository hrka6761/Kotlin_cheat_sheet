package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * We can nest classes in classes, interfaces in classes, classes in interfaces, and interfaces in interfaces:
 * ```
 * class KotlinClass {
 *     class NestedClass { ... }
 * }
 * ```
 * ```
 * class KotlinClass {
 *     interface NestedInterface { ... }
 * }
 * ```
 * ```
 * class KotlinInterface {
 *     class NestedClass { ... }
 * }
 * ```
 * ```
 * class KotlinInterface {
 *     interface NestedInterface { ... }
 * }
 * ```
 * * A nested class marked as inner can access the members of its outer class.
 * * Inner classes carry a reference to an object of an outer class:
 *    * We can access the member of outer class in the inner class.
 *    * We cannot access the member of outer class in the nested class.
 * ```
 * class KotlinClass {
 *     val outerProperty: Int = 1
 *
 *     fun outerFun(value: Int) { ... }
 *
 *     class NestedClass {
 *          outerFun(outerProperty)
 *     }
 * }
 * ```
 * * An inner class is like a regular nested class in java, but a nested class is like static nested class in java.
 * * A nested class in an interface is like static nested class in java.
 */

class OuterClass() {

    val outerProperty = 1


    fun outerFun() {

    }


    class NestedClass {
        val nestedProperty = 1

        // We can not access to the outer member in the nested class
        fun nestedFun() {
            /*printYellow("outer property = $outerProperty")
            outerFun()*/
        }
    }


    inner class InnerClass {
        val innerProperty = 1

        fun innerFun() {
            printYellow("outer property = $outerProperty")
            outerFun()
        }
    }


    interface NestedInterface {

        class NestedClass {

        }

        interface NestedInterface {

        }
    }
}