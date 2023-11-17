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
    private var mActivePtrId = 0

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

                    mActivePtrId = motion.getPointerId(0)

                    rotatableCamera.anchor(
                        motion.rawX,
                        motion.rawY
                    )
                    Log.d(TAG, "onCreate: ACTION_DOWN")
                }
                MotionEvent.ACTION_MOVE -> {

                    if (mHas2Fingers) {

                        val ptrIndex  = motion
                            .findPointerIndex(mActivePtrId)
                        Log.d(TAG, "onCreate: ACTION_MOVE: mHas2Fingers: ")

                        rotatableCamera.zoom(
                            motion.getX(ptrIndex),
                            motion.getY(ptrIndex))
                        return@setOnTouchListener true
                    }

                    rotatableCamera.rotate(
                        motion.rawX,
                        motion.rawY)
                    Log.d(TAG, "onCreate: ACTION_MOVE")
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    rotatableCamera.anchor(
                        motion.rawX,
                        motion.rawY
                    )
                    mHas2Fingers = true
                    Log.d(TAG, "onCreate: ACTION_POINTER_DOWN")
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    mHas2Fingers = false
                    Log.d(TAG, "onCreate: ACTION_POINTER_UP")
                }

                MotionEvent.ACTION_UP -> {
                    Log.d(TAG, "onCreate: ACTION_UP")
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