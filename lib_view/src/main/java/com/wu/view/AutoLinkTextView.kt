package com.wu.view

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Point
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.wu.view.util.*
import com.wu.view.util.TextViewLinkify.NONE
import java.util.*


/**
 * @author wkq
 *
 * @date 2021年12月08日 12:38
 *
 *@des 兼容链接的TextView
 *
 */

open class AutoLinkTextView : AppCompatTextView, OnSpanClickListener, SpanTouchFix, OnLongClickListener {

    var mContext: Context? = null
    var AUTO_LINK_MASK_REQUIRED: Int =
        TextViewLinkify.PHONE_NUMBERS or TextViewLinkify.EMAIL_ADDRESSES or TextViewLinkify.WEB_URLS


    //链接文字颜色
    private var mLinkTextColor: ColorStateList? = null

    //链接背景颜色
    private var mLinkBgColor: ColorStateList? = null

    //高亮颜色
    private var mHighLightColor = 0

    //高亮类型
    protected var mAutoLinkMaskCompat = 0

    //是否支持 长按操作
    private var useLongClick = false
    private var mOriginText: CharSequence? = null

    //起止位置
    private var start = 0
    private var end: Int = 0

    //点击的Span
    private var clickableSpan: ClickableSpan? = null

    //双击的消息
    private val doubleMsg= 1000

    //是否传递
    private var mNeedForceEventToParent = false

    // 1  手机号  2 mail 3 url
    private var clickLinkType = -1

    private var startX: Int = 0
    private var startY: Int = 0

    private var mOnAutoLinkClickListener: OnAutoLinkClickListener? = null
    private var mOnAutoLinkLongClickListener: OnAutoLinkLongClickListener? = null
    public var mBufferType = BufferType.NORMAL
    init {
        mLinkTextColor = ContextCompat.getColorStateList(context, R.color.color_auto_link_default)
    }

     constructor(mContext: Context) : this(mContext, null)
     constructor(mContext: Context, mAttributeSet: AttributeSet?) : this(mContext, mAttributeSet, 0)
     constructor(mContext: Context, mAttributeSet: AttributeSet?, defStyleAttr: Int) : super(
        mContext,
        mAttributeSet,
        defStyleAttr
    ) {

        initDefult(mContext, mAttributeSet)
        initView()
    }


    //初始换数据
    private fun initDefult(mContext: Context, mAttributeSet: AttributeSet?) {
        this.mContext = mContext
        mAutoLinkMaskCompat = autoLinkMask or AUTO_LINK_MASK_REQUIRED

        val array: TypedArray = context.obtainStyledAttributes(
            mAttributeSet,
            R.styleable.AutoLinkTextView
        )
        mLinkTextColor = array.getColorStateList(R.styleable.AutoLinkTextView_linkTextColor)
        mHighLightColor = mContext.resources.getColor(R.color.color_auto_high_default)
        mAutoLinkMaskCompat = array.getInt(R.styleable.AutoLinkTextView_AutoLinkMaskCompat, -1001)
        mLinkBgColor = array.getColorStateList(R.styleable.AutoLinkTextView_linkBackgroundColor)

        useLongClick = array.getBoolean(R.styleable.AutoLinkTextView_useLongClick, false)

        if (mAutoLinkMaskCompat == 0 || mAutoLinkMaskCompat == -1001)
            mAutoLinkMaskCompat = TextViewLinkify.PHONE_NUMBERS or TextViewLinkify.EMAIL_ADDRESSES or TextViewLinkify.WEB_URLS or TextViewLinkify.MAP_ADDRESSES
        array.recycle()
    }

    private fun initView() {

        //如果是链接，文本颜色就会有特殊变化
        autoLinkMask = 0
        movementMethod = LinkTouchMovementMethod.getInstance()
        //设置高亮颜色
        highlightColor = mHighLightColor
        if (useLongClick) {
            setOnLongClickListener(this)
        }
        if (mOriginText != null) text = mOriginText

    }

