package cl.kath.rubypurrs.data
data class User(val email: String, val pass: String)
object UserRepository {
    private val users = mutableListOf(
        User("ana@demo.cl","1234"),
        User("beto@demo.cl","1234"),
        User("cata@demo.cl","1234"),
        User("diego@demo.cl","1234"),
        User("eva@demo.cl","1234")
    )
    fun login(email: String, pass: String) = users.any { it.email==email && it.pass==pass }
    fun register(email: String, pass: String): Boolean {
        if (users.any { it.email==email }) return false
        users.add(User(email, pass)); return true
    }
}
