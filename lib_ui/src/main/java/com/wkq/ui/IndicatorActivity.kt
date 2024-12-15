package com.wkq.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.wkq.ui.databinding.ActivityIndicatorBinding
import com.wkq.ui.fragment.FragmentDemo
import com.wkq.ui.util.FragmentAdapter
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class IndicatorActivity :AppCompatActivity (){

    companion object{
        fun startActivity(content:Context){
            content.startActivity(Intent(content,IndicatorActivity::class.java))
        }
    }


    lateinit var binding:ActivityIndicatorBinding

    val CHANNELS: Array<String> = arrayOf(
        "CUPCAKE",
        "DONUT",
        "ECLAIR",
        "GINGERBREAD",
        "HONEYCOMB",
        "ICE_CREAM_SANDWICH",
        "JELLY_BEAN",
        "KITKAT",
        "LOLLIPOP",
        "M",
        "NOUGAT"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityIndicatorBinding.inflate(LayoutInflater.from(this))
       setContentView(binding.root)

        val fragmentList= ArrayList<Fragment>()
        for (i in 0 until CHANNELS.size) {
            fragmentList.add(FragmentDemo.genInstance("页面:"+i+1))
        }
        val mAdapter= FragmentAdapter(this,fragmentList)
        binding.vpContent.adapter=mAdapter
        initIndicator()
        indicator2()

    }

    private fun initIndicator() {
        val magicIndicator = binding.magicIndicator1
        magicIndicator.setBackgroundColor(Color.parseColor("#00c853"))
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.25f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return CHANNELS.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.setText(CHANNELS.get(index))
                simplePagerTitleView.normalColor = Color.parseColor("#c8e6c9")
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.textSize = 12f
                simplePagerTitleView.setOnClickListener(View.OnClickListener {
                    binding.vpContent.setCurrentItem(
                        index
                    )
                })
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.yOffset = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.setColors(Color.parseColor("#ffffff"))
                return indicator
            }
        }
        magicIndicator.navigator = commonNavigator
        binding.vpContent.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                magicIndicator.onPageScrollStateChanged(state)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                magicIndicator.onPageSelected(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        });


    }

    fun indicator2(){

        binding.magicIndicator2.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.35f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return CHANNELS.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.setText(CHANNELS.get(index))
                simplePagerTitleView.normalColor = Color.parseColor("#333333")
                simplePagerTitleView.selectedColor = Color.parseColor("#e94220")
                simplePagerTitleView.setOnClickListener(View.OnClickListener {
                    binding.vpContent.setCurrentItem(
                        index
                    )
                })
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = WrapPagerIndicator(context)
                indicator.fillColor = Color.parseColor("#ebe4e3")
                return indicator
            }
        }
        binding.magicIndicator2.navigator = commonNavigator

        binding.vpContent.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                binding.magicIndicator2.onPageScrollStateChanged(state)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.magicIndicator2.onPageSelected(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                binding.magicIndicator2.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        });

    }
}