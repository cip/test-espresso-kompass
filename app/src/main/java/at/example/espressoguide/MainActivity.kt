package at.example.espressoguide

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.util.Locale

class MainActivity : Activity() {
    private var currentStep = 0
    private var secondsLeft = 30
    private var running = false
    private val handler = Handler(Looper.getMainLooper())
    private var timerView: TextView? = null
    private var timerButton: Button? = null
    private val tick = object : Runnable {
        override fun run() {
            if (!running) return
            if (secondsLeft > 0) secondsLeft--
            timerView?.text = formatTime(secondsLeft)
            if (secondsLeft == 0) {
                running = false
                timerButton?.text = "Timer erneut starten"
            } else handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentStep = savedInstanceState?.getInt("step")?.coerceIn(0, BrewSteps.espresso.lastIndex) ?: 0
        secondsLeft = savedInstanceState?.getInt("seconds")?.coerceIn(0, 30) ?: 30
        render()
    }

    private fun render() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(24), dp(24), dp(24))
            setBackgroundColor(Color.rgb(250, 246, 239))
        }
        val scroll = ScrollView(this).apply { isFillViewport = true }
        val content = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        scroll.addView(content)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(root)

        content.addView(text("☕ ESPRESSO KOMPASS", 15, Color.rgb(107, 69, 42), true))
        content.addView(space(20))
        content.addView(text("Dein Espresso.\nSchritt für Schritt.", 31, Color.rgb(44, 32, 26), true))
        content.addView(space(14))
        content.addView(text("Doppelter Espresso · 18 g rein · ca. 36 g raus", 14, Color.DKGRAY, false))
        content.addView(space(28))

        val step = BrewSteps.espresso[currentStep]
        content.addView(text("SCHRITT ${currentStep + 1} VON ${BrewSteps.espresso.size}", 13, Color.rgb(132, 89, 57), true))
        content.addView(space(9))
        val progress = android.widget.ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
            max = BrewSteps.espresso.size
            progress = currentStep + 1
            progressTintList = android.content.res.ColorStateList.valueOf(Color.rgb(113, 68, 36))
        }
        content.addView(progress, LinearLayout.LayoutParams(-1, dp(8)))
        content.addView(space(25))
        content.addView(text(step.title, 27, Color.rgb(44, 32, 26), true))
        content.addView(space(14))
        content.addView(text(step.description, 19, Color.rgb(50, 45, 40), false))
        content.addView(space(24))
        content.addView(text("TIPP", 13, Color.rgb(132, 89, 57), true))
        content.addView(space(7))
        content.addView(text(step.tip, 16, Color.rgb(76, 59, 44), false))

        if (step.durationSeconds > 0) {
            content.addView(space(24))
            timerView = text(formatTime(secondsLeft), 39, Color.rgb(74, 43, 24), true)
            content.addView(timerView)
            timerButton = Button(this).apply {
                text = if (running) "Pause" else if (secondsLeft < 30 && secondsLeft > 0) "Fortsetzen" else "30-Sekunden-Timer starten"
                setOnClickListener {
                    if (secondsLeft == 0) secondsLeft = 30
                    running = !running
                    handler.removeCallbacks(tick)
                    if (running) handler.postDelayed(tick, 1000)
                    render()
                }
            }
            content.addView(timerButton)
            content.addView(Button(this).apply {
                text = "Timer zurücksetzen"
                setOnClickListener { stopTimer(); secondsLeft = 30; render() }
            })
        } else { timerView = null; timerButton = null }

        content.addView(space(22))
        val nav = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        nav.addView(Button(this).apply {
            text = "Zurück"
            isEnabled = currentStep > 0
            setOnClickListener { goTo(currentStep - 1) }
        }, LinearLayout.LayoutParams(0, -2, 1f))
        nav.addView(Button(this).apply {
            text = if (currentStep == BrewSteps.espresso.lastIndex) "Neu beginnen" else "Weiter →"
            setOnClickListener { goTo(if (currentStep == BrewSteps.espresso.lastIndex) 0 else currentStep + 1) }
        }, LinearLayout.LayoutParams(0, -2, 1f))
        content.addView(nav)
        content.addView(space(16))
        content.addView(Button(this).apply { text = "Über die App"; setOnClickListener { showAbout() } })
        content.addView(space(12))
    }

    private fun goTo(step: Int) { stopTimer(); secondsLeft = 30; currentStep = step; render() }
    private fun stopTimer() { running = false; handler.removeCallbacks(tick) }
    private fun showAbout() {
        val p = packageManager.getPackageInfo(packageName, 0)
        @Suppress("DEPRECATION") val code = if (android.os.Build.VERSION.SDK_INT >= 28) p.longVersionCode else p.versionCode.toLong()
        android.app.AlertDialog.Builder(this)
            .setTitle("Espresso Kompass")
            .setMessage("Offline-Schritt-für-Schritt-Anleitung für Siebträgermaschinen.\nVersion ${p.versionName} (Build $code)\nAlle Werte sind Startpunkte und können je nach Bohnen und Maschine abweichen.")
            .setPositiveButton("OK", null).show()
    }
    private fun text(s: String, size: Int, color: Int, bold: Boolean) = TextView(this).apply {
        text = s
        textSize = size.toFloat()
        setTextColor(color)
        if (bold) setTypeface(null, Typeface.BOLD)
        gravity = Gravity.START
    }
    private fun space(h: Int) = View(this).apply { layoutParams = LinearLayout.LayoutParams(1, dp(h)) }
    private fun dp(n: Int) = (n * resources.displayMetrics.density).toInt()
    private fun formatTime(n: Int) = String.format(Locale.GERMANY, "%02d:%02d", n / 60, n % 60)
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("step", currentStep)
        outState.putInt("seconds", secondsLeft)
        super.onSaveInstanceState(outState)
    }
    override fun onDestroy() { stopTimer(); super.onDestroy() }
}
