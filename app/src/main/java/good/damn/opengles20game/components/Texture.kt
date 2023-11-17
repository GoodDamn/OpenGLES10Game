package good.damn.opengles20game.components

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES10.*
import android.opengl.GLUtils

class Texture {

    companion object {
        fun load(context: Context,
                 filePath: String): IntArray {

            val mTextureID = intArrayOf(1)

            glGenTextures(1, mTextureID, 0)

            glBindTexture(GL_TEXTURE_2D, mTextureID[0])

            glTexParameterx(GL_TEXTURE_2D,
                GL_TEXTURE_MIN_FILTER,
                GL_NEAREST)

            glTexParameterx(GL_TEXTURE_2D,
                GL_TEXTURE_MAG_FILTER,
                GL_LINEAR)

            val iStream = context.assets.open(filePath)
            val bitmap = BitmapFactory.decodeStream(iStream)
            iStream.close()

            GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
            bitmap.recycle()

            return mTextureID
        }
    }
}