package good.damn.opengles20game

import android.annotation.SuppressLint
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.MotionEventCompat
import good.damn.opengles20game.components.camera.RotatableCamera
import good.damn.opengles20game.renderer.MainRenderer
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    companion object {
        val mRandom = Random()
    }

    private var mHas2Fingers = false

    private lateinit var mSurfaceView: GLSurfaceView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rotatableCamera = RotatableCamera()
        val renderer = MainRenderer(this)
        renderer.setCamera(rotatableCamera)

        mSurfaceView = GLSurfaceView(this)
        mSurfaceView.setEGLContextClientVersion(1)
        mSurfaceView.setRenderer(renderer)
        mSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY

        mSurfaceView.setOnTouchListener { _, motion ->
            when (motion.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    rotatableCamera.anchor(
                        motion.rawX,
                        motion.rawY
                    )
                }
                MotionEvent.ACTION_MOVE -> {
                    if (mHas2Fingers) {
                        rotatableCamera.zoom(
                            motion.rawX,
                            motion.rawY)
                        return@setOnTouchListener true
                    }

                    rotatableCamera.rotate(
                        motion.rawX,
                        motion.rawY)
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    rotatableCamera.anchor(
                        motion.rawX,
                        motion.rawY
                    )
                    mHas2Fingers = true
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    mHas2Fingers = false
                    return@setOnTouchListener false
                }

                MotionEvent.ACTION_UP -> {
                    mHas2Fingers = false
                    return@setOnTouchListener false
                }
            }

            return@setOnTouchListener true
        }

        setContentView(mSurfaceView)
    }

    override fun onPause() {
        super.onPause()
        mSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mSurfaceView.onResume()
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
}