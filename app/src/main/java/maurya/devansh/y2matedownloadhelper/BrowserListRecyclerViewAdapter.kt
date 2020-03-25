package maurya.devansh.y2matedownloadhelper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Devansh on 25/3/20
 */

class BrowserListRecyclerViewAdapter(private val browsersList: List<String>)
    : RecyclerView.Adapter<BrowserItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowserItemViewHolder =
        BrowserItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_browser_list, parent, false))

    override fun getItemCount(): Int = browsersList.size

    override fun onBindViewHolder(holder: BrowserItemViewHolder, position: Int) {
        holder.bind(browsersList[position])
    }
}