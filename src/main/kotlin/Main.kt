object Test{
    fun test01(){
        println("hello test01")
    }
}

data class Test02(val acc:String){
    var ddd = "1"
    fun test02(){
        println("hello $ddd")
    }
}

class Test03{
    val SOME_STATIC_VARIABLE_01 = "some_static_variable"

    companion object Inner {
        val SOME_STATIC_VARIABLE_02 = "some_static_variable"
        fun test03(){
            println("hello test03")
        }
    }

}



fun main(args: Array<String>) {

//    Test.test01();
//    Test02().test02();
//
//    val test03 = Test03()
//    test03.SOME_STATIC_VARIABLE_01;
//
//    Test03.SOME_STATIC_VARIABLE_02
//    Test03.test03()


}

