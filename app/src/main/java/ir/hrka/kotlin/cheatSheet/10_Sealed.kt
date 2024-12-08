package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * Sealed classes and interfaces provide controlled inheritance of a class hierarchies.
 * * All direct subclasses of a sealed class are known at compile time.
 *    * Direct subclasses are classes that are derived at the first level.
 * * A sealed class itself is always an abstract class,
 * and as a result, can't be instantiated directly.
 * * Each enum constant exists only as a single instance, while subclasses of a sealed class may have multiple instances.
 * * Constructors of sealed classes can have one of two visibilities:
 *    * Protected (by default).
 *    * Private.
 * * We can override function of interface that implemented by sealed class in every sub class or by own sealed class.
 * * Sealed class can contain abstract member that must be implemented by all sub classes:
 * ```
 * interface KotlinInterface1 {
 *     fun fun1()
 * }
 * ```
 * ```
 * interface KotlinInterface2 {
 *     fun fun2()
 * }
 * ```
 * ```
 * sealed class sealedClass(val value: Int) :
 *     KotlinInterface1,
 *     KotlinInterface2 {
 *
 *     class Child1(value: Int) : SealedClass(value) {
 *         override val absValue: Int
 *             get() = { ... }
 *
 *         override fun absFun() { ... }
 *         override fun fun2() { ... }
 *     }
 *     class Child2(value: Int) : SealedClass(value) {
 *         override val absValue: Int
 *             get() = { ... }
 *
 *         override fun absFun() { ... }
 *         override fun fun2() { ... }
 *     }
 *     data class Child3(value: Int) : SealedClass(value) {
 *         override val absValue: Int
 *             get() = { ... }
 *
 *         override fun absFun() { ... }
 *         override fun fun2() { ... }
 *     }
 *
 *     abstract val absValue: Int
 *     abstract fun absFun()
 * }
 * ```
 * * A sealed interface can be derived from another interface then sub classes must be implemented its members:
 * ```
 * sealed interface sealedInterface {
 *     data object Impl1 : sealedInterface {
 *         override fun fun3() { ... }
 *     }
 *
 *     data class Impl2(val value: Int) : sealedInterface {
 *         override fun fun3() { ... }
 *     }
 *
 *     class Impl3(val value: Int) : sealedInterface {
 *         override fun fun3() { ... }
 *     }
 *
 *
 *     fun fun3()
 * }
 * ```
 * * When you combine sealed classes or interfaces with the when expression,
 * you can cover the behavior of all possible subclasses and ensure that no new subclasses
 * are created to affect your code adversely (you don't need to add an else clause):
 * ```
 * val instance: SealedClass
 *
 * when(instance) {
 *     is SealedClass.Child1 -> { ... }
 *     is SealedClass.Child2 -> { ... }
 *     is SealedClass.Child3 -> { ... }
 * }
 * ```
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