package good.damn.opengles20game.loader

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

final class FileOBJ {

    var mNormals: FloatArray
    var mTexCoords: FloatArray
    var mVertices: FloatArray
    var mIndices: ShortArray

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
        mVertices = FloatArray(faces.size * 3)
        mIndices = ShortArray(faces.size)

        var texIndex = 0
        var posIndex = 0
        var normIndex = 0

        for ((index, face) in faces.withIndex()) {
            val parts = face.split("/")
            val vertexIndex = (parts[0].toShort() - 1).toShort()
            mIndices[index] = index.toShort()

            var i = 3 * vertexIndex
            mVertices[posIndex++] = vertices[i++]
            mVertices[posIndex++] = vertices[i++]
            mVertices[posIndex++] = vertices[i]

            i = 2 * (parts[1].toInt() - 1)
            mTexCoords[texIndex++] = textures[i++]
            mTexCoords[texIndex++] = 1 - textures[i]

            i = 3 * (parts[2].toInt() - 1)
            mNormals[normIndex++] = normals[i++]
            mNormals[normIndex++] = normals[i++]
            mNormals[normIndex++] = normals[i]
        }
    }

}