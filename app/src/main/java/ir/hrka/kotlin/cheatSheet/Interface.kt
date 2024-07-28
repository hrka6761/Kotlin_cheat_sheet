package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.helpers.Log.printYellow

/**
 * * Interface is open and public by default.
 * * An interface cannot contain any constructor.
 * * We cannot instantiate from an Interface directly.
 * * An interface can derive from other interfaces.
 * * What makes them different from abstract classes is that interfaces cannot store state.
 * * Properties declared in interfaces can't have backing fields,
 * and therefore accessors declared in interfaces can't reference them.
 * * A property declared in an interface can either be abstract or provide implementations for accessors.
 * * Interface can't have properties with initializers.(because it cannot have state)
 * * Interface can contains abstract and concrete methods.
 * * Concrete member is open by default.
 * * We can't use 'final' keyword in an interface.
 */


interface Interface {

    val abstractProperty: Int

    val valWithImplementation: String
        get() = "foo"

    var varWithImplementation: String
        get() = "foo"
        set(value) {}

    companion object {
        val CONSTANT = "hamidreza"
    }

    fun fun1() {
        printYellow("fun1 --> Implemented in Interface")
    }

    fun fun2() {
        printYellow("fun2 --> Implemented in Interface")
    }
}

interface AnotherInterface {
    fun fun2()
}