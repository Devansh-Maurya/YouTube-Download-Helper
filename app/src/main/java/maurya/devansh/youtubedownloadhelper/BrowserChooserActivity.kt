package maurya.devansh.youtubedownloadhelper

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*

class BrowserChooserActivity : AppCompatActivity() {

    companion object {
        const val BROWSER_PREF = "browser_pref"
        const val DEFAULT_BROWSER = "default_browser"
        const val FIRST_LAUNCH = "first_launch"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
        val browserResolverInfoList = packageManager.queryIntentActivities(browserIntent,
            PackageManager.MATCH_ALL)

        recyclerView.adapter =
            BrowserListRecyclerViewAdapter(makeBrowserPackageNamesList(browserResolverInfoList)) {
                createConfirmationDialog(it)
        }

        if (isFirstLauch){
            showInfoDialog()
            isFirstLauch = false
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

    private fun createConfirmationDialog(packageName: String) {
        val dialogMessage = buildSpannedString {
            append("Do you want to set ")
            bold {
                append(getBrowserName(packageName))
            }
            append(" as the default browser for downloading videos ?")
        }

        val dialogTitleBold = buildSpannedString {
            bold {
                append("Set default browser")
            }
        }

        MaterialAlertDialogBuilder(this)
            .setTitle(dialogTitleBold)
            .setMessage(dialogMessage)
            .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                setDefaultBrowser(packageName)
                recyclerView.adapter?.notifyDataSetChanged()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun getBrowserName(packageName: String): CharSequence {
        val pm = packageManager
        val applicationInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        return pm.getApplicationLabel(applicationInfo)
    }

    private fun showInfoDialog() {

        val dialogTitleBold = buildSpannedString {
            bold {
                append("Welcome!")
            }
        }

        MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
            .setTitle(dialogTitleBold)
            .setMessage(R.string.introduction)
            .setPositiveButton("Ok",null)
            .show()
    }

    private var isFirstLauch : Boolean
        get() {
            val prefs = getSharedPreferences(BROWSER_PREF,Context.MODE_PRIVATE)
            return prefs.getBoolean(FIRST_LAUNCH, true)
        }
        set(value) {
            val prefs = getSharedPreferences(BROWSER_PREF,Context.MODE_PRIVATE)
            prefs.edit {
                putBoolean(FIRST_LAUNCH, value)
            }
        }
}
