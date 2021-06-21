package com.test.anderson.searchable.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.anderson.searchable.R
import com.test.anderson.searchable.databinding.BottomSheetAnotherResultsBinding
import com.test.anderson.searchable.models.LfsItem
import com.test.anderson.searchable.ui.views.ResultItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

class AnotherResultsBottomSheetFragment: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetAnotherResultsBinding

    private var item: LfsItem? = null

    private val recyclerAdapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NORMAL,
            R.style.BottomSheetDialog
        )
        arguments?.let {
            item = it.getParcelable(ITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_another_results, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        val manager = LinearLayoutManager(context)

        recyclerAdapter.clear()

        binding.rvAcronymDetails.apply {
            layoutManager = manager
            adapter = recyclerAdapter
        }
        item?.vars.orEmpty().forEach {
            recyclerAdapter.add(ResultItem(it))
        }
    }

    companion object {

        val TAG: String = this::class.java.name

        internal const val ITEM = "item"

        @JvmStatic
        fun newInstance(item: LfsItem) =
            AnotherResultsBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ITEM, item)
                }
            }
    }
}