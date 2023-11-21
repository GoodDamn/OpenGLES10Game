package good.damn.opengles20game.components.camera

import android.opengl.GLES10.*
import android.opengl.GLU
import android.os.SystemClock
import android.util.Log
import good.damn.opengles20game.renderer.MainRenderer
import java.lang.Math.cos
import java.lang.Math.sin
import javax.microedition.khronos.opengles.GL10
import kotlin.math.hypot

class RotatableCamera {

    private val TAG = "RotatableCamera"

    private var mRadius = 15.0

    private var mAngleY = 0.0
    private var mAngleX = 0.0
    private var mX = 0.0f
    private var mY = 0.0f
    private var mZ = 0.0f

    private var mAnchorX = 0.0f
    private var mAnchorY = 0.0f
    private var mAnchorZ = 0.0f

    private var mSwapTouchX = 0.0f
    private var mSwapTouchY = 0.0f

    constructor() {
        recalPos()
    }

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

    fun setAnchorPosition(
        x: Float,
        y: Float,
        z: Float) {
        mAnchorX = x
        mAnchorY = y
        mAnchorZ = z
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
        Log.d(TAG, "rotate: $mAngleX $mAngleY ${MainRenderer.mFPSCounter.fps} ${MainRenderer.mFPSCounter.delta}")
        mAngleX += (x - mSwapTouchX) * 0.1f * MainRenderer.mFPSCounter.delta
        if (mAngleX >= 360) {
            mAngleX -= 360
        } else if (mAngleX <= -360) {
            mAngleX += 360
        }

        mAngleY += (y - mSwapTouchY) * 0.1f * MainRenderer.mFPSCounter.delta
        if (mAngleY < 4.8) {
            mAngleY = 4.8
        } else if (mAngleY > 5.7f) {
            mAngleY = 5.7
        }

        recalPos()
        mSwapTouchX = x
        mSwapTouchY = y
    }

    fun zoom(x: Float,
             y: Float) {
        mRadius -= (x - mSwapTouchX) * 0.01f
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
        val ay = mAngleY
        val ax = mAngleX

        val ySin = sin(ay)

        mX = mAnchorX + (mRadius * ySin * cos(ax)).toFloat()
        mY = mAnchorY + (mRadius * cos(ay)).toFloat()
        mZ = mAnchorZ + (mRadius * ySin * sin(ax)).toFloat()
    }
}