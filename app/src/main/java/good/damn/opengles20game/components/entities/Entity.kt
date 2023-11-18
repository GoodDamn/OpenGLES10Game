package good.damn.opengles20game.components.entities

import android.opengl.GLES10.*
import good.damn.opengles20game.MainActivity
import good.damn.opengles20game.components.Mesh
import java.nio.FloatBuffer

abstract class Entity {

    private var mX = 0.0f
    private var mY = 0.0f
    private var mZ = 0.0f

    private var mPitch = 0.0f // XZ Rot
    private var mYaw = 0.0f // XY
    private var mRoll = 0.0f // ZY

    private var mesh: Mesh?

    private val modelBuffer =
        FloatBuffer.allocate(16)

    constructor(mesh: Mesh?) {
        this.mesh = mesh
    }

    init {
        modelBuffer.put(floatArrayOf(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f, // position (x,y,z)
        )).position(0)
        randomPosition()
    }

    fun randomPosition() {
        setPosition(
            MainActivity.mRandom.nextInt(5).toFloat(),
            -0.51f,
            5f)
    }

    fun setPosition(x: Float,
                    y: Float,
                    z: Float
    ) {
        modelBuffer.put(12,x)
        modelBuffer.put(13,y)
        modelBuffer.put(14,z)
    }

    open fun draw() {
        glPushMatrix()
        glMultMatrixf(modelBuffer)
        mesh?.draw()
        glPopMatrix()
    }
}