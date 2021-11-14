/**
 * lambda 식 기본 문법
 *
 *   --parameter--
 *  |             \    body
 * { x: Int, y: Int -> x+y }
 * - 항상 중괄호 사이에 위치 함
 */

/**
 * 1. basic grammer
 */
val sum = { x: Int, y: Int -> x + y }
// how to call : 1. println(sum(1,2))  2. {println(42)}()  3. run{println(42)}

/**
 *  1. peopleForTest.maxByOrNull ({p:Person01 -> p.age})
 *      -> 2. peopleForTest.maxByOrNull (){p:Person01 -> p.age} : kotlin 에는 함수 호출 시 맨 뒤에 있는 인자가 람다식이면 그 람다를 괄호 밖으로 빼낼 수 있다는 문법 관습이 존재
 *          -> 3. peopleForTest.maxByOrNull (){p -> p.age} : 파라미터 타입을 생략(컴파일러가 추론)
 *              -> 4. peopleForTest.maxByOrNull { it.age } : 람다의 파라미터가 하나뿐이고 그 타입을 컴파일러가 추론할 수 있는 경우 it 을 바로 쓸 수 있다.
 *
 */
val peopleForTest = listOf(Person01("Robert", 29), Person01("Carrot", 31))

val peopleForTest02 = listOf(Person01("Robert2", 29), Person01("Carrot2", 32))

/**
 * 2. member reference
 */
fun salute(a: String = "a", b: String = "b") = println("Salute! $a $b")

// 이레 힘수와 동일 : val getAge = {person:Person -> person.age}
val ageTest = Person01::age

// 람다가 인자가 여럿인 다른 함수한테 작업을 위임하는 경우 람다를 정의하지 않고 직접 위임 함수에 대한 참조를 제공할 수 있다.
val action = { person: Person01, message: String -> salute(person.age.toString(), message) }
val nextAction = ::salute

// constructor reference 를 사용하면 클래스 생성 작업을 연기하거나 저장해둘 수 있다
val createPerson01Ob = ::Person01
// val p = createPerson01ob("Robert",29)

// page)213 확장함수도 멤버함수와 똑같은 방슥으로 참조 가능

/**
 * 3. collection fun api
 */
// filter,map 생략

// all,any,count,find
val canBeInClub27 = { p: Person01 -> p.age <= 27 }
val testVal = listOf(Person01("Alice", 27), Person01("Bob", 31))

// groupBy
val testVal02 = listOf(Person01("Alice", 31), Person01("Bob", 29), Person01("Carol", 31))
val stringExtensionList = listOf("a", "ab", "b")

// flatmap : list의 list를 처리할때 쓴다
class Book(val title: String, val authors: List<String>)

val strings = listOf("abc", "def")
val books = listOf(
    Book("Thursday Next", listOf("Jasper Fforde")),
    Book("Mort", listOf("Terry Pratchett")),
    Book("Good Omens", listOf("Terry Pratchett", "Neil Gaiman"))
)


// sequence
// - java 의 stream 과 같은 기능이라고 보면 됨 ( 단, stream 처럼 parallel 하게 실행은 불가 )
// - sequence 를 사용하지 않으면 중간연산 결과과 collection 으로 만들어지는 비효율성이 발생
// - asSequence() or generateSequence()

//  자바의 functional interface 호출
//  - 코틀린에서 자바의 functional interface 호출시 람다식으로 바로 표현할 수 있다.
//  - 내부적으로 람다식은 익명클래스로 치환된다.
//  - lambda capturing 발생하지 않는다면, 익명클래스는 한번만 생성되어 재사용된다.
//  - lambda capturing 발생하면, 익명클래스는 매번 생성되어 사용된다.

//  SAM 생성자
//  - SAM 생성자는 컴파일러가 람다식을 자바의 functional interface로 자동으로 변환하는 함수
//  - 컴파일러가 자동으로 변환을 못하는경우에는 직접 SAM 생성자를 사용하여 코드를 작성 할 수도 있다.
fun createAllDoneRunnable(): Runnable {
    return Runnable { println("All done!") }
}

// 위와 동일
fun createAllDoneRunnable02(): Runnable {
    return object : Runnable {
        override fun run() { println("All done!") }
    }
}

// lambda with receiver
// 1. with : 람다 식의 본문에 있는 마지막 식의 값을 리턴
class OuterClass {

    fun alphabet() = with(StringBuilder()) {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")

//        println(this@OuterClass.toString())
        toString()
    }
}

// 2. apply
// - with 과의 유일한 차이 : 항상 자신에게 전달된 객체를 반환한다.
// - 주로 builder pattern 의 목적으로 사용
fun alphabet() = StringBuilder().apply {
    for(letter in 'A'..'Z'){
        this.append(letter)
    }
    append("\nNow I know the alphabet!")
}.toString()


fun main() {

    // ==== 이름 붙인 인자를 사용해 람다 넘기기 ==== //
    //***** 람다를 변수에 저장할 때는 파라미터의 타입을 추론할 문맥이 존재하지 않기에 파라미터 타입을 명시해야 한다 ******//
    val names = peopleForTest02.joinToString(separator = " ", transform = { p: Person01 -> p.name })

    // ==== 최상위 함수 or 프로퍼티 참조 ==== //
//    run(::salute)

    // ==== all,any,count,find ==== //
    // all : 모든 술어(predicate)가 이 조건을 만족하는지 체크
//    println(testVal.all(canBeInClub27))
//    // any : 술어를 만족하는 원소가 하나라도 있는지 궁금하면 any
//    println(testVal.any(canBeInClub27))
//    // count : 술어를 만족하는 원소의 갯수
//    println(testVal.count(canBeInClub27))
//    // find : 술어를 만족하는 원소를 하나 찾고 싶으면 사용
//    println(testVal.find(canBeInClub27))
//
//    // ==== groupBy ==== //
//    println(testVal02.groupBy { it.age })
//    // 확장 함수에 대해서도 멤버참조를 사용해 first 에 접근 가능
//    println(stringExtensionList.groupBy(String::first))

    // ==== flatmap ==== //
//    println(books.flatMap { it.authors }.toSet())

    // ==== sequence ====//
//    listOf(1,2,3,4).asSequence()
//        .map { print("map($it)"); it*it }
//        .filter { print("filter($it)"); it%2 == 0 }
//        .toList()

//    val naturalNumbers = generateSequence(0) { it + 1 }
//    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
//    println(numbersTo100.sum())


}