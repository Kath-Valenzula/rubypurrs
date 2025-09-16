package cl.kath.rubypurrs.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object AuthStore {
    val users: SnapshotStateList<User> = mutableStateListOf()

    fun register(u: User): Boolean {
        if (users.size >= 5) return false
        if (users.any { it.email.equals(u.email, ignoreCase = true) }) return false
        users += u
        return true
    }

    fun login(email: String, pass: String): Boolean =
        users.any { it.email.equals(email, ignoreCase = true) && it.pass == pass }

    fun resetPass(email: String, newPass: String): Boolean {
        val i = users.indexOfFirst { it.email.equals(email, ignoreCase = true) }
        if (i < 0) return false
        users[i] = users[i].copy(pass = newPass)
        return true
    }
}
