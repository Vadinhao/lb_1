object Constants {
    //start main values
    val N = 3

    //time in milliseconds
    const val T_dinner = 50L
    const val T_observation = 20L
    const val t_eat = 10L
    const val t_think = 5L
    //end main values

    //start 1.0 lb
    val N_of_forks = N * 2
    //end 1.0 lb

    //start extra options and values
    private const val CommentsMode = true
    const val T_waiting_before_new_attempt_to_eating = 1L
    //end extra options and values

    fun outputInformation(string: String) {
        if (CommentsMode)
            println(string)
    }

}