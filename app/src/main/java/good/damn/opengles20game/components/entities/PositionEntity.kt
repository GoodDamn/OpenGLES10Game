package good.damn.opengles20game.components.entities

open class PositionEntity {

    protected var mX = 0.0f
    protected var mY = 0.0f
    protected var mZ = 0.0f


    open fun setPosition(
        x: Float,
        y: Float,
        z: Float
    ) {
        mX = x
        mY = y
        mZ = z
    }

    open fun addPosition(
        x: Float,
        y: Float,
        z: Float) {
        mX += x
        mY += y
        mZ += z
    }
}