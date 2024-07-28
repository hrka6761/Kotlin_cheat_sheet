package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.helpers.Log.printYellow


/**
 * * When you call such a function on an object with a lambda expression provided,
 * it forms a temporary scope. In this scope, you can access the object without its name.
 * * Scope functions don't introduce any new technical capabilities, but they can make your code more concise and readable.
 * * There are two main differences between each scope function:
 *    * The way they refer to the context object.
 *    * Their return value.
 * * Each scope function uses one of two ways to reference the context object:
 *    * as a lambda receiver (this) --> run, with, apply
 *    * as a lambda argument (it).  --> also, let
 * * Each scope function returns one of the following two items:
 *    * context object --> apply, also
 *    * lambda result  --> let, run, with
 */

//    ------------------------------------------------------
//    |\\\\\\\\\\\\\\\\|--Receiver(this)--|--Argument(it)--|
//    |-lambda result--|     run-with     |       let      |
//    |-context object-|      apply       |       also     |
//    ------------------------------------------------------


class ScopeFunctions {

    private var data: DataClass? = null


    fun let(condition: Boolean) {
        var returned:String? = data?.let {
            printYellow(it.toString())
            if (condition)
                return
            else
                return@let "let returned"
        }

        printYellow(returned.toString())
    }

    fun with() {
        with(data) {

        }
    }
}