package com.wu.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.wu.view.util.TextViewLinkify
import com.wu.view.util.TouchableSpan
import java.util.*


/**
 * @author wkq
 *
 * @date 2021年12月08日 12:38
 *
 *@des 扩展TextView
 *
 */

class ExpandableTextView : AutoLinkTextView {


    private var mMaxCollapsedLength = 0

    //省略号
    private var mEllipsisHint: String = "..."

    //展开文字
    private var mExpandHint: String? = null

    //收起文字
    private var mCollapseHint: String? = null

    //展开/收起文字颜色
    var mHintColor: Int? = null

    //原始文本
    private var mOrigText: CharSequence? = null

    //截取后的文本
    private var mSubText: CharSequence? = null
    private var mCurrentState = 0

    //关闭状态
    val STATE_COLLAPSE = 0

    //展开状态
    val STATE_EXPAND = 1


    private var mOnExpandListener: OnExpandListener? = null

    private var copyText: String? = null

    init {
        mHintColor = R.color.color_auto_high_default
    }

    constructor(mContext: Context) : this(mContext, null)
    constructor(mContext: Context, mAttributeSet: AttributeSet?) : this(mContext, mAttributeSet, 0)
    constructor(mContext: Context, mAttributeSet: AttributeSet?, defStyleAttr: Int) : super(
            mContext,
            mAttributeSet,
            defStyleAttr
    ) {
        initAttributeSet(mAttributeSet!!)
        initTextView()
    }

    private fun initTextView() {
        mOrigText = text
        setTextInternal(getNewTextByConfig()!!, mBufferType)
    }


    //初始换数据
    @SuppressLint("ResourceAsColor")
    fun initAttributeSet(mAttributeSet: AttributeSet) {
        val array: TypedArray = context!!.obtainStyledAttributes(
                mAttributeSet,
                R.styleable.ExpandableTextView
        )
        mMaxCollapsedLength = array.getInt(
                R.styleable.ExpandableTextView_maxCollapsedLength, 100
        )
        mExpandHint = array.getString(R.styleable.ExpandableTextView_new_expandHint)
        mCollapseHint = array.getString(R.styleable.ExpandableTextView_new_collapseHint)
        mHintColor = array.getColor(R.styleable.ExpandableTextView_new_hintColor, R.color.color_auto_high_default)
        if (TextUtils.isEmpty(mExpandHint)) {
            mExpandHint = "全文"
        }
        if (TextUtils.isEmpty(mCollapseHint)) {
            mCollapseHint = "收起"
        }
        array.recycle()
        mCurrentState = STATE_COLLAPSE
    }


    fun setText(text: CharSequence?, state: Int) {
        mCurrentState = state
        mOrigText = text
        setText(getNewTextByConfig()!!)
    }

    private fun setTextInternal(text: CharSequence, type: BufferType) {
        super.setText(text, type)
    }

    //扩展的点击事件
    fun setExpandListener(listener: OnExpandListener) {
        mOnExpandListener = listener
    }

    private fun getNewTextByConfig(): CharSequence? {
        if (TextUtils.isEmpty(mOrigText)) return ""
        when (mCurrentState) {
            STATE_COLLAPSE -> {
                val lastBracket: Int
                if (mOrigText!!.length <= mMaxCollapsedLength) {
                    setClickableSpan(null, 0, 0)
                    return mOrigText
                }
                mSubText = mOrigText!!.subSequence(0, mMaxCollapsedLength)
                //自定义表情处理
                lastBracket = mSubText.toString().lastIndexOf("]")
                if (mMaxCollapsedLength - lastBracket <= 4) {
                    mMaxCollapsedLength = lastBracket + 1
                }
                //链接处理
                val links: ArrayList<TextViewLinkify.LinkSpec> =
                        TextViewLinkify.getGatherLinks(mAutoLinkMaskCompat, mOrigText.toString())
                if (links != null && links.size > 0) {
                    for (linkSpec in links) {
                        if (mMaxCollapsedLength < linkSpec.getEnd() && mMaxCollapsedLength > linkSpec.getStart()) {
                            mMaxCollapsedLength = linkSpec.getEnd()
                        }
                    }
                }
                mSubText = mOrigText!!.subSequence(0, mMaxCollapsedLength)
                var ssbCollapsed = SpannableStringBuilder(mSubText)
                        .append(mEllipsisHint) //省略号
                        .append(mExpandHint)
                setClickableSpan(
                        Clickable(
                                OnClickListener { toggle() }), ssbCollapsed.length - 2, ssbCollapsed.length
                )
                return ssbCollapsed
            }
            STATE_EXPAND -> {
                if (mOrigText!!.length <= mMaxCollapsedLength) {
                    setClickableSpan(null, 0, 0)
                    return mOrigText
                }
                val ssbExpand = SpannableStringBuilder(mOrigText)
                        .append(
                                """

                    $mCollapseHint
                    """.trimIndent()
                        )
                setClickableSpan(
                        Clickable(
                                OnClickListener { toggle() }), ssbExpand.length - 2, ssbExpand.length
                )
                return ssbExpand
            }
        }
        return mOrigText
    }


    private fun toggle() {
        when (mCurrentState) {
            STATE_COLLAPSE -> mCurrentState = STATE_EXPAND
            STATE_EXPAND -> mCurrentState = STATE_COLLAPSE
        }
        if (mOnExpandListener != null) mOnExpandListener!!.onExpand(this)
        setTextInternal(getNewTextByConfig()!!, mBufferType)
    }

    inner class Clickable(private val mListener: OnClickListener) : ClickableSpan(), OnClickListener, TouchableSpan {
        protected var mPressed = false

        override
        fun setPressed(pressed: Boolean) {
            mPressed = pressed
        }

        override fun onClick(v: View) {
            mListener.onClick(v)
        }

        override fun onLongClick(widget: View?) {

        }

        @SuppressLint("ResourceAsColor")
        override fun updateDrawState(ds: TextPaint) {
            ds.setColor(mHintColor!!)
            ds.isUnderlineText = false //去除超链接的下划线
        }
    }

    interface OnExpandListener {
        fun onExpand(view: ExpandableTextView?)
    }
}

