package com.arctouch.codechallenge.core.ktx

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity

fun <T : ViewModel> FragmentActivity.obtainViewModel(viewModelFactory: ViewModelProvider.Factory, viewModelClass: Class<T>) =
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)