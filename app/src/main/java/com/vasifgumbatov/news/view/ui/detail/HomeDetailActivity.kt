package com.vasifgumbatov.news.view.ui.detail

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.databinding.ActivityNewsDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeDetailActivity @Inject constructor() : AppCompatActivity() {

    private var binding: ActivityNewsDetailBinding? = null
    private val maxLines = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val author = intent.getStringExtra("author")
        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")
        val description = intent.getStringExtra("description")
        val content = intent.getStringExtra("content")
        val url = intent.getStringExtra("url")

        binding?.newsAuthor?.text = author
        binding?.newsTitle?.text = title
        binding?.newsDescription?.text = description
        binding?.newsContent?.text = content
        binding?.newsUrl?.text = url

        Glide.with(this)
            .load(imageUrl)
            .into(binding?.newsImage!!)

        setupViewMore(binding?.newsContent!!)
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