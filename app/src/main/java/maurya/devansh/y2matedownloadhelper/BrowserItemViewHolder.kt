package maurya.devansh.y2matedownloadhelper

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_browser_list.view.*

/**
 * Created by Devansh on 25/3/20
 */

class BrowserItemViewHolder(private val view: View, private val clickAction: (String) -> Unit) : RecyclerView.ViewHolder(view) {

    fun bind(packageName: String) {
        view.apply {

            if (packageName == getDefaultBrowserPackageName()) {
                val colorPrimary = ContextCompat.getColor(view.context, R.color.colorPrimary)
                setCardAppearance(colorPrimary, Color.WHITE)
            } else {
                setCardAppearance(Color.WHITE, Color.BLACK)
            }

            browserIconView.setImageDrawable(getBrowserIcon(packageName))

            val browserName = getBrowserName(packageName)
            browserNameTV.text = browserName
            setOnClickListener {
                if (browserName != getBrowserName(getDefaultBrowserPackageName()))
                    clickAction(packageName)
            }
        }
    }

    private fun getBrowserIcon(packageName: String) : Drawable =
        view.context.packageManager.getApplicationIcon(packageName)

    private fun getBrowserName(packageName: String): CharSequence {
        val pm = view.context.packageManager
        val applicationInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        return pm.getApplicationLabel(applicationInfo)
    }

    private fun getDefaultBrowserPackageName(): String {
        val prefs = view.context.getSharedPreferences(BrowserChooserActivity.BROWSER_PREF, Context.MODE_PRIVATE)
        return prefs.getString(BrowserChooserActivity.DEFAULT_BROWSER,
            "com.android.chrome") ?: "com.android.chrome"
    }

    private fun setCardAppearance(backgroundColor: Int, textColor: Int) {
        (view as CardView).apply {
            setCardBackgroundColor(backgroundColor)
            browserNameTV.setTextColor(textColor)
        }
    }
}