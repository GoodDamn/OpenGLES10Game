package good.damn.opengles20game.loader

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.IntBuffer
import java.util.*

final class FileOBJ {

    val mNormals: FloatArray
    var mTexCoords: FloatArray
    var mPositions: FloatArray
    var mIndices: IntArray

    constructor(context: Context,
                fileName: String?) {

        val vertices: Vector<Float> = Vector()
        val normals: Vector<Float> = Vector()
        val textures: Vector<Float> = Vector()
        val faces: Vector<String> = Vector()

        var reader: BufferedReader? = null

        try {
            val inStream = InputStreamReader(
                fileName?.let {
                    context.assets.open(it)
            })
            reader = BufferedReader(inStream)

            // read file until EOF
            var line = reader.readLine()
            while (line != null) {
                val parts = line.split("\\s+".toRegex())
                when (parts[0]) {
                    "v" -> {
                        // vertices
                        vertices.add(parts[1].toFloat())
                        vertices.add(parts[2].toFloat())
                        vertices.add(parts[3].toFloat())
                    }
                    "vt" -> {
                        // textures
                        textures.add(parts[1].toFloat())
                        textures.add(parts[2].toFloat())
                    }
                    "vn" -> {
                        // normals
                        normals.add(parts[1].toFloat())
                        normals.add(parts[2].toFloat())
                        normals.add(parts[3].toFloat())
                    }
                    "f" -> {
                        // faces: vertex/texture/normal
                        faces.add(parts[1])
                        faces.add(parts[2])
                        faces.add(parts[3])
                    }
                }
                line = reader.readLine()
            }
        } catch (e: IOException) {
            // cannot load or read file
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    //log the exception
                }
            }
        }

        mNormals = FloatArray(faces.size * 3)
        mTexCoords = FloatArray(faces.size * 2)
        mPositions = FloatArray(faces.size * 3)
        mIndices = IntArray(faces.size)

        var posIndex = 0
        var normIndex = 0
        var texIndex = 0

        for (i in 0 until faces.size) {
            val parts = faces[i].split("/")
            val vertexIndex = parts[0].toInt() - 1
            mIndices[i] = vertexIndex

            var index = 3 * vertexIndex
            mPositions[posIndex++] = vertices[index++]
            mPositions[posIndex++] = vertices[index++]
            mPositions[posIndex++] = vertices[index]

            index = 2 * (parts[1].toInt() - 1)
            mTexCoords[normIndex++] = textures[index++]
            mTexCoords[normIndex++] = 1 - textures[index]

            index = 3 * (parts[2].toInt() - 1)
            mNormals[texIndex++] = normals[index++]
            mNormals[texIndex++] = normals[index++]
            mNormals[texIndex++] = normals[index]
        }
    }
}