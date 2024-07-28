package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.helpers.Log.printYellow


/**
 *  * All classes in Kotlin have a common superclass, Any,
 *  which is the default superclass for a class with no supertypes declared.
 *  * Each class can only derive from one class but can implement multiple interfaces.
 *  * By default, Kotlin classes are final.
 *  * By default, all members of Kotlin classes are final.
 *  * We can use the override keyword as part of the property declaration in a primary constructor.
 *  * We can use the override keyword as part of the property declaration in the body.
 *  * We can also override a val property with a var property, but not vice versa.
 *  * We can to prohibit re-overriding by using final.
 *  * An overriding function is not allowed to specify default values for its parameters.
 *  * We can't call companion members of superclass by the subclass.
 *  * If there is no open modifier on a function,
 *  declaring a method with the same signature in a subclass is not allowed,
 *  either with override or without it.
 *  * Code in a subclass can call its superclass functions and property accessor implementations
 *  using the super keyword.
 *  * Inside an inner class, accessing the superclass of the outer class.
 *  is done using the super keyword qualified with the outer class name: super@Outer.
 *  * During the construction of a new instance of a derived class,
 *  the base class initialization is done as the first step.
 *  * If a same function is declared in the both super class and interface,
 *  (or a same concrete function into two interface) must be implemented in sub class.
 *  * To denote the supertype from which the inherited implementation is taken,
 *  use super qualified by the supertype name in angle brackets.
 *  * In the subclass, we cannot reduce the access level of the member but we can increase
 *  (if it public in super class, we cannot reduce to protected or private).
 */


open class Inheritance(
    int: Int, override val abstractProperty: Int
) : Class(int), Interface, AnotherInterface {

//    override val abstractProperty: Int = 1

    override val openProperty: String
        get() = super.openProperty

    override val valWithImplementation: String
        get() = super.valWithImplementation

    override var varWithImplementation: String
        get() = super.varWithImplementation
        set(value) {}


    init {
        printYellow("Inheritance: init block 1")
    }

    init {
        printYellow("Inheritance: init block 2")
    }


    constructor(int: Int, string: String = "a") : this(int, int) {
        printYellow("Inheritance: secondary constructor 1")
    }

    constructor(int: Int, string: String, float: Float) : this(int, string) {
        printYellow("Inheritance: secondary constructor 3")
    }


    override fun fun2() {
        super<Interface>.fun2()
        printYellow("fun2 --> Implemented in Inheritance")
    }

    override fun fun1() {
        super<Class>.fun1()
        super<Interface>.fun1()
    }

    final override fun fun2(x: Int) {
        super<Class>.fun2(2)
        printYellow("Inheritance: fun2")
    }
}