package sample.com.matches.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import sample.com.matches.util.Constants
import sample.com.matches.views.ListFragment

class ViewPagerAdapter(fragmentManager:FragmentManager):FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        val fragment = ListFragment()
        fragment.position = position
        return fragment
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        when (position){
            0 -> return "Special Blend"
            1 -> return "Matches %"
        }
        return ""
    }
}