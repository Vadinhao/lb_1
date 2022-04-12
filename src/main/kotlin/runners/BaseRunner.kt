package runners

import Constants
import entities.base.BasePhilosopher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class BaseRunner(
    private val philosopher: BasePhilosopher,
) {

    suspend fun runDinerAsync(scope: CoroutineScope) = scope.async {
        while (true) {
            while (!philosopher.eat())
                delay(Constants.T_waiting_before_new_attempt_to_eating)
            philosopher.think()
        }
    }

    suspend fun runCheckEatAndThinkCountAsync(scope: CoroutineScope) = scope.async {
        while (true) {
            Constants.outputInformation("*****Philosopher #" + philosopher.id + ": eatingCount: " + philosopher.getEatCount() + ", thinking count: " + philosopher.getThinkCount())
            delay(Constants.T_observation)
        }
    }

}