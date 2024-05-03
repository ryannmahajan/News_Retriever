package com.ryannm.newsretriever.repository

import android.util.Log
import com.google.gson.Gson
import com.ryannm.newsretriever.model.NewsItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class NewsRepository {

    companion object {
        suspend fun get():List<NewsItemModel> {
            return withContext(Dispatchers.IO) {
                val url =
                    URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
                val conn = url.openConnection() as HttpsURLConnection

                // Step 2: Configure the Connection
                conn.requestMethod = "GET" // Set the request method
                conn.setRequestProperty("Accept", "application/json") // Set the Accept header

                // Step 3: Open InputStream to Connection
                conn.connect()
                val inputStream = conn.inputStream

                // Step 4: Read the Response
                val response = StringBuilder()
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                // Step 5: Close the Connection
                inputStream.close()
                conn.disconnect()

                // Print the response
                val a = (response.toString())
                Log.d("NewsRepository", a)

                Gson().fromJson(a, NewsResponse::class.java).articles
            }
        }
    }

    data class NewsResponse(
        val status:String,
        val articles: List<NewsItemModel>
    )

}