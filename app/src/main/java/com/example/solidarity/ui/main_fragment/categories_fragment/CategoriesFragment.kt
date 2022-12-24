package com.example.solidarity.ui.main_fragment.categories_fragment


import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.solidarity.common.BaseFragment
import com.example.solidarity.data.model.Categories
import com.example.solidarity.data.model.categoriesList
import com.example.solidarity.databinding.FragmentCategoriesBinding
import com.example.solidarity.ui.adapters.CategoriesAdapter

class CategoriesFragment :
    BaseFragment<FragmentCategoriesBinding>(FragmentCategoriesBinding::inflate) {

    private val categoriesAdapter: CategoriesAdapter by lazy { CategoriesAdapter() }

    override fun viewCreated() {
        populateList()
        setupRecyclerView()
        categoriesAdapter.submitList(categoriesList)

    }

    override fun listeners() {
       categoriesAdapter.setOnItemClickListener { categories, i ->
           findNavController().navigate(CategoriesFragmentDirections.actionCategoriesFragmentToMapsFragment2())
       }
    }


    private fun setupRecyclerView() {
        binding.rvCategories.apply {
            adapter = categoriesAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }

    private fun populateList(){
        categoriesList.add(
            Categories("Food, Medicines, Clothing, ChildCare Supplies",
                "Help people  in life-threading conditions and assist with essential supplies such as first aid,  tissues,  diapers, sanitizers, etc.")
        )
        categoriesList.add(
            Categories("Food, Medicines, Clothing, ChildCare Supplies",
                "Help people  in life-threading conditions and assist with essential supplies such as first aid,  tissues,  diapers, sanitizers, etc.")
        )
        categoriesList.add(
            Categories("Food, Medicines, Clothing, ChildCare Supplies",
                "Help people  in life-threading conditions and assist with essential supplies such as first aid,  tissues,  diapers, sanitizers, etc.")
        )
    }


}