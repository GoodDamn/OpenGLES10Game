package good.damn.opengles20game.components.entities.players

import good.damn.opengles20game.components.Mesh
import good.damn.opengles20game.components.entities.Entity
import android.opengl.GLES10.*
import good.damn.opengles20game.components.light.Light

class Player : Entity() {

    private val mLight = Light(4)

    init {
        mLight.setColor(1.0f,1.0f,0.0f)
    }

    override fun draw() {
        //mLight.setPosition(mX,mY,mZ)
        //mLight.setDirection(mX,mY+25,mZ)
        //glColor4f(0.0f,1.0f,0.0f,1.0f)
        //mLight.draw()
        super.draw()
    }

    override fun toString(): String {
        return "X: $mX y: $mY z: $mZ"
    }
}