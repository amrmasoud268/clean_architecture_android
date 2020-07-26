package com.example.cleanarch.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.cleanarch.R
import com.example.cleanarch.core.utils.easyToast
import com.example.cleanarch.core.utils.invisible
import com.example.cleanarch.core.utils.visible
import kotlinx.android.synthetic.main.activity_base.*


abstract class BaseActivity : AppCompatActivity(){

    @LayoutRes abstract fun  getLayout()  : Int

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        initMainView()
        initLoadingLayout()
        init()
    }

    private fun initMainView(){
        vsMainLayout.layoutResource = getLayout()
        vsMainLayout.inflate()
    }

    open fun initLoadingLayout(@LayoutRes loadingLayout : Int = R.layout.view_loading){
        /*vsLoadingLayout.layoutResource = loadingLayout
        vsLoadingLayout.inflate()*/
    }

    open fun showLoading(){
        //vsLoadingLayout?.visible()
    }

    open fun hideLoading(){
       // vsLoadingLayout?.invisible()
    }

    open fun showError(@StringRes message : Int){
        easyToast(message)
    }

    open fun showError(message : String){
        easyToast(message)
    }

}