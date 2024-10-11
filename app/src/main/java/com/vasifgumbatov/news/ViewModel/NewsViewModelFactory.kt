//package com.vasifgumbatov.news
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.vasifgumbatov.news.ViewModel.NewsViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
//
//@HiltViewModel
//class NewsViewModelFactory(private val getNewsUseCase: GetNewsUseCase) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
//            return NewsViewModel(getNewsUseCase) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}