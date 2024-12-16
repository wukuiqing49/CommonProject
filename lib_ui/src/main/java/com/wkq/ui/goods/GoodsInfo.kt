package com.wkq.ui.goods

/**
 *@Desc:
 *
 *@Author: wkq
 *
 *@Time: 2024/12/16 11:32
 *
 */

const val TYPE_VIDEO=0x001
const val TYPE_GOODS=0x002
const val TYPE_GOODS_OTHER=0x003
class GoodsInfo(var type:Int,var bannerList:List<BannerInfo>?,var content:String) {

}


class BannerInfo(var type:Int,var url:String) {
}