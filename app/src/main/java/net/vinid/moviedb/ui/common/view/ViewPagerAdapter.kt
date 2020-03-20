package net.vinid.moviedb.ui.common.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by Nguyen Van Lieu on 2/21/2020.
 */
class ViewPagerAdapter (fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = ArrayList<Fragment>()
    private val titleFragmentList = ArrayList<String>()

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = titleFragmentList[position]

    fun addFragment(fragment: Fragment, title:String){
        fragmentList.add(fragment)
        titleFragmentList.add(title)
    }
}
