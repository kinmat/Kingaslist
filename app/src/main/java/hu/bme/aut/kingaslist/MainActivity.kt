package hu.bme.aut.kingaslist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import hu.bme.aut.kingaslist.adapter.AdAdapter
import hu.bme.aut.kingaslist.data.AdItem
import hu.bme.aut.kingaslist.data.AdListDatabase
import hu.bme.aut.kingaslist.details.DetailsActivity
import hu.bme.aut.kingaslist.fragments.NewAdItemDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), AdAdapter.AdItemClickListener,NewAdItemDialogFragment.NewAdItemDialogListener {
    private lateinit var adapter: AdAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: AdListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

       database = Room.databaseBuilder(
            applicationContext,
            AdListDatabase::class.java,
            "ad-list"
        ).fallbackToDestructiveMigration().build()

        fab.setOnClickListener{
            NewAdItemDialogFragment().show(
                supportFragmentManager,
                NewAdItemDialogFragment.TAG
            )
        }
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        var menuitemm =menu!!.findItem(R.id.categoryFilter);
        var categorySpinner = menuitemm.actionView as Spinner
          categorySpinner.setAdapter(
              ArrayAdapter(
                  this,
                  android.R.layout.simple_spinner_dropdown_item,
                  resources.getStringArray(R.array.category_items)
              )
          )

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                adapter.filterCategory(position)
            }

        }


        var menuItem = menu!!.findItem(R.id.searchView)
        var searchView = menuItem.actionView as SearchView
        searchView.maxWidth=Int.MAX_VALUE


        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    adapter.filter(query)
                }
                return true;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filter(newText)
                };
                return true;
            }

        })


        return true
    }

    private fun initRecyclerView() {
        recyclerView = MainRecyclerView
        adapter = AdAdapter(this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.AdItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onAdSelected(item: AdItem?) {
       val showDetailsIntent = Intent()
        showDetailsIntent.setClass(this@MainActivity, DetailsActivity::class.java)
        if (item != null) {
            showDetailsIntent.putExtra(DetailsActivity.EXTRA_AD_ID, item.id)
            showDetailsIntent.putExtra(DetailsActivity.EXTRA_AD_TITLE, item.title)
            showDetailsIntent.putExtra(DetailsActivity.EXTRA_AD_DESC, item.description)
            showDetailsIntent.putExtra(DetailsActivity.EXTRA_AD_PRICE, item.price)
            showDetailsIntent.putExtra(DetailsActivity.EXTRA_AD_CATEGORY, item.category)
            showDetailsIntent.putExtra(DetailsActivity.EXTRA_AD_CONTACT, item.contact)

        }
        startActivity(showDetailsIntent)


    }

    override fun onAdItemCreated(newItem: AdItem) {
        thread {
            val newId = database.AdItemDao().insert(newItem)
            val newShoppingItem = newItem.copy(
                id = newId
            )
            runOnUiThread {
                adapter.addItem(newShoppingItem)
            }
        }
    }

}
