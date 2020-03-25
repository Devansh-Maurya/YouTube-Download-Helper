package maurya.devansh.y2matedownloadhelper

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*

class BrowserChooserActivity : AppCompatActivity() {

    companion object {
        const val BROWSER_PREF = "browser_pref"
        const val DEFAULT_BROWSER = "default_browser"
    }

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

        recyclerView.adapter =
            BrowserListRecyclerViewAdapter(makeBrowserPackageNamesList(browserResolverInfoList)) {
                setDefaultBrowser(it)
        }
    }

    private fun makeBrowserPackageNamesList(resolverInfoList: List<ResolveInfo>): List<String> {
        val browserPackageNamesList = ArrayList<String>()

        resolverInfoList.forEach {
            browserPackageNamesList.add(it.activityInfo.packageName)
        }
        browserPackageNamesList.sort()

        return browserPackageNamesList
    }

    private fun setDefaultBrowser(packageName: String) {
        val prefs = getSharedPreferences(BROWSER_PREF, Context.MODE_PRIVATE)
        prefs.edit {
            putString(DEFAULT_BROWSER, packageName)
        }
    }
}
