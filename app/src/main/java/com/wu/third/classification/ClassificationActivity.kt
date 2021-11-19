package com.wu.third.classification

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.wu.base.adapter.KtAdapter
import com.wu.third.R
import com.wu.third.databinding.ActivityClassificationBinding
import java.util.*
import kotlin.collections.ArrayList


class ClassificationActivity : AppCompatActivity(), Observer {

    var binding: ActivityClassificationBinding? = null
    //右侧内容
    var currentFragment: ClassificationContentFragment? = null
    //左侧内容适配器
    var tagAdapter: ClassificationTagAdapter? = null
    //静态图片
    var imgs = arrayListOf<String>(
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F16%2F10%2F29%2F2ac8e99273bc079e40a8dc079ca11b1f.jpg&refer=http%3A%2F%2Fbpic.588ku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=0b42192b10b6d521d58cd0650a0148e6",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F17%2F09%2F15%2F67351408baad11ce25c9b14166a049a6.jpg&refer=http%3A%2F%2Fbpic.588ku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=a6c67c2f08b86a82348b2c87e75ffd9c",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ftupian.qqjay.com%2Fu%2F2018%2F0222%2F2_163119_13.jpg&refer=http%3A%2F%2Ftupian.qqjay.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=995f87498f2d19ab6c885e09904d1ef3",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdpic.tiankong.com%2Ftc%2Feb%2FQJ9124407543.jpg&refer=http%3A%2F%2Fdpic.tiankong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=c76415814c9c52ae20480a89199a128c",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdpic.tiankong.com%2Ftc%2Feb%2FQJ9124407543.jpg&refer=http%3A%2F%2Fdpic.tiankong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=c76415814c9c52ae20480a89199a128c",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fitbbs%2F1705%2F10%2Fc35%2F46579542_1494425402317_mthumb.jpg&refer=http%3A%2F%2Fimg.pconline.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=3ddc7531c2a48fd48712346466ebd228",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp01%2F1ZZQ20QJS6-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=5f6463e28700d48143f6be2c554f8a28"
    )
    //左侧商品分类
    var tags = arrayListOf<ClassificationTagInfo>(
        ClassificationTagInfo("家电", true),
        ClassificationTagInfo("食品", false),
        ClassificationTagInfo("服装", false),
        ClassificationTagInfo("电子", false),
        ClassificationTagInfo("农产品", false),
        ClassificationTagInfo("饮料", false),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityClassificationBinding>(
            this,
            R.layout.activity_classification
        )
        ClassificationObservable.addObserver(this)
        ClassificationFinshObservable.addObserver(this)
        initView()
    }




    private fun initView() {
        tagAdapter = ClassificationTagAdapter(this)
        binding!!.rvClassify.layoutManager = LinearLayoutManager(this)
        binding!!.rvClassify.adapter = tagAdapter
        tagAdapter!!.setAsyncListDatas(tags)
        currentFragment = ClassificationContentFragment.newInstance(0)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_content, currentFragment!!) .commit()

        tagAdapter!!.setOnViewClickListener(object :
            KtAdapter.OnAdapterViewClickListener<ClassificationTagInfo> {
            override fun onViewClick(v: View?, program: ClassificationTagInfo?) {
                var position = tagAdapter!!.getAsyncListItems()!!.indexOf(program)
                currentFragment!!.setData(getContentData(position), position)
                processFinish(position)
            }
        })
    }

    fun getContentData(position: Int): ArrayList<ClassificationContentInfo> {
        var contentInfos = ArrayList<ClassificationContentInfo>()
        var contentInfo = ClassificationContentInfo()
        contentInfo.title = tags.get(position).title
        contentInfo.imgs = imgs
        contentInfos.add(contentInfo)
        return contentInfos
    }

    override fun onDestroy() {
        super.onDestroy()
        ClassificationObservable.deleteObserver(this)
        ClassificationFinshObservable.deleteObserver(this)
    }

    //右侧刷新的观察者
    private fun processRefreshLoadMoreData(info: ClassificationObservableInfo) {
        if ((info.type == 0 && info.position == 0) || (info.type == 1 && info.position >= tags.size - 1)) {
            currentFragment!!.finish()
            return
        }
        if (info.type == 0) {
            //下拉 刷新
            info.position -= 1
        } else {
            //上拉加载
            info.position += 1
        }
        currentFragment!!.setData(getContentData(info.position),  info.position)
    }
    //刷新完成后改变 左侧标题数据
    private fun processFinish(position: Int) {
        var newList = ArrayList<ClassificationTagInfo>()
        var oldList = tagAdapter!!.getAsyncListItems()
        oldList.forEachIndexed { index, classificationTagInfo ->
            var info: ClassificationTagInfo? = null
            if (index == position) {
                info = ClassificationTagInfo(classificationTagInfo.title, true)
            } else {
                info = ClassificationTagInfo(classificationTagInfo.title, false)
            }
            newList.add(info)
        }
        tagAdapter!!.setAsyncListDatas(newList)
    }


    override fun update(o: Observable?, arg: Any?) {
        if (o is ClassificationObservable) {
            var info = arg as ClassificationObservableInfo
            processRefreshLoadMoreData(info)
        } else if (o is ClassificationFinshObservable) {
            processFinish(arg as Int)
        }
    }


}