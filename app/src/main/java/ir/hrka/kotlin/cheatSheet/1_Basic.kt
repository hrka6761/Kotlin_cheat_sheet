package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printRed
import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * We declare a `package` at the top of your Kotlin file using the `package` keyword:
 *    * Use all lowercase letters.
 *    * Use reverse domain name notation to ensure uniqueness.
 *    * Avoid using reserved words or special characters.
 * ```
 * package ir.hrka.kotlin.cheatSheet
 * ```
 * * When you want to use a class, function, or extension,
 * you can Import it into your file to use the class or function without
 * specifying its full name every time:
 *    * To use a function or extension in the same package, you don't need to use the full name or import its package.
 * ```
 * import ir.hrka.kotlin.cheatSheet.Class
 * ```
 * * We can declare value by `val` that is immutable:
 *    * It equals final variable in java.
 *    * It only contains getter.
 *    * It is thread safety.
 *    * It is more memory-efficient.
 * ```
 * val a = 1
 *     get() {
 *         return field + 1
 *     }
 * ```
 * ```
 * val b: Long = 1L
 * ```
 * * We can declare variable by `var` that is mutable:
 *    * It equals regular variable in java.
 *    * It contains getter and setter.
 * ```
 * var a: Float = 2.0f
 * a = 3.2f
 * ```
 * ```
 * var b = "text"
 *     get() {
 *         return field
 *     }
 *     set(value) {
 *         field = value
 *     }
 * ```
 * * A backing field will be generated for a property if it uses the default implementation of at least one of the accessors,
 * or if a custom accessor references it through the field identifier.
 * * A `field` is only used as a part of a property to hold its value in memory
 * (Fields cannot be declared directly and Kotlin provides it).
 * * The field identifier can only be used in the accessors of the property.
 * * You can define custom accessors for a property:
 *    * If you define a custom getter, it will be called every time you access the property.
 *    * If you define a custom setter, it will be called every time you assign a value to the property, except its initialization.
 * * Use `val` wherever possible.
 * * Use `var` when necessary.
 * * Late-initialized properties and variables `lateinit var`:
 *    * This modifier can be used on var properties declared inside the body of a class.
 *    * You can not declare primitive types by late init (only reference types such String or etc).
 *    * It is not allowed on properties with a custom getter or setter.
 *    * It is not allowed on properties of nullable types.
 * ```
 * lateinit var person: Person
 *
 * if (this::person.isInitialized) {
 *    // Do something
 * }
 * ```
 * * Compile-time constants `const val`:
 *    * It must be a top-level property, or a member of an object declaration or a companion object.
 *    * It must be initialized with a value of type String or a primitive type.
 *    * It cannot be a custom getter.
 * ```
 * const val A = "constant"
 * ```
 * * The compiler will inline usages of the constant, replacing the reference to the constant with its actual value.
 * However, the field will not be removed and therefore can be interacted with using reflection.
 */

class SampleCLass1 {
    val a = 1
        get() {
            return field
        }
    var b = "text"
        get() {
            return field
        }
        set(value) {
            field = value
        }
    val c: Long = 1L
    var d: Float = 2.0f

    lateinit var topLevelClassA: TopLevelClassA


    fun fun1(): Unit {
        if (this::topLevelClassA.isInitialized)
            printYellow("topLevelClassA property is initialized.")
        else
            printRed("topLevelClassA property is not initialized yet.")
    }

    fun fun2(): Nothing {
        throw Exception()
    }
}

