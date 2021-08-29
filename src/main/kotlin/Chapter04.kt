/**
 * 이하 Class & Interface
 *
 * 1. 한 클래스에서 showOff 라는 동일 method 가 있는 interface 를 동시에 implements 한다면 (?)
 *    : 구현체에서 showOFf 의 명시적인 구현을 제공해야 한다.
 */
interface Clickable{
    fun click()
    fun showOff() = println("I`m clickable!")
}

interface Focusable{
    fun setFocus(b: Boolean) = println("I ${if(b) "got" else "lost"} focus.")
    fun showOff() = println("I`m focusable!")
}

class Button : Clickable, Focusable{
    override fun click() = println("I`m clickable!")
    override fun showOff() {
        super<Clickable>.showOff() // how to call super type
        super<Focusable>.showOff()
    }

}

/**
 * Kotlin 의 class, method 는 기본적으로 final 이다
 */
open class RichButton : Clickable{ // This class is open. Another class can inherit this class
    fun disable(){} // this fun is final. Another class can`t override this fun
    open fun animate(){} // open. Another class can override this fun
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
internal open class TalkativeButton : Focusable{
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let`s talk!")
}

//fun TalkativeButton.giveSpeech(){
//    yell()
//    whisper()
//}


fun main(){

}