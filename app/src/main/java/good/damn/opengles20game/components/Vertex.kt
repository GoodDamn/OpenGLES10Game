package good.damn.opengles20game.components

import java.nio.FloatBuffer

data class Vertex(
    val coord: FloatBuffer = FloatBuffer.allocate(3),
    val normal: FloatBuffer = FloatBuffer.allocate(3),
    val texCoord: FloatBuffer = FloatBuffer.allocate(3)
)