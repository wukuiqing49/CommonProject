package com.wu.third.textview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wu.third.R
import com.wu.third.databinding.ActivityTextviewBinding
import com.wu.view.AutoLinkTextView


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
        binding = DataBindingUtil.setContentView<ActivityTextviewBinding>(this, R.layout.activity_textview)
    }
}