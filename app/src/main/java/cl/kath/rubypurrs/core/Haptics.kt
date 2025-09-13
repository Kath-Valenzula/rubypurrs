package cl.kath.rubypurrs.core
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class Haptics(context: Context) {
    private val vib: Vibrator? = context.getSystemService(Vibrator::class.java)
    private fun vibrate(ms: Long, amp: Int) {
        if (Build.VERSION.SDK_INT >= 26) vib?.vibrate(VibrationEffect.createOneShot(ms, amp))
        else @Suppress("DEPRECATION") vib?.vibrate(ms)
    }
    fun ok() = vibrate(40, 180)
    fun error() = vibrate(120, 255)
    fun ramp(level: Int) = vibrate(30L + (10 - level) * 10L, 80 + level * 15)
}
