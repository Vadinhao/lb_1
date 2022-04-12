import entities.base.BaseFork
import entities.base.BasePhilosopher
import kotlinx.coroutines.*
import runners.BaseRunner
import java.awt.SystemColor

fun main() {
    val forksArray = arrayListOf<BaseFork>().also { fillForksArray(it) }
    val philosophersArray = arrayListOf<BasePhilosopher>().also { fillPhilosophersArray(it, forksArray) }
    runDinner(philosophersArray)
}

private fun fillForksArray(forksArray: ArrayList<BaseFork>) {
    for (index in 1..Constants.N_of_forks) {
        forksArray.add(BaseFork(index))
    }
}

private fun fillPhilosophersArray(philosophersArray: ArrayList<BasePhilosopher>, forksArray: ArrayList<BaseFork>) {
    for (index in 1 until Constants.N) {
        val forksIndex = (index - 1) * 2
        philosophersArray.add(BasePhilosopher(index, forksArray[forksIndex], forksArray[forksIndex + 1]))
    }
    philosophersArray.add(
        BasePhilosopher(
            philosophersArray.size + 1,
            forksArray[forksArray.size - 2],
            forksArray[forksArray.size - 1]
        )
    )
}

private fun runDinner(philosophersArray: ArrayList<BasePhilosopher>) {
    try {
        runBlocking {
            val baseRunnerJobsList = arrayListOf<Deferred<Deferred<Unit>>>().also {
                fillBaseRunnerJobsList(this, it, philosophersArray)
            }
            Constants.outputInformation("Dinner started " + System.currentTimeMillis().toString() + " mls")
            baseRunnerJobsList.forEachIndexed { _, deferred ->
                deferred.join()
            }
            delay(Constants.T_dinner)
            this.cancel()
        }
    } catch (e: CancellationException) {
        Constants.outputInformation("Dinner was stopped: " + System.currentTimeMillis().toString() + " mls\n$e")
    }
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


