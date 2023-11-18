package good.damn.opengles20game.components.light

import android.opengl.GLES10.*

class Light {

    private var mX = 0.0f
    private var mY = 0.0f
    private var mZ = 0.0f

    private var mLightID = 0

    private val mAmbient = floatArrayOf(
        0.2f, 0.2f, 0.2f, 1.0f
    )

    private val mDiffuse = floatArrayOf(
        0.8f, 0.8f, 0.8f, 1.0f
    )

    private val mSpecular = floatArrayOf(
        0.5f, 0.2f, 0.2f, 1.0f
    )

    private val mPosition = floatArrayOf(
        5f, 5.0f, 0f, 1.0f
    )

    constructor(lightIndex: Int) {
        mLightID = GL_LIGHT0 + lightIndex
    }

    fun disable() {
        glDisable(mLightID)
    }

    fun draw() {
        glLightModelfv(GL_LIGHT_MODEL_AMBIENT, mAmbient,0)

        glEnable(mLightID)
        glLightfv(mLightID, GL_SPECULAR, mSpecular, 0)
        glLightfv(mLightID, GL_DIFFUSE, mDiffuse, 0)
        glLightfv(mLightID, GL_POSITION, mPosition, 0)
    }
}