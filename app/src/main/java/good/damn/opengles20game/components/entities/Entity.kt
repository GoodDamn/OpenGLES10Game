package good.damn.opengles20game.components.entities

import android.opengl.GLES10.*
import good.damn.opengles20game.MainActivity
import good.damn.opengles20game.components.Mesh
import java.nio.FloatBuffer

abstract class Entity: PositionEntity {

    private var mPitch = 0.0f // XZ Rot
    private var mYaw = 0.0f // XY
    private var mRoll = 0.0f // ZY

    private var mesh: Mesh? = null

    private val modelBuffer =
        FloatBuffer.allocate(16)

    constructor()

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

    fun setMesh(mesh: Mesh?) {
        this.mesh = mesh
    }

    fun randomPosition() {
        setPosition(
            MainActivity.mRandom.nextInt(5).toFloat(),
            -0.51f,
            5f)
    }

    override fun setPosition(x: Float,
                    y: Float,
                    z: Float
    ) {
        modelBuffer.put(12,x)
        modelBuffer.put(13,y)
        modelBuffer.put(14,z)
        super.setPosition(x,y,z)
    }

    override fun addPosition(
        x: Float,
        y: Float,
        z: Float
    ) {
        super.addPosition(x,y,z)
        modelBuffer.put(12,mX)
        modelBuffer.put(13,mY)
        modelBuffer.put(14,mZ)
    }

    open fun draw() {
        glPushMatrix()
        glMultMatrixf(modelBuffer)
        mesh?.draw()
        glPopMatrix()
    }
}