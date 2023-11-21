package good.damn.opengles20game.components.entities

import android.content.Context
import good.damn.opengles20game.components.Mesh
import good.damn.opengles20game.components.entities.map.MapElement
import java.util.*

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

    private lateinit var mapElements: Array<MapElement>

    fun create(mContext: Context) {
        val elements = LinkedList<MapElement>()

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

        var x = mX
        var z = mZ

        for (i in mPassabilityMap.indices) {
            if (i % 21 == 0) {
                z++
                x = mX
            }

            lateinit var element:MapElement
            when (mPassabilityMap[i]) {
                0 -> {
                    x++
                    continue
                }
                1 -> element = MapElement(mesh1)
                2 -> element = MapElement(mesh2)
                3 -> element = MapElement(mesh3)
            }
            element.setPosition(x,mY,z)
            elements.add(element)
            x++
        }

        val lPlane = MapElement(
            Mesh(
            "objs/simple_plane.obj",
            "textures/plane.jpg",
            mContext)
        )
        lPlane.setPosition(mX+10f,mY-0.51f,mZ+11f)
        elements.add(lPlane)

        mapElements = elements.toTypedArray()
    }

    fun draw() {
        for (element in mapElements) {
            element.draw()
        }
    }
}