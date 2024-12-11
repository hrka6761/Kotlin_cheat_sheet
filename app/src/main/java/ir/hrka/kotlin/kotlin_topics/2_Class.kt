package ir.hrka.kotlin.kotlin_topics

import ir.hrka.kotlin.core.utilities.Log.printYellow


/**
 * * A class in Kotlin can only have one primary constructor and one or more secondary constructors:
 * ```
 * class Person(val age: Int) {
 *    constructor(age: Int, name: String) : this(age) {
 *         // Do something
 *     }
 *
 *     constructor(age: Int, name: String, nationalCode: Long) :
 *     this(age, name) {
 *         // Do something
 *     }
 * }
 * ```
 * * A class is public and final and with a public zero-param constructor by default.
 * * The Functions of a class are final by default and must be open to overriding.
 * * The Properties of a class must be open to overriding.
 * * An open member can not be private.
 * * If a class has a primary constructor, secondary constructors must be invoke primary constructor,
 * directly or indirectly by calling another secondary constructor that the primary constructor calls.
 * * During the initialization of an instance, the initializer blocks are executed in the same order
 * as they appear in the class body, interleaved with the property initializers.
 * * Members that declared in the companion object block must be called by the class name without instantiating:
 * ```
 * class Person(val age: Int) {
 *     companion object {
 *          val MAX_AGE = 100
 *     }
 * }
 *
 * Person.MAX_AGE
 *
 * val p1 = Person(10)
 * p1.age
 * ```
 * * Class members can access the private members of the corresponding companion object.
 * * After decompile in java create static final class Companion and member function of companion object
 * is declared in it and member property of companion object is declared as static final property:
 *   * To get byteCode and decompile to java code go to the bellow path in the android studio.
 * ```
 * Tools -> Kotlin -> Show Kotlin Bytecode
 * ```
 */

open class Class(val int: Int = 1) {

    open val openProperty: String
        get() = inlineInitFun1()

    private lateinit var topLevelClassA: TopLevelClassA


    init {
        printYellow("Class: init block 1")
    }

    private var variable = inlineInitFun2()

    init {
        printYellow("Class: init block 2")
    }


    companion object {
        const val COMPANION_PROP = "COMPANION_PROP"
        private const val PRIVATE_COMPANION_PROP = "PRIVATE_COMPANION_PROP"

        fun companionFun() {
            printYellow("companionFun")
        }
    }


    constructor(int: Int, string: String = "a") : this(int) {
        printYellow("Class: secondary constructor 1")
    }

    constructor(int: Int, string: String, long: Long) : this(int) {
        printYellow("Class: secondary constructor 2")
    }

    constructor(int: Int, string: String, float: Float) : this(int, string) {
        printYellow("Class: secondary constructor 3")
    }


    private fun inlineInitFun1(): String {
        printYellow("inlineInitFun 1 + $PRIVATE_COMPANION_PROP")

        return "hamidreza"
    }

    private fun inlineInitFun2(): String {
        printYellow("inlineInitFun 2")

        return "hamidreza"
    }


    open fun fun1() {
        printYellow("fun1 -->  Implemented in Class")
    }

    open fun fun2(x: Int = 2) {
        printYellow("fun2 -->  Implemented in Class")
    }


    fun fun3() {
        if (this::topLevelClassA.isInitialized)
            topLevelClassA = TopLevelClassA()
    }

    protected open fun fun9(){}
}