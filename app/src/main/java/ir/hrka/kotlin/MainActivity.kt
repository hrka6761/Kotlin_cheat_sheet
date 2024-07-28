package ir.hrka.kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import ir.hrka.kotlin.cheatSheet.*
import ir.hrka.kotlin.helpers.Log.printBlue
import ir.hrka.kotlin.helpers.Log.printRed
import ir.hrka.kotlin.helpers.Log.printYellow
import kotlin.time.DurationUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

///////// 1) Basic /////////////////////////////////////////////////////////////////////////////////

//        printYellow("Set is ${set.toTypedArray()} and its size = ${set.size}")

///////// 2) Class /////////////////////////////////////////////////////////////////////////////////

//        Class(1)
//        Class(1, "a")
//        Class(1, "a", 2)
//        Class(1, "a", 2f)
//        printYellow(Class.toString())
//        printYellow(Class.COMPANION_PROP)

///////// 3) Abstract class ////////////////////////////////////////////////////////////////////////

//        val abstractClassImplementor = object : AbstractClass("", 1) {
//            override val abstractProperty: Int
//                get() = 5
//
//            override fun abstractFun() {
//                printYellow("abstractClassImplementor")
//            }
//        }
//
//        abstractClassImplementor.abstractFun()

///////// 4) Interface /////////////////////////////////////////////////////////////////////////////

//        val interfaceImplementor = object : Interface {
//            override val abstractProperty: Int
//                get() = -1
//
//
//            override fun concreteFunction() {
//                super.concreteFunction()
//            }
//        }
//
//        interfaceImplementor.concreteFunction()

///////// 5) Inheritance ///////////////////////////////////////////////////////////////////////////

//        Inheritance(1)
//        Inheritance(1, "a")
//        Inheritance(1, "a", 2f)

///////// 6) Data class ////////////////////////////////////////////////////////////////////////////

//        val data1 = DataClass(1, "a")
//        val data2 = data1
//        val data3 = data1.copy()
//        val data4 = data1.copy(2, "a")
//        printYellow("data1 == data2 ---> ${data1 == data2}")
//        printYellow("data1 === data2 ---> ${data1 === data2}")
//        printYellow( "data1 == data3 ---> ${data1 == data3}")
//        printYellow( "data1 === data3 ---> ${data1 === data3}")
//        printYellow( "data1 == data4 ---> ${data1 == data4}")
//        val (a,b) = data1
//        printYellow( "a = $a , b = $b")

///////// 7) Enum class ////////////////////////////////////////////////////////////////////////////

//        printYellow(EnumClass.entries.toString())
//        printYellow(EnumClass.valueOf("Instance1").name)
//        try {
//            EnumClass.valueOf("Instance4")
//        }catch (e: Exception) {
//            printYellow(e.toString())
//        }

///////// 8) Sealed class //////////////////////////////////////////////////////////////////////////

//        val randInt = Random(1).nextInt(1, 200)
//
//        val a = if (randInt <= 70)
//            SealedClass.Child1(1, "a")
//        else if (randInt in 71..140)
//            SealedClass.Child2(2, "b")
//        else
//            SealedClass.Child3(2, "b")
//
//        when (a) {
//            is SealedClass.Child1 -> {
//                printYellow("$randInt --> Child1")
//            }
//
//            is SealedClass.Child2 -> {
//                printRed("$randInt --> Child2")
//            }
//
//            is SealedClass.Child3 -> {
//                printBlue("$randInt --> Child3")
//            }
//        }

///////// 9) Extensions ////////////////////////////////////////////////////////////////////////////

//        val clazz = Class()
//        clazz.extensionFun1()
//        Class.extensionFun2()
//        val inheritance = Inheritance(1, 1)
//        inheritance.extensionOverriding()
//        inheritance.fun1()
//        inheritance.fun1(1)
//        val parent = Parent()
//        parent.fun1()
//        val child = Child()
//        child.fun1()

///////// 10) Functional interface /////////////////////////////////////////////////////////////////

//        val sam1 = SAM1 { printBlue("Implementation of SAM1 function") }
//        sam1.singleAbstractFun()
//        val sam2 = SAM2 { printBlue("Implementation of SAM2 function") }
//        sam2.singleAbstractFun()
//        sam2.concreteFunction()
//        useSingleAbstractInterface1(singleAbstractInterface = object : SingleAbstractInterface {
//            override fun singleAbstractFunction() {
//
//            }
//        })
//        useSingleAbstractInterface2 { printRed("Implementation of SAM1 function") }

///////// 11) Nested and inner classes /////////////////////////////////////////////////////////////

//        val outerClass = OuterClass()
//        val nested = OuterClass.NestedInterface.NestedClass()
//        val inner = outerClass.InnerClass()

///////// 12) Object ///////////////////////////////////////////////////////////////////////////////

//        val objectExpression = ObjectExpression()
//        objectExpression.fun1()
//        ObjectDeclaration1.InnerObject
//        ObjectDeclaration1.Inner.InnerObject
//        val name = ObjectDeclaration1.valWithImplementation

///////// 13) Functions ////////////////////////////////////////////////////////////////////////////

//        val clazz = Class()
//        clazz infixFun 2
//        val localFunction = functionScope(1)
//        printYellow(localFunction.toString())
//        printYellow(measureInlinePerformance(false).toLong(DurationUnit.MILLISECONDS).toString())
//        printYellow(reifiedTypeParam(clazz).toString())
//        5[5]

///////// 14) Generics /////////////////////////////////////////////////////////////////////////////

//        val produceInt = ProduceInt()
//        val produceNumber: CovariantGenerics<Number> = produceInt
//        printYellow(produceNumber.produce()::class.java.name)
//        val consumeNumber = ConsumeNumber()
//        val consumeInt: ContravariantGenerics<Int> = consumeNumber
//        consumeInt.consume(1)

///////// 15) Delegation ///////////////////////////////////////////////////////////////////////////

//        val baseImpl1 = BaseImpl1()
//        val baseImpl2 = BaseImpl2()
//        val delegatedClass = DelegatedClass(baseImpl1)
//        delegatedClass.fun1()
//        delegatedClass.fun2()
//        printYellow(delegatedClass.value1.toString())
//        printYellow(delegatedClass.value2.toString())
//        val delegation = Delegation()
//        delegation.observableProperty = "green"
//        delegation.observableProperty = "blue"
//        printYellow("${delegation.vetoableProperty}")
//        delegation.vetoableProperty = 1L
//        printYellow("${delegation.vetoableProperty}")
//        delegation.notNullProperty = 1
//        printYellow("${delegation.notNullProperty}")
//        delegation.delegatedProperty = 3
//        printYellow("lazyProperty2 ---> ${delegation.delegatedProperty}")

///////// 16) Type alias ///////////////////////////////////////////////////////////////////////////

//        val fi = FI()
//        val lambda: lambda = {str, int -> false}
//        val generic = Generic()
//        val nestedClass = NestedClass()
//        val innerClass: InnerClass = OuterClass().InnerClass()

///////// 17) Scope functions //////////////////////////////////////////////////////////////////////


    }
}