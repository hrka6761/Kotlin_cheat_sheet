package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printBlue
import ir.hrka.kotlin.core.utilities.Log.printRed
import ir.hrka.kotlin.core.utilities.Log.printYellow
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * * The Delegation pattern has proven to be a good alternative to implementation inheritance, and
 * Kotlin supports it natively requiring zero boilerplate code.
 * * By using the 'by' keyword we can delegate an interface implementation to the implemented class
 * without writing all of functions to call implementations.
 * * The compiler will use your override implementations instead of those in the delegate object.
 */

interface Base {

    val value1: Int
    val value2: Int
    var value3: String

    fun fun1()
    fun fun2()
    fun fun3()
    fun fun4()
    fun fun5()
    fun fun6()
    fun fun7()
}

class BaseImpl1() : Base {
    override val value1: Int
        get() = 1
    override val value2: Int
        get() = 2
    override var value3: String
        get() = "BaseImpl1"
        set(value) {}

    override fun fun1() {
        printYellow("fun1 in BaseImpl1")
    }

    override fun fun2() {
        printYellow("fun2 in BaseImpl1 --> value1 = $value1, value2 = $value2")
    }

    override fun fun3() {
        printYellow("fun3 in BaseImpl1")
    }

    override fun fun4() {
        printYellow("fun4 in BaseImpl1")
    }

    override fun fun5() {
        printYellow("fun5 in BaseImpl1")
    }

    override fun fun6() {
        printYellow("fun6 in BaseImpl1")
    }

    override fun fun7() {
        printYellow("fun7 in BaseImpl1")
    }

}

class BaseImpl2() : Base {
    override val value1: Int
        get() = 3
    override val value2: Int
        get() = 4
    override var value3: String
        get() = "BaseImpl2"
        set(value) {}

    override fun fun1() {
        printBlue("fun1 in BaseImpl2")
    }

    override fun fun2() {
        printBlue("fun2 in BaseImpl2 --> value1 = $value1, value2 = $value2")
    }

    override fun fun3() {
        printBlue("fun3 in BaseImpl2")
    }

    override fun fun4() {
        printBlue("fun4 in BaseImpl2")
    }

    override fun fun5() {
        printBlue("fun5 in BaseImpl2")
    }

    override fun fun6() {
        printBlue("fun6 in BaseImpl2")
    }

    override fun fun7() {
        printBlue("fun7 in BaseImpl2")
    }
}

class DelegateClass1(baseImplementation: Base) : Base by baseImplementation

class DelegateClass2(baseImplementation: Base) : Base by baseImplementation {

    override val value1: Int
        get() = 5

    override val value2: Int
        get() = 6

    override fun fun1() {
        printRed("fun1 in DelegatedClass")
    }
}


