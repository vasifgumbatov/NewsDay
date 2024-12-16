package com.vasifgumbatov.news.view.ui.detail

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.databinding.ActivityFavoriteDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteDetailActivity @Inject constructor() : AppCompatActivity() {
    private var binding: ActivityFavoriteDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val author = intent.getStringExtra("author")
        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")
        val description = intent.getStringExtra("description")
        val url = intent.getStringExtra("url")
        val content = intent.getStringExtra("content")

        binding?.newsAuthor?.text = author
        binding?.newsTitle?.text = title
        binding?.newsDescription?.text = description
        binding?.newsContent?.text = content
        binding?.newsUrl?.text = url

        Glide.with(this)
            .load(imageUrl)
            .into(binding?.newsImage!!)
    }
}