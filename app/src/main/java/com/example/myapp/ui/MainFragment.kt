package com.example.myapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.common.UIState
import com.example.myapp.databinding.FragmentMainBinding
import com.example.myapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        collector()
        binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = MainAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        adapter.setOnItemClickListener {
            Toast.makeText(this@MainFragment.requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun collector() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mainItem.collect {
                    when(it) {
                        is UIState.Success -> {
                            binding.progress.visibility = View.GONE
                            binding.error.visibility = View.GONE
                            binding.rv.visibility = View.VISIBLE
                            adapter.setItems(it.data)
                        }

                        is UIState.Failure -> {
                            binding.progress.visibility = View.GONE
                            binding.error.visibility = View.VISIBLE
                            binding.rv.visibility = View.GONE
                            binding.error.text = it.throwable.toString()
                        }

                        is UIState.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                            binding.error.visibility = View.GONE
                            binding.rv.visibility = View.GONE
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "MainFragment"
        fun newInstance(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private fun openExtraFragmentFromMainFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, ExtraFragment.newInstance(), ExtraFragment.TAG)
            .commit()
    }

    private var isLoading = false
    private fun manualPagination() {
        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val total = recyclerView.layoutManager?.itemCount
                val firstItemPos = (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                val visItems = recyclerView.layoutManager?.childCount
                if (total != null && firstItemPos != null && visItems != null) {
                    if (visItems + firstItemPos >= total && !isLoading) {
                        isLoading = true
//                        mainViewModel.loadNext() //maintain page count in viewmodel and loadNext, make isLoading false after received
                    }
                }
            }
        })
    }

}