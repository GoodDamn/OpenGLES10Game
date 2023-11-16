package good.damn.opengles20game.components

import android.content.Context
import android.opengl.GLES11.*
import android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER
import android.opengl.GLES20.GL_UNSIGNED_INT
import android.util.Log
import good.damn.opengles20game.loader.FileOBJ
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.ShortBuffer
import kotlin.math.log

class Mesh {

    private val TAG = "Mesh"

    private val mBufferIds: IntBuffer =
        IntBuffer.allocate(2)

    private val mObjFile: FileOBJ

    private var mIndicesCount: Int

    private var mPositions: FloatBuffer
    private var mNormals: FloatBuffer
    private var mTexCoords: FloatBuffer
    private var mIndices: ShortBuffer

    constructor(context: Context,
                fileName: String) {

        mObjFile = FileOBJ(context, fileName)

        mIndicesCount = mObjFile.mIndices.size

        mIndices = ByteBuffer.allocateDirect(mObjFile.mIndices.size * 4)
            .order(ByteOrder.nativeOrder()).asShortBuffer()

        mPositions = ByteBuffer.allocateDirect(mObjFile.mPositions.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mPositions.put(mObjFile.mPositions).position(0)

        mNormals = ByteBuffer.allocateDirect(mObjFile.mNormals.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mNormals.put(mObjFile.mNormals).position(0)

        mTexCoords = ByteBuffer.allocateDirect(mObjFile.mTexCoords.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mTexCoords.put(mObjFile.mTexCoords).position(0)

        Log.d(TAG, ": " + mPositions.capacity() + " " + mObjFile.mPositions.size)

        glGenBuffers(1,mBufferIds)
        glBindBuffer(GL_ARRAY_BUFFER, mBufferIds[0])
        glBufferData(GL_ARRAY_BUFFER,
            mObjFile.mPositions.size,
            mPositions,
            GL_STATIC_DRAW)
        glBindBuffer(GL_ARRAY_BUFFER,0)

        glGenBuffers(1, mBufferIds)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mBufferIds[1])
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,
            mObjFile.mIndices.size,
            mIndices,
            GL_STATIC_DRAW)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    fun draw() {

        glBindBuffer(GL_ARRAY_BUFFER,mBufferIds[0])
        glEnableClientState(GL_VERTEX_ARRAY)
        glVertexPointer(3, GL_FLOAT,4, mPositions)

        glEnableClientState(GL_NORMAL_ARRAY)
        glNormalPointer(GL_FLOAT, 4, mNormals)

        glEnableClientState(GL_TEXTURE_COORD_ARRAY)
        glTexCoordPointer(2, GL_FLOAT, 4, mTexCoords)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mBufferIds[1])

        glDrawElements(GL_TRIANGLES,
            mIndicesCount,
            GL_UNSIGNED_SHORT,
            mIndices)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)

        glDisableClientState(GL_VERTEX_ARRAY)
        glDisableClientState(GL_NORMAL_ARRAY)
        glDisableClientState(GL_TEXTURE_COORD_ARRAY)
    }

}