package mockData

import android.content.Context
import com.google.gson.Gson
import io.reactivex.Single
import java.io.InputStream

class ParseMockData {

    @Throws(Exception::class)
    fun <T> parseMockData(context: Context, filePath: String, modelObject: Class<T>): T? {
        val stream = getStream(context, filePath)
        val jsonResponse = stream.bufferedReader().use { it.readText() }
        val responses = Gson().fromJson(jsonResponse, modelObject)
        stream.close()
        return responses
    }

    private fun getStream(context: Context, filePath: String) : InputStream {
        return context.assets.open(filePath)
    }
}