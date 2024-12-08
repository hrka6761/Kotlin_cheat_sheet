package ir.hrka.kotlin.cheatSheet

/**
 * * There are four visibility modifiers in Kotlin: `private`, `protected`, `internal`, `public`.
 * * `private` means that the member is visible inside class or file only.
 * * `protected` means that the member has the same visibility as one marked as private, but that it is also visible in subclasses.
 * * `internal` means that any client inside this module who sees the declaring class sees its internal members(a module is a set of Kotlin files compiled together).
 * * `public` means that any client who sees the declaring class sees its public members.
 * * If you don't use a visibility modifier, public is used by default.
 * * The protected modifier is not available for top-level declarations.
 * * When we declare a class private, we cannot inherit from it anywhere except in the same file or class.
 * * When we declare a class private, we can only inherit from it when the subClass is internal or private:
 * ```
 * internal open class TopLevelClassA()
 * ```
 * ```
 * private open class TopLevelClassB()
 * ```
 * ```
 * private class Derived1 : TopLevelClassA()
 * internal class Derived2 : TopLevelClassA()
 * ```
 * ```
 * private class Derived3 : TopLevelClassB()
 * ```
 */

const val topLevelA = 1
private lateinit var topLevelB: TopLevelClassC
internal var topLevelC = "1"

public class TopLevelClassA()
internal open class TopLevelClassB()
private open class TopLevelClassC()
private class DerivedC : TopLevelClassC()
internal class DerivedB1 : TopLevelClassB()
private class DerivedB2 : TopLevelClassB()