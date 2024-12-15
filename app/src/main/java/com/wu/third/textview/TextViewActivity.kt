package com.wu.third.textview

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.wu.third.databinding.ActivityTextviewBinding


/**
 * @author wkq
 *
 * @date 2021年12月07日 14:21
 *
 *@des
 *
 */

class TextViewActivity : AppCompatActivity() {
    var binding:ActivityTextviewBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextviewBinding.inflate(LayoutInflater.from(this))
    }
}