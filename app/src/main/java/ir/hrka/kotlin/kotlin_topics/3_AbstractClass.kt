package ir.hrka.kotlin.kotlin_topics

/**
 * * Abstract class is open and public by default.
 * * We cannot instantiate from an abstract class directly.
 * * Abstract class can contain constructor but this constructor is only used to inheritance not instantiation.
 * * Abstract class can contain both abstract and concrete members:
 * ```
 * abstract class job constructor(val jobName: String) {
 *
 *     abstract val Salary: Long
 *     var taxPercentage: Int = 10
 *
 *     constructor(jobName: String, jobId: Int) : this(jobName) {}
 *
 *     companion object {
 *         const val COUNTRY = "Iran"
 *     }
 *
 *     fun getJobNameAndId(): String {
 *         return "jobId = $jobId - jobName = $jobName"
 *     }
 *
 *     abstract fun calculateTax()
 * }
 * ```
 * * Abstract members is open and public by default.
 * * Concrete members must be open for overriding.
 * * An abstract member can not be private.
 * * 'final' modifier is incompatible with 'abstract' because abstract must be open to implementation.
 * * Abstract properties cannot be have custom getter and setter.
 */

abstract class AbstractClass constructor(val value: String) {

    abstract val abstractProperty: Int
    var concreteProperty: Int = 1

    constructor(str: String, integer: Int) : this(str) {}

    companion object {
        const val CONSTANT = "Abstract companion"
    }

    fun concreteFun() {}

    abstract fun abstractFun()
}