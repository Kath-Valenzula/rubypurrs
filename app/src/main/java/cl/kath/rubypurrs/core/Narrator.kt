package cl.kath.rubypurrs.core
import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class Narrator(ctx: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(ctx, this)
    private var ready = false
    override fun onInit(status: Int) {
        ready = status == TextToSpeech.SUCCESS
        tts?.language = Locale("es","CL")
    }
    fun speak(text: String, flush: Boolean = true) {
        if (!ready) return
        val mode = if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
        tts?.speak(text, mode, null, System.currentTimeMillis().toString())
    }
    fun shutdown() { tts?.shutdown() }
}
