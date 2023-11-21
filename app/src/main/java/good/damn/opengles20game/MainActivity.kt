package good.damn.opengles20game

import android.annotation.SuppressLint
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import good.damn.opengles20game.renderer.Renderer
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

        val renderer = Renderer(this)

        mSurfaceView = GLSurfaceView(this)
        mSurfaceView.setEGLContextClientVersion(1)
        mSurfaceView.setRenderer(renderer)
        mSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY

        mSurfaceView.setOnTouchListener(renderer)

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