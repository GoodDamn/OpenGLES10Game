package good.damn.opengles20game.renderer

import android.content.Context
import android.opengl.GLES11.*
import android.opengl.GLSurfaceView
import android.util.Log
import good.damn.opengles20game.components.camera.RotatableCamera
import good.damn.opengles20game.components.entities.LevelMap
import good.damn.opengles20game.components.light.Light
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MainRenderer: GLSurfaceView.Renderer {

    private val TAG = "MainRenderer"

    private lateinit var mLevelMap: LevelMap

    private lateinit var mLights: Array<Light>

    private var mRotatableCamera = RotatableCamera()

    private var mAspect: Float = 1f
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mContext: Context

    constructor(context: Context) {
        mContext = context
    }

    override fun onSurfaceCreated(gl: GL10?, p1: EGLConfig?) {
        for (ex in gl!!.glGetString(GL_EXTENSIONS).split(" ")) {
            Log.d(TAG, "onSurfaceCreated: " + ex)
        }

        mLevelMap.create(mContext)

        mRotatableCamera.setAnchorPosition(
            10f,0f, 11f)

        mLights = arrayOf(
            Light(0,10f,5f,11f)
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
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glClearColor(0f,0f,0f,0f)
        glViewport(0,0,mWidth,mHeight)

        mRotatableCamera.layout(gl!!)

        mLevelMap.draw()

        for (light in mLights) {
            light.draw()
        }
    }

    fun setCamera(rotatableCamera: RotatableCamera) {
        mRotatableCamera = rotatableCamera
    }
}