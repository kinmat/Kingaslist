package hu.bme.aut.kingaslist.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.room.Room
import hu.bme.aut.kingaslist.R
import hu.bme.aut.kingaslist.data.AdItem
import hu.bme.aut.kingaslist.data.AdListDatabase
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private var itemID: Int? = null
    private var itemTitle: String? = null;
    private var itemDesc: String? = null;
    private var itemPrice: Int? = null;
    private var itemContact: String? = null;
    private var itemCategory: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        itemID=intent.getIntExtra(EXTRA_AD_ID, 0);
        itemTitle=intent.getStringExtra(EXTRA_AD_TITLE);
        itemDesc=intent.getStringExtra(EXTRA_AD_DESC);
        itemPrice=intent.getIntExtra(EXTRA_AD_PRICE, 0);
        itemContact=intent.getStringExtra(EXTRA_AD_CONTACT);
        itemCategory=intent.getStringExtra(EXTRA_AD_CATEGORY);

        AdItemTitleDetView.text=itemTitle;
        AdItemContactDetView.text=getString(R.string.contact_details,itemContact)
        AdItemDescriptionDetView.text=getString(R.string.desc_details,itemDesc)
        AdItemPriceDetView.text=getString(R.string.price_details,itemPrice.toString())

    }

    companion object {
        private const val TAG = "DetailsActivity"
        const val EXTRA_AD_ID = "extra.ad_id"
        const val EXTRA_AD_TITLE = "extra.ad_title"
        const val EXTRA_AD_DESC = "extra.ad_desc"
        const val EXTRA_AD_PRICE = "extra.ad_price"
        const val EXTRA_AD_CATEGORY = "extra.ad_category"
        const val EXTRA_AD_CONTACT = "extra.ad_contact"
    }
}

