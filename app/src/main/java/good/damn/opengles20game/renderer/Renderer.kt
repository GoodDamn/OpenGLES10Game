package good.damn.opengles20game.renderer

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.GLES11.*
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import good.damn.opengles20game.components.Mesh
import good.damn.opengles20game.components.camera.RotatableCamera
import good.damn.opengles20game.components.entities.LevelMap
import good.damn.opengles20game.components.entities.players.Player
import good.damn.opengles20game.components.light.Light
import good.damn.opengles20game.fps.FPSCounter
import java.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.abs

class Renderer
    : GLSurfaceView.Renderer,
      View.OnTouchListener,
      SensorEventListener {

    var resetX: Float = 0f
    var resetZ: Float = 0f
    var mTextViewMsg: TextView? = null

    private val TAG = "MainRenderer"

    companion object {
        val mFPSCounter = FPSCounter()
    }

    private var mSum = 0.0f

    private val mLevelMap = LevelMap()
    private var mRotatableCamera = RotatableCamera()

    private var mPlayer = Player()

    private var mAspect: Float = 1f
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mHas2Fingers = false

    private val mLight = Light(2)

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

        mPlayer.setMesh(Mesh(
            "objs/sphere.obj",
            "textures/plane.jpg",
            mContext
        ))

        mLight.setColor(0f,1f,0f)
        mLight.setPosition(5.0f,5.0f,5.0f)
        mPlayer.setPosition(1f,0f,2.0f)

        glClearDepthf(1.0f)
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LEQUAL)
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST)
        glShadeModel(GL_SMOOTH)
        glDisable(GL_DITHER)
        glEnable(GL_LIGHTING)
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

        mPlayer.draw()

        mLight.draw()

        mSum += mFPSCounter.delta
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, motion: MotionEvent?): Boolean {
        if (motion == null) {
            return false
        }

        when (motion.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "onTouch: ACTION_DOWN:")
                mRotatableCamera.swap(
                    motion.rawX,
                    motion.rawY
                )
            }
            MotionEvent.ACTION_MOVE -> {

                if (mHas2Fingers) {
                    Log.d(TAG, "onTouch: ACTION_MOVE: mHas2Fingers")
                    mRotatableCamera.zoom(
                        motion.rawX,
                        motion.rawY)
                    return true
                }

                Log.d(TAG, "onTouch: ACTION_MOVE:")

                mRotatableCamera.rotate(
                    motion.rawX,
                    motion.rawY)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.d(TAG, "onTouch: ACTION_POINTER_DOWN")
                mHas2Fingers = true
            }
            MotionEvent.ACTION_POINTER_UP -> {
                Log.d(TAG, "onTouch: ACTION_POINTER_UP")
                mRotatableCamera.swap(
                    motion.rawX,
                    motion.rawY
                )
                mHas2Fingers = false
                return true
            }

            MotionEvent.ACTION_UP -> {
                mHas2Fingers = false
                return true
            }
        }

        return true
    }

    private var mGravity: FloatArray? = null
    private var mGeomagnetic: FloatArray? = null

    private val R = FloatArray(9)
    private val I = FloatArray(9)
    private val mOrient = FloatArray(3)

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) {
            return
        }

        val type = event.sensor.type

        if (type == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values
        }

        if (type == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = event.values
        }

        if (mGravity == null || mGeomagnetic == null) {
            return
        }

        if (SensorManager.
            getRotationMatrix(R,I,
                mGravity, mGeomagnetic)) {
            SensorManager.getOrientation(R,mOrient)
            mPlayer.addPosition(
                (abs(mOrient[0]) - 1.5707f) * 0.05f,
                0f,
                (abs(mOrient[2]) - 1.5707f) * 0.05f
            )
            mTextViewMsg?.text = "$mPlayer\n${mOrient.contentToString()}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}