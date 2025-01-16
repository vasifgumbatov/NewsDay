package com.vasifgumbatov.news.ui.detail

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.databinding.FragmentBtcDetailBinding
import com.vasifgumbatov.news.databinding.FragmentBusinessDetailBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessDetail : CoreFragment<FragmentBusinessDetailBinding>() {
    private val maxLines = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBusinessDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.backToHome?.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        setUpNewsDetails(
            binding!!,
            arguments?.getString("author"),
            arguments?.getString("title"),
            arguments?.getString("imageUrl"),
            arguments?.getString("content"),
            arguments?.getString("publishedAt")
        )
    }

    private fun setUpNewsDetails(
        binding: FragmentBusinessDetailBinding,
        author: String?,
        title: String?,
        imageUrl: String?,
        content: String?,
        publishedAt: String?,
    ) {

        binding.newsAuthor.text = author
        binding.newsTitle.text = title
        binding.publishedTime.text = publishedAt
        binding.newsContent.text = content

        Glide.with(this)
            .load(imageUrl)
            .into(binding.newsImage)
        setUpViewMore(binding.newsContent)
    }

    private fun setUpViewMore(textView: TextView) {
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