package good.damn.opengles20game.components.camera

import android.opengl.GLES10.*
import android.opengl.GLU
import java.lang.Math.cos
import java.lang.Math.sin
import javax.microedition.khronos.opengles.GL10
import kotlin.math.hypot

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

    private var mSwapTouchX = 0.0f
    private var mSwapTouchY = 0.0f

    fun layout(gl: GL10) {
        glMatrixMode(GL_MODELVIEW)
        glLoadIdentity()
        GLU.gluLookAt(
            gl,
            mX, mY, mZ,
            mAnchorX, mAnchorY, mAnchorZ,
            0f, 1f, 0f
        )
    }

    fun anchor(
        x: Float,
        y: Float
    ) {
        mSwapTouchX = x
        mSwapTouchY = y
    }

    fun rotate(
        x: Float,
        y: Float
    ) {

        mAngleX += (x - mSwapTouchX) * 0.01f
        if (mAngleX >= 360) {
            mAngleX -= 360
        } else if (mAngleX <= -360) {
            mAngleX += 360
        }

        mAngleY -= (y - mSwapTouchY) * 0.01f
        if (mAngleY < 5) {
            mAngleY = 5.0
        } else if (mAngleY > 90) {
            mAngleY = 90.0
        }

        recalPos()
        mSwapTouchX = x
        mSwapTouchY = y
    }

    fun zoom(x: Float,
             y: Float) {
        mRadius -= (x - mSwapTouchX) * 0.01f
        mRadius -= (y - mSwapTouchY) * 0.01f
        recalPos()
        mSwapTouchX = x
        mSwapTouchY = y
    }

    fun perspective(
        fov: Float,
        ratio: Float,
        zNear: Float,
        zFar: Float,
        gl: GL10
    ) {
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        GLU.gluPerspective(gl, fov, ratio, zNear, zFar)
    }

    private fun recalPos() {
        val ySin = sin(mAngleY)

        mX = (mRadius * ySin * cos(mAngleX)).toFloat()
        mY = (mRadius * cos(mAngleY)).toFloat()
        mZ = (mRadius * ySin * sin(mAngleX)).toFloat()
    }
}