package com.example.solidarity.ui.main_fragment



import androidx.viewpager2.widget.ViewPager2
import com.example.solidarity.R
import com.example.solidarity.common.BaseFragment
import com.example.solidarity.databinding.FragmentMainBinding
import com.example.solidarity.ui.adapters.BottomNavAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout



    override fun viewCreated() {
        setupTabLayout()
    }

    override fun listeners() {

    }

    private fun setupTabLayout() {
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
        viewPager.isUserInputEnabled = false
        viewPager.adapter = BottomNavAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager) { tab, index ->
            tab.text = when (index) {
                0 -> "Categories"
                1 -> "Request"
                2 -> "Donation"
                3 -> "Profile"
                else -> "Tab Not Found"
            }
        }.attach()
        setupTabIcons()
    }

    private fun setupTabIcons() {
        tabLayout.getTabAt(0)?.setIcon(R.drawable.categories)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.request)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.donation)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.profile)
        tabLayout.getTabAt(4)?.setIcon(R.drawable.notification)
    }


}