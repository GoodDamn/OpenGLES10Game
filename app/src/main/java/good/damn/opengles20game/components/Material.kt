package good.damn.opengles20game.components

import android.opengl.GLES10.*

class Material {

    private val mAmbient = floatArrayOf(
        0.2f, 0.2f, 0.2f, 1.0f
    )

    private val mDiffuse = floatArrayOf(
        0.8f, 0.8f, 0.8f, 1.0f
    )

    private val mSpecular = floatArrayOf(
        0.0f, 0.0f, 0.0f, 1.0f
    )

    private val mShininess = 1.0f


    fun draw() {
        glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, mDiffuse, 0)
        glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, mAmbient, 0)
        glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, mSpecular, 0)
        glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, mShininess)
    }
}