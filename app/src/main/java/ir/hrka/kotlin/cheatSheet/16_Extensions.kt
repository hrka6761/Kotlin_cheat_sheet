package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * Kotlin provides the ability to extend a class or an interface with new functionality without
 * having to inherit from the class and This is done via special declarations called extensions:
 * ```
 * fun KotlinClass.extensionFun(val str: String) { ... }
 * ```
 * ```
 * var KotlinClass.extensionProperty: Int
 *     get { ... }
 *     set(value) { ... }
 * ```
 * ```
 * infix fun Int.plus(int: Int): Int = this + int
 * val result = 1 + 1
 * ```
 * * The 'this' keyword inside an extension function or getter and setter body of extension property
 * corresponds to the receiver object.
 * * If an extension is declared outside its receiver type, it cannot access
 * the receiver's private or protected members.
 * * By defining an extension, you are not inserting new members into a class,
 * only making new functions callable with the dot-notation on variables of this type.
 * * If a class has a member function, and an extension function is defined which has the same receiver type,
 * the same name, and is applicable to given arguments, the member always wins.
 * * It's perfectly OK for extension functions to overload member functions that have the same name
 * but a different signature.
 * * Since extensions do not actually insert members into classes,
 * there's no efficient way for an extension property to have a backing field.
 * This is why initializers are not allowed for extension properties.
 * * Extensions behavior can only be defined by explicitly providing getters/setters.
 * * We can call extensions on the instance of subclasses:
 * ```
 * class SuperClass() { ... }
 * class SubClass() : SuperClass() { ... }
 * ```
 * ```
 * fun SuperClass.extensionFun(val str: String) { ... }
 * val SuperClass.extensionProperty: Int
 *     get { ... }
 *     set(value) { ... }
 * ```
 * ```
 * val sub: SubClass = SubClass()
 * sub.extensionFun("str")
 * sub.extensionProperty = 2
 * ```
 * * If a class has a companion object defined, you can also define extension functions and
 * properties for the companion object:
 * ```
 * class KotlinClass {
 *    companion object {
 *       ...
 *    }
 * }
 * ```
 * ```
 * fun KotlinClass.Companion.extensionFun() { ... }
 * var KotlinClass.Companion.extensionProperty: Int
 *     get { ... }
 *     set(value) { ... }
 * ```
 * * To use an extension outside its declaring package, import it at the call site:
 * ```
 * import ir.hrka.kotlin.cheatSheet.extensionFun1
 * ```
 * * We can declare extensions as members of a class, in this way there are two implicit receivers:
 *    * Dispatch receiver (Instance of class that in which the extension is declared).
 *    * Extension receiver (Instance of class that extension declared on it).
 * ```
 * open class Parent {
 *     open var KotlinClass.extensionProperty: String
 *         get() = { ... }
 *         set(value) { ... }
 *
 *     open fun KotlinClass.extensionFunction() { ... }
 * }
 * ```
 * ```
 * class Child : Parent() {
 *
 *     override var KotlinClass.extensionProperty: String
 *         get() = { ... }
 *         set(value) { ... }
 *
 *     override fun KotlinClass.extensionFunction() { ... }
 * }
 * ```
 * * In the event of a conflict name between the members of dispatch receiver and extension receiver,
 * the extension receiver takes precedence.
 * * In extension function 'this' refer to the extension receiver and 'this@DispatchReceiver' refer
 * to the dispatch receiver.
 * * when defining an extension with a nullable receiver type, we recommend performing a this == null
 * check inside the function body to avoid compiler errors.
 */


fun Class.extensionFun1() {
    printYellow("extensionFun1")
}

fun Class.Companion.extensionFun2() {
    printYellow("extensionFun1")
}

fun Interface.extensionOverriding() {
    printYellow("extensionOverriding in Interface")
}

fun Inheritance.extensionOverriding() {
    printYellow("extensionOverriding in inheritance")
}

fun Inheritance.outerFun() {
    printYellow("fun1 -->  Implemented in extension fun")
}

fun Inheritance.outerFun(int: Int) {
    printYellow("fun1 -->  Implemented in extension fun with the different signature")
}

var Inheritance.exProperty: Int
    get() = 1
    set(value) {
        printYellow(this.openProperty)
    }

var Interface.exProperty: String
    get() {
        return "ExPro"
    }
    set(value) {}


open class Parent {

    private val parentValue = 1
    val clazz = Class()


    open var Class.memberExtensionProperty: String
        get() = "Parent"
        set(value) {}

    open fun Class.memberExtensionFunction() {
        printYellow("parentValue = $parentValue")
        printYellow("memberExtensionProperty = $memberExtensionProperty")
        printYellow(this.toString())
        printYellow(this@Parent.toString())
    }

    open fun fun1() {
        printYellow("memberExtensionProperty = ${clazz.memberExtensionProperty}")
        clazz.memberExtensionFunction()
    }
}

class Child : Parent() {

    override var Class.memberExtensionProperty: String
        get() = "Child"
        set(value) {}

    override fun Class.memberExtensionFunction() {
        printYellow("memberExtensionProperty = $memberExtensionProperty")
        printYellow(this.toString())
        printYellow(this@Child.toString())
    }

    override fun fun1() {
        printYellow("memberExtensionProperty = ${clazz.memberExtensionProperty}")
        clazz.memberExtensionFunction()
    }
}

