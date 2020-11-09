package space.prussak.calc

import android.app.Activity
import android.os.Bundle
import space.prussak.calc.controller.MathController
import space.prussak.calc.view.CalcView

class MainActivity : Activity() {

    private val controller = MathController()
    private var layout : CalcView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = CalcView(this, controller)
        setContentView(layout)
    }
}