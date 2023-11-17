package good.damn.opengles20game.components

import android.content.Context
import android.opengl.GLES10.*
import good.damn.opengles20game.loader.FileOBJ
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class Mesh {

    private val TAG = "Mesh"

    private val mObjFile: FileOBJ

    private var mTexCoords: FloatBuffer
    private var mVertices: FloatBuffer
    private var mIndices: ShortBuffer

    private var mTextureID: IntArray

    constructor(filePath: String,
                texturePath: String,
                context: Context) {

        mObjFile = FileOBJ(context, filePath)
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

        mTexCoords = ByteBuffer
            .allocateDirect(mObjFile.mTexCoords.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(mObjFile.mTexCoords)
        mTexCoords.position(0)

        mTextureID = Texture.load(context, texturePath)
    }

    fun draw() {
        glFrontFace(GL_CCW)
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)

        glEnableClientState(GL_VERTEX_ARRAY)
        glVertexPointer(3, GL_FLOAT, 0, mVertices)

        glEnableClientState(GL_TEXTURE_COORD_ARRAY)
        glTexCoordPointer(2,GL_FLOAT, 0, mTexCoords)

        //glBindTexture(GL_TEXTURE_2D, mTextureID[0])

        glDrawElements(
            GL_TRIANGLES,
            mIndices.capacity(),
            GL_UNSIGNED_SHORT,
            mIndices
        )

        glDisableClientState(GL_TEXTURE_COORD_ARRAY)
        glDisableClientState(GL_VERTEX_ARRAY)
        glDisable(GL_CULL_FACE)
    }

}