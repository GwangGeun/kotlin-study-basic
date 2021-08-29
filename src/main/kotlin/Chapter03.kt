import java.lang.IllegalArgumentException

/**
 * 1. 가변 인자 함수 : 매개변수 'vararg' keyword 사용
 *                 인자 *intArray 사용 ( java 와 달리 배열 앞에 * 키워드 팔요 )
 */
val intArray: Array<Int> = arrayOf(1, 2, 3, 4)

fun <T> varargTest(vararg values: T) {
    for (value in values) {
        println(value)
    }
}

/**
 * 2. infix call
 * 3. destructing declaration : ex) val (number, name) = 1 to "one"
 */

// 1 to "one" : infix fun call
// 10.to("ten") : general fun call
val map = mapOf(1 to "one", 7 to "seven", 10.to("ten"))

/**
 * 4. regex
 *
 * cf) 3 quotes String : 줄 바꿈을 표현하는 아무 문자열이나 그대로 들어간다
 */
// String 확장 함수
fun parsePath(path: String) {
    val dir = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")

    println("Dir: $dir, name: $fileName, ext: $extension")
}

// regex
fun parsePathRegex(path:String){
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if(matchResult != null){
        val (directory, filename, extension) = matchResult.destructured
        println("Dir: $directory, name: $filename, ext: $extension")
    }
}

/**
 * 5. 로컬 함수와 확장
 */
class User(val id: Int, val name:String, val address:String)

// 로컬 함수
fun saveUser01(user:User){
    fun validate01(value:String, fieldName: String){
        if(value.isEmpty()){
            throw IllegalArgumentException(
                "Can`t save user ${user.id} :" + "empty $fieldName"
            )
        }

        validate01(user.name, "Name")
        validate01(user.address, "Address")
    }
}

// 로컬 함수 + 확장 함수
fun User.validateBeforeSave(){
    fun validate02(value:String, fieldName:String){
        if(value.isEmpty()){
            throw IllegalArgumentException("Can`t save user $id : empty $fieldName") // 여기서의 id 는 수신객체(User) 의 id
        }
    }

    validate02(name, "Name")
    validate02(address, "Address")

}

fun saveUser02(user:User){
    user.validateBeforeSave();
}


fun main() {

    //====== 가변 인자 함수 ======//
//    varargTest(*intArray)

    //====== String 확장 함수 ======//
//    val testStr = "/Users/yole/kotlin-book/chapter.adoc"
//    parsePath(testStr)

    //====== 3 quotes String ====//
//    val kotlinLogo = """|   //
//                       .| //
//                       .|/ \""".trimMargin(".")
//    println(kotlinLogo)




}