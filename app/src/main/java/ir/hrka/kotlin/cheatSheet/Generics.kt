package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.helpers.Log.printYellow
import java.io.Serializable

/**
 * * Generic types is only for compile time.
 * * There is no general way to check whether an instance of a generic type was created with certain type arguments at runtime.
 * * Generic types in Java are invariant, meaning List<String> is not a subclass of List<Object> But not so in Kotlin.
 * * In Kotlin, we can use the 'in' and 'out' keywords to define the variance of a type parameter.
 * * covariant type parameter (class C is covariant in the parameter T or T parameter is covariant):
 *    * The 'out' modifier is called a variance annotation.
 *    * We can annotate the type parameter of generic class by 'out' modifier
 *    to make sure that it is only returned (produced) from members of that, and never consumed.
 *    * out T (in kotlin) ~ ? extends T (in java)
 * * contravariant type parameter (class C is contravariant in the parameter T or T parameter is contravariant):
 *    * The 'in' modifier is called a variance annotation.
 *    * It makes a type parameter contravariant, meaning it can only be consumed and never produced.
 *    * in T (in kotlin) ~ ? super T (in java)
 * * The most common use case for declaring definitely non-nullable types is when
 * you want to override a Java method that contains @NotNull as an argument. (T & Any)
 * * The underscore operator _ can be used for type arguments.
 * Use it to automatically infer a type of the argument when other types are explicitly specified.
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