package good.damn.opengles20game.renderer

import android.content.Context
import android.opengl.GLES11.*
import android.opengl.GLSurfaceView
import good.damn.opengles20game.components.Mesh
import good.damn.opengles20game.components.camera.RotatableCamera
import good.damn.opengles20game.templates.Square
import good.damn.opengles20game.templates.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MainRenderer: GLSurfaceView.Renderer {

    private val TAG = "MainRenderer"

    private lateinit var mesh: Mesh

    private var mSquare: Square
    private var mTriangle: Triangle

    private var mRotatableCamera = RotatableCamera()

    private var mAspect: Float = 1f
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mContext: Context

    constructor(context: Context) {
        mContext = context
        mSquare = Square()
        mTriangle = Triangle()
    }

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        mesh = Mesh(mContext,"box.obj")
        glClearDepthf(1.0f)
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LEQUAL)
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST)
        glShadeModel(GL_SMOOTH)
        glDisable(GL_DITHER)
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
            0.1f, 100.0f, gl!!)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glClearColor(0f,0f,0f,0f)
        glViewport(0,0,mWidth,mHeight)

        mRotatableCamera.layout(gl!!)

        glTranslatef(-1.5f,0.0f,-6.0f)

        mesh.draw()
    }

    fun setCamera(rotatableCamera: RotatableCamera) {
        mRotatableCamera = rotatableCamera
    }
}