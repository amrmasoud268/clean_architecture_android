package com.example.cleanarch.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    @LayoutRes
    abstract fun getLayout(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    open fun showLoading() {
        (context as BaseActivity).showLoading()
    }

    open fun hideLoading() {
        (context as BaseActivity).hideLoading()
    }

    open fun showError(@StringRes message: Int) {
        (context as BaseActivity).showError(message)
    }

    open fun showError(message: String) {
        (context as BaseActivity).showError(message)
    }
}