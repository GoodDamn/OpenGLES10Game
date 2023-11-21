package good.damn.opengles20game.fps

class FPSCounter {
    var startTime = System.currentTimeMillis()
    var frames = 0
    var fps = 0
    var delta = 1.0f

    fun count() {
       frames++
       if (System.currentTimeMillis() - startTime >= 1000) {
           fps = frames
           delta = frames / 1000.0f
           frames = 0
           startTime = System.currentTimeMillis()
       }
    }

}