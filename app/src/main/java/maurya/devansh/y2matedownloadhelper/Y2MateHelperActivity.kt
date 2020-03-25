package maurya.devansh.y2matedownloadhelper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Y2MateHelperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if (intent.type == "text/plain") {
                    val defaultBrowser = getDefaultBrowser()
                    openUrlInBrowser(getY2MateDownloaderUrl(
                        intent.getStringExtra(Intent.EXTRA_TEXT) ?: "").toString(),
                        defaultBrowser)
                }
            }
        }
        finish()
    }

    private fun openUrlInBrowser(url: String, browserPackageName: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage(browserPackageName)
        startActivity(intent)
    }

    private fun getY2MateDownloaderUrl(youtubeUrl: String): Uri {
        val videoId = Uri.parse(youtubeUrl).lastPathSegment

        return Uri.Builder()
            .scheme("https")
            .authority("www.y2mate.com")
            .appendPath("youtube")
            .appendPath(videoId)
            .build()
    }

    private fun getDefaultBrowser(): String {
        val prefs = getSharedPreferences(BrowserChooserActivity.BROWSER_PREF, Context.MODE_PRIVATE)
        return prefs.getString(BrowserChooserActivity.DEFAULT_BROWSER,
            "com.android.chrome") ?: "com.android.chrome"
    }
}
