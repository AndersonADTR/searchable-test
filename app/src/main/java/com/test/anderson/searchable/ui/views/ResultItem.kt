package com.test.anderson.searchable.ui.views

import com.test.anderson.searchable.R
import com.test.anderson.searchable.models.LfsItem
import com.test.anderson.searchable.models.VarsItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_result.view.*

class ResultItem(
    private val item: Any
) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.containerView.rootView?.let {
            when (item) {
                is LfsItem -> {
                    it.textView_lf.text = item.lf
                    it.textView_freq.append(item.freq.toString())
                }
                is VarsItem -> {
                    it.cardContainer.cardElevation = 2f
                    it.textView_lf.text = item.lf
                    it.textView_freq.append(item.freq.toString())
                }
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_result
    }

    fun getItem() = item
}