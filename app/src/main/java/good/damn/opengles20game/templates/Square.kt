package good.damn.opengles20game.templates

import android.opengl.GLES10.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Square {

    private var mVertexBuffer: FloatBuffer

    private val mVertices = floatArrayOf(
        -1.0f, -1.0f,  0.0f, // 0 left-bottom
        -1.0f,  1.0f,  0.0f, // 1 left-top
         1.0f, -1.0f,  0.0f, // 2 right-bottom
         1.0f,  1.0f,  0.0f  // 3 right-top
    )

    init {

        val vbb = ByteBuffer.allocateDirect(mVertices.size * 4)
            .order(ByteOrder.nativeOrder())

        mVertexBuffer = vbb.asFloatBuffer()
        mVertexBuffer.put(mVertices)
        mVertexBuffer.position(0)

    }

    fun draw() {
        glEnableClientState(GL_VERTEX_ARRAY)
        glVertexPointer(3, GL_FLOAT, 0, mVertexBuffer)
        glColor4f(0.5f,1.0f,1.0f,1.0f)
        glDrawArrays(GL_TRIANGLE_STRIP, 0, mVertices.size / 3)
        glDisableClientState(GL_VERTEX_ARRAY)
    }
}