package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log
import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * Sometimes it is useful to wrap a value in a class to create a more domain-specific type,
 * However, it introduces runtime overhead due to additional heap allocations,
 * To solve such issues, Kotlin introduces a special kind of class called an inline class:
 *    * Inline classes are a subset of value-based classes.
 * ```
 * // For JVM backends
 * @JvmInline
 * value class ValueClass (val value: Int)
 * ```
 * * In the inline classes data of the class is inlined into its usages.
 * * Inline classes don't have an identity and can only hold values.
 * * An inline class must have a single property initialized in the primary constructor.
 * * Inline classes allowed to declare properties and functions, have an init block and secondary constructors.
 * * Inline class properties cannot have backing fields.
 *    * They can only have simple computable properties (no lateinit /delegated properties).
 * * Value classes can be only final.
 * * Value classes cannot extend other classes.
 * * value classes are allowed to inherit from interfaces.
 * * n generated code, the Kotlin compiler keeps a wrapper for each inline class,
 * Inline class instances can be represented at runtime either as wrappers or as the underlying type.
 * * As a rule of thumb, inline classes are boxed whenever they are used as another type.
 * * Inline classes can also have a generic type parameter as the underlying type:
 *    * In this case, the compiler maps it to Any? or, generally, to the upper bound of the type parameter.
 * ```
 * @JvmInline
 * value class ValueClass<T>(val value: T)
 * ```
 */

@JvmInline
value class ValueClass (val int: Int): Interface {

    val str: String
        get() = "str"


    init {
        printYellow("init block")
    }


    constructor(int: Int, boolean: Boolean): this(int)

    override val abstractProperty: Int
        get() = TODO("Not yet implemented")


    override fun fun1() {
        printYellow("fun1")
    }

    override fun fun2() {
        printYellow("fun2")
    }

    fun fun3() {
        printYellow("fun3")
    }
}