package com.example.cleanarch.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarch.core.base.BaseViewHolder

abstract class BaseAdapter(@LayoutRes val layout : Int) : RecyclerView.Adapter<BaseViewHolder>(){
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout , parent , false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        with(holder.itemView){
            bindView(holder , position)
        }
    }

    abstract override fun getItemCount(): Int

    abstract fun bindView(holder: BaseViewHolder, position: Int)


}