package com.skillbox.fragment.viewpager

import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.skillbox.fragment.R
import com.skillbox.fragment.withArguments
import kotlinx.android.synthetic.main.fragment_pages.*

class SetPagesFragment : Fragment(R.layout.fragment_pages) {

    val tags: List<ArticleTag> =
        listOf(ArticleTag.Waterfalls, ArticleTag.Rivers, ArticleTag.Mountains, ArticleTag.Bays)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageViewPages.setImageResource(requireArguments().getInt(KEY_IMAGE))
        textViewHeader.setText(requireArguments().getInt(KEY_NAME))
        textViewArticle.setText(requireArguments().getInt(KEY_ARTICLE))
        requireView().setBackgroundResource(requireArguments().getInt(KEY_COLOR))

            generateButton.setOnClickListener {
                 button()
            }

    }

    companion object {

        private lateinit var button: () -> Unit

        private const val KEY_ARTICLE = "article"
        private const val KEY_NAME = "name"
        private const val KEY_COLOR = "color"
        private const val KEY_IMAGE = "image"

        fun newInstancePage(
            @StringRes articleRes: Int,
            @StringRes nameRes: Int,
            @ColorRes bgColorRes: Int,
            @DrawableRes drawableRes: Int,
            action: () -> Unit
        ): SetPagesFragment {
            return SetPagesFragment().withArguments {
                putInt(KEY_ARTICLE, articleRes)
                putInt(KEY_NAME, nameRes)
                putInt(KEY_COLOR, bgColorRes)
                putInt(KEY_IMAGE, drawableRes)
                button = action
            }
        }
    }

}