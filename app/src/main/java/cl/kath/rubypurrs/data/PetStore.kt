package cl.kath.rubypurrs.data
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.petDataStore by preferencesDataStore("ruby_pet")

object PetKeys {
    val hunger = intPreferencesKey("hunger")
    val energy = intPreferencesKey("energy")
    val joy = intPreferencesKey("joy")
}
data class PetState(val hunger: Int, val energy: Int, val joy: Int)

suspend fun initPet(ctx: Context) {
    ctx.petDataStore.edit {
        if (!it.contains(PetKeys.hunger)) {
            it[PetKeys.hunger]=70; it[PetKeys.energy]=70; it[PetKeys.joy]=70
        }
    }
}
fun petFlow(ctx: Context): Flow<PetState> = ctx.petDataStore.data.map {
    PetState(it[PetKeys.hunger]?:70, it[PetKeys.energy]?:70, it[PetKeys.joy]?:70)
}
suspend fun updatePet(ctx: Context, hunger: Int?=null, energy: Int?=null, joy: Int?=null) {
    ctx.petDataStore.edit {
        hunger?.let { h-> it[PetKeys.hunger]=h.coerceIn(0,100) }
        energy?.let { e-> it[PetKeys.energy]=e.coerceIn(0,100) }
        joy?.let { j-> it[PetKeys.joy]=j.coerceIn(0,100) }
    }
}
