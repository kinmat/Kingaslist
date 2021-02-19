package hu.bme.aut.kingaslist.fragments

import android.app.Dialog
import android.content.Context
import android.opengl.ETC1.isValid
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.internal.ViewUtils.getContentView
import hu.bme.aut.kingaslist.R
import hu.bme.aut.kingaslist.data.AdItem

class NewAdItemDialogFragment : DialogFragment() {
    interface NewAdItemDialogListener {
        fun onAdItemCreated(newItem: AdItem)
    }

    private lateinit var listener: NewAdItemDialogListener
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var contactEditText: EditText
    private lateinit var categorySpinner: Spinner

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewAdItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewAdItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_ad_item)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onAdItemCreated(getAdItem())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }
    private fun isValid() = titleEditText.text.isNotEmpty()

    private fun getAdItem() = AdItem(
        id = null,
        title = titleEditText.text.toString(),
        description = descriptionEditText.text.toString(),
        contact=contactEditText.text.toString(),
        price = try {
            priceEditText.text.toString().toInt()
        } catch (e: java.lang.NumberFormatException) {
            0
        },
        category = AdItem.Category.getByOrdinal(categorySpinner.selectedItemPosition)
            ?: AdItem.Category.BOOK
    )

    private fun getContentView(): View {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.dialog_new_ad_item, null)
        titleEditText = contentView.findViewById(R.id.AdItemTitleEditText)
        descriptionEditText = contentView.findViewById(R.id.AdItemDescriptionEditText)
        priceEditText = contentView.findViewById(R.id.AdItemPriceEditText)
        contactEditText=contentView.findViewById(R.id.AdItemContactEditText)
        categorySpinner = contentView.findViewById(R.id.AdItemCategorySpinner)
        categorySpinner.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.category_items)
            )
        )
        return contentView
    }

    companion object {
        const val TAG = "NewAdItemDialogFragment"
    }
}