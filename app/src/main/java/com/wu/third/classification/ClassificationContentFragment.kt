package com.wu.third.classification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.wu.third.R
import com.wu.third.databinding.FragmentClassificationContentBinding


/**
 * @author wkq
 *
 * @date 2021年11月18日 16:06
 *
 *@des
 *
 */

class ClassificationContentFragment : Fragment() {
    //设置出艰苦
    var imgs = arrayListOf<String>(
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F16%2F10%2F29%2F2ac8e99273bc079e40a8dc079ca11b1f.jpg&refer=http%3A%2F%2Fbpic.588ku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=0b42192b10b6d521d58cd0650a0148e6",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F17%2F09%2F15%2F67351408baad11ce25c9b14166a049a6.jpg&refer=http%3A%2F%2Fbpic.588ku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=a6c67c2f08b86a82348b2c87e75ffd9c",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ftupian.qqjay.com%2Fu%2F2018%2F0222%2F2_163119_13.jpg&refer=http%3A%2F%2Ftupian.qqjay.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=995f87498f2d19ab6c885e09904d1ef3",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ftupian.qqjay.com%2Fu%2F2018%2F0222%2F2_163119_13.jpg&refer=http%3A%2F%2Ftupian.qqjay.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=995f87498f2d19ab6c885e09904d1ef3",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ftupian.qqjay.com%2Fu%2F2018%2F0222%2F2_163119_13.jpg&refer=http%3A%2F%2Ftupian.qqjay.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=995f87498f2d19ab6c885e09904d1ef3",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ftupian.qqjay.com%2Fu%2F2018%2F0222%2F2_163119_13.jpg&refer=http%3A%2F%2Ftupian.qqjay.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=995f87498f2d19ab6c885e09904d1ef3",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdpic.tiankong.com%2Ftc%2Feb%2FQJ9124407543.jpg&refer=http%3A%2F%2Fdpic.tiankong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=c76415814c9c52ae20480a89199a128c",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fitbbs%2F1705%2F10%2Fc35%2F46579542_1494425402317_mthumb.jpg&refer=http%3A%2F%2Fimg.pconline.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=3ddc7531c2a48fd48712346466ebd228",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp01%2F1ZZQ20QJS6-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639813297&t=5f6463e28700d48143f6be2c554f8a28"
    )
    var binding: FragmentClassificationContentBinding? = null
    var position = 0;
    var finisTime: Long = 1000

    companion object {
        fun newInstance(position: Int): ClassificationContentFragment {
            val args = Bundle()
            args.putInt("position", position)
            val fragment = ClassificationContentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassificationContentBinding.inflate(layoutInflater,container,false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments!!.getInt("position")
        initData()
        initView()

    }

    var mContentInfos = ArrayList<ClassificationContentInfo>()
    var contentAdapter: ClassificationContentAdapter? = null
    private fun initData() {
        var contentInfo = ClassificationContentInfo()
        contentInfo.title = "家电"
        contentInfo.imgs = imgs
        mContentInfos.add(contentInfo)
    }

    private fun initView() {

        contentAdapter = ClassificationContentAdapter(activity!!);
        binding!!.rvContent.layoutManager = LinearLayoutManager(activity!!)
        binding!!.rvContent.adapter = contentAdapter
        contentAdapter!!.addItems(mContentInfos)

        binding!!.sfLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                postRefreshLoadMore(0)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                postRefreshLoadMore(1)
            }
        })
    }
    //延时执行刷新 为了保留 顶部和顶部的刷新布局
    fun postRefreshLoadMore(type: Int) {
        binding!!.sfLayout.postDelayed(object : Runnable {
            override fun run() {
                ClassificationObservable.update(ClassificationObservableInfo(type, position))
            }
        }, finisTime)

    }

    fun setData(contentInfos: ArrayList<ClassificationContentInfo>, position: Int) {
        this.position = position
        mContentInfos = contentInfos
        contentAdapter!!.setNewData(mContentInfos)
        ClassificationFinshObservable.update(position)
        finish()
    }

    fun finish() {
        binding!!.sfLayout.finishLoadMore()
        binding!!.sfLayout.finishRefresh()
        goTop()

    }

    //为了兼容一下 sfLayout 的定时问题 做了一下延时
    fun goTop() {
        binding!!.sfLayout.postDelayed(object : Runnable {
            override fun run() {
                binding!!.rvContent.scrollToPosition(0);
                var mLayoutManager = binding!!.rvContent.getLayoutManager() as LinearLayoutManager
                mLayoutManager.scrollToPositionWithOffset(0, 0);
            }
        }, 100)

    }


}