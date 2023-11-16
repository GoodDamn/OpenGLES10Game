package good.damn.opengles20game.renderer

import android.content.Context
import android.opengl.GLES11.*
import android.opengl.GLU.*
import android.opengl.GLSurfaceView
import good.damn.opengles20game.components.Mesh
import good.damn.opengles20game.templates.Square
import good.damn.opengles20game.templates.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MainRenderer: GLSurfaceView.Renderer {

    //private lateinit var mesh: Mesh

    private var mSquare: Square
    private var mTriangle: Triangle

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mContext: Context

    constructor(context: Context) {
        mContext = context
        mSquare = Square()
        mTriangle = Triangle()
    }

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        //mesh = Mesh(mContext,"box.obj")
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
        val aspect = width.toFloat() / height
        glViewport(0,0,mWidth,mHeight)

        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        gluPerspective(gl, 45.0f, aspect, 0.1f, 100.0f)

        glMatrixMode(GL_MODELVIEW)
        glLoadIdentity()
    }

    override fun onDrawFrame(p0: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glClearColor(0f,0f,0f,0f)
        glViewport(0,0,mWidth,mHeight)

        glLoadIdentity() // Reset model-view matrix
        glTranslatef(-1.5f,0.0f,-6.0f)
        mTriangle.draw()

        glTranslatef(3.0f,0.0f,0.0f)
        mSquare.draw()

        //mesh.draw()
    }
}