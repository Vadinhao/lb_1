package entities.base

import kotlinx.coroutines.delay

class BasePhilosopher(
    val id: Int,
    private var leftFork: BaseFork,
    private var rightFork: BaseFork
) {

    private var eatCount = 0
    private var thinkCount = 0

    fun getEatCount() = eatCount

    fun getThinkCount() = eatCount

    suspend fun eat(): Boolean {
        return if (leftFork.getAvailability() && rightFork.getAvailability()) {
            eatCount++
            leftFork.takeFork()
            rightFork.takeFork()
            Constants.outputInformation("Philosopher #" + id + " start eating with forks: l = " + leftFork.id + " " + leftFork.getAvailability() + " " + " r = " + rightFork.id + " " + rightFork.getAvailability())
            delay(Constants.t_eat)
            leftFork.putFork()
            rightFork.putFork()
            Constants.outputInformation("Philosopher #" + id + " end eating with forks: l = " + leftFork.id + " " + leftFork.getAvailability() + " " + " r = " + rightFork.id + " " + rightFork.getAvailability())
            true
        } else {
            false
        }
    }

    suspend fun think() {
        thinkCount++
        Constants.outputInformation("Philosopher #$id start thinking")
        delay(Constants.t_think)
        Constants.outputInformation("Philosopher #$id end thinking")
    }

}