package com.example.solidarity.ui.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.solidarity.ui.main_fragment.categories_fragment.CategoriesFragment
import com.example.solidarity.ui.main_fragment.donation_fragment.DonationFragment
import com.example.solidarity.ui.main_fragment.profile_fragment.ProfileFragment
import com.example.solidarity.ui.main_fragment.request_fragment.RequestsFragment

class BottomNavAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                CategoriesFragment()
            }
            1 -> {
                RequestsFragment()
            }
            2 -> {
                DonationFragment()
            }
            3 -> {
                ProfileFragment()
            }

            else ->{throw Resources.NotFoundException("not found")}
        }
    }


}