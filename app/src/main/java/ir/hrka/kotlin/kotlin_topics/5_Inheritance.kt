package ir.hrka.kotlin.kotlin_topics

import ir.hrka.kotlin.core.utilities.Log.printYellow


/**
 * * All classes in Kotlin have a common superclass, `Any`,
 * which is the default superclass for a class with no supertypes declared:
 *    * Every kotlin class is an Any.
 * ```
 * class KotlinClass {  ...  }
 *
 * if(KotlinClass is Any) {
 *     // Do something
 * }
 * ```
 * * Each class can only derive from one class but can implement multiple interfaces:
 * ```
 * open class KotlinClass {  ...  }
 * interface KotlinInterface1 {  ...  }
 * interface KotlinInterface2 {  ...  }
 * interface KotlinInterface3 {  ...  }
 * ```
 * ```
 * class DerivedClass : KotlinClass(), KotlinInterface1,
 *     KotlinInterface2,
 *     KotlinInterface3, ...
 * ```
 * * An interface can derive from other interfaces:
 * ```
 * interface DerivedInterface : KotlinInterface1, KotlinInterface2,
 *    KotlinInterface3, ...
 * ```
 * * By default, Kotlin classes are final and cannot have any sub classes.
 * * By default, all functions of Kotlin classes are final an cannot be overridden these in subclasses.
 * * We can use the override keyword as part of the property declaration in a primary constructor:
 * ```
 * interface KotlinInterface {
 *    val value: Int
 *    ...
 * }
 * ```
 * ```
 * class KotlinClass(override val value: Int)
 *     : KotlinInterface {
 *    ...
 * }
 * ```
 * * We can use the override keyword as part of the property declaration in the body:
 * ```
 * class KotlinClass : KotlinInterface() {
 *     override val value: Int
 *         get() = {
 *             // Do something
 *         }
 *    ...
 * }
 * ```
 * ```
 * class KotlinClass : KotlinInterface() {
 *     override val value: Int = 1
 *    ...
 * }
 * ```
 * * We can also override a val property with a var property, but not vice versa:
 * ```
 * class KotlinClass(override var value: Int) : KotlinInterface {
 *     ...
 * }
 * ```
 * ```
 * class KotlinClass : KotlinInterface() {
 *     override var value: Int
 *         get() = {
 *             // Do something
 *         }
 *         set(value) {
 *             // Do something
 *         }
 *    ...
 * }
 * ```
 * ```
 * class KotlinClass : KotlinInterface() {
 *     override var value: Int = 1
 *    ...
 * }
 * ```
 * * We can to prohibit re-overriding by using final:
 *    * This members can no longer be overridden in subclasses.
 * ```
 * interface KotlinInterface {
 *    val value: Long
 *    fun fun1()
 *    ...
 * }
 * ```
 * ```
 * class KotlinClass : KotlinInterface {
 *    final override value: Long = 1L
 *
 *    final override fun1() {
 *        // Do something
 *    }
 * }
 * ```
 * * An overriding function is not allowed to specify default values for its parameters.
 * * We can't call companion members of superclass by the subclass.
 * * If there is no open modifier on a function,
 * declaring a method with the same signature in a subclass is not allowed,
 * either with override or without it.
 * * In a subclass can call its superclass functions and property accessor implementations
 * using the super keyword:
 * ```
 * abstract KotlinAbstractClass {
 *
 *    open var variable: String
 *        get() {
 *            // KotlinAbstractClass implementation
 *        }
 *        set(value) {
 *            // KotlinAbstractClass implementation
 *        }
 *
 *    open fun fun1() {
 *        // KotlinAbstractClass implementation
 *    }
 *    ...
 * }
 * ```
 * ```
 * class KotlinClass : KotlinAbstractClass {
 *
 *    var variable: String
 *        get() {
 *            super.variable
 *            ...
 *        }
 *        set(value) {
 *            super.variable
 *            ...
 *        }
 *
 *    override fun1() {
 *        super.fun1()
 *        ...
 *    }
 * }
 * ```
 * * During the construction of a new instance of a derived class,
 * the base class initialization is done as the first step.
 * * If a same function is declared in the both super class and interface,
 * (or a same concrete function into two interface) must be implemented in sub class:
 * ```
 * open class KotlinClass {
 *     open fun fun1() {
 *         // KotlinClass implementation
 *     }
 * }
 * ```
 * ```
 * interface KotlinInterface1 {
 *     fun fun1()
 *
 *     fun fun2() {
 *        // KotlinInterface1 implementation
 *     }
 * }
 * ```
 * ```
 * interface KotlinInterface2 {
 *     fun fun2() {
 *        // KotlinInterface2 implementation
 *     }
 * }
 * ```
 * ```
 * class DerivedClass : KotlinClass(), KotlinInterface1,
 *     KotlinInterface2 {
 *
 *     override fun fun1() {
 *         // DerivedClass implementation
 *     }
 *
 *     override fun fun2() {
 *        // DerivedClass implementation
 *     }
 * }
 * ```
 * * To denote the supertype from which the inherited implementation is taken,
 * use super qualified by the supertype name in angle brackets:
 * ```
 * class DerivedClass : KotlinClass(), KotlinInterface1,
 *     KotlinInterface2 {
 *
 *     override fun fun1() {
 *         super<KotlinClass>.fun1()
 *         ...
 *     }
 *
 *     fun fun2() {
 *        super<KotlinInterface2>.fun2()
 *        super<KotlinInterface1>.fun2()
 *        ...
 *     }
 * }
 * ```
 * * In the subclass, we cannot reduce the access level of the member but we can increase:
 *    * if it public in super class, we cannot reduce to protected or private.
 *    * if it protected in super class, we can increase to public.
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
        set(value) {
            super.varWithImplementation
        }


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

    public override fun fun9() {
        super.fun9()
    }
}