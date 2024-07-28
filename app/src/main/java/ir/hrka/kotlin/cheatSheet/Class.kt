package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.helpers.Log.printYellow


/**
 *  * A class in Kotlin has a primary constructor and possibly one or more secondary constructors.
 *  * Members of a class is final by default and must be open to overriding (an open member can not be private).
 *  * If a class has a primary constructor, secondary constructors must be invoke primary constructor.
 *  (directly or indirectly by calling another secondary constructor that the primary constructor calls)
 *  * A class is public and final and with a public zero-param constructor by default.
 *  * During the initialization of an instance,
 *  the initializer blocks are executed in the same order as they appear in the class body,
 *  interleaved with the property initializers.
 *  * Initialization block codes convert to constructor in java.
 *  * Members that declared in the companion object block must be called by the class name without instantiating.
 *  * Class members can access the private members of the corresponding companion object.
 *  * After decompile in java create static final class Companion and member function of companion object
 *  is declared in it and member property of companion object is declared as static final property.
 *  * Late-initialized properties and variables:
 *    * This modifier can be used on var properties declared inside the body of a class.
 *    * You can not declare primitive types by late init (only reference types such String or etc).
 *    * It is not allowed on properties with a custom getter or setter.
 *    * It is not allowed on properties of nullable types.
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
}