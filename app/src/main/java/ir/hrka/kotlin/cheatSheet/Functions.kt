package ir.hrka.kotlin.cheatSheet

import ir.hrka.kotlin.helpers.Log.printYellow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.measureTime

/**
 * * If a function does not return a useful value, its return type is Unit.
 * * Inside a function, a vararg-parameter of type T is visible as an array of T.
 * * Only one parameter can be marked as vararg.
 * * If you already have an array and want to pass its contents to the function,
 * use the spread operator (prefix the array with *)
 * * Infix functions must meet the following requirements:
 *    * They must be member functions or extension functions.
 *    * They must have a single parameter.
 *    * The parameter must not accept variable number of arguments and must have no default value.
 * * There are three type of function scope:
 *    * Top level function (in a file)
 *    * local function (in a function)
 *    * member function (in a class)
 * * A tailrec function is a recursive function that is optimized
 * by the compiler to prevent stack overflow errors by reusing the same stack frame for recursive calls.
 * * In the tailrec function, optimization is only possible if the recursive call is the last operation in the function.
 * * You cannot use tail recursion when there is more code after the recursive call,
 * within try/catch/finally blocks, or on open functions.
 * * Kotlin functions are first-class, which means they can be stored in variables and data structures,
 * and can be passed as arguments to and returned from other higher-order functions.
 * * A higher-order function is a function that takes functions as parameters, or returns a function.
 * * Lambda expressions and anonymous functions are function literals.
 * * If an exception is thrown from an lambda passed into high order function rest of the code after it dose not execute.
 * Function literals are functions that are not declared but are passed immediately as an expression.
 * * We can use a callable reference to an existing declaration:
 *    * a top-level, local, member, or extension function: ::isOdd, String::toInt,
 *    * a top-level, member, or extension property: List<Int>::size,
 *    * a constructor: ::Regex
 * * A lambda expression or anonymous function (as well as a local function and an object expression) can access its closure,
 * which includes the variables declared in the outer scope.
 * * Using higher-order functions imposes certain runtime penalties:
 * each function is an object, and it captures a closure.
 * A closure is a scope of variables that can be accessed in the body of the function.
 * * Benefits of Inline Functions:
 *    * When you mark a function as inline, the Kotlin compiler replaces the function call with the actual function body.
 *    This avoids creating a function object and stack frame for each call, which reduces overhead and can improve performance.
 *    (This is particularly beneficial for short and frequently called functions).
 *    * You can use return inside a lambda passed to an inline function to return from the enclosing function.
 * * Avoid inlining large functions because the generated code to grow.
 * * The inline modifier affects both the function itself and the lambdas passed to it:
 * all of those will be inlined into the call site.
 * * We can not use local function in inline functions.
 * * If you don't want all of the lambdas passed to an inline function to be inlined,
 * mark some of your function parameters with the noinline modifier.
 * * Use the reified modifier to make the type parameter accessible inside the inline function
 * (no reflection is needed and normal operators like '!is' and 'as' are now available for you to use).
 * * Normal functions (not marked as inline) cannot have reified parameters.
 * * The 'inline' modifier can be used on accessors of properties that don't have backing fields.
 *    * 'inline' property cannot have backing field.
 *    * 'inline' modifier is not allowed on virtual members.
 *    * In an interface only private or final members can be inlined.
 * * At the call site, inline accessors are inlined as regular inline functions.
 * * You can't directly create an inline var.
 * * To implement an operator, provide a member function or an extension function
 * with a specific name for the corresponding type.
 * * When we mark a function with suspend, we can only use this function in coroutines or another suspend function.
 */

fun regularFun1(x: Int, y: String, z: Boolean = true) {
    val clazz = Class(1)
    clazz infixFun 2
    val intArray = intArrayOf(1, 2)
    regularFun3(*intArray, y = "")
}

fun regularFun2(x: Int, y: String): Boolean = false

fun regularFun3(vararg x: Int, y: String): Nothing {
    throw Exception("exception")
}

infix fun Class.infixFun(a: Int) {}

fun functionScope(int: Int): (String) -> Unit {
    fun localFunction(str: String) {
        printYellow("localFunction --> $str -- $int")
    }
    printYellow("functionScope --> $int")
    localFunction("argument")

    return ::localFunction
}

tailrec fun factorial(n: Int, acc: Int = 1): Int {
    return if (n <= 1) acc else factorial(n - 1, acc * n)
}

fun highOrderFunction1(function1: () -> Unit, function2: (str: String, Int) -> Boolean) {
    function1()
    function2("String", 1)
}

fun <T> highOrderFunction2(function: (List<T>) -> Int): Int {
    val list = mutableListOf<T>()

    return function(list)
}

val lambdaExpression1: (Int, Int) -> Int = { x, y -> x + y }

val lambdaExpression2: (String) -> Unit = { printYellow(it) }

val lambdaExpression3 = { i: Int, str: String ->
    String
    printYellow("int= $i , string = $str")
    str
}

val anonymousFunction1 = fun(int: Int): Boolean = int > 0

val anonymousFunction2 = fun(str: String, int: Int) {
    printYellow("str = $str - int = $int")
}

val callableReferenceFunction1 = ::anonymousFunction1

val callableReferenceFunction2 = List<String>::size

fun literalFunctions() {
    // Here, we passed an anonymous function in highOrderFunction
    highOrderFunction1(
        fun() {},
        fun(str: String, int: Int): Boolean {
            return !(int > 2 && str.isNotEmpty())
        })
    // here, we passed an lambda expression in highOrderFunction
    highOrderFunction1({}, { str, int -> !(int > 2 && str.isNotEmpty()) })
    // here, we passed an callback reference in highOrderFunction
    highOrderFunction2(List<String>::size)
}

inline fun inlineFunction(block1: () -> Unit, block2: (Boolean) -> Int): Boolean {
    block1()
    return (block2(true) > 0)
}

fun inlineUseCase() {
    inlineFunction({}) {
        if (!it) return
        else return@inlineFunction 1
    }
}

inline fun inlineAction(action: (Int) -> Unit) {
    repeat(1_000_000) {
        action(it)
    }
}

fun noInlineAction(action: (Int) -> Unit) {
    repeat(1_000_000) {
        action(it)
    }
}

fun measureInlinePerformance(isInlined: Boolean): Duration {
    val total: StringBuilder = StringBuilder("")

    val duration = measureTime {
        if (isInlined)
            inlineAction {
                total.append("$it-")
            }
        else
            noInlineAction {
                total.append("$it-")
            }
    }

    return duration
}

inline fun noInlineArgFunction(inlined: () -> Unit, noinline noInlined: (Boolean) -> Int) {
    inlined()
    noInlined(true)
}

inline fun <reified T> reifiedTypeParam(value: T): Boolean {
    val clazz = Class()

    return clazz is T
}

inline val inlinedValue: Int
    get() {
        return 1
    }

inline var inlinedVariable: Int
    get() {
        return 1
    }
    set(value) {}

operator fun Int.get(int: Int) {
    printYellow("Int number is = $this")
}

suspend fun suspendFunction() {
    coroutineScope {
        launch {
            printYellow("suspendFunction")
        }
    }
}

suspend fun highOrderFunction2(
    function1: Class.() -> Unit,
    function2: suspend (str: String, Int) -> Boolean
) {
    val clazz = Class()
    clazz.function1()
    function1(clazz)
    function2("String", 1)
}