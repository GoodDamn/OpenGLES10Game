package good.damn.opengles20game

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import good.damn.opengles20game.renderer.Renderer
import java.util.*

class MainActivity
    : AppCompatActivity(){

    private val TAG = "MainActivity"

    companion object {
        val mRandom = Random()
    }

    private lateinit var mRenderer: Renderer
    private lateinit var mSensorManager: SensorManager
    private lateinit var mSurfaceView: GLSurfaceView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val metrics = resources.displayMetrics

        mRenderer = Renderer(this)

        mSurfaceView = GLSurfaceView(this)
        mSurfaceView.setEGLContextClientVersion(1)
        mSurfaceView.setRenderer(mRenderer)
        mSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY

        mSurfaceView.setOnTouchListener(mRenderer)

        mSensorManager = getSystemService(SENSOR_SERVICE)
                as SensorManager

        val textViewMsg = TextView(this)
        textViewMsg.setTextColor(0xffffff00.toInt())
        textViewMsg.setBackgroundColor(0)

        mRenderer.mTextViewMsg = textViewMsg

        val frameLayout = FrameLayout(this)
        frameLayout.addView(mSurfaceView)
        frameLayout.addView(
            textViewMsg,
            (metrics.widthPixels * 0.5f).toInt(),
            -1)

        setContentView(frameLayout)
    }

    override fun onPause() {
        super.onPause()
        mSurfaceView.onPause()
        mSensorManager.unregisterListener(mRenderer)
    }

    override fun onResume() {
        super.onResume()
        mSurfaceView.onResume()
        registerSensor(Sensor.TYPE_ACCELEROMETER, mRenderer)
        registerSensor(Sensor.TYPE_MAGNETIC_FIELD, mRenderer)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (!hasFocus) {
            return
        }

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE

    }

    private fun registerSensor(
        sensorType: Int,
        listener: SensorEventListener
    ) {
        mSensorManager.registerListener(
            listener,
            mSensorManager.getDefaultSensor(sensorType),
            SensorManager.SENSOR_DELAY_GAME)
    }

}