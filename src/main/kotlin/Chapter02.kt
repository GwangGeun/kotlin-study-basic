import java.lang.IllegalArgumentException

/**
 * 1. 함수 : 식이 본문인 함수의 반환 타입만 생략이 가능하다.
 */
// 반환타타입 지정 필요
fun maxBody(a: Int, b: Int): Int {
    return if (a > b) a else b
}

// 반환타입 생략 가능
fun maxExpression(a: Int, b: Int): Int = if (a > b) a else b


/**
 * 2. Property (필드,getter,setter) : kotlin 에서 property 를 선언하는 방식은 property 와 관련 있는 접근자(var,val)를 선언하는 것이다.
 */
class Person01(val name: String, var age: Int)

// 직접 custom getter, setter 를 만드는 방법
class Person02(name: String, age: Int) {
    val name = name
        get() {
            return field
        }
    var age = age
        get() {
            return field
        }
        set(value) {
            field = value
        }
}

/**
 * 3. Enum : kotlin 에서 유일하게 ; 이 필수이다.
 */
enum class Color01 {
    RED, ORANGE, YELLOW, GREEN
}

enum class Color02(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0), ORANGE(255, 165, 0),
    YELLOW(255, 255, 0), GREEN(0, 255, 0);

    fun rgb() = (r * 256 + g) * 256 + b
}

/**
 * 4. Smart Cast : 컴파일러가 캐스팅을 수행해준다.
 *                 단, 프로퍼티가 val 이 아니거나 커스텀 접근자를 사용한 경우에는 smart cast 가 작동하지 않는다
 */
interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int {
    if (e is Num) {
        val n = e as Num // smart cast 예시 1) as Num 처럼 형변환 필요 X
        return n.value
    }
    if (e is Sum) {
        return eval(e.right) + eval(e.left) // smart cast 예시 2 ( smart cast 에 해당하는 경우 IDE 에 표시해줌 )
    }
    throw IllegalArgumentException("Unknown expression")
}

fun eval2(e: Expr): Int =
    if (e is Num) {
        e.value
    } else if (e is Sum) {
        eval2(e.right) + eval2(e.left)
    } else {
        throw IllegalArgumentException("Unknown expression")
    }

// eval2 와 동일한 로직
fun eval3(e: Expr): Int =
    when (e) {
        is Num ->
            e.value
        is Sum ->
            eval3(e.right) + eval3(e.left)
        else ->
            throw IllegalArgumentException("Unknown expression")
    }

//fun recognize(c: Char) = when(c){
//    in '0'..'9' -> "It`s a digit!"
//    in 'a'..'z', in 'A'..'Z' -> "It`s a letter!"
//    else -> "I don`t know.."
//}

fun main(args: Array<String>) {

    //====== Property ======//
//    val ob01 = Person01("a",10)
//    ob01.name = "b" -> 불가
//    ob01.age = 10;

    //   val ob02 = Person02("a",10)
//    ob02.name = "b" -> 불가
//    ob02.age = 10;

    //====== Enum ======//
//    println(Color02.ORANGE.rgb())

    //====== Smart Cast ======//
//    var res = Sum(Sum(Num(1),Num(2)), Num(4))
//    var res2 = Sum(Num(1), Num(2))
//    println(eval2(res2))
//    println(eval(res))

}

