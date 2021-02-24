package com.skillbox.fragment.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SetPagesAdapter(
    private val resources: List<SetPagesRes>,
    activity: Fragment,
    private val button: () -> Unit
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return resources.size
    }

    override fun createFragment(position: Int): Fragment {
        val res:SetPagesRes = resources[position]
        return SetPagesFragment.newInstancePage(
            articleRes = res.articleRes,
            nameRes = res.nameRes,
            bgColorRes = res.bgColorRes,
            drawableRes = res.drawableRes,
            action = button)
    }
}