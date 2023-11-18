package good.damn.opengles20game.renderer

import android.content.Context
import android.opengl.EGL14
import android.opengl.GLES11.*
import android.opengl.GLSurfaceView
import android.util.Log
import good.damn.opengles20game.components.Mesh
import good.damn.opengles20game.components.camera.RotatableCamera
import good.damn.opengles20game.components.entities.enemies.Ghost
import good.damn.opengles20game.components.entities.map.MapElement
import good.damn.opengles20game.components.entities.players.Player
import good.damn.opengles20game.components.light.Light
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MainRenderer: GLSurfaceView.Renderer {

    private val TAG = "MainRenderer"

    private lateinit var mapElements: Array<MapElement>
    private lateinit var mGhosts: Array<Ghost>
    private lateinit var mLights: Array<Light>
    private lateinit var mPlayer: Player

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

        val meshGhost = Mesh(
            "objs/box1.obj",
            "textures/border_object.png",
            mContext)
        mGhosts = arrayOf(
            Ghost(meshGhost),
            Ghost(meshGhost),
            Ghost(meshGhost),
            Ghost(meshGhost)
        )

        mapElements = arrayOf(
            MapElement(Mesh(
                "objs/simplePlane.obj",
                "textures/plane.jpg",
                mContext))
        )

        mLights = arrayOf(
            Light(0)
        )

        mPlayer = Player(Mesh(
            "objs/box.obj",
                    "textures/heavy_object.png",
                    mContext))

        mPlayer.setPosition(5f,0f,0f)

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
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glClearColor(0f,0f,0f,0f)
        glViewport(0,0,mWidth,mHeight)

        mRotatableCamera.layout(gl!!)

        for (map in mapElements) {
            map.draw()
        }

        for (ghost in mGhosts) {
            ghost.draw()
        }

        mPlayer.draw()

        for (light in mLights) {
            light.draw()
        }
    }

    fun setCamera(rotatableCamera: RotatableCamera) {
        mRotatableCamera = rotatableCamera
    }
}