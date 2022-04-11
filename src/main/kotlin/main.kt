import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun main() {


    runBlocking {
        println("start")
        launch {
            println("async work started...")
            delay(200)
            println("async work done!")
        }
        println("end")
    }


    /*
    runBlocking {
        println("start")
        thread {
            Thread.sleep(500)
            println("test4")
        }
        delay(2000)
        thread {
            Thread.sleep(200)
            println("test5")
        }
        doSomethingAsync()
        thread {
            Thread.sleep(100)
            println("test6")
        }
        println("end")
    }

    thread {
        Thread.sleep(1000)
        println("test1")
    }
    thread {
        Thread.sleep(1500)
        println("test2")
    }
    thread {
        Thread.sleep(500)
        println("test3")
    }
    */
}

suspend fun doSomethingAsync() = coroutineScope {
    launch {
        println("async work started...")
        delay(1200)
        println("async work done!")
    }
}
