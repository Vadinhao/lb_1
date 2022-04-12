package entities.base

class BaseFork(
    val id: Int,
    private var isAvailable: Boolean = true
) {
    fun getAvailability() = isAvailable

    fun takeFork() {
        isAvailable = false
    }

    fun putFork() {
        isAvailable = true
    }
}