package good.damn.opengles20game.templates

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import android.opengl.GLES10.*

class Triangle {

    private var mVertexBuffer: FloatBuffer
    private var mColorBuffer: FloatBuffer
    private var mIndicesBuffer: ByteBuffer

    private val mVertices = floatArrayOf(
        0.0f, 1.0f, 0.0f, // 0. top
        -1.0f, -1.0f, 0.0f, // 1. left-bottom
        1.0f, -1.0f, 0.0f  // 2. right-bottom
    )

    private val mColors = floatArrayOf(
        1.0f, 0.0f, 0.0f, 1.0f, // RED
        0.0f, 1.0f, 0.0f, 1.0f, // GREEN
        0.0f, 0.0f, 1.0f, 1.0f // BLUE
    )

    private val mIndices = byteArrayOf(0, 1, 2)

    init {
        // vertex-array buffer
        val vbb = ByteBuffer.allocateDirect(mVertices.size * 4)
            .order(ByteOrder.nativeOrder())

        mVertexBuffer = vbb.asFloatBuffer()
        mVertexBuffer.put(mVertices)
        mVertexBuffer.position(0)

        // color-array buffer
        val cbb = ByteBuffer.allocateDirect(mColors.size * 4)
            .order(ByteOrder.nativeOrder())
        mColorBuffer = cbb.asFloatBuffer()
            .put(mColors)
        mColorBuffer.position(0)

        mIndicesBuffer = ByteBuffer.allocateDirect(mIndices.size)
        mIndicesBuffer.put(mIndices)
        mIndicesBuffer.position(0)
    }

    fun draw() {
        glEnableClientState(GL_VERTEX_ARRAY)
        glVertexPointer(3, GL_FLOAT, 0, mVertexBuffer)

        glEnableClientState(GL_COLOR_ARRAY)
        glColorPointer(4, GL_FLOAT, 0, mColorBuffer)

        glDrawElements(
            GL_TRIANGLES,
            mIndices.size,
            GL_UNSIGNED_BYTE,
            mIndicesBuffer
        )

        glDisableClientState(GL_COLOR_ARRAY)
        glDisableClientState(GL_VERTEX_ARRAY)
    }

}