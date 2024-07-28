package ir.hrka.kotlin.cheatSheet

/**
 * * We declare a package at the top of your Kotlin file using the package keyword.
 *    * Use all lowercase letters.
 *    * Use reverse domain name notation to ensure uniqueness (e.g., com.example).
 *    * Avoid using reserved words or special characters.
 * * When we want to use another file or class in our class,
 * we must import its package name in our class unless it is in the same package.
 * * We can declare value by 'val' that is immutable:
 *    * It equals final variable in java.
 *    * It only has getter.
 *    * It is thread safety.
 *    * It is more memory-efficient.
 * * We can declare variable by 'var' that is mutable:
 *    * It equals regular variable in java.
 *    * It has getter and setter.
 * * Compile-time constants (const val A = "a"):
 *    * It must be a top-level property, or a member of an object declaration or a companion object
 *    * It must be initialized with a value of type String or a primitive type
 *    * It cannot be a custom getter
 * * The compiler will inline usages of the constant, replacing the reference to the constant with its actual value.
 * However, the field will not be removed and therefore can be interacted with using reflection.
 * * Use val wherever possible.
 * * Use var when necessary.
 * * You can define custom accessors for a property:
 *    * If you define a custom getter, it will be called every time you access the property.
 *    * If you define a custom setter, it will be called every time you assign a value to the property, except its initialization.
 * * A field is only used as a part of a property to hold its value in memory
 * (Fields cannot be declared directly and Kotlin provides it).
 * * The field identifier can only be used in the accessors of the property.
 * * A backing field will be generated for a property if it uses the default implementation of at least one of the accessors,
 * or if a custom accessor references it through the field identifier.
 * * 'Unit' is an object that oly have a method (toString).
 * * 'Any' is parent of all classes in kotlin
 * (directly when the class is not derived from any class and indirect when the class is derived from a class).
 * * 'Nothing' is a regular class that has not any instance because its constructor is private
 * (if a function has the return type of Nothing, it means that it never returns (always throws an exception)).
 * * 'TODO()' return Noting and throws exception. it is a standard function of kotlin that is used in the body of functions that is not implemented yet.
 * (we can pass it a string as a reason.)
 */

class Pattern1 {
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


    fun fun1(): Unit {
        TODO()
    }

    fun fun2(): Nothing {
        throw Exception()
    }
}

/**
 * * In Kotlin, everything is an object.
 * * Numbers are stored as primitive types:
 *    * Except when you create a nullable number reference such as Int?.
 *    * Except when you use generics.
 * * JVM applies the memory optimization to Integers between -128 and 127:
 *    * val a: Int = 100
 *    * val boxedA: Int? = a
 *    * val anotherBoxedA: Int? = a
 *    * println(boxedA === anotherBoxedA) // true
 * * Unsigned integer is declared to as int in the java.
 * * All integer and decimal types are number and extend it and comparable class.
 */

class Pattern2 {
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

    /**
     * * To perform a runtime check use 'is' and '!is' operators:
     * * Smart casts occurred after using 'is' and '!is' operators in a control flow like 'if' and 'when'.
     * * To cast, you must use 'as' operator.
     * * To safe casting on nullable use 'as?' :
     *    * val x: String? = y as String? if y=null x=null , if y is not a string throw ClassCastException.
     *    * val x: String? = y as? String if y=null or is not a string x=null.
     */


    fun fun1() {
        val a = (number is Byte).toString()
        val b = (number !is Byte).toString()
        val casted = float as? Float
    }

    /**
     * * return    -> by default returns from the nearest enclosing function or anonymous function.
     * * break     -> terminates the nearest enclosing loop.
     * * continue  -> proceeds to the next step of the nearest enclosing loop.
     * * val s = person.name ?: return  --> s is Nothing
     * * It dose not have any checked exception in kotlin.
     * * To handling exceptions, we can use try-catch-finally blocks.
     * * To explicit throw exception by function use @Throws annotation
     * * val s = person.name ?: throw NullPointerException()  --> s is Nothing
     */


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
}

/**
 * * Kotlin has the following collections for grouping items:
 *    * Array: Ordered collections of items that immutable and you can only change the value of indices but you can not add new index.
 *    * List: Ordered collections of items just like arrays but you can not change the value of indices.
 *    * Set: Unique unordered collections of items.
 *    * Map: Sets of key-value pairs where keys are unique and map to only one value.
 */

val array: Array<Int> = arrayOf(1, 2, 1)

val list: List<Int> = listOf(1, 2, 1)
val mutableList: MutableList<Int> = mutableListOf(1, 2, 2)

val set: Set<Int> = setOf(1, 2, 2)
val mutableSet: MutableSet<Int> = mutableSetOf(1, 2, 2)

val map: Map<Int, Boolean> = mapOf(Pair(1, true), Pair(1, false), Pair(2, false))
val mutableMap: Map<Int, Long> = mutableMapOf(Pair(1, 1L), Pair(1, 2L), Pair(2, 3L))


/**
 * * There are four visibility modifiers in Kotlin: (private, protected, internal, public)
 * * 'private' means that the member is visible inside class or file only.
 * * 'protected' means that the member has the same visibility as one marked as private, but that it is also visible in subclasses.
 * * 'internal' means that any client inside this module who sees the declaring class sees its internal members(a module is a set of Kotlin files compiled together).
 * * 'public' means that any client who sees the declaring class sees its public members.
 * * If you don't use a visibility modifier, public is used by default.
 * * The protected modifier is not available for top-level declarations.
 * * When we declare a class private, we cannot inherit or instantiate it anywhere except in the same file or class.
 * * When we declare a class private, we can only inherit or instantiate it when the subClass is internal or private.
 */

const val topLevelA = 1
private lateinit var topLevelB: TopLevelClassC
internal var topLevelC = "1"

public class TopLevelClassA() {}
internal open class TopLevelClassB() {}
private open class TopLevelClassC() {}
private class DerivedC : TopLevelClassC() {}
internal class DerivedB1 : TopLevelClassB() {}
private class DerivedB2 : TopLevelClassB() {}