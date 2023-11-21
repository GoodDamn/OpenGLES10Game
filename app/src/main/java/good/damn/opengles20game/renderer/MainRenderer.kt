package good.damn.opengles20game.renderer

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLES11.*
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.MotionEvent
import android.view.View
import good.damn.opengles20game.components.camera.RotatableCamera
import good.damn.opengles20game.components.entities.LevelMap
import good.damn.opengles20game.components.light.Light
import good.damn.opengles20game.fps.FPSCounter
import java.lang.Math.sin
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.abs

class MainRenderer
    : GLSurfaceView.Renderer,
      View.OnTouchListener {

    private val TAG = "MainRenderer"

    companion object {
        val mFPSCounter = FPSCounter()
    }

    private val mLevelMap = LevelMap()
    private lateinit var mLights: Array<Light>
    private var mRotatableCamera = RotatableCamera()

    private var mAspect: Float = 1f
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mHas2Fingers = false

    private var mContext: Context

    constructor(context: Context) {
        mContext = context
    }

    override fun onSurfaceCreated(gl: GL10?, p1: EGLConfig?) {
        for (ex in gl!!.glGetString(GL_EXTENSIONS).split(" ")) {
            Log.d(TAG, "onSurfaceCreated: " + ex)
        }

        mLevelMap.setPosition(0f,0f,0f)
        mLevelMap.create(mContext)

        mRotatableCamera.setAnchorPosition(
            10f,0f, 11f)


        val mapLight = Light(0,10f, -5f, 11f)
        mapLight.setColor(0.8f,0.8f,0.8f)

        val playerLight = Light(1, 1.5f,-0.5f,1.5f)
        playerLight.setColor(0f,0f,0.8f)
        mLights = arrayOf(
            playerLight
        )

        glClearDepthf(1.0f)
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LEQUAL)
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST)
        glShadeModel(GL_SMOOTH)
        glDisable(GL_DITHER)
        //glEnable(GL_LIGHTING)
    }

    override fun onSurfaceChanged(gl: GL10?,
                                  width: Int,
                                  height: Int) {
        mWidth = width
        mHeight = height
        mAspect = width.toFloat() / height
        glViewport(0,0,mWidth,mHeight)

        mRotatableCamera.perspective(
            45.0f, mAspect,
            0.02f, 100.0f, gl!!)
    }

    override fun onDrawFrame(gl: GL10?) {
        mFPSCounter.count()

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glClearColor(0f,0f,0f,0f)
        glViewport(0,0,mWidth,mHeight)

        mRotatableCamera.layout(gl!!)

        mLevelMap.draw()

        /*val r = abs(sin(System.currentTimeMillis().toDouble()).toFloat())

        mLights[0].setPosition(10 * r, -0.5f, 1.5f);
*/
        for (light in mLights) {
            light.draw()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, motion: MotionEvent?): Boolean {

        if (motion == null) {
            return false
        }

        when (motion.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                mRotatableCamera.anchor(
                    motion.rawX,
                    motion.rawY
                )
            }
            MotionEvent.ACTION_MOVE -> {
                if (mHas2Fingers) {
                    mRotatableCamera.zoom(
                        motion.rawX,
                        motion.rawY)
                    return true
                }

                mRotatableCamera.rotate(
                    motion.rawX,
                    motion.rawY)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                mRotatableCamera.anchor(
                    motion.rawX,
                    motion.rawY
                )
                mHas2Fingers = true
            }
            MotionEvent.ACTION_POINTER_UP -> {
                mHas2Fingers = false
                return false
            }

            MotionEvent.ACTION_UP -> {
                mHas2Fingers = false
                return false
            }
        }

        return true
    }
}