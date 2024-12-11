package ir.hrka.kotlin.kotlin_topics

import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * Interface is open and public by default.
 * * An interface cannot contain any constructor.
 * * A property declared in an interface can either be abstract or provide implementations for accessors.
 * * Interface can't have properties with initializers because it cannot have state.
 * * Interface can contains abstract and concrete methods:
 * ```
 * interface Employer {
 *
 *     val travelExpenses: Long
 *
 *     var salary: Long
 *         get() = MINIMUM_SALARY +
 *                 travelExpenses -
 *                 calculateInsurance()
 *         set(value) {
 *             MINIMUM_SALARY
 *         }
 *
 *     companion object {
 *         const val MINIMUM_SALARY = 12_000_000L
 *     }
 *
 *     fun calculateInsurance(): Long {
 *         return MINIMUM_SALARY * 10 / 100
 *     }
 *
 *     fun work()
 * }
 * ```
 * * We cannot instantiate from an Interface directly.
 * * What makes them different from abstract classes is that interfaces cannot store state.
 * * Properties declared in interfaces can't have backing fields,
 * and therefore accessors declared in interfaces can't reference them.
 * * Concrete member is open by default in an interface.
 * * We can't use `final` keyword in an interface.
 */


interface Interface {

    val abstractProperty: Int

    val valWithImplementation: String
        get() = "foo"

    var varWithImplementation: String
        get() = "foo"
        set(value) {}

    companion object {
        val CONSTANT = "hamidreza"
    }

    fun fun1() {
        printYellow("fun1 --> Implemented in Interface")
    }

    fun fun2() {
        printYellow("fun2 --> Implemented in Interface")
    }
}

interface AnotherInterface {
    fun fun2()
}