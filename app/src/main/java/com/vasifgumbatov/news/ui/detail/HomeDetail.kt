package com.vasifgumbatov.news.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
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
import com.vasifgumbatov.news.databinding.FragmentHomeDetailBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class HomeDetail : CoreFragment<FragmentHomeDetailBinding>() {
    private val maxLines = 3
    private lateinit var textToSpeech: TextToSpeech
    private var isSpeaking = false
    private var lastSpokenText: String = ""
    private var lastSpokenProgress = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US

                // speech speed
                textToSpeech.setSpeechRate(1.0f)

                // tone of sound
                textToSpeech.setPitch(1.0f)
            }
        }

        binding?.textToSpeech?.setOnClickListener {
            if (!isSpeaking) {
                if (lastSpokenProgress == 0) {
                    lastSpokenText =
                        "${binding?.newsTitle?.text}\n${binding?.newsContent?.text}\n${binding?.newsDescription?.text}"
                }
                val remainingText =
                    lastSpokenText.substring(lastSpokenProgress)
                textToSpeech.speak(remainingText, TextToSpeech.QUEUE_FLUSH, null, "TTS_ID")
                isSpeaking = true
            } else {
                textToSpeech.stop()
                isSpeaking = false
            }
        }

        // Listen for TextToSpeech progress
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                isSpeaking = true
            }

            override fun onDone(utteranceId: String?) {
                isSpeaking = false
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                isSpeaking = false
            }

            override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
                lastSpokenProgress = start
            }
        })

        // Click listener for news image
        binding?.newsImage?.setOnClickListener {
            Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse(arguments?.getString("imageUrl"))
                startActivity(it)
            }
        }

        binding?.backToHome?.setOnClickListener {
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
        binding: FragmentHomeDetailBinding,
        author: String?,
        title: String?,
        imageUrl: String?,
        content: String?,
        description: String?,
        publishedAt: String?,
    ) {

        binding.newsAuthor.text = author
        binding.newsTitle.text = title
        binding.publishedTime.text = publishedAt
        binding.newsContent.text = content
        binding.newsDescription.text = description

        // NEW: Combine description and content
        val combinedText = "${content ?: ""}\n\n${description ?: ""}"
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

    override fun onDestroyView() {
        super.onDestroyView()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}