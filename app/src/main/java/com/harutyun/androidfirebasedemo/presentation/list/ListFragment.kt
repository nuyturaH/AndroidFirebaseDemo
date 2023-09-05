package com.harutyun.androidfirebasedemo.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.harutyun.androidfirebasedemo.R
import com.harutyun.androidfirebasedemo.databinding.FragmentListBinding
import com.harutyun.androidfirebasedemo.presentation.navigation.NavigationCommand
import com.harutyun.androidfirebasedemo.presentation.helpers.DividerItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
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

        observeState()

        observeNavigation()

        setupListRecyclerView()

        addListeners()

        handleBackButton()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listViewModel.uiState.collect { uiState ->
                    handleUiState(uiState)
                }
            }
        }
    }

    private fun handleUiState(uiState: ListUiState) {
        binding.apply {

            if (uiState.items.isNotEmpty()) {
                (rvList.adapter as ListItemAdapter).submitList(uiState.items)
                tvNoDataList.visibility = View.GONE
                rvList.visibility = View.VISIBLE
            } else {
                tvNoDataList.visibility = View.VISIBLE
                rvList.visibility = View.GONE
            }

            binding.tvBackList.visibility =
                if (uiState.isBackButtonVisible) View.VISIBLE else View.GONE

            if (uiState.toastMessage != 0) {
                Toast.makeText(context, getString(uiState.toastMessage), Toast.LENGTH_LONG).show()
                listViewModel.toastMessageIsShown()
            }

            if (uiState.restoredItemPosition != null)
            (binding.rvList.adapter as ListItemAdapter).notifyItemChanged(uiState.restoredItemPosition)
        }
    }

    private fun addListeners() {
        binding.fabAddList.setOnClickListener {
            showTextAddingDialog()
        }

        binding.tvProfileList.setOnClickListener {
            listViewModel.goToProfileFragment()
        }

        binding.tvBackList.setOnClickListener {
            listViewModel.navigateBack()
        }

        binding.switchList.setOnCheckedChangeListener { _, checked ->
            listViewModel.getItems(fromLocal = !checked)
        }
    }


    private val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            val position = viewHolder.adapterPosition
            listViewModel.removeItemRemote(position, !binding.switchList.isChecked)
        }
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

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvList)
    }

    private fun observeNavigation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listViewModel.navigation.collect { navigationCommand ->
                    handleNavigation(navigationCommand)
                }
            }
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions)
            is NavigationCommand.Back -> findNavController().navigateUp()
            is NavigationCommand.None -> {}
        }
        listViewModel.navigationClear()
    }

    private fun showTextAddingDialog() {
        val inflater = requireActivity().layoutInflater
        val customView = inflater.inflate(R.layout.dialog_add_text, null)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.text_adding_dialog_title))
            .setView(customView)
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.add)) { dialog, _ ->
                val editText: EditText = customView.findViewById(R.id.et_text_dialog)
                val text = editText.text.toString()
                if (text.isNotEmpty()) {
                    listViewModel.addItemRemote(text, !binding.switchList.isChecked)
                } else {
                    Toast.makeText(context, getString(R.string.text_is_empty), Toast.LENGTH_LONG).show()
                }
            }
            .show()
    }


    private fun handleBackButton() {
        listViewModel.handleBackButton(findNavController().previousBackStackEntry)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}