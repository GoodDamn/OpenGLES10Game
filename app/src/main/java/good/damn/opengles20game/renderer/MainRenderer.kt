package good.damn.opengles20game.renderer

import android.content.Context
import android.opengl.GLES11.*
import android.opengl.GLSurfaceView
import android.util.Log
import good.damn.opengles20game.components.Mesh
import good.damn.opengles20game.components.camera.RotatableCamera
import good.damn.opengles20game.components.entities.map.MapElement
import good.damn.opengles20game.components.light.Light
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MainRenderer: GLSurfaceView.Renderer {

    private val TAG = "MainRenderer"

    private val mPassabilityMap = intArrayOf(
        3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,
        3,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,3,
        3,0,2,1,2,0,2,0,2,2,2,1,2,0,2,0,2,0,2,2,3,
        3,0,2,0,2,0,0,0,2,0,2,0,0,0,2,0,1,0,0,0,3,
        3,0,1,0,2,2,1,2,2,0,2,0,2,2,2,1,2,0,2,0,3,
        3,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,2,0,3,
        3,0,2,2,1,1,2,0,2,0,2,2,2,2,2,0,2,2,2,0,3,
        3,0,2,0,0,0,2,0,2,0,0,0,0,0,2,0,0,0,0,0,3,
        3,0,2,0,2,2,2,0,2,0,2,2,1,2,2,2,1,2,2,0,3,
        3,0,0,0,2,0,0,0,2,0,2,0,0,0,0,0,0,0,1,0,3,
        3,2,2,2,2,0,2,2,2,0,2,0,2,2,2,2,2,2,2,0,3,
        3,0,0,0,2,0,0,0,1,0,2,0,0,0,2,0,0,0,0,0,3,
        3,0,2,0,2,2,2,0,2,1,2,0,2,2,2,0,2,2,2,2,3,
        3,0,2,0,0,0,2,0,0,0,2,0,0,0,2,0,2,0,0,0,3,
        3,2,2,2,2,0,2,2,2,0,2,2,2,0,1,0,2,2,2,0,3,
        3,0,0,0,0,0,2,0,2,0,0,0,2,0,1,0,0,0,2,0,3,
        3,0,2,0,2,1,2,0,2,0,2,2,2,0,2,2,2,0,2,0,3,
        3,0,1,0,1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,3,
        3,0,2,1,2,0,2,2,2,2,2,0,2,0,2,0,2,2,2,2,3,
        3,0,0,0,0,0,0,0,0,0,0,0,2,0,2,0,0,0,0,0,3,
        3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3
    );

    private lateinit var mapElements: Array<MapElement?>
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

        mapElements = arrayOfNulls(mPassabilityMap.size + 1)

        val mesh1 = Mesh(
            "objs/chamfer_box.obj",
            "textures/light_object.png",
            mContext)

        val mesh2 = Mesh(
            "objs/box.obj",
            "textures/heavy_object.png",
            mContext)

        val mesh3 = Mesh(
            "objs/box.obj",
            "textures/border_object.png",
            mContext)

        var x = 0f
        var y = 0f

        for (i in mPassabilityMap.indices) {
            if (i % 21 == 0) {
                y++
                x = 0f
            }
            when (mPassabilityMap[i]) {
                1 -> {
                    mapElements[i] = MapElement(mesh1)
                }
                2 -> {
                    mapElements[i] = MapElement(mesh2)
                }
                3 -> {
                    mapElements[i] = MapElement(mesh3)
                }
            }
            mapElements[i]?.setPosition(x,0f,y)
            x++
        }

        val lPlane = MapElement(Mesh(
            "objs/simple_plane.obj",
            "textures/plane.jpg",
            mContext))
        lPlane.setPosition(10f,-0.51f,11f)
        mapElements[mapElements.size-1] = lPlane

        mRotatableCamera.setAnchorPosition(
            10f,0f, 11f)

        mLights = arrayOf(
            Light(0)
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

        for (map in mapElements) {
            map?.draw()
        }

        for (light in mLights) {
            light.draw()
        }
    }

    fun setCamera(rotatableCamera: RotatableCamera) {
        mRotatableCamera = rotatableCamera
    }
}