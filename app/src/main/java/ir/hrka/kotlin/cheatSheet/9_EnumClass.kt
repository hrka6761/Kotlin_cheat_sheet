package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.core.utilities.Log.printYellow


/**
 * * The most basic use case for enum classes is the implementation of type-safe enums.
 * * An enum class can implement interfaces.
 * * An enum class cannot derive from a class.
 * * We can override members in every instance or by own enum class.
 * * Enum class can contain abstract member that must be implemented by all instances:
 * ```
 * interface KotlinInterface1 {
 *     fun fun1()
 * }
 * ```
 * ```
 * interface KotlinInterface2 {
 *     fun fun2()
 * }
 * ```
 * ```
 * enum class EnumClass(val value: Int) :
 *     KotlinInterface1,
 *     KotlinInterface2 {
 *
 *     Instance1(1) {
 *         override val absValue: Int
 *             get() = { ... }
 *
 *         override fun absFun() { ... }
 *         override fun fun2() { ... }
 *     },
 *     Instance2(2) {
 *         override val absValue: Int
 *             get() = { ... }
 *
 *         override fun absFun() { ... }
 *         override fun fun2() { ... }
 *     }, ...
 *
 *     override fun fun2() { ... }
 *
 *     abstract val absValue: Int
 *     abstract fun absFun()
 * }
 * ```
 * * Primary constructor of enum class is private:
 *    * we cannot instantiate it outside the class body.
 * * No class can derive from the enum class:
 *    * Enum classes cannot be open and always is final.
 * * All enum classes implemented the Comparable interface by default.
 * * Each enum constant is an object.
 * * Enum classes in Kotlin have synthetic properties and methods
 * for listing the defined enum constants and getting an enum constant by its name:
 * ```
 * EnumClass.valueOf(value: String)  // return EnumClass instance
 * EnumClass.entries: // return EnumEntries<EnumClass>
 * ```
 * * Every enum constant also has properties: name and ordinal,
 * for obtaining its name and position (starting from 0) in the enum class declaration:
 * ```
 * val name: String = Instance1.name
 * val index: Int = Instance1.ordinal
 * ```
 * * The valueOf() method throws an `IllegalArgumentException` if
 * the specified name does not match any of the enum constants defined in the class.
 * ```
 * val enums: Array<EnumClass> = EnumClass.values()
 * val enum: EnumClass =  EnumClass.valueOf("Instance1")
 * ```
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