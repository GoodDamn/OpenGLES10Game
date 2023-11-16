package good.damn.opengles20game.components.camera

import android.opengl.GLES10.*
import android.opengl.GLU
import java.lang.Math.cos
import java.lang.Math.sin
import javax.microedition.khronos.opengles.GL10

class RotatableCamera {


    private var mRadius = 15.0

    private var mAngleY = 15.0
    private var mAngleX = 15.0
    private var mX = 10.0f
    private var mY = 15.0f
    private var mZ = 10.0f

    private var mAnchorX = 0.0f
    private var mAnchorY = 0.0f
    private var mAnchorZ = 0.0f

    private var mAnchorTouchX = 0.0f
    private var mAnchorTouchY = 0.0f

    fun layout(gl: GL10) {
        glMatrixMode(GL_MODELVIEW)
        glLoadIdentity()
        GLU.gluLookAt(gl,
            mX, mY, mZ,
            mAnchorX, mAnchorY, mAnchorZ,
            0f,1f,0f)
    }

    fun anchor(x:Float, y: Float) {
        mAnchorTouchX = x
        mAnchorTouchY = y
    }

    fun rotate(x: Float,
               y: Float) {

        mAngleX += (mAnchorTouchX - x) * 0.0001f
        if (mAngleX >= 360) {
            mAngleX -= 360
        } else if (mAngleX <= -360) {
            mAngleX += 360
        }

        mAngleY -= (mAnchorTouchY - y) * 0.0001f
        if (mAngleY < 5) {
            mAngleY = 5.0
        } else if (mAngleY > 90) {
            mAngleY = 90.0
        }

        val ySin = sin(mAngleY)

        mX = (mRadius * ySin * cos(mAngleX)).toFloat()
        mY = (mRadius * ySin * sin(mAngleX)).toFloat()
        mZ = (mRadius * cos(mAngleY)).toFloat()
    }

    fun perspective(fov: Float,
                   ratio: Float,
                   zNear: Float,
                   zFar: Float,
                   gl: GL10
    ) {
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        GLU.gluPerspective(gl, fov, ratio, zNear, zFar)
    }
}