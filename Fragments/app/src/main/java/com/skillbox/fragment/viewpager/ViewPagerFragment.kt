package com.skillbox.fragment.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.fragment.R
import kotlinx.android.synthetic.main.fragment_viewpager.*
import java.util.*


class ViewPagerFragment : Fragment(R.layout.fragment_viewpager), ItemFilter {

    private var resources: ArrayList<SetPagesRes> = arrayListOf(
        SetPagesRes(
            articleRes = R.string.text1,
            nameRes = R.string.name1,
            bgColorRes = R.color.color1,
            drawableRes = R.drawable.drawable1,
            tags = listOf(ArticleTag.Rivers)
        ),
        SetPagesRes(
            articleRes = R.string.text2,
            nameRes = R.string.name2,
            bgColorRes = R.color.color2,
            drawableRes = R.drawable.drawable2,
            tags = listOf(ArticleTag.Mountains)
        ),
        SetPagesRes(
            articleRes = R.string.text3,
            nameRes = R.string.name3,
            bgColorRes = R.color.color3,
            drawableRes = R.drawable.drawable3,
            tags = listOf(ArticleTag.Waterfalls)
        ),
        SetPagesRes(
            articleRes = R.string.text4,
            nameRes = R.string.name4,
            bgColorRes = R.color.color4,
            drawableRes = R.drawable.drawable4,
            tags = listOf(ArticleTag.Bays)
        )
    )

    var filter = arrayOf(true, true, true, true).toBooleanArray()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            filter = savedInstanceState.getBooleanArray(KEY_FILTER) ?: error("")
            resources = savedInstanceState.getParcelableArrayList(KEY_LIST) ?: error("")
        }

        retainInstance = true
        getViewPager(resources)
        getTabLayout()
        getMenuItem()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayoutPager.getTabAt(position)?.removeBadge()
            }
        })
    }


    private fun getViewPager(res: List<SetPagesRes>) {
        var count = 1
        val adapter = SetPagesAdapter(res, this) {
            tabLayoutPager.getTabAt(Random().nextInt(res.size))!!.orCreateBadge.apply {
                number = count++
                badgeGravity = BadgeDrawable.TOP_END
            }
        }
        viewPager.adapter = adapter
        dots_indicator.setViewPager2(viewPager)
        viewPager.setPageTransformer(DepthTransformation())

    }

    private fun getTabLayout() {
        TabLayoutMediator(tabLayoutPager, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Реки"
                1 -> tab.text = "Горы"
                2 -> tab.text = "Водопад"
                3 -> tab.text = "Бухты"
            }
        }.attach()
    }

    private fun getTabLayoutFilter() {
        TabLayoutMediator(tabLayoutPager, viewPager) { tab, position ->
            if (position > -1) {
                tab.text = "Статья ${position + 1}"
            }
        }.attach()
    }


    private fun getMenuItem() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_1 -> {
                    FilterDialogFragment.newInstanceDialog(filter)
                        .show(childFragmentManager, KEY_FRAGMENT)
                    true
                }
                else -> false
            }
        }
    }

    override fun onItemFilter(checked: BooleanArray) {
        val filterRes = arrayListOf<SetPagesRes>()
        resources.forEach { res ->
            res.tags.forEach { tag ->
                if (tag == ArticleTag.valueOf(tag.name)) {
                    if (checked[tag.ordinal]) {
                        filterRes.add(res)
                    }
                }
            }
        }
        getViewPager(filterRes)
        getTabLayoutFilter()
        resources = filterRes
        filter = checked
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBooleanArray(KEY_FILTER, filter)
        outState.putParcelableArrayList(KEY_LIST, resources)
    }


    companion object {
        const val KEY_FRAGMENT = "fragment"
        const val KEY_FILTER = "filter"
        const val KEY_LIST = "list"
    }
}

