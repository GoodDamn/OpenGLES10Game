package good.damn.opengles20game.components.entities

import android.content.Context
import good.damn.opengles20game.components.Mesh
import good.damn.opengles20game.components.entities.map.MapElement

class LevelMap: PositionEntity() {

    private val mPassabilityMap = intArrayOf(
        3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,
        3,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,3,
        3,0,2,1,2,0,2,0,2,2,2,1,2,0,2,0,2,0,2,2,3,
        3,0,2,0,2,0,0,0,2,0,2,0,0,0,2,0,1,0,0,0,3,
        3,0,1,0,2,2,1,2,2,0,2,0,2,2,2,1,2,0,2,0,3,
        3,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,2,0,3,
        3,0,2,2,1,1,2,0,2,0,2,2,2,2,2,0,2,2,2,0,3,
        3,0,2,0,0,0,2,0,2,0,0,0,0,0,2,0,0,0,0,0,3,
        3,0,2,0,2,2,2,0,2,0,2,2,1,2,2,2,1,2,2,0,3,
        3,0,0,0,2,0,0,0,2,0,2,0,0,0,0,0,0,0,1,0,3,
        3,2,2,2,2,0,2,2,2,0,2,0,2,2,2,2,2,2,2,0,3,
        3,0,0,0,2,0,0,0,1,0,2,0,0,0,2,0,0,0,0,0,3,
        3,0,2,0,2,2,2,0,2,1,2,0,2,2,2,0,2,2,2,2,3,
        3,0,2,0,0,0,2,0,0,0,2,0,0,0,2,0,2,0,0,0,3,
        3,2,2,2,2,0,2,2,2,0,2,2,2,0,1,0,2,2,2,0,3,
        3,0,0,0,0,0,2,0,2,0,0,0,2,0,1,0,0,0,2,0,3,
        3,0,2,0,2,1,2,0,2,0,2,2,2,0,2,2,2,0,2,0,3,
        3,0,1,0,1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,3,
        3,0,2,1,2,0,2,2,2,2,2,0,2,0,2,0,2,2,2,2,3,
        3,0,0,0,0,0,0,0,0,0,0,0,2,0,2,0,0,0,0,0,3,
        3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3
    );

    private lateinit var mapElements: Array<MapElement?>

    fun create(mContext: Context) {
        mapElements = arrayOfNulls(mPassabilityMap.size + 1)

        val mesh1 = Mesh(
            "objs/chamfer_box.obj",
            "textures/light_object.png",
            mContext)

        val mesh2 = Mesh(
            "objs/box.obj",
            "textures/heavy_object.png",
            mContext)

        val mesh3 = Mesh(
            "objs/box.obj",
            "textures/border_object.png",
            mContext)

        var x = 0f
        var y = 0f

        for (i in mPassabilityMap.indices) {
            if (i % 21 == 0) {
                y++
                x = 0f
            }
            when (mPassabilityMap[i]) {
                1 -> {
                    mapElements[i] = MapElement(mesh1)
                }
                2 -> {
                    mapElements[i] = MapElement(mesh2)
                }
                3 -> {
                    mapElements[i] = MapElement(mesh3)
                }
            }
            mapElements[i]?.setPosition(x,0f,y)
            x++
        }

        val lPlane = MapElement(
            Mesh(
            "objs/simple_plane.obj",
            "textures/plane.jpg",
            mContext)
        )
        lPlane.setPosition(10f,-0.51f,11f)
        mapElements[mapElements.size-1] = lPlane
    }

    fun draw() {

    }
}