package com.wkq.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.wkq.ui.databinding.ActivityUiBinding
import com.wkq.ui.goods.GoodsDetail2Activity
import com.wkq.ui.goods.GoodsDetailActivity

class UIActivity :AppCompatActivity (){

    companion object{
        fun startActivity(content: Context){
            content.startActivity(Intent(content,UIActivity::class.java))
        }
    }

    lateinit var binding:ActivityUiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityUiBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btIndicator.setOnClickListener {
            IndicatorActivity.startActivity(this)
        }
   binding.btGoods.setOnClickListener {
       GoodsDetail2Activity.startActivity(this)
        }

    }
}