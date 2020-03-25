package maurya.devansh.y2matedownloadhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_y2mate_helper.*

class Y2MateHelperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_y2mate_helper)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if (intent.type == "text/plain") {
                    Log.d("Helper", intent.toString())
                    textView.text = intent.getStringExtra(Intent.EXTRA_TEXT)
                    openUrlInBrowser(intent.getStringExtra(Intent.EXTRA_TEXT) ?: "")
                }
            }
        }
    }

    private fun openUrlInBrowser(url: String) {

    }
}
