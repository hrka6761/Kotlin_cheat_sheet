package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.helpers.Log.printYellow

/**
 * * Sometimes you need to create an object that is a slight modification of some class,
 * without explicitly declaring a new subclass for it.
 * * Object expressions create objects of anonymous classes (anonymous objects).
 * * When an anonymous object is used as a type of a local or private but not inline declaration (function or property),
 * all its members are accessible via this function or property.
 * * If this function or property is public or private inline, its actual type is:
 *    * Any if the anonymous object doesn't have a declared supertype.
 *    * The declared supertype of the anonymous object, if there is exactly one such type.
 *    * The explicitly declared type if there is more than one declared supertype.
 * * In all these cases, members added in the anonymous object are not accessible unless the property or function is private and inherit at most from one type.
 * * Overridden members are accessible if they are declared in the actual type of the function or property.
 * * Anonymous classes declared have access to the variables in the enclosing scope. (scope of class or function)
 */

class ObjectExpression {

    val classValue = 2

    private val objectExpression1 = object {
        val value1: Int = classValue
        var variable1: String = "a"
    }

    val objectExpression2 = object {
        val value1: Int = classValue
        var variable1: String = "a"
    }

    private fun getObjectExpression1() = object : Class(1) {
        val value1: Int = classValue
        var variable1: String = "a"
    }

    fun getObjectExpression2(): Class = object : Class(1), Interface {
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

        val funExpression = object : Interface {
            override val abstractProperty: Int
                get() = functionValue
        }

        printYellow("objectExpression1 --> $objectExpression1")
        printYellow("objectExpression1 parameters -->  ${objectExpression1.value1} - ${objectExpression1.variable1}")
        printYellow("objectExpression2 --> $objectExpression2")
        printYellow("getObjectExpression1 function --> ${getObjectExpression1().value1}")
        printYellow("getObjectExpression2 function --> ${getObjectExpression2().fun2()}")
    }
}


/**
 * * Object declarations are used to implementation of the Singleton pattern in kotlin.
 * * An object can derive from a class or implement interfaces.
 * * Object declarations can't be local (that is, they can't be nested directly inside a function),
 * but they can be nested into other object declarations or non-inner classes.
 * * Just like data classes in data object the compiler generates toString(), equals(), hashCode().
 * * You can't provide a custom equals or hashCode implementation for a data object.
 * * Data object does not have copy() and componentN() function.
 * * An object declaration inside a class can be marked with the companion keyword (companion object).
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