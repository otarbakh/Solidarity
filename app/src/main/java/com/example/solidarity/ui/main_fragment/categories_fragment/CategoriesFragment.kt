package com.example.solidarity.ui.main_fragment.categories_fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.solidarity.R
import com.example.solidarity.common.BaseFragment
import com.example.solidarity.databinding.FragmentCategoriesBinding

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>(FragmentCategoriesBinding::inflate) {
    override fun viewCreated() {

    }

    override fun listeners() {
        binding.mapTest.setOnClickListener {
            findNavController().navigate(CategoriesFragmentDirections.actionCategoriesFragmentToMapsFragment2())
        }
    }


}