/**
 * * In Kotlin, everything is an object:
 * ```
 * val number: Number = 1
 *
 * val byte: Byte = 1
 * val short: Short = 1
 * val int: Int = 1_000_000
 * val long: Long = 1L
 *
 * val float: Float = 1f
 * val double: Double = 1.0
 *
 * val uByte: UByte = 1u
 * val uShort: UShort = 1u
 * val uInt: UInt = 1u
 * val uLong: ULong = 1uL
 *
 * val boolean: Boolean = true
 * val character: Char = 'a'
 * ```
 * * Numbers are stored as primitive types at compile time except:
 *    * When you create a nullable number reference such as Int?.
 *    * When you use generics.
 * ```
 * val int: Int? = 1
 * ```
 * ```
 * val list: List<Int> = listOf(1, 2, 3)
 * ```
 * * JVM applies the memory optimization to Integers between -128 and 127:
 * ```
 * val a: Int = 100
 * val boxedA: Int? = a
 * val anotherBoxedA: Int? = a
 * println(boxedA === anotherBoxedA) // true
 * ```
 * * Unsigned integer is declared to as int in the java.
 * * All integer and decimal types are number and extend it and comparable class.
 * * To perform a runtime check use `is` and `!is` operators:
 * ```
 * if (number is Byte) {
 *     // Do something
 * }
 *
 * if (number !is Number) {
 *     // Do something
 * }
 * ```
 * * Smart casts occurred after using `is` and `!is` operators in a control flow like `if` and `when`.
 * * To cast, you must use `as` operator:
 * ```
 * val casted = fl as Float
 * ```
 * * To safe casting on nullable use `as?`:
 * ```
 * val x: String? = y as String?
 * // if y=null x=null , if y is not a string throw ClassCastException.
 * val x: String? = y as? String
 * // if y=null or is not a string x=null.
 * ```
 * * `return`: by default returns from the nearest enclosing function or anonymous function.
 * * `break`: terminates the nearest enclosing loop.
 * * `continue`: proceeds to the next step of the nearest enclosing loop.
 * * There is not any checked exception in kotlin.
 * * To handling exceptions, we can use try-catch-finally blocks:
 * ```
 * try {
 *     // Do something
 * } catch (e: Exception) {
 *     // Do something
 * }
 * ```
 * ```
 * try {
 *     // Do something
 * } catch (e: NullPointerException) {
 *     // Do something
 * } catch (e: ArithmeticException) {
 *     // Do something
 * } catch (e: Exception) {
 *     // Do something
 * } finally {
 *     // Do something
 * }
 * ```
 * ```
 * try {
 *    // Do something
 * } finally {
 *    // Do something
 * }
 * ```
 * * To explicit throw exception by function use `@Throws` annotation:
 * ```
 * @Throws(Exception::class)
 * fun function() {
 *     TODO("Not implemented yet")
 * }
 * ```
 */


val number: Number = 1

val byte: Byte = 1
val short: Short = 1
val int: Int = 1_000_000
val long: Long = 1L

val float: Float = 1f
val double: Double = 1.0

val uByte: UByte = 1u
val uShort: UShort = 1u
val uInt: UInt = 1u
val uLong: ULong = 1uL

val boolean: Boolean = true
val character: Char = 'a'


fun fun1() {
    val a = (number is Byte).toString()
    val b = (number !is Byte).toString()
    val casted = float as? Float
}

fun fun2() {
    try {

    } catch (e: Exception) {

    }
    /* ------------------------------------------------------------------ */
    try {

    } catch (e: Exception) {

    } finally {

    }
    /* ------------------------------------------------------------------ */
    try {

    } catch (e: NullPointerException) {

    } catch (e: ArithmeticException) {

    } catch (e: Exception) {

    } finally {

    }
    /* ------------------------------------------------------------------ */
    try {

    } finally {

    }
}


/**
 * * Kotlin has the following collections for grouping items:
 *    * `Array` is Ordered collections of items that immutable and you can only change the value of indices but you can not add new index.
 *    * `List` is Ordered collections of items just like arrays but you can not change the value of indices.
 *    * `Set` is Unique unordered collections of items.
 *    * `Map` is Sets of key-value pairs where keys are unique and map to only one value.
 * ```
 * val array: Array<Int> = arrayOf(1, 2, 1)
 * array[1] = 3
 * ```
 * ```
 * val list: List<Int> = listOf(1, 2, 1)
 * val mutableList: MutableList<Int> = mutableListOf(1, 2, 2)
 * mutableList.add(5)
 * ```
 * ```
 * val set: Set<Int> = setOf(1, 2, 2)
 * val mutableSet: MutableSet<Int> = mutableSetOf(1, 2, 2)
 * mutableSet.add(-1)
 * ```
 * ```
 * val map: Map<Int, Boolean> = mapOf(Pair(1, true), Pair(1, false))
 * val mutableMap: MutableMap<Int, Int> = mutableMapOf(Pair(1, 1))
 * mutableMap[10] = 100L
 * ```
 */

val array: Array<Int> = arrayOf(1, 2, 1)

val list: List<Int> = listOf(1, 2, 1)
val mutableList: MutableList<Int> = mutableListOf(1, 2, 2)

val set: Set<Int> = setOf(1, 2, 2)
val mutableSet: MutableSet<Int> = mutableSetOf(1, 2, 2)

val map: Map<Int, Boolean> = mapOf(Pair(1, true), Pair(1, false), Pair(2, false))
val mutableMap: MutableMap<Int, Long> = mutableMapOf(Pair(1, 1L), Pair(1, 2L), Pair(2, 3L))