    override
    fun setText(text: CharSequence?, type: BufferType?) {
        mOriginText = text
        if (!TextUtils.isEmpty(mOriginText)) {
            val builder = SpannableStringBuilder(mOriginText)
            if (mLinkTextColor == null) {
                mLinkTextColor = ContextCompat.getColorStateList(
                    context,
                    R.color.color_auto_link_default
                )
            }
            TextViewLinkify.addLinks(
                text.toString(),
                builder,
                mAutoLinkMaskCompat,
                mLinkTextColor,
                mLinkBgColor,
                this
            )
            builder.setSpan(getClickableSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            mOriginText = builder
        }
        super.setText(mOriginText, type)

        if (mNeedForceEventToParent && linksClickable) {
            isFocusable = false
            isClickable = false
            isLongClickable = false
        }

    }

    /**
     * 是否强制把TextView的事件强制传递给父元素。
     * TextView在有ClickSpan的情况下默认会消耗掉事件
     *
     * @param needForceEventToParent true 为强制把TextView的事件强制传递给父元素，false 则不传递
     */
    fun setNeedForceEventToParent(needForceEventToParent: Boolean) {
        if (mNeedForceEventToParent != needForceEventToParent) {
            mNeedForceEventToParent = needForceEventToParent
            if (mOriginText != null) {
                text = mOriginText
            }
        }
    }

    private val mSingleTapConfirmedHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (doubleMsg != msg.what) {
                return
            }
            if (msg.obj is String) {
                val url = msg.obj as String
                val schemeUrl = url.toLowerCase()
                if (!TextUtils.isEmpty(url)) if (mOnAutoLinkClickListener == null) {
                   AutoTextViewUtil. defaultLinkClick(mContext!!, url, schemeUrl)
                } else if (null != mOnAutoLinkClickListener) {
                    if (schemeUrl.startsWith("tel:")) {
                        clickLinkType = 3
                        val phoneNumber = Uri.parse(url).schemeSpecificPart
                        mOnAutoLinkClickListener!!.onLinkClick(clickLinkType, phoneNumber)
                    } else if (schemeUrl.startsWith("mailto:")) {
                        val mailAddr = Uri.parse(url).schemeSpecificPart
                        clickLinkType = 2
                        mOnAutoLinkClickListener!!.onLinkClick(clickLinkType, mailAddr)
                    } else if (schemeUrl.startsWith("http") || schemeUrl.startsWith("https")) {
                        clickLinkType = 1
                        mOnAutoLinkClickListener!!.onLinkClick(clickLinkType, url)
                    }
                }
            }
        }
    }



    /**
     * 记录当前Touch事件对应的点是不是在span上面
     */
    private var mTouchSpanHit = false
    private val mRunnable =
        Runnable { if (parent is ViewGroup && mTouchSpanHit) defaultLinkLongClick() }

    private fun defaultLinkLongClick() {
        if (text is Spannable) {
            val span: TouchableSpan = LinkTouchDecorHelper
                .getPressedSpan(this, text as Spannable, startX, startY)
            if (span != null) span.onLongClick(this)
        }
    }

    private fun disallowOnSpanClickInterrupt() {
        mSingleTapConfirmedHandler.removeMessages(doubleMsg)
        mDownMillis = 0
    }

    private var mDownMillis: Long = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mAutoLinkMaskCompat == NONE) return false
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x.toInt()
                startY = event.y.toInt()
                val hasSingleTap: Boolean = mSingleTapConfirmedHandler.hasMessages(doubleMsg)
                if (!hasSingleTap) {
                    mDownMillis = SystemClock.uptimeMillis()
                } else {
                    disallowOnSpanClickInterrupt()
                }
                /* 长按操作 */
                postDelayed(mRunnable, ViewConfiguration.getLongPressTimeout().toLong())
            }
            MotionEvent.ACTION_MOVE -> {
                val lastX = event.x.toInt()
                val lastY = event.y.toInt()
                if (Math.abs(lastX - startX) > 10 || Math.abs(lastY - startY) > 10) {
                    removeCallbacks(mRunnable)
                    return false
                }
            }
            MotionEvent.ACTION_UP -> removeCallbacks(mRunnable)
        }
        val ret = super.onTouchEvent(event)
        return if (mNeedForceEventToParent) {
            mTouchSpanHit
        } else ret
    }

    private val point = Point()
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            point.x = ev.rawX.toInt()
            point.y = ev.rawY.toInt()
        }
        return super.dispatchTouchEvent(ev)
    }


    fun getClickableSpan(): ClickableSpan? {
        return clickableSpan
    }

    var isSpanClick = false

    override fun onSpanClick(text: String?): Boolean {
        if (null == text) {
            return true
        }

        isSpanClick = true
        val clickUpTime = SystemClock.uptimeMillis() - mDownMillis

        if (mSingleTapConfirmedHandler.hasMessages(doubleMsg)) {
            disallowOnSpanClickInterrupt()
            return true
        }

        if (200 < clickUpTime) {

            return true
        }

        var scheme = Uri.parse(text).scheme
        if (scheme != null) {
            scheme = scheme.toLowerCase()
        }

        if (AutoTextViewUtil.linkType.contains(scheme)) {
            val waitTime: Long = ViewConfiguration.getDoubleTapTimeout().toLong() - clickUpTime
            mSingleTapConfirmedHandler.removeMessages(doubleMsg)
            val msg = Message.obtain()
            msg.what = doubleMsg
            msg.obj = text
            mSingleTapConfirmedHandler.sendMessageDelayed(msg, waitTime)
            return true
        }
        return false
    }


    //link 点击
    fun setOnAutoLinkClickListener(onAutoLinkClickListener: OnAutoLinkClickListener?) {
        mOnAutoLinkClickListener = onAutoLinkClickListener
    }
    //link 长按
    fun setOnAutoLinkLongClickListener(onAutoLinkLongClickListener: OnAutoLinkLongClickListener?) {
        mOnAutoLinkLongClickListener = onAutoLinkLongClickListener
    }


    //Span点击
    override fun onSpanLongClick(text: String?) {
        if (mOnAutoLinkLongClickListener != null) mOnAutoLinkLongClickListener!!.onLongClick(text)

    }

    override fun setTouchSpanHit(hit: Boolean) {
        if (mTouchSpanHit != hit) {
            mTouchSpanHit = hit
        }
    }

    //长按点击
    override fun onLongClick(v: View?): Boolean {
        val items: MutableList<String> = ArrayList()
        items.add(resources.getString(R.string.copy))
        LinkDialogUtil.showButtonsDialog(
            context,
            items,
            object : LinkDialogUtil.DialogButtonsClick {
                override fun onButtonsClick(position: Int, dialog: Dialog?) {
                    when (position) {
                        0 -> {
                            AutoTextViewUtil.copyText(context, mOriginText.toString())
                        }
                    }
                    dialog!!.dismiss()
                }

            })
        return false
    }
    // 点击事件
    interface OnAutoLinkClickListener {
        fun onLinkClick(type: Int, lintText: String?)
    }
    // 长按事件
    interface OnAutoLinkLongClickListener {
        fun onLongClick(text: String?)
    }


    open fun setClickableSpan(clickableSpan: ClickableSpan?, start: Int, end: Int) {
        this.clickableSpan = clickableSpan
        this.start = start
        this.end = end
    }

}
