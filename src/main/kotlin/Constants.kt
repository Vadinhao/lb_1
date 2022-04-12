object Constants {
    val N = 3

    //time in milliseconds
    const val T_dinner = 50L
    const val T_observation = 20L
    const val t_eat = 10L
    const val t_think = 5L
    //
    val N_of_forks = N * 2
    private const val CommentsMode = false
    const val T_waiting_before_new_attempt_to_eating = 1L

    fun outputInformation(string: String){
        if(CommentsMode)
            println(string)
    }

}