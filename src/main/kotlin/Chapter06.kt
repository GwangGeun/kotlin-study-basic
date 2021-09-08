import java.lang.IllegalArgumentException

/**
 * nullable
 * - String?, Int? 등 물음표를 붙임으로써 해결
 */

/**
 * null safe operator (?.)
 */
fun printAllCaps(s: String?) {
    // "if(s != null) s.toUpperCase() else null" 과 같은 구문
    val allCaps: String? = s?.toUpperCase()
    println(allCaps)
}


/**
 * elvis operator (?:)
 * - null 대신 default 값을 지정할때 주로 사용
 * - 함수의 전제 조건을 검사하는 경우에 특히 유용
 * - kotlin 에서는 return, throw 등의 연산도 식이다. 따라서 엘비스 우항에 return,throw 을 넣을 수 있다.
 */

fun strLenSafe(s: String ?): Int = s?.length ?: 0

class Address01(val streetAddress:String, val zipCode:Int, val city:String, val country:String)
class Company01(val name:String, val address:Address01?)
class Persons01(val name:String, val company:Company01?)

fun printShippingLabel(person:Persons01){
    val address = person.company?.address ?: throw IllegalArgumentException("NO ADDRESS")
    with(address){
        println(streetAddress)
        println("$zipCode $city, $country")
    }
}

/**
 * safe cast (as?)
 */
class SafeCastPerson(val firstName:String, val lastName:String){
    override fun equals(other: Any?): Boolean {
        // 타입이 서로 일치하지 않으면 false 를 반환한다
        val otherPerson = other as? Person01 ?: return false
        return otherPerson.name == firstName
    }
    override fun hashCode(): Int = firstName.hashCode()*27 + lastName.hashCode()
}

/**
 * not null assertion (!!)
 * - null 이 아님을 단언하는 것이다
 * - 최대한 다른 방법을 찾아서 해결하는 것을 권장
 */
fun ignoreNulls(s:String?){
    // s 가 null 이면 NullPointerException 이 발생.
    // 발생한 예외는 null 값을 사용하는 코드가 아니라 단언문이 위치한 곳을 가르킨다.
    val sNotNull: String = s!!
    println(sNotNull.length)
}

/**
 * let 함수
 * - 자신의 수신 객체를 인자로 전달받은 람다에게 넘긴다.
 * - let 을 사용하는 가장 흔한 용례는 null이 될 수 있는 값을 null이 아닌 값만 인자로 받는 함수에 넘기는 경우이다.
 */
fun sendEmailsTo(email:String){
    println("Sending email to $email")
}
var email: String? = "jgg0328@gmail.com"

/**
 * lateinit ( 나중에 초기화할 프로퍼티 )
 * - 객체 인스턴스를 일단 생성한 다음에 나중에 초기화하는 프레임워크들을 위한 기능
 */


fun main(){

    //====== let ======//
    email?.let { sendEmailsTo(it) }

}