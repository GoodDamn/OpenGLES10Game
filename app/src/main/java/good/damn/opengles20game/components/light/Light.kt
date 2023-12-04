package good.damn.opengles20game.components.light

import android.opengl.GLES10.*
import good.damn.opengles20game.components.entities.PositionEntity

class Light: PositionEntity {

    private var mLightID = 0

    private val mAmbient = floatArrayOf(
        0.5f, 0.5f, 0.5f, 1.0f
    )

    private val mDiffuse = floatArrayOf(
        0.0f, 1.0f, 1.0f, 1.0f
    )

    private val mSpecular = floatArrayOf(
        0.0f, 0.0f, 0.0f, 1.0f
    )

    private val mPosition = floatArrayOf(
        5f, 5.0f, 0f, 1.0f
    )

    private val mSpotDirection = floatArrayOf(
        0.0f, 0.0f, -1.0f
    )

    constructor(lightIndex: Int) {
        mLightID = lightIndex
    }

    constructor(
        lightIndex: Int,
        x:Float,
        y:Float,
        z:Float
    ) {
        mLightID = GL_LIGHT0 + lightIndex
        setPosition(x,y,z)
    }

    fun disable() {
        glDisable(mLightID)
    }

    override fun setPosition(
        x: Float,
        y: Float,
        z: Float
    ) {
        mPosition[0] = x
        mPosition[1] = y
        mPosition[2] = z
        super.setPosition(x, y, z)
    }

    fun setDirection(
        x: Float,
        y: Float,
        z: Float
    ) {
        mSpotDirection[0] = x
        mSpotDirection[1] = y
        mSpotDirection[2] = z
    }

    fun setColor(
        r: Float,
        g:Float,
        b: Float
    ) {
        mDiffuse[0] = r
        mDiffuse[1] = g
        mDiffuse[2] = b
    }

    fun draw() {
        glLightModelfv(GL_LIGHT_MODEL_AMBIENT, mAmbient,0)

        glEnable(mLightID)
        glLightfv(mLightID, GL_SPECULAR, mSpecular, 0)
        glLightfv(mLightID, GL_DIFFUSE, mDiffuse, 0)
        glLightfv(mLightID, GL_POSITION, mPosition, 0)
        glLightfv(mLightID, GL_SPOT_DIRECTION, mSpotDirection, 0)
        //glLightf(mLightID, GL_SPOT_EXPONENT, 5.0f)
        //glLightf(mLightID, GL_SPOT_CUTOFF, 70f)
    }
}