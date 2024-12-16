package com.qian.du.base.module.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 *@Desc:  RecycleView 搭配ViewBinding基类
 *
 *@Author: wkq
 *
 *@Time: 2024/9/14 14:21
 *
 */
abstract class BaseRvTypeAdapter<D>(context: Context) :
    RecyclerView.Adapter<BaseViewTypeHolder>() {
    var dataList = ArrayList<D>();
    val mContext = context



    var  mListener: OnItemClickListener?=null
    var  mLongListener: OnItemLongClickListener?=null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        mLongListener = listener
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    /**
     * 设置数据 等同 addData()
     * @param data ArrayList<T>
     */
    fun setData(data: List<D>) {
        if (data!=null){
            dataList.addAll(data)
            notifyDataSetChanged()
        }

    }

    /**
     * 添加数据
     * @param data ArrayList<T>
     */
    fun addData(data: List<D>) {
        dataList.addAll(data)
        val hashSet= LinkedHashSet<D>(dataList)
        dataList=ArrayList<D>(hashSet)
        notifyDataSetChanged()
    }


    /**
     * 添加数据
     * @param data ArrayList<T>
     */
    fun addStartData(data: List<D>) {
        dataList.addAll(0,data)
        notifyDataSetChanged()
    }

    /**
     * 设置新数据
     * @param data ArrayList<T>?
     */
    fun setNewData(data: List<D>?) {
        dataList.clear()
        if (data != null) {
            dataList.addAll(data)
        }
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     * @param data D
     */
    fun addItem(data: D) {
        dataList.add(data)
        notifyDataSetChanged()
    }

    fun addItems(data: List<D>?) {
        dataList.addAll(data!!)
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     * @param data D
     */
    fun addItem(position:Int,data: D) {
        dataList.add(position,data)
        notifyDataSetChanged()
    }

    /**
     * 清空数据
     */
    fun removeAllData() {
        dataList.clear()
        notifyDataSetChanged()
    }



    /**
     * 获取指定条目
     * @param position Int
     * @return T
     */
    fun getBean(position: Int): D {
        return dataList.get(position)
    }

    /**
     * 获取列表信息
     * @return ArrayList<D>
     */
    fun getData(): ArrayList<D> {
        return dataList
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }
}