package com.harutyun.androidfirebasedemo.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.harutyun.androidfirebasedemo.R
import com.harutyun.androidfirebasedemo.databinding.FragmentListBinding
import com.harutyun.androidfirebasedemo.presentation.helpers.DividerItemDecorator
import com.harutyun.domain.models.Item


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val listViewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListRecyclerView()

    }

    private fun setupListRecyclerView() {
        val listAdapter = ListItemAdapter()
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter

            val dividerItemDecoration =
                DividerItemDecorator(ContextCompat.getDrawable(context, R.drawable.divider))
            addItemDecoration(dividerItemDecoration)
        }

        // For testing
        val list = mutableListOf(
            Item("asdf", "Text 1"),
            Item("arrdf", "Text 2"),
            Item("araardf", "Text 3"),
            Item("arr7df", "Text 4")
        )
        (binding.rvList.adapter as ListItemAdapter).submitList(list)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}