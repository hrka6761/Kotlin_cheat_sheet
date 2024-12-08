package ir.hrka.kotlin.cheatSheet


/**
 * * When you call such a function on an object with a lambda expression provided,
 * it forms a temporary scope and In this scope, you can access the object without its name.
 * * Scope functions don't introduce any new technical capabilities, but they can make your code more concise and readable.
 * * There are two main differences between each scope function:
 *    * The way they refer to the context object.
 *    * Their return value.
 * * Each scope function uses one of two ways to reference the context object:
 *    * as a lambda receiver (this) --> `run` `with` `apply`.
 *    * as a lambda argument (it)   --> `also` `let`.
 * * Each scope function returns one of the following two items:
 *    * context object --> `apply` `also`.
 *    * lambda result  --> `let` `run` `with`.
 * * `takeIf`:
 *    * When called on an object along with a predicate, takeIf returns this object if it satisfies the
 *    given predicate Otherwise, it returns null So, takeIf is a filtering function for a single object.
 * * `takeUnless`:
 *    * takeUnless has the opposite logic of takeIf.
 *    * When called on an object along with a predicate,
 *    takeUnless returns null if it satisfies the given predicate Otherwise, it returns the object.
 * * Here is a short guide for choosing scope functions depending on the intended purpose:
 *    * Executing a lambda on non-nullable objects (`let`)
 *    * Introducing an expression as a variable in local scope (`let`)
 *    * Object configuration (`apply`)
 *    * Object configuration and computing the result (`run`)
 *    * Running statements where an expression is required (`non-extension run`)
 *    * Additional effects (`also`)
 *    * Grouping function calls on an object (`with`)
 */


//  Features table
//
//    --------------------------------------------------------------------
//    |\\\\\\\\\\\\\\\\|--Receiver object(this)--|--Lambda argument(it)--|
//    |-lambda result--|        run-with         |          let          |
//    |-context object-|         apply           |          also         |
//    --------------------------------------------------------------------


//  Decision tree
//
//                                                       [Return type ???]
//                                                              /  \
//                                                             /    \
//                                          (Context object)  /      \  (lambda result) or (No result)
//                                                           /        \
//                                                          /          \
//                                                [apply-also]        [run-with-let]
//                                                        /              \
//                                                       /                \
//                                                      /                  \
//                                                     /                    \
//                                                    /                      \
//                               [Context reference ???]                    [Context reference ???]
//                                 /  \                                                      /  \
//       (Lambda argument -> it)  /    \  (Receiver object -> this)                         /    \
//                               /      \                                                  /      \
//                           [also]   [apply]                                             /        \
//                                                              (Lambda argument -> it)  /          \  (Receiver object -> this)
//                                                                                      /            \
//                                                                                     /              \
//                                                                                  [let]         [run-with]
//                                                                                                      \
//                                                                                                       \
//                                                                                                        \
//                                                                                                         \
//                                                                                                  [Null check ???]
//                                                                                                        /   \
//                                                                                                 (Yes) /     \ (No)
//                                                                                                      /       \
//                                                                                                   [run]     [with]

class ScopeFunctions {}