package good.damn.opengles20game.components.entities.players

import good.damn.opengles20game.components.Mesh
import good.damn.opengles20game.components.entities.Entity
import android.opengl.GLES10.*

class Player(mesh: Mesh) : Entity(mesh) {

    override fun draw() {
        glColor4f(1f,1f,0f,1f) // RGBA
        super.draw()
    }

}