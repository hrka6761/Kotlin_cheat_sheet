package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printYellow
import java.io.Serializable

/**
 * * Generics:
 * ```
 * // Generics class
 * class GenericClass<T : Serializable, U, V> { ... }
 *
 * // Generics interface
 * interface GenericInterface<T, U> {
 *     fun produce(): T
 *     fun consume(value: U)
 * }
 * ```
 * ```
 * // Generics function
 * fun <T, U, V> genericFunction(t: T, u: U, v: V): V { ... }
 * ```
 * * Generic types are only available at compile time.
 * * There is no general way to check whether an instance of a generic type was created with certain type arguments at runtime.
 * * Generic types in Java are invariant, meaning List<String> is not a subclass of List<Object> But not so in Kotlin:
 * ```
 * // In kotlin
 * val a: List<String> = listOf()
 * val b: List<Any> = a
 * ```
 * * In Kotlin, we can use the `in` and `out` keywords to define the variance of a type parameter.
 * * covariant type parameter (class C is covariant in the parameter T or T parameter is covariant):
 *    * The `out` modifier is called a variance annotation.
 *    * We can annotate the type parameter of generic class by 'out' modifier.
 *    to make sure that it is only returned (produced) from members of that, and never consumed.
 *    * `out T` (in kotlin) ~ `? extends T` (in java).
 *    * In a class, when a type is marked with "out", that type should only be used as a function output.
 * ```
 * interface CovariantGenerics<out T> {
 *
 *     fun produce(): T
 * }
 * ```
 * ```
 * class ProduceNumber() : CovariantGenerics<Number> {
 *     override fun produce(): Number {
 *         val num: Number = 1
 *
 *         return num
 *     }
 * }
 * ```
 * ```
 * class ProduceInt() : CovariantGenerics<Int> {
 *     override fun produce(): Int {
 *         val num = 1
 *         println("ProduceInt --> ${num::class.java.name}")
 *
 *         return num
 *     }
 * }
 * ```
 * ```
 * val produceInt = ProduceInt()
 * val produceNumber: CovariantGenerics<Number> = produceInt
 *
 * produceNumber.produce()::class.java.name // Returned value is Int
 * ```
 * * contravariant type parameter (class C is contravariant in the parameter T or T parameter is contravariant):
 *    * The `in` modifier is called a variance annotation.
 *    * It makes a type parameter contravariant, meaning it can only be consumed and never produced.
 *    * `in T` (in kotlin) ~ `? super T` (in java).
 *    * In a class, when a type is marked with "in", that type should only be used as an input parameter to a function.
 * ```
 * interface ContravariantGenerics<in T> {
 *
 *     fun consume(value: T)
 * }
 * ```
 * ```
 * class ConsumeNumber : ContravariantGenerics<Number> {
 *     override fun consume(value: Number) {
 *         println("ConsumeNumber --> ${value::class.java.name}")
 *         value.toByte()
 *     }
 * }
 * ```
 * ```
 * class ConsumeInt : ContravariantGenerics<Int> {
 *     override fun consume(value: Int) {
 *         println("ConsumeInt --> ${value::class.java.name}")
 *         value.toByte()
 *     }
 * }
 * ```
 * ```
 * val consumeNumber = ConsumeNumber()
 * val consumeInt: ContravariantGenerics<Int> = consumeNumber
 * consumeInt.consume(1)
 * // print "ConsumeNumber --> java.lang.Integer"
 * ```
 * * We can use both covariant and contravariant type parameter in a class or interface:
 * ```
 * interface GenericInterface<out T, in V> {
 *
 *     fun produce(): T
 *     fun consume(value: V)
 * }
 * ```
 * * definitely non-nullable:
 *    * The most common use case for declaring definitely non-nullable types is when
 *    you want to override a Java method that contains @NotNull as an argument (`T & Any`).
 * ```
 * fun consume(value: U & Any) { ... }
 * ```
 * * The underscore operator `_` can be used for type arguments.
 * * Use underscore operator to automatically infer a type of the argument when other types are explicitly specified.
 * * star projection `*` is a way to work with generic types when you don't know or don't need to specify the exact type parameter:
 * ```
 * val list: MutableList<+> = mutableListOf(1, "", false, Class())
 * list.add(1)
 * ```
 * * When we want to constraint type params in generic function we can use `where` keyword:
 *    * When we use "where" for a parameter, that parameter must be of the same type or subclasses.
 * ```
 * fun <T, U, V> genericFunction(t: T, u: U, v: V): V
 *   where T: AnyType, U:AnyType { ... }
 * ```
 */

class GenericClass<T : Serializable, U, V>

interface GenericInterface<T, U> {

    fun produce(): T
    fun consume(value: U & Any)
}

interface CovariantGenerics<out T> {

    fun produce(): T
}

interface ContravariantGenerics<in T> {

    fun consume(value: T)
}

class ProduceNumber() : CovariantGenerics<Number> {
    override fun produce(): Number {
        val num: Number = 1
        printYellow("ProduceNumber --> ${num::class.java.name}")

        return num
    }
}

class ProduceInt() : CovariantGenerics<Int> {
    override fun produce(): Int {
        val num = 1
        printYellow("ProduceInt --> ${num::class.java.name}")

        return num
    }
}

class ConsumeNumber : ContravariantGenerics<Number> {
    override fun consume(value: Number) {
        printYellow("ConsumeNumber --> ${value::class.java.name}")
        value.toByte()
    }
}

class ConsumeInt : ContravariantGenerics<Int> {
    override fun consume(value: Int) {
        printYellow("ConsumeInt --> ${value::class.java.name}")
        value.toByte()
    }
}

fun <T, U, V> genericFunction(value1: T, value2: U, value3: V): V {
    printYellow("value1 = ${value1!!::class.java}\nvalue2 = ${value2!!::class.java}\nvalue3 = ${value3!!::class.java}")

    return value3
}

fun <T, U, V> constraintGenericFunction(value1: T, value2: U, value3: V): V
 where T: Class, U: Interface, V: AbstractClass{
    printYellow("value1 = ${value1::class.java}\nvalue2 = ${value2::class.java}\nvalue3 = ${value3::class.java}")

    return value3
}