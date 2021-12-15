package com.wu.view.util

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.text.ClipboardManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.wu.view.R
import java.lang.Exception
import java.util.ArrayList

object AutoTextViewUtil {
    //支持的类型
    val linkType=  hashSetOf("tel","mailto","http","https")

    //打开浏览器
    fun openBrowser(context: Context?, url: String?) {

        try {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(url)
            if (intent.resolveActivity(context!!.getPackageManager()) != null) {
                val componentName = intent.resolveActivity(context!!.getPackageManager())
                context!!.startActivity(Intent.createChooser(intent, "外部文件"))
            } else {
                Log.e("err", "链接错误或无浏览器")
            }
        }catch (e :Exception){
            Log.e("err", "链接错误或无浏览器")
        }

    }
    //打开浏览器
    fun openEmail(context: Context?, url: String?) {
        try {
            var intent =  Intent(Intent.ACTION_SENDTO,Uri.parse(url))
            intent.putExtra(Intent.EXTRA_CC, arrayListOf(url)); // 抄送人
            intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
            intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
            context!!.startActivity(Intent.createChooser(intent, "请选择邮箱"));
        }catch (e :Exception){

        }

    }

    //联系人
     fun addToPhoneList(context:Context?,phoneNum: String) {
        val items: MutableList<String> = ArrayList()
        items.add(context!!.resources.getString(R.string.create_new_contact))
        items.add(context!!.resources.getString(R.string.add_exist_contact))
        LinkDialogUtil.showButtonsDialog(
            context,
            items,
            object : LinkDialogUtil.DialogButtonsClick {
                override fun onButtonsClick(position: Int, dialog: Dialog?) {
                    when (position) {
                        0 -> {
                            val addIntent = Intent(
                                Intent.ACTION_INSERT,
                                Uri.withAppendedPath(
                                    Uri.parse("content://com.android.contacts"),
                                    "contacts"
                                )
                            )
                            addIntent.type = "vnd.android.cursor.dir/person"
                            addIntent.type = "vnd.android.cursor.dir/contact"
                            addIntent.type = "vnd.android.cursor.dir/raw_contact"
                            //                                            addIntent.putExtra(ContactsContract.Intents.Insert.NAME,number);
                            addIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNum)
                            context!!.startActivity(addIntent)
                            dialog!!.dismiss()
                        }
                        1 -> {
                            val oldConstantIntent = Intent(Intent.ACTION_INSERT_OR_EDIT)
                            oldConstantIntent.type = ContactsContract.Contacts.CONTENT_ITEM_TYPE
                            oldConstantIntent.putExtra(
                                ContactsContract.Intents.Insert.PHONE,
                                phoneNum
                            )
                            oldConstantIntent.putExtra(
                                ContactsContract.Intents.Insert.PHONE_TYPE,
                                3
                            )
                            if (oldConstantIntent.resolveActivity(context.packageManager) != null) {
                                context!!.startActivity(oldConstantIntent)
                            } else {
                            }
                            dialog!!.dismiss()
                        }
                    }
                }

            })
    }
    //打电话
     fun jumpPhone(context:Context?,phoneNumber: String?) {
        val dialIntent = Intent(
            Intent.ACTION_DIAL, Uri.parse(
                "tel:$phoneNumber"
            )
        ) //跳转到拨号界面，同时传递电话号码
        context!!.startActivity(dialIntent)
    }

    /**
     * 复制内容至剪贴板
     *
     * @param context
     * @param text
     */
    fun copyText(context: Context?, text: String?) {
        if (TextUtils.isEmpty(text)) return
        val cm = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (cm != null) cm.text = text

    }


     fun defaultLinkClick(context:Context,url: String, schemeUrl: String) {
        if (schemeUrl.startsWith("tel:")) {
            val phoneNumber = Uri.parse(url).schemeSpecificPart
            val items: MutableList<String> = ArrayList()
            items.add(context.resources.getString(R.string.call))
            items.add(context.resources.getString(R.string.copy))
            items.add(context.resources.getString(R.string.add_phone_list))
            LinkDialogUtil.showButtonsDialog(
                context,
                items,
                object : LinkDialogUtil.DialogButtonsClick {
                    override fun onButtonsClick(position: Int, dialog: Dialog?) {
                        when (position) {
                            0 -> {
                                jumpPhone(context, phoneNumber)
                            }
                            1 -> {
                                copyText(context, phoneNumber)
                                Toast.makeText(context, "复制到剪切板", Toast.LENGTH_SHORT).show()
                            }
                            2 -> {
                                addToPhoneList(context, phoneNumber)
                            }
                        }
                        dialog!!.dismiss()
                    }

                })
        } else if (schemeUrl.startsWith("mailto:")) {
            openEmail(context!!, schemeUrl)
        } else if (schemeUrl.startsWith("http") || schemeUrl.startsWith("https")) {
            openBrowser(context!!, schemeUrl)
        }
    }



}