import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * 이하 Class & Interface
 *
 * 1. 한 클래스에서 showOff 라는 동일 method 가 있는 interface 를 동시에 implements 한다면 (?)
 *    : 구현체에서 showOFf 의 명시적인 구현을 제공해야 한다.
 */
interface Clickable {
    fun click()
    fun showOff() = println("I`m clickable!")
}

interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if (b) "got" else "lost"} focus.")
    fun showOff() = println("I`m focusable!")
}

class Button : Clickable, Focusable {
    override fun click() = println("I`m clickable!")
    override fun showOff() {
        super<Clickable>.showOff() // how to call super type
        super<Focusable>.showOff()
    }
}

/**
 * Kotlin 의 class, method 는 기본적으로 final 이다
 */
open class RichButton : Clickable { // This class is open. Another class can inherit this class
    fun disable() {} // this fun is final. Another class can`t override this fun
    open fun animate() {} // open. Another class can override this fun
    override fun click() {} // override method is open (default),
}


/**
 * [ 가시성 ]
 * - Kotlin 은 public 함수인 giveSpeech 안에서 그보다 가시성이 더 낮은(이 경우 internal) 타입인 TalkativeButton 을 참조하지 못하게 한다.
 *   따라서, 아래의 경우 컴파일 오류를 없애려면 giveSpeech 의 extension 의 가시성을 internal 로 바꾸거나 TalkativeButton 의 가시성을 public 으로
 *   바꿔야 한다.
 *
 * - 클래스를 확장한 함수는 그 클래스의 private 이나 protected 멤버에 접근 할 수 없다.
 */
internal open class TalkativeButton : Focusable {
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let`s talk!")
}

//fun TalkativeButton.giveSpeech(){
//    yell()
//    whisper()
//}


/**
 * sealed Class
 * - when 식에서 sealed 클래스의 모든 하위 클래스를 처리한다면 디폴트 분기가 필요 없다.
 * - sealed class 는 open 이 default
 * - sealed interface 는 없다
 */
sealed class SealedExpr {
    class SealedNum(val value: Int) : SealedExpr()
    class SealedSum(val left: SealedExpr, val right: SealedExpr) : SealedExpr()
}

fun evalFun(e: SealedExpr): Int =
    when (e) {
        is SealedExpr.SealedNum -> e.value
        is SealedExpr.SealedSum -> evalFun(e.right) + evalFun(e.left)
    }

/**
 * constructor
 * - Class 에 primary constructor 이 없다면 모든 secondary constructor 는 상위 클래스를 초기화 하거나 다른 생성자에게 생성을 위임해야 한다.
 */
open class View {
    constructor(str: String) {}
    constructor(str: String, age: Int) {}
}

class MyButton : View {
    constructor(str: String) : super(str)

    //    constructor(str:String, age:Int) : this(str){}
    constructor(str: String, age: Int) : super(str, age) {}
}

/**
 * Interface
 * - Kotlin 에서는 interface 에 추상 프로퍼티 선언을 넣을 수 있다.
 * - getter, setter 가 있는 property 를 선언할 수 있다
 */
interface UserIf {
    val nickname: String
    val subName:String
        get() = nickname.substringBefore("_")
}

class PrivateUserIf(override val nickname: String) : UserIf
class SubscribingUserIf(private val email:String) : UserIf{
//     1.매번 이메일 주소를 계산 중.
    override val nickname: String
        get() = email.substringBefore('@')

//     2. 객체 초기화 시 계산한 데이터를 backing field 에 저장 후 불러오는 방식
//    override val nickname: String = emailContent(email)
//
//    private fun emailContent(emails :String):String {
//        return emails.substringBefore("@")
//    }
}

class UserB(val name: String){
    var address:String = "unspecified"
    set(value:String){
        println("""Address was changed for $name:
            "$field" -> "$value".
        """.trimMargin())
        field = value
    }
}

/**
 * 접근자의 가시성 변경
 * ex) set 에 private 을 부여
 */
class LengthCounter{
    var counter: Int = 0
        private set
    fun addWord(word:String){
        counter += word.length
    }
}

/**
 *              java          kotlin
 * equality     equals()       ==
 * identity      ==             ===
 *
 * - equality 의 경우 override 필요 ( with hashcode() )
 * - kotlin 에서는 data class 선언을 통해 주로 override
 */
class Compares(val valueA: String)

/**
 * Data Class
 * - equals, hashcode, toString 을 override
 * - copy method 포함
 * - cf) equals, hashcode 는 주생성자에 나열된 모든 프로퍼티를 고려해 만들어진다.
 */
data class Client(val name:String, val postalCode:Int)

fun main() {


//    val userB = UserB("AAA")
//    userB.address = "대한민국 서울 어딘가"
//    println("hello result : " + userB.address)


    val obj01 = Compares("a")
    val obj02 = Compares("a")

    println(obj01 == obj02)

}