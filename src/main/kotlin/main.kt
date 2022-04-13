import entities.base.BaseFork
import entities.base.BasePhilosopher
import kotlinx.coroutines.*
import runners.BaseRunner
import java.lang.invoke.ConstantBootstraps

fun main() {
    val forksArray = arrayListOf<BaseFork>().also { fillForksArray(it) }
    val philosophersArray = arrayListOf<BasePhilosopher>().also { fillPhilosophersArray(it, forksArray) }
    decoratedRunDinner(philosophersArray)
}

private fun fillForksArray(forksArray: ArrayList<BaseFork>) {
    for (index in 1..Constants.N_of_forks) {
        forksArray.add(BaseFork(index))
    }
}

private fun fillPhilosophersArray(philosophersArray: ArrayList<BasePhilosopher>, forksArray: ArrayList<BaseFork>) {
    for (index in 1..Constants.N) {
        val forksIndex = (index - 1) * 2
        philosophersArray.add(BasePhilosopher(index, forksArray[forksIndex], forksArray[forksIndex + 1]))
    }
}

private fun runDinner(philosophersArray: ArrayList<BasePhilosopher>) {
    try {
        runBlocking {
            val baseRunnerJobsList = arrayListOf<Deferred<Deferred<Unit>>>().also {
                fillBaseRunnerJobsList(this, it, philosophersArray)
            }
            baseRunnerJobsList.forEachIndexed { _, philosopherProcess ->
                philosopherProcess.join()
            }
        }
    } catch (e: CancellationException) {
        Constants.outputInformation("Dinner was stopped\n$e")
    }
}

private fun decoratedRunDinner(philosophersArray: ArrayList<BasePhilosopher>){
    Constants.outputInformation("Dinner started")
    val dinnerStartTime = System.nanoTime()
    runDinner(philosophersArray)
    val dinnerEndTime = System.nanoTime()
    val dinnerTimeMls = (dinnerEndTime - dinnerStartTime).inMilliseconds()
    Constants.outputInformation("Dinner time: $dinnerTimeMls")
}

private fun fillBaseRunnerJobsList(
    scope: CoroutineScope,
    jobList: ArrayList<Deferred<Deferred<Unit>>>,
    philosophersArray: ArrayList<BasePhilosopher>
) {
    philosophersArray.forEachIndexed { _, basePhilosopher ->
        val job = scope.async {
            val baseRunner = BaseRunner(basePhilosopher)
            baseRunner.runDinerAsync(scope)
            baseRunner.runCheckEatAndThinkCountAsync(scope)
        }
        jobList.add(job)
    }
}

fun Long.inMilliseconds(): Long {
    return this / 1000000
}


