package com.vasifgumbatov.news.ui.detail

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.databinding.FragmentFavoriteDetailBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteDetail : CoreFragment<FragmentFavoriteDetailBinding>() {

    private val maxLines = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.backToFavorite?.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        setupNewsDetails(
            binding!!,
            arguments?.getString("author"),
            arguments?.getString("title"),
            arguments?.getString("imageUrl"),
            arguments?.getString("content"),
            arguments?.getString("description"),
            arguments?.getString("publishedAt")
        )
    }

    private fun setupNewsDetails(
        binding: FragmentFavoriteDetailBinding,
        author: String?,
        title: String?,
        imageUrl: String?,
        content: String?,
        description: String?,
        publishedAt: String?
    ) {

        binding.newsAuthor.text = author
        binding.newsTitle.text = title
        binding.publishedTime.text = publishedAt
        binding.newsContent.text = content
        binding.newsDescription.text = description

        // NEW: Combine description and content
        val combinedText = "${description ?: ""}\n\n${content ?: ""}"
        binding.newsContent.text = combinedText

        Glide.with(this)
            .load(imageUrl)
            .into(binding.newsImage)

        setupViewMore(binding.newsContent)
    }

    private fun setupViewMore(textView: TextView) {
        val originalText = textView.text.toString()
        textView.text = getTrimmedTextWithViewMore(originalText)
        textView.setOnClickListener {
            if (textView.maxLines == maxLines) {
                textView.text = originalText
                textView.maxLines = Int.MAX_VALUE
            } else {
                textView.text = getTrimmedTextWithViewMore(originalText)
                textView.maxLines = maxLines
            }
        }
    }

    private fun getTrimmedTextWithViewMore(text: String): SpannableString {
        val trimmedText = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        val spannableString = SpannableString("$trimmedText... View More")
        val viewMoreClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
            }
        }
        spannableString.setSpan(
            viewMoreClickable,
            spannableString.length - 9,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }
}