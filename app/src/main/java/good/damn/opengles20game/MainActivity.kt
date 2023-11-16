package good.damn.opengles20game

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import good.damn.opengles20game.renderer.MainRenderer

class MainActivity : AppCompatActivity() {

    private lateinit var mSurfaceView:GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSurfaceView = GLSurfaceView(this)
        mSurfaceView.setEGLContextClientVersion(1)
        mSurfaceView.setRenderer(MainRenderer(this))
        mSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
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
}