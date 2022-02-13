package com.joten.timetrack.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.joten.timetrack.databinding.MainFragmentBinding
import com.joten.timetrack.util.formatted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startTimingBtn.setOnClickListener { mainViewModel.startTiming() }
        mainViewModel.duration.observe(viewLifecycleOwner) { d ->
            if (d != null) {
                binding.messageTxt.text = d.formatted()
            }
        }
    }

}