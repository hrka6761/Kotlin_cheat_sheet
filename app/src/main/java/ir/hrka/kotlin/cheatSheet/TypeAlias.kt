package ir.hrka.kotlin.cheatSheet

/**
 * * Type aliases provide alternative names for existing types.
 * * Type aliases do not introduce new types. They are equivalent to the corresponding underlying types.
 * If the type name is too long you can introduce a different shorter name and use the new one instead.
 * * It's useful to shorten long generic types
 * * It's useful to provide different aliases for function types.
 * * It's useful to have new names for inner and nested classes.
 */

typealias FI = FunctionalInterface

typealias lambda = (String, Int) -> Boolean

typealias Generic = GenericClass<String, Int, Long>

typealias NestedClass = OuterClass.NestedClass

typealias InnerClass = OuterClass.InnerClass