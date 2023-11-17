package good.damn.opengles20game.components

import android.content.Context
import android.opengl.GLES10.*
import good.damn.opengles20game.loader.FileOBJ
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.ShortBuffer

class Mesh {

    private val TAG = "Mesh"

    private val mBufferIds: IntBuffer =
        IntBuffer.allocate(2)

    private val mObjFile: FileOBJ

    private var mVertices: FloatBuffer
    private var mIndices: ShortBuffer

    constructor(context: Context,
                fileName: String) {

        mObjFile = FileOBJ(context, fileName)
        mIndices = ByteBuffer
            .allocateDirect(mObjFile.mIndices.size * 2)
            .order(ByteOrder.nativeOrder())
            .asShortBuffer()
            .put(mObjFile.mIndices)

        mIndices.position(0)

        mVertices = ByteBuffer
            .allocateDirect(mObjFile.mVertices.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(mObjFile.mVertices)

        mVertices.position(0)
    }

    fun draw() {
        glEnableClientState(GL_VERTEX_ARRAY)
        glVertexPointer(3, GL_FLOAT, 0, mVertices)

        //glEnableClientState(GL_COLOR_ARRAY)
        //GLES10.glColorPointer(4, GL_FLOAT, 0, mColorBuffer)

        glDrawElements(
            GL_TRIANGLES,
            mIndices.capacity(),
            GL_UNSIGNED_SHORT,
            mIndices
        )

        //glDisableClientState(GL_COLOR_ARRAY)
        glDisableClientState(GL_VERTEX_ARRAY)
    }

}