/**
 * * `Lazy properties`:
 *    * the value is computed only on first access.
 * * We can pass a map object into a class by storing properties in a map instead of a separate field
 * for each property and inferred the value by `by` keyword.
 * * `lazy()` is a function that takes a lambda and returns an instance of Lazy<T>,
 * which can serve as a delegate for implementing a lazy property.
 * The first call to get() executes the lambda passed to lazy() and remembers the result.
 * Subsequent calls to get() simply return the remembered result.
 * * The property that be delegated by lazy must be as a val no var.
 * * Modes of lazy are:
 *    * `SYNCHRONIZED`(default) -> The value is computed only in one thread, but all threads will see the same value.
 *    * `PUBLICATION` -> If the synchronization of the initialization delegate is not required to allow multiple threads to execute it simultaneously
 *    * `NONE` -> If you're sure that the initialization will always happen in the same thread as the one where you use the property
 * * `Observable` properties:
 *    * Delegates.observable() takes two arguments, the initial value and a handler for modifications.
 *    the handler is called every time you assign to the property
 * * `vetoable` properties:
 *    * If you want to intercept assignments and veto them
 * * A property can delegate its getter and setter to another property.
 * * We can create custom property delegate.
 * * For a read-only property (val), a delegate should provide an operator function getValue() with the following parameters:
 *    * thisRef must be the same type as, or a supertype of, the property owner (for extension properties, it should be the type being extended).
 *    * property must be of type KProperty<*> or its supertype.
 *    * getValue() must return the same type as the property (or its subtype).
 * * For a mutable property (var), a delegate has to additionally provide an operator function setValue() with the following parameters:
 *    * thisRef must be the same type as, or a supertype of, the property owner (for extension properties, it should be the type being extended).
 *    * property must be of type KProperty<*> or its supertype.
 *    * value must be of the same type as the property (or its supertype).
 * * The type of thisRef (T) can vary depending on where the property is declared or accessed:
 *    * If the property is in a class, thisRef is an instance of that class.
 *    * For an extension property, thisRef is the instance on which the extension is applied.
 *    * In singleton objects, thisRef is the singleton object itself.
 *    * For top-level properties, thisRef might be null or refer to the context in which it's used.
 * * You can create delegates as anonymous objects without creating new classes,
 * by using the interfaces `ReadOnlyProperty` and `ReadWriteProperty` from the Kotlin standard library.
 * T the type of object which owns the delegated property and V the type of the property value.
 * * Storing properties in a map:
 *    * we can use the map instance itself as the delegate for a delegated property.
 * * We can declare local variables as delegated properties (In the function).
 * The variable will be computed on first access only.
 */

class Data1(map: Map<String, Any?>) {
    val p1: String by map
    val p2: Int by map
}

class Data2(map: MutableMap<String, Any?>) {
    var p1: String by map
    var p2: Int by map
}

class Delegation {

    val clazz = Class()

    val lazyProperty: Int by lazy(
        mode = LazyThreadSafetyMode.NONE,
        initializer = {
            printYellow("Lazy initializer")
            return@lazy 1
        }
    )

    var observableProperty: String by Delegates.observable(
        initialValue = "red",
        onChange = { prop, old, new ->
            printYellow("$prop --> old value = $old, new value = $new")
        }
    )

    var vetoableProperty: Long by Delegates.vetoable(
        2L,
        onChange = { prop, old, new ->
            printYellow("$prop --> old value = $old, new value = $new")
            return@vetoable new >= old
        }
    )

    var notNullProperty by Delegates.notNull<Byte>()

    val delegatedAnotherProperty1: Int by ::lazyProperty

    val delegatedAnotherProperty2: Int by ::topLevelProperty

    val delegatedAnotherProperty3: Int by clazz::int

    var delegatedProperty: Int by Delegate1(2)
}

val topLevelProperty: Int by lazy(
    mode = LazyThreadSafetyMode.NONE,
    initializer = {
        printYellow("Lazy initializer")
        return@lazy 1
    }
)

val delegation = Delegation()
val Class.extensionProperty1: Int by Delegate1(1)
val Class.extensionProperty2: Int by ::topLevelProperty

fun localDelegation(condition: Boolean, initializer: () -> Int) {
    val a: Int by lazy(mode = LazyThreadSafetyMode.NONE, initializer = initializer)

    if (condition) {
        printYellow("The value of a = $a")
    }
}

class Delegate1(private var int: Int) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        printYellow("getValue")
        return this.int
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, i: Int) {
        printYellow("setValue ---> $i")
        this.int = i
    }
}

class Delegate2 : ReadWriteProperty<Delegation, String> {
    override fun getValue(thisRef: Delegation, property: KProperty<*>): String {
        printYellow("getValue")

        return "get value in Delegate2"
    }

    override fun setValue(thisRef: Delegation, property: KProperty<*>, value: String) {
        printYellow("set value in Delegate2 ---> $value")
    }
}

class Delegate3(private val boolean: Boolean) : ReadOnlyProperty<Delegation, Boolean> {
    override fun getValue(thisRef: Delegation, property: KProperty<*>): Boolean {
        printYellow("getValue")
        return boolean
    }
}
