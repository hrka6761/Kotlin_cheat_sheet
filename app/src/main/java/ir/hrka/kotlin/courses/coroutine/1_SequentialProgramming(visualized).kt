package ir.hrka.kotlin.courses.coroutine

import ir.hrka.kotlin.core.utilities.Log.printYellow

/**
 * * Programming paradigms:
 *    * Sequential programming.
 *    * Concurrent programming.
 *    * Parallel programming.
 * * Sequential programming is a programming paradigm where instructions are executed one after another in a specific:
 *    * In this paradigm, instructions are executed in the single thread.
 * * Concurrent programming is a programming paradigm in which instructions are executed simultaneously, independently of each other:
 *    * In this paradigm, instructions are executed in the multi thread or coroutine.
 *    * In this paradigm, tasks are not executed parallel.
 *    * In this paradigm, only one task is processed at a time and other tasks are suspended.
 * * Parallel programming is a programming paradigm in which instructions are executed at a time and in parallel:
 *    * In this paradigm, instructions are executed by multi cores or processors.
 *    * Unlike Concurrent programming in this paradigm, tasks are executed together and their execution processes at a time.
 * ```
 *    fun main() {
 *        task1()
 *        task2()
 *        task3()
 *    }
 *
 *    private fun task1() { ... }
 *    private fun task2() { ... }
 *    private fun task3() { ... }
 * ```
 */

class SequentialProgramming {

    fun fun1() {
        task1()
        task2()
        task3()
    }


    private fun task1() {
        printYellow("Start task1")
        Thread.sleep(1_000)
        printYellow("End task1")
    }

    private fun task2() {
        printYellow("Start task2")
        Thread.sleep(4_000)
        printYellow("End task2")
    }

    private fun task3() {
        printYellow("Start task3")
        Thread.sleep(2_000)
        printYellow("End task3")
    }
}