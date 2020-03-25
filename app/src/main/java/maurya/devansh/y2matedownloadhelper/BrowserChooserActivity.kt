package maurya.devansh.y2matedownloadhelper

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class BrowserChooserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
        val browserResolverInfoList = packageManager.queryIntentActivities(browserIntent,
            PackageManager.MATCH_ALL)

        val listString = StringBuilder()
        browserResolverInfoList.forEach {
            val packageName = it.activityInfo.packageName
            listString.append("$packageName\n")
        }

        recyclerView.adapter = BrowserListRecyclerViewAdapter(makeBrowserPackageNamesList(browserResolverInfoList))
    }

    private fun makeBrowserPackageNamesList(resolverInfoList: List<ResolveInfo>): List<String> {
        val browserPackageNamesList = ArrayList<String>()

        resolverInfoList.forEach {
            browserPackageNamesList.add(it.activityInfo.packageName)
        }

        return browserPackageNamesList
    }
}
