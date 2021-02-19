package hu.bme.aut.kingaslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.kingaslist.R
import hu.bme.aut.kingaslist.data.AdItem

class AdAdapter(private val listener: AdItemClickListener) :
    RecyclerView.Adapter<AdAdapter.AdViewHolder>() {

    private val items = mutableListOf<AdItem>()
    private val itemsCopy = mutableListOf<AdItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_ad_list, parent, false)

        return AdViewHolder(itemView)

    }
    override fun getItemCount(): Int {
        return items.size
    }

    interface AdItemClickListener {
        fun onAdSelected(item: AdItem?)
    }

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val iconImageView: ImageView
        val titleTextView: TextView
        val categoryTextView: TextView
        var item: AdItem? = null

        init {
            itemView.setOnClickListener { listener?.onAdSelected(item) }
            iconImageView = itemView.findViewById(R.id.AdItemIconImageView)
            titleTextView = itemView.findViewById(R.id.AdItemNameTextView)
            categoryTextView = itemView.findViewById(R.id.AdItemCategoryTextView)
        }
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.text = item.title
        holder.categoryTextView.text = item.category.name
        holder.iconImageView.setImageResource(getImageResource(item.category))

        holder.item = item
    }
     @DrawableRes
     private fun getImageResource(category: AdItem.Category) = when (category) {

         AdItem.Category.BOOK -> R.drawable.open_book
         AdItem.Category.NONE -> R.drawable.open_book
         AdItem.Category.ELECTRONIC -> R.drawable.lightning
         AdItem.Category.FOOD -> R.drawable.groceries
         AdItem.Category.JOB -> R.drawable.suitcase
         AdItem.Category.HOUSING -> R.drawable.house
         AdItem.Category.ROMANCE -> R.drawable.bouquet
         AdItem.Category.PET -> R.drawable.dog

     }


    fun addItem(item: AdItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun filter(text: String) {

        var text = text
        items.clear()
        if (text.isEmpty()) {
            items.addAll(itemsCopy)
        } else {
            text = text.toLowerCase()
            for (item in itemsCopy) {
                if (item.title.toLowerCase().contains(text)) {
                    items.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun filterCategory(pos: Int) {
        items.clear()
        if (pos==0) {
            items.addAll(itemsCopy)
        } else {
            for (item in itemsCopy) {
                if (item.category.equals(AdItem.Category.getByOrdinal(pos))) {
                    items.add(item)
                }
            }
        }
        notifyDataSetChanged()

    }

    fun update(shoppingItems: List<AdItem>) {
        items.clear()
        items.addAll(shoppingItems)
        itemsCopy.addAll(items)
        notifyDataSetChanged()
    }
}
