package maurya.devansh.y2matedownloadhelper

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
        val browsersList = packageManager.queryIntentActivities(browserIntent,
            PackageManager.MATCH_ALL)

        val listString = StringBuilder()
        browsersList.forEach {
            val packageName = it.activityInfo.packageName
            listString.append("$packageName\n")
        }

        textView.text = listString
    }
}
