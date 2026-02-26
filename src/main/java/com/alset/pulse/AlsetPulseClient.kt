
import okhttp3.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class AlsetPulseClient(private val url: String) {
    private val client = OkHttpClient()
    private var isReconnecting = false

    fun connect(): Flow<Pair<String, Any>> = flow {
        while (true) {
            try {
                val request = Request.Builder()
                    .url(url)
                    .post(RequestBody.create("application/alset-pulse".toMediaType(), "{}"))
                    .build()

                client.newCall(request).execute().use { response ->
                    val reader = BufferedReader(InputStreamReader(response.body!!.byteStream()))
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        try {
                            val json = JSONObject(line)
                            val target = json.getString("target")
                            val data = json.get("data")
                            if (target != "hb") { // ignorar heartbeat
                                emit(target to data)
                            }
                        } catch (e: Exception) {
                            // Ignorar pulsos corruptos
                        }
                    }
                }
            } catch (e: Exception) {
                if (!isReconnecting) {
                    isReconnecting = true
                    println("📡 Resintonizando señal Alset...")
                    delay(1000) // Reintento en 1 segundo
                    isReconnecting = false
                }
            }
        }
    }
}
