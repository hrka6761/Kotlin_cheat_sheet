package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.helpers.Log.printYellow


/**
 *  * The most basic use case for enum classes is the implementation of type-safe enums.
 *  * An enum class can implement interfaces.
 *  * An enum class cannot derive from a class.
 *  * We can override members (implemented by enum class) can be implemented
 *  in every instance or by own enum class.
 *  * Enum class can contain abstract member that must be implemented by all instances.
 *  * Primary constructor of enum class is private (we cannot instantiate it outside the class body).
 *  * No class can derive from the enum class(Enum classes cannot be open and always is final).
 *  * All enum classes implemented the Comparable interface by default.
 *  * Each enum constant is an object.
 *  * Enum classes in Kotlin have synthetic properties and methods
 *  for listing the defined enum constants and getting an enum constant by its name:
 *    * EnumClass.valueOf(value: String): EnumClass
 *    * EnumClass.entries: EnumEntries<EnumClass>
 *  * Every enum constant also has properties: name and ordinal,
 *  for obtaining its name and position (starting from 0) in the enum class declaration.
 *  * The valueOf() method throws an IllegalArgumentException if
 *  the specified name does not match any of the enum constants defined in the class.
 */


enum class EnumClass(val value: Int) : A, B {
    Instance1(1) {
        override val e: Int
            get() = 1

        override fun c() {
            printYellow("C implementation in Instance1")
        }

        override fun b() {
            printYellow("B implementation in Instance1")
        }
    },
    Instance2(2) {
        override val e: Int
            get() = 2

        override fun c() {
            printYellow("C implementation in Instance2")
        }

        override fun b() {
            printYellow("B implementation in Instance2")
        }
    },
    Instance3(3) {
        override val e: Int
            get() = 3

        override fun c() {
            printYellow("C implementation in Instance3")
        }

        override fun b() {
            printYellow("B implementation in Instance3")
        }
    };


    companion object {

        const val CONSTANT = "hamidreza"

        fun e() {
            printYellow("EnumClass companion function")
        }
    }


    override fun a() {
        printYellow("A implementation in EnumClass")
    }


    abstract val e: Int

    abstract fun c()


    fun d() {
        printYellow("D implementation in EnumClass")
    }
}


interface A {
    fun a()
}

interface B {
    fun b()
}