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
 * without writing all of functions to call implementations:
 * ```
 * interface Base {
 *
 *     val value: Int
 *     var variable: String
 *
 *     fun fun1()
 *     fun fun2()
 *     fun fun3()
 *     fun fun4()
 *     fun fun5()
 *     fun fun6()
 *     fun fun7()
 * }
 * ```
 * ```
 * class BaseImpl1() : Base {
 *     override val value: Int
 *         get() = 1
 *     override var variable: String
 *         get() = "BaseImpl1"
 *         set(value) {}
 *
 *     override fun fun1() { BaseImpl1 implementation }
 *     override fun fun2() { BaseImpl1 implementation }
 *     override fun fun3() { BaseImpl1 implementation }
 *     override fun fun4() { BaseImpl1 implementation }
 *     override fun fun5() { BaseImpl1 implementation }
 *     override fun fun6() { BaseImpl1 implementation }
 *     override fun fun7() { BaseImpl1 implementation }
 * }
 * ```
 * ```
 * class BaseImpl2() : Base {
 *     override val value: Int
 *         get() = 2
 *     override var variable: String
 *         get() = "BaseImpl2"
 *         set(value) {}
 *
 *     override fun fun1() { BaseImpl2 implementation }
 *     override fun fun2() { BaseImpl2 implementation }
 *     override fun fun3() { BaseImpl2 implementation }
 *     override fun fun4() { BaseImpl2 implementation }
 *     override fun fun5() { BaseImpl2 implementation }
 *     override fun fun6() { BaseImpl2 implementation }
 *     override fun fun7() { BaseImpl2 implementation }
 * }
 * ```
 * ```
 * class DelegateClass1(baseImplementation: Base) :
 *        Base by baseImplementation
 * ```
 * * The compiler will use your override implementations instead of those in the delegate object:
 * ```
 * class DelegateClass2(baseImplementation: Base) :
 *        Base by baseImplementation {
 *
 *     override val value1: Int
 *         get() = 3
 *
 *     override fun fun1() { DelegateClass2 implementation }
 * }
 * ```
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
 * ```
 * val lazyProperty: String by lazy (
 *     mode = LazyThreadSafetyMode.SYNCHRONIZED,
 *     initializer = {
 *         return@lazy "str"
 *     }
 * )
 * ```
 * * `lazy()` is a function that takes a lambda and returns an instance of Lazy<T>,
 * which can serve as a delegate for implementing a lazy property.
 * The first call to get() executes the lambda passed to lazy() and remembers the result.
 * Subsequent calls to get() simply return the remembered result.
 * * The property that be delegated by lazy must be as a val no var.
 * * Storing properties in a map:
 *    * we can use the map instance itself as the delegate for a delegated property.
 * ```
 * class Data1(map: Map<String, String?>) {
 *     val p1: String by map
 *     val p2: Int by map
 * }
 * ```
 * ```
 * class Data2(map: MutableMap<String, String?>) {
 *     var p1: String by map
 *     var p2: Int by map
 * }
 * ```
 * ```
 * val data1 = mapOf<String, Any?>(
 *      Pair("p1", "str"), Pair("p2", 1)
 * )
 * Data1(data1)
 * ```
 * ```
 * val data2 = mutableMapOf<String, Any?>(
 *      Pair("p1", "str"), Pair("p2", 1)
 * )
 * Data2(data2)
 * ```
 * * Modes of lazy are:
 *    * `SYNCHRONIZED`(default) -> The value is computed only in one thread, but all threads will see the same value.
 *    * `PUBLICATION` -> If the synchronization of the initialization delegate is not required to allow multiple threads to execute it simultaneously.
 *    * `NONE` -> If you're sure that the initialization will always happen in the same thread as the one where you use the property.
 * * `Observable` properties:
 *    * observable() takes two arguments, the initial value and a handler for modifications
 *    the handler is called every time you assign to the property.
 * ```
 * var observableProperty: String by Delegates.observable(
 *         initialValue = "value1",
 *         onChange = { prop, old, new ->
 *             ...
 *         }
 *     )
 * ```
 * * `vetoable` properties:
 *    * If you want to intercept assignments and veto them.
 * ```
 * var vetoableProperty: Long by Delegates.vetoable(
 *         initialValue = 1L,
 *         onChange = { prop, old, new ->
 *             ...
 *         }
 *     )
 * ```
 * * A property can delegate its getter and setter to another property:
 * ```
 * val delegatedAnotherProperty: Int by ::lazyProperty
 * ```
 * ```
 * val topLevelProperty: Int by lazy(
 *     mode = LazyThreadSafetyMode.NONE,
 *     initializer = {
 *         return@lazy 1
 *     }
 * )
 * val delegatedAnotherProperty: Int by ::topLevelProperty
 * ```
 * ```
 * val clazz = Class()
 * val delegatedAnotherProperty3: Int by clazz::int
 * ```
 * * We can create custom property delegate:
 * ```
 * class Delegate(private var int: Int) {
 *     operator fun getValue(
 *          thisRef: Any?,
 *          property: KProperty<*>
 *     ): Int {
 *         return this.int
 *     }
 *
 *     operator fun setValue(
 *           thisRef: Any?,
 *           property: KProperty<*>,
 *            i: Int
 *     ) {
 *         this.int = i
 *     }
 * }
 * ```
 * ```
 * var delegatedProperty: Int by Delegate(2)
 * ```
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
 * by using the interfaces `ReadOnlyProperty` and `ReadWriteProperty` from the Kotlin standard library:
 * ```
 * class Delegate(private val boolean: Boolean) :
 *   ReadOnlyProperty<Delegation, Boolean> {
 *     override fun getValue(
 *         thisRef: Delegation,
 *         property: KProperty<*>
 *     ): Boolean {
 *         return boolean
 *     }
 * }
 * ```
 * ```
 * class Delegate(private var str: String) :
 *   ReadWriteProperty<Delegation, String> {
 *      override fun getValue(
 *            thisRef: Delegation,
 *            property: KProperty<*>
 *      ): String {
 *          return "get value in Delegate2"
 *      }
 *
 *      override fun setValue(
 *            thisRef: Delegation,
 *            property: KProperty<*>,
 *            value: String
 *      ) {
 *           this.str = value
 *      }
 * }
 * ```
 * * T the type of object which owns the delegated property and V the type of the property value:
 * ```
 * public fun interface ReadOnlyProperty<in T, out V>
 * public interface ReadWriteProperty<in T, V>
 * ```
 * * We can declare local variables as delegated properties (In the function).
 * The variable will be computed on first access only:
 * ```
 * fun localDelegation(condition: Boolean, initializer: () -> Int) {
 *     val a: Int by lazy(
 *         mode = LazyThreadSafetyMode.NONE,
 *         initializer = initializer
 *     )
 *
 *     if (condition) {
 *         // Do something
 *     }
 * }
 * ```
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
        mode = LazyThreadSafetyMode.SYNCHRONIZED,
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
        initialValue = 2L,
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

class Delegate2(private var str: String) : ReadWriteProperty<Delegation, String> {
    override fun getValue(thisRef: Delegation, property: KProperty<*>): String {
        printYellow("getValue")

        return "get value in Delegate2"
    }

    override fun setValue(thisRef: Delegation, property: KProperty<*>, value: String) {
        this.str = value
        printYellow("set value in Delegate2 ---> $value")
    }
}

class Delegate3(private val boolean: Boolean) : ReadOnlyProperty<Delegation, Boolean> {
    override fun getValue(thisRef: Delegation, property: KProperty<*>): Boolean {
        printYellow("getValue")
        return boolean
    }
}
