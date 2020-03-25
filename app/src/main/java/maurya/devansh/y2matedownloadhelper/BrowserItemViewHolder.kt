package maurya.devansh.y2matedownloadhelper

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_browser_list.view.*

/**
 * Created by Devansh on 25/3/20
 */

class BrowserItemViewHolder(private val view: View, private val clickAction: (String) -> Unit) : RecyclerView.ViewHolder(view) {

    fun bind(packageName: String) {
        view.apply {
            browserIconView.setImageDrawable(getBrowserIcon(packageName))

            val browserName = getBrowserName(packageName)
            browserNameTV.text = browserName
            setOnClickListener {
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
}