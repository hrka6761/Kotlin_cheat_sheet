package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.helpers.Log.printYellow

/**
 * * Sealed classes and interfaces provide controlled inheritance of a class hierarchies.
 * * All direct subclasses of a sealed class are known at compile time.
 * * A sealed class itself is always an abstract class,
 * and as a result, can't be instantiated directly.
 * * Each enum constant exists only as a single instance,
 * while subclasses of a sealed class may have multiple instances.
 * * Constructors of sealed classes can have one of two visibilities:
 *    * Protected (by default)
 *    * Private
 * * We can override function of interface that implemented by sealed class,
 * can be implemented in every sub class or by own sealed class.
 * * Enum class can contain abstract member that must be implemented by all instances.
 * * A sealed interface can be derived from another interface then sub classes must be implemented its members.
 * * When you combine sealed classes or interfaces with the when expression,
 * you can cover the behavior of all possible subclasses and ensure that no new subclasses
 * are created to affect your code adversely (you don't need to add an else clause).
 */

sealed class SealedClass(val int: Int, val string: String) : A {
    class Child1(val i: Int, val s: String) : SealedClass(i, s) {
        override fun abstractFunction() {
            TODO("Not yet implemented")
        }

        override fun a() {
            TODO("Not yet implemented")
        }
    }

    class Child2(val i: Int, val s: String) : SealedClass(i, s) {
        override fun abstractFunction() {
            TODO("Not yet implemented")
        }

        override fun a() {
            TODO("Not yet implemented")
        }
    }

    data class Child3(val i: Int, val s: String) : SealedClass(i, s) {
        override fun abstractFunction() {
            TODO("Not yet implemented")
        }

        override fun a() {
            TODO("Not yet implemented")
        }
    }


    abstract fun abstractFunction()

    companion object {

        private const val CONSTANT = "hamidreza"

        fun a() {
            printYellow("SealedClass companion function")
        }
    }
}

sealed interface SealedInterface {
    data object Impl1 : SealedInterface {
        override val int: Int = 1

        override fun a() {
            printYellow("A implementation in Impl1")
        }
    }

    data object Impl2 : SealedInterface {
        override val int: Int
            get() = 2

        override fun a() {
            printYellow("A implementation in Impl2")
        }
    }

    class Impl3(override val int: Int = 3) : SealedInterface {
        override fun a() {
            printYellow("A implementation in Impl3")
        }
    }


    companion object {

        private const val CONSTANT = "hamidreza"

        fun a() {
            printYellow("SealedInterface companion function")
        }
    }


    val int: Int

    fun a()
}