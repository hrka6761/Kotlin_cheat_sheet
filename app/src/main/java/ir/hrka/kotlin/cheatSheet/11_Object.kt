package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * Object expression:
 *    * Sometimes you need to create an object that is a slight modification of some class,
 *    without explicitly declaring a new subclass for it.
 * ```
 * open class KotlinClass {
 *     open fun fun1() {
 *         // KotlinClass implementation
 *     }
 * }
 * ```
 * ```
 * val anonymousObject = object : KotlinClass {
 *     override fun fun1() {
 *         // Anonymous object implementation
 *     }
 * }
 * ```
 * * Object expressions create objects of anonymous classes (anonymous objects).
 * * When an anonymous object is used as a type of a local or private but not inline declaration (function or property),
 * all its members are accessible via this function or property:
 * ```
 * // anonymousObject is <anonymous object: Any>
 * private val anonymousObject = object : KotlinClass {
 *     val value: Int
 *
 *     override fun fun1() {
 *         // Anonymous object implementation
 *     }
 * }
 *
 * // Can call value by anonymousObject
 * anonymousObject.value
 * ```
 * ```
 * // anonymousObject is Any
 * val anonymousObject = object : KotlinClass {
 *     val value: Int
 *
 *     override fun fun1() {
 *         // Anonymous object implementation
 *     }
 * }
 * // Cannot call value by anonymousObject
 * ```
 * * If a function or property is public or private inline, its actual type is:
 *    * Any if the anonymous object doesn't have a declared supertype.
 *    * The declared supertype of the anonymous object, if there is exactly one such type.
 *    * The explicitly declared type if there is more than one declared supertype.
 * ```
 * // anonymousObject is Any
 * val anonymousObject = object : KotlinClass { ... }
 *
 * // Returned anonymousObject Any
 * fun getAnonymousObject() = object : KotlinClass { ... }
 * ```
 * ```
 * // anonymousObject is KotlinClass
 * val anonymousObject: KotlinClass =
 *    object : KotlinClass { ... }
 *
 * // Returned anonymousObject KotlinClass
 * fun getAnonymousObject(): KotlinClass =
 *    object : KotlinClass { ... }
 * ```
 * ```
 * interface KotlinInterface {  ...  }
 *
 * // anonymousObject is KotlinInterface
 * val anonymousObject: KotlinInterface =
 *    object : KotlinClass, KotlinInterface { ... }
 *
 * // Returned anonymousObject KotlinInterface
 * fun getAnonymousObject(): KotlinInterface =
 *    object : KotlinClass, KotlinInterface { ... }
 * ```
 * * In all these cases, members added in the anonymous object are not accessible unless the
 * property or function is private.
 * * If an object expression inherits from more than one class or interface, the members added to the
 * anonymous object are not accessible, and we must explicitly declare the property type or return type to the function.
 * * Overridden members are accessible if they are declared in the actual type of the function or property.
 */

class ObjectExpression {

    val classValue = 2

    private val privateObjectExpression = object {
        val value1: Int = classValue
        var variable1: String = "a"
    }

    val publicObjectExpression1 = object {
        val value1: Int = classValue
        var variable1: String = "a"
    }

    val publicObjectExpression2 = object : Class() {
        val value1: Int = classValue
        var variable1: String = "a"
    }

    val publicObjectExpression3: Interface = object : Class(), Interface {
        val value1: Int = classValue
        var variable1: String = "a"
        override val abstractProperty: Int
            get() = 2

        override fun fun1() {
            super<Class>.fun1()
            super<Interface>.fun1()
        }
    }

    private fun privateFunReturnedObjectExpression() = object : Class(1) {
        val value1: Int = classValue
        var variable1: String = "a"

        override fun fun9() {
            super.fun9()
        }
    }

    fun publicFunReturnedObjectExpression(): Class = object : Class(1), Interface {
        val value1: Int = classValue
        var variable1: String = "a"

        override fun fun1() {

        }

        override fun fun2(x: Int) {
            printYellow("Implemented in getObjectExpression2")
        }

        override val abstractProperty: Int
            get() = 110
    }

    fun fun1() {
        val functionValue = 2

        val localObjectExpression = object : Interface {
            val value1: Int = 1

            override val abstractProperty: Int
                get() = functionValue
        }

        printYellow("privateObjectExpression --> $privateObjectExpression")
        printYellow("privateObjectExpression parameters -->  ${privateObjectExpression.value1} - ${privateObjectExpression.variable1}")
        printYellow("publicObjectExpression1 --> $publicObjectExpression1")
        printYellow("publicObjectExpression2 --> ${publicObjectExpression2.fun1()}")
        printYellow("publicObjectExpression3 --> ${publicObjectExpression3.fun1()}")
        printYellow("${localObjectExpression.value1}")
        printYellow("privateFunReturnedObjectExpression --> ${privateFunReturnedObjectExpression().value1}")
        printYellow("publicFunReturnedObjectExpression --> ${publicFunReturnedObjectExpression().fun2()}")
    }
}


/**
 * * Object declaration:
 *    * it is used to implementation of the Singleton pattern in kotlin.
 * * An object can derive from a class or implement interfaces:
 * ```
 * object DerivedObject : KotlinClass(),
 *     KotlinInterface1, KotlinInterface2, ... { ... }
 * ```
 * * Object declarations can't be local (that is, they can't be nested directly inside a function),
 * but they can be nested into other object declarations or non-inner classes.
 * * Just like data classes in data object the compiler generates `toString()`, `equals()`, `hashCode()`.
 * ```
 * data object DataObject { ... }
 * ```
 * * You can't provide a custom equals or hashCode implementation for a data object.
 * * Data object does not have `copy()` and `componentN()` function.
 */


class ObjectDeclaration1 {

    companion object Name : Interface {
        override val abstractProperty: Int
            get() = 66
    }

    fun fun1() {
        ObjectDeclaration2.objectFun()
        ObjectDeclaration3.fun1()
        printYellow(InnerObject.INNER_VALUE)
        printYellow("$abstractProperty")
    }

    object InnerObject {
        const val INNER_VALUE = "inner value"
    }

    class Inner {
        object InnerObject {
            const val INNER_VALUE = "inner value"
        }
    }
}

object ObjectDeclaration2 {
    private val a = 1
    var b = "b"
    const val A = 2

    fun objectFun() {
        printYellow("objectFun -> a = $a, b = $b, A = $A")
        printYellow(ObjectDeclaration3.InnerObject.objectFun())
    }
}

object ObjectDeclaration3 : Class(1, "a"), Interface {

    override val abstractProperty: Int
        get() = 110
    override val openProperty: String
        get() = super.openProperty

    private val a = 1
    var b = "b"
    const val A = 2


    override fun fun1() {
        super<Class>.fun1()
    }

    fun objectFun() {
        printYellow("objectFun -> a = $a, b = $b, A = $A")
    }

    object InnerObject {

        fun objectFun(): String {
            return "InnerObject"
        }
    }
}

data object DataObject {

    override fun toString(): String {
        return super.toString()
    }

//    override fun equals(other: Any?): Boolean {
//        return super.equals(other)
//    }

//    override fun hashCode(): Int {
//        return super.hashCode()
//    }
}