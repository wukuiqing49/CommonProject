package com.wu.third.classification

import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.DiffUtil


/**
 * @author wkq
 *
 * @date 2021年09月06日 14:33
 *
 *@des  异步差分对比的监听
 *
 */

class ClassificationTagDiffItemCallBack() :
    DiffUtil.ItemCallback<ClassificationTagInfo>() {
    //是否相同的id
    override fun areItemsTheSame(oldItem: ClassificationTagInfo, newItem: ClassificationTagInfo): Boolean {
        return oldItem.title!!.equals(oldItem.title)

    }
    //是否相同的数据
    override fun areContentsTheSame(oldItem: ClassificationTagInfo, newItem: ClassificationTagInfo): Boolean {
        var oldItem = oldItem.toString()
        var newItem = newItem.toString()
        return oldItem.equals(newItem)
    }


    //  局部刷新  返回null 整条数据刷新
    override fun getChangePayload(oldItem: ClassificationTagInfo, newItem: ClassificationTagInfo): Any? {
        var bundle = Bundle()
        // onBindViewHolder  实现三个参数  payloads   第一个数据为  封装的bundle
        if (!TextUtils.equals(oldItem.title, newItem.title)) {
            bundle.putString("title", newItem.title)
        }
        if (oldItem.isShow!=newItem.isShow) {
            bundle.putBoolean("isShow", newItem.isShow)
        }
        return bundle
    }


}