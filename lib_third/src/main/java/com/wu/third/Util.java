package com.wu.third;

/**
 * 作者: 吴奎庆
 * <p>
 * 时间: 2018/8/22
 * <p>
 * 简介:
 */


import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;



public class Util {

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        if (bmp == null) return null;
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                // F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }

    public static byte[] inputStreamToByte(InputStream is) {
        try {
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

//    /**
//     * 判断是否安装了微信客户端
//     */
//    public static boolean isWXAppInstalledAndSupported(Context context) {
//        IWXAPI api = WXAPIFactory.createWXAPI(context, BuildConfig.WECHAT_SHARE);
//        return api.isWXAppInstalled();
//    }
//
//
//    public static boolean isWeixinAvilible(Context context) {
//        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
//        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
//        if (pinfo != null) {
//            for (int i = 0; i < pinfo.size(); i++) {
//                String pn = pinfo.get(i).packageName;
//                if (pn.equals("com.tencent.mm")) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 判断qq是否可用
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isQQClientAvailable(Context context) {
//        final PackageManager packageManager = context.getPackageManager();
//        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
//        if (pinfo != null) {
//            for (int i = 0; i < pinfo.size(); i++) {
//                String pn = pinfo.get(i).packageName;
//                if (pn.equals("com.tencent.mobileqq")) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//
//    //Scheme 检测QQ分享
//    public static boolean checkQQ(Context context) {
//        if (context == null || context.getPackageManager() == null) return false;
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mqqapi://share"));
//        return !context.getPackageManager().queryIntentActivities(intent, 0).isEmpty();
//    }
//
//    public static boolean checkWechat(Context context) {
//        if (context == null || context.getPackageManager() == null) return false;
//        if (com.cnlive.share.util.Util.isWXAppInstalledAndSupported(context)) return true;
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("weixin://"));
//        return !context.getPackageManager().queryIntentActivities(intent, 0).isEmpty();
//    }
//
//
//    /**
//     * 处理分享地址
//     *
//     * @param shareInfoUrl
//     * @param mContext
//     */
//    public static String processUrlData(String shareInfoUrl, Context mContext, boolean isToWjj) {
//        if (shareInfoUrl.contains("http://wjjh5.cnlive.com") || shareInfoUrl.contains("http://wjjh5test.cnlive.com")) {
//            Uri oldUri = Uri.parse(shareInfoUrl);
//            //判断分享地址中是否有参数，如果没有则先拼接问号
//            if (oldUri.getQueryParameterNames().size() <= 0) {
//                if (!shareInfoUrl.contains("?"))
//                    shareInfoUrl = shareInfoUrl.concat("?");
//            }
//            if (!shareInfoUrl.contains("?sid=") && !shareInfoUrl.contains("&sid=")) {
//                StringBuilder pageUrl = new StringBuilder(shareInfoUrl);
//                pageUrl.append(oldUri.getQueryParameterNames().size() <= 0 ? "sid=" : "&sid=").append(UserService.getUid(mContext));
//                shareInfoUrl = pageUrl.toString();
//            }
//
//            if (!shareInfoUrl.contains("?shareSid=") && !shareInfoUrl.contains("&shareSid=")) {
//                StringBuilder pageUrl = new StringBuilder(shareInfoUrl);
//                pageUrl.append("&shareSid=").append(UserService.getUid(mContext));
//                shareInfoUrl = pageUrl.toString();
//            }
//            if (!shareInfoUrl.contains("?isApp=") && !shareInfoUrl.contains("&isApp=")) {
//                StringBuilder pageUrl = new StringBuilder(shareInfoUrl);
//                pageUrl.append("&isApp=").append(isToWjj);
//                shareInfoUrl = pageUrl.toString();
//            }
//
//
//            if (!shareInfoUrl.contains("?ver=") && !shareInfoUrl.contains("&ver=")) {
//                StringBuilder pageUrl = new StringBuilder(shareInfoUrl);
//                pageUrl.append("&ver=").append(AppConfig.getAppVersion());
//                shareInfoUrl = pageUrl.toString();
//            }
//
//            if (!shareInfoUrl.contains("?plat=") && !shareInfoUrl.contains("&plat=")) {
//                StringBuilder pageUrl = new StringBuilder(shareInfoUrl);
//                pageUrl.append("&plat=").append("a");
//                shareInfoUrl = pageUrl.toString();
//            }
//
//            if (!shareInfoUrl.contains("?wjjFrom=") && !shareInfoUrl.contains("&wjjFrom=")) {
//                StringBuilder pageUrl = new StringBuilder(shareInfoUrl);
//                pageUrl.append("&wjjFrom=").append(AppConfig.getAppChannel());
//                shareInfoUrl = pageUrl.toString();
//            }
//            if (!shareInfoUrl.contains("?appId=") && !shareInfoUrl.contains("&appId=")) {
//                StringBuilder pageUrl = new StringBuilder(shareInfoUrl);
//                pageUrl.append("&appId=").append(AppConfig.getAppId());
//                shareInfoUrl = pageUrl.toString();
//            }
//            Uri uri = Uri.parse(shareInfoUrl);
//
//            String[] urls = shareInfoUrl.split("\\?");
//            String url = urls[0];
//            StringBuilder pageUrl = new StringBuilder(url);
//            pageUrl.append("?");
//            for (String key : uri.getQueryParameterNames()) {
//                if ("sid".equals(key)) {
//                    pageUrl.append(key).append("=").append(Uri.encode(TextUtils.isEmpty(UserService.getUid(mContext)) ? "" : UserService.getUid(mContext))).append("&");
//                } else if ("ver".equals(key)) {
//                    pageUrl.append(key).append("=").append(Uri.encode(TextUtils.isEmpty(AppConfig.getAppVersion()) ? "" : AppConfig.getAppVersion())).append("&");
//                } else if ("plat".equals(key)) {
//                    pageUrl.append(key).append("=").append(Uri.encode("a")).append("&");
//                } else if ("shareSid".equals(key)) {
//                    pageUrl.append(key).append("=").append(Uri.encode(TextUtils.isEmpty(UserService.getUid(mContext)) ? "" : UserService.getUid(mContext))).append("&");
//                } else if ("isApp".equals(key)) {
//                    pageUrl.append(key).append("=").append(isToWjj).append("&");
//                } else if ("wjjFrom".equals(key)) {
//                    pageUrl.append(key).append("=").append(Uri.encode(AppConfig.getAppChannel())).append("&");
//                } else if ("appId".equals(key)) {
//                    pageUrl.append(key).append("=").append(Uri.encode(AppConfig.getAppId())).append("&");
//                } else {
//                    String value = UriUtil.getQueryParameterWithDecode(uri.getEncodedQuery(), key);
//                    pageUrl.append(key).append("=").append(Uri.encode(TextUtils.isEmpty(value) ? "" : value)).append("&");
//                }
//            }
//            pageUrl = deleteCharOfEnd(pageUrl, "&", true);
//            shareInfoUrl = pageUrl.toString();
//
//
//        }
//        return shareInfoUrl;
//    }
//
//    public static StringBuilder deleteCharOfEnd(StringBuilder strBud, String item, boolean asLast) {
//        if (strBud == null) return null;
//        int index = asLast ? strBud.lastIndexOf(item) : strBud.indexOf(item);
//        if (index == strBud.length() - 1) strBud.deleteCharAt(index);
//        return strBud;
//    }
//
//    public static String buildTransaction(final String type) {
//        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
//    }
//
//    public static boolean isNetUrl(String url) {
//
//        if (TextUtils.isEmpty(url)) return false;
//
//        if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("rtsp:") || url.startsWith("ftp:")) {
//            return true;
//        } else {
//            return false;
//        }
////        String UrlEndAppendNextChars = "(http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:;_/=<>]*)?";
////
////        // 邮箱验证规则
////        // 编译正则表达式
////
////        Pattern pattern = Pattern.compile(UrlEndAppendNextChars);
////        // 忽略大小写的写法
////        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
////        Matcher matcher = pattern.matcher(url);
////        // 字符串是否与正则表达式相匹配
////        boolean rs = matcher.matches();
////        return Patterns.WEB_URL.matcher(url).matches();
//    }
//
//    public static boolean isWeiboInstallAndInit(Context context) {
//        if (context == null || context.getPackageManager() == null) return false;
//        if (!WbSdk.isWbInstall(context)) return false;
//        if (!com.cnlive.share.util.Util.initWeibo(context)) return false;
//        return true;
//    }
//
//    public static boolean initWeibo(Context context) {
//        try {
//            AuthInfo info = new AuthInfo(context, "3884700821",
//                    "http://wjjh5.cnlive.com/apkdownload.php",
//                    "email,direct_messages_read,direct_messages_write,"
//                            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
//                            + "follow_app_official_microblog," + "invitation_write");
//            WbSdk.install(context, info);
//            WbSdk.checkInit();
//            return true;
//        } catch (Exception ignored) {
//        }
//        return false;
//    }
//
//
//    /**
//     * string转成bitmap
//     *
//     * @param str
//     */
//    public static Bitmap convertStringToIcon(String str) {
//        Bitmap bitmap = null;
//        try {
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(str, Base64.DEFAULT);
//            bitmap =
//                    BitmapFactory.decodeByteArray(bitmapArray, 0,
//                            bitmapArray.length);
//            return bitmap;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * bitmap 转化为File
//     *
//     * @param context
//     * @param bitMap
//     * @param listener
//     */
//    public static void bitmapToFile(Context context, Bitmap bitMap, BitmapToFileListener listener) {
//
//        Observable.create(new ObservableOnSubscribe<File>() {
//            @Override
//            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
//
//                String fileName = AlbumProcessUtil.saveBitmapNoInsert(context, bitMap);
//                File file = new File(fileName);
//                if (file.exists()) {
//                    emitter.onNext(file);
//                } else {
//                    emitter.onError(new Throwable("图片转化异常"));
//                }
//            }
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(File file) {
//                listener.onSucessful(file);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                listener.onFailed(e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//
//    }

}
