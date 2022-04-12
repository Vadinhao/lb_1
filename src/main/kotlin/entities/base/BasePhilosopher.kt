package entities.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class BasePhilosopher(
    val id: Int,
    var leftFork: BaseFork,
    var rightFork: BaseFork
) {

    private var eatCount = 0
    private var thinkCount = 0

    fun getEatCount() = eatCount

    fun getThinkCount() = eatCount

    suspend fun eat(): Boolean {
        if (leftFork.getAvailability() && rightFork.getAvailability()) {

            leftFork.takeFork()
            rightFork.takeFork()
            Constants.outputInformation("Philosopher #" + id + " start eating with forks: l = " + leftFork.id + " " + leftFork.getAvailability() + " " + " r = " + rightFork.id + " " + rightFork.getAvailability())
            delay(Constants.t_eat)
            leftFork.putFork()
            rightFork.putFork()
            Constants.outputInformation("Philosopher #" + id + " end eating with forks: l = " + leftFork.id + " " + leftFork.getAvailability() + " " + " r = " + rightFork.id + " " + rightFork.getAvailability())

            eatCount++

            return true
        } else {
            return false
        }
    }

    suspend fun think() {
        Constants.outputInformation("Philosopher #$id start thinking")
        delay(Constants.t_think)
        Constants.outputInformation("Philosopher #$id end thinking")

        thinkCount++
    }

}