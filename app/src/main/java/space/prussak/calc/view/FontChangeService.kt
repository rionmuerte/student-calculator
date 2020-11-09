package space.prussak.calc.view

import android.util.TypedValue
import android.view.ViewTreeObserver
import android.widget.TextView

class FontChangeService(upperText: TextView, lowerText: TextView, fontSizeArray: Array<Float>) {
    private val upperField = upperText
    private val lowerField = lowerText
    private val fontSizes = fontSizeArray

    private var currentTextSizePosition = 0

    fun increaseFonts() {
        addOnPreDrawListener(lowerField, this::increaseFontSize)
        addOnPreDrawListener(upperField, this::increaseFontSize)
    }

    fun decreaseFonts() {
        addOnPreDrawListener(lowerField, this::decreaseFontSize)
        addOnPreDrawListener(upperField, this::decreaseFontSize)
    }

    private fun addOnPreDrawListener(obj : TextView, fontChanger: () -> Unit ) {
        obj.viewTreeObserver?.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                val fontCursor = currentTextSizePosition
                fontChanger()
                if (fontCursor != currentTextSizePosition) return false
                obj.viewTreeObserver?.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    private fun increaseFontSize() {
        if (lowerField.lineCount <= 1 && upperField.lineCount <= 1 && currentTextSizePosition > 0) {
            val size = fontSizes[--currentTextSizePosition]
            lowerField.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
            upperField.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
        }
        decreaseFonts()
    }

    private fun decreaseFontSize() {
        if ((lowerField.lineCount > 1 || upperField.lineCount > 1) && currentTextSizePosition < fontSizes.size) {
            val size = fontSizes[currentTextSizePosition++]
            lowerField.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
            upperField.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
        }
    }
}