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
 * : 타입 캐스트 연산자는 값을 주어진 타입으로 변환하려 시도하고 타입이 맞지 않으면 null 을 반환한다
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
class MyServiceLateInit{
    fun performAction(): String = "foo"
}

class MyTest03{

//    1.lateinit 을 사용하지 않는 경우
//    private var myServiceLateInit:MyServiceLateInit? = null **** 핵심 포인트 <- null setting ****
//    @Before fun setUp{
//        myServiceLateInit = MyServiceLateInit()
//    }
//    @Test fun testAction(){
//        Assert.assertEquals("foo", myServiceLateInit!!.performAction()) **** 핵심 포인트 <- null 검사 필요 ****
//    }

//    2. lateinit 을 사용하는 경우
//    private lateinit var myServiceLateInit:MyServiceLateInit **** 나중에 초기화하는 프로퍼티는 항상 var 여야 한다 ****
//    @Before fun setUp{
//        myServiceLateInit = MyServiceLateInit()
//    }
//    @Test fun testAction(){
//        Assert.assertEquals("foo", myServiceLateInit.performAction())  **** 핵심 포인트 <- null 검사를 사용하지 않고 프로퍼티를 사용 ****
//    }

}

/**
 * null이 될 수 있는 타입의 확장
 * - null이 될 수 있는 타입의 확장 함수는 안전한 호출 없이도 호출 가능하다. ex) input.isNullOrBlank() -> "?." 를 사용하지 않음
 */

fun verifyUserInput(input:String?){
    if(input.isNullOrBlank()){ // 안전한 호출을 하지 않아도 된다
        println("Please fill in the required fields")
    }
}

// verifyUserInput(null) -> 아무런 예외가 발생하지 않는다.

// java 에서는 메소드 안의 this는 그 메소드가 호출된 수신 객체를 가리키므로 항상 null이 아니다.
// kotlin 에서는 널이 될 수 있는 타입의 확장 함수 안에서는 this가 null이 될 수 있다는 점이 자바와 다르다.
fun String?.isNullOrBlanks():Boolean =
    this == null || this.isBlank()


/**
 * 타입 파라미터의 null 가능성
 * - 타입 파라미터 T를 클래스나 함수 안에서 타입 이름으로 사용하면 이름 끝에 물음표가 없더라도 T가 null이 될 수 있는 타입이다.
 */
fun <T> printHashCode(t:T){
    println(t?.hashCode()) // t는 null 이 될 수 있으므로 안전한 호출(safe operator)를 사용해야만 한다
}

// printHashCode <- "T"의 타입은 "Any?"로 추론된다.

/**
 * null 가능성과 java
 * 1. platform type
 * 2. inheritance
 */

/**
 * 1. platform type
 * - kotlin 이 null 관련 정보를 알 수 없는 타입
 * - 타입을 null 로 처리해도 되고 null 이 될 수 없는 타입으로 처리해도 된다. 즉, 모든 책임은 개발자에게 있으며 NullPointerException 이 발생할 수 있다.
 * - Kotlin 에서 플랫폼 타입을 선언할 수는 없다. 자바에서 가져온 타입만 플랫폼 타입이 된다.
 */
// >>> val i:Int = person.name
// ERROR: Type mismatch: inferred type is String! but Int was expected  --> ! 표기는 String! 타입의 null 가능성에 대해 아무 정보가 없다는 뜻

/**
 * 2. inheritance
 */
//  java code
//  interface StringProcessor{
//      void process(String value);
//  }

// - kotlin 에서는 아래 2가지 구현 모두 가능
// - override fun process(value:String){...}  // override fun process(value:String?){ if(value!=null)...}


fun main(){

    //====== let ======//
    email?.let { sendEmailsTo(it) }

}