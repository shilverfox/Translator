//package com.translatmaster.utils;
//
//import android.app.Activity;
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.View.OnClickListener;
//
//import com.google.gson.Gson;
//import com.tencent.bugly.beta.Beta;
//import com.tencent.bugly.beta.UpgradeInfo;
//
//import java.io.File;
//import java.io.IOException;
//
//import base.net.open.JDErrorListener;
//import base.net.open.JDListener;
//import base.net.open.JDStringRequest;
//import base.net.open.RequestEntity;
//import base.utils.ApkTools;
//import base.utils.EventBusManager;
//import base.utils.FilePathSelector;
//import jd.app.EventBusConstant;
//import jd.app.JDApplication;
//import jd.config.ConfigHelper;
//import jd.net.PDJRequestManager;
//import jd.net.ServiceProtocol;
//import jd.point.DataPointUtils;
//
///**
// *
// * @author 123
// */
//public class NewUpdateServer {
//
//    public interface UpdateListener {
//        void onSuccess(boolean isBugly, UpgradeInfo updateInfo, VersionData versionData);
//        void onFailed(String error);
//    }
//
//    /**
//     * 通用检测更新
//     *
//     * @param  updateListener 检测结果回调
//     */
//    public static void checkUpdateCommon(UpdateListener updateListener){
//        if (ConfigHelper.getInstance().isSpecalChannel()){
//            checkUpdateViaBugly(updateListener, false);
//        }else {
//            checkUpdate(updateListener);
//        }
//    }
//
//    /**
//     * 通用检测更新
//     *
//     * @param  updateListener 检测结果回调
//     */
//    public static void checkUpdateCommon(UpdateListener updateListener, boolean isManual){
//        if (ConfigHelper.getInstance().isSpecalChannel()){
//            checkUpdateViaBugly(updateListener, isManual);
//        }else {
//            checkUpdate(updateListener);
//        }
//    }
//
//    /**
//     * 自定义检测更新
//     *
//     * @param updateListener 检测结果回调
//     */
//    public static void checkUpdate(final UpdateListener updateListener) {
//        JDApplication.getInstance().versionData = null;
//        RequestEntity requestEntity = ServiceProtocol.getUpdateResponse();
//        requestEntity.isHandleLogin = false;
//        JDListener<String> listener = new JDListener<String>() {
//
//            @Override
//            public void onResponse(String string) {
//                Gson gson = new Gson();
//                try {
//                    VersionData versionData = gson.fromJson(string, VersionData.class);
//                    if (versionData.code.equals("301")) {
//                        versionData.newResult();
//                        versionData.result.forceUpdate = true;
//                        versionData.result.newest = false;
//                        versionData.result.discrible = versionData.msg;
//                        versionData.result.newestDownloadUrl = (String) versionData.detail;
//                        versionData.result.currentVersion = "V3.8.0";
//                        versionData.success = true;
//                    }
//                    JDApplication.getInstance().versionData = versionData;
//                    if (!versionData.getNewest()) {
//                        if (updateListener != null) {
//                            updateListener.onSuccess(false, null, versionData);
//                        }
//                    }else {
//                        if (updateListener != null) {
//                            updateListener.onFailed("已是最新版！");
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    if (updateListener != null) {
//                        updateListener.onFailed(e.getMessage());
//                    }
//                }
//            }
//        };
//
//        JDErrorListener errorListener = new JDErrorListener() {
//            @Override
//            public void onErrorResponse(String error, int code) {
//                if (updateListener != null) {
//                    updateListener.onFailed(error);
//                }
//            }
//        };
//
//        JDStringRequest stringRequest = new JDStringRequest(requestEntity, listener, errorListener);
//        PDJRequestManager.addRequest(stringRequest, "checkUpdate");
//    }
//
//    /**
//     * bugly检测更新
//     *
//     * @param updateListener 检测结果回调
//     */
//    public static void checkUpdateViaBugly(final UpdateListener updateListener, boolean isManual){
//        JDApplication.getInstance().versionData = null;
//        EventBusManager.getInstance().register(new Object(){
//
//            public void onEvent(EventBusConstant.OnCheckVersionEvent checkVersionEvent){
//                EventBusManager.getInstance().unregister(this);
//                if (updateListener == null) {
//                    return;
//                }
//                if (checkVersionEvent != null){
//                    if (checkVersionEvent.ret == 0) {
//                        if (checkVersionEvent.strategy instanceof UpgradeInfo){
//                            updateListener.onSuccess(true, (UpgradeInfo) checkVersionEvent.strategy, null);
//                        }else {
//                            updateListener.onFailed("已是最新版！");
//                        }
//                    }else {
//                        updateListener.onFailed("请求失败！");
//                    }
//                }
//            }
//        });
//        Beta.checkUpgrade(isManual, true);
//    }
//
//    //非wifi或APK存在显示弹窗，APK不存在wifi自动下载
//    public static void showUpdateDialog(final FragmentActivity activity, VersionData versionData) {
//
//        if (versionData == null || versionData.success == false) {
//            return;
//        }
//        if (versionData.getNewest() == false) {
//            if (versionData.getCurrentVersion() != null) {
//
//                if (DownApkDialog.isApkExists(versionData.getCurrentVersion())) {
//                    showDialog(activity, versionData, false);
//                    return;
//                }
//                ConnectivityManager connectMgr = (ConnectivityManager) JDApplication.getInstance()
//                        .getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo info = connectMgr.getActiveNetworkInfo();
//                if ((info.getType() != ConnectivityManager.TYPE_WIFI) || versionData.result.forceUpdate) {
//                    showDialog(activity, versionData, false);
//                    return;
//                }
//                DownApkDialog.downloadApk(versionData.result.forceUpdate, versionData.result.newestDownloadUrl, versionData.getCurrentVersion());
//            }
//        }
//    }
//
//    /**
//     * 直接显示弹窗
//     * @param activity
//     * @param versionData 升级数据
//     * @param isManual 是否是手动点击
//     */
//    public static void showDialog(final FragmentActivity activity, final VersionData versionData, final boolean isManual) {
//
//        if (versionData == null || versionData.success == false) {
//            return;
//        }
//        final boolean isForceUpdate = versionData.result.forceUpdate;
//        final String urlString = versionData.result.newestDownloadUrl;
//        final String version = versionData.result.currentVersion;
//
//        final boolean isapk = DownApkDialog.isApkExists(versionData.getCurrentVersion());
//
//        OnClickListener okListener = new OnClickListener() {
//            public void onClick(View v) {
//                if (isManual) {
//                    DataPointUtils.addClick(activity, null, "update_now");
//                } else {
//                    createMdFile("ok");
//                }
//                if (isapk) {
//                    DataPointUtils.addClick(activity,null,"upgrade","type","无流量安装");
//                    String apkName = FilePathSelector.getPath(FilePathSelector.SPACE_ONLY_EXTERNAL, "temp", "paidaojia_" + versionData.getCurrentVersion() + ".apk");
//                    ApkTools.installAPK(apkName, JDApplication.getInstance());
//                } else {
//                    if(isForceUpdate){
//                        DataPointUtils.addClick(activity,null,"upgrade","type","立即升级");
//                    }else{
//                        DataPointUtils.addClick(activity,null,"upgrade","type","立即下载");
//                    }
//                    DownApkDialog.newInstance(isForceUpdate, version, urlString, new OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            if (activity != null && activity instanceof Activity) {
//                                activity.finish();
//                            }
//                        }
//                    }).show(activity);
//                }
//            }
//        };
//        OnClickListener cacelListener = new OnClickListener() {
//            public void onClick(View v) {
//                if(isapk) {
//                    DataPointUtils.addClick(activity, null,"upgrade", "type", "暂不安装");
//                }else{
//                    DataPointUtils.addClick(activity, null,"upgrade", "type", "暂不升级");
//                }
//                UpdateDialog d = (UpdateDialog) v.getTag();
//                d.dismissAllowingStateLoss();
//                if (!isManual){
//                    createMdFile("can");
//                }
//            }
//        };
//        if (isManual) {
//            DataPointUtils.addClick(activity, null, "update_remind");
//        }
//        if (isapk)
//            if (isForceUpdate) {
//                UpdateDialog.newInstance(versionData.getDiscrible(), "无流量安装", okListener, "", null).show(activity);
//            } else {
//                UpdateDialog.newInstance(versionData.getDiscrible(), "无流量安装", okListener, "暂不安装", cacelListener).show(activity);
//            }
//        else {
//            if (isForceUpdate) {
//                UpdateDialog.newInstance(versionData.getDiscrible(), "立即升级", okListener, "", null).show(activity);
//            } else {
//                UpdateDialog.newInstance(versionData.getDiscrible(), "立即下载", okListener, "暂不升级", cacelListener).show(activity);
//            }
//        }
//    }
//
//    /**
//     * 显示buglyDialog
//     *
//     * @param activity
//     * @param isManual 是否手动触发检测
//     */
//    public static void showBuglyDialog(FragmentActivity activity, UpgradeInfo upgradeInfo, boolean isManual){
//        VersionData versionData = translate(upgradeInfo);
//        showDialog(activity, versionData, isManual);
//    }
//
//    /**
//     * 显示buglyDialog
//     * 非wifi或APK存在显示弹窗，APK不存在wifi自动下载
//     *
//     * @param activity
//     * @param upgradeInfo
//     */
//    public static void showBuglyUpdateDialog(FragmentActivity activity, UpgradeInfo upgradeInfo){
//        VersionData versionData = translate(upgradeInfo);
//        showUpdateDialog(activity, versionData);
//    }
//
//    /**
//     * 显示buglyDialog
//     *
//     * @param upgradeInfo 升级信息
//     * @param isManual 是否手动触发检测
//     */
//    public static void showBuglyDialog(UpgradeInfo upgradeInfo, boolean isManual){
//        if (upgradeInfo != null) {
//            String title = upgradeInfo.title;
//            int upgradeType = upgradeInfo.upgradeType;
//            String newFeature = upgradeInfo.newFeature;
//            long publishTime = upgradeInfo.publishTime;
//            int buildNo = 1;
//            int versionCode = upgradeInfo.versionCode;
//            String versionName = upgradeInfo.versionName;
//            String apkUrl = upgradeInfo.apkUrl;
//            long fileSize = upgradeInfo.fileSize;
//            String apkMd5 = upgradeInfo.apkMd5;
//            String imageUrl = upgradeInfo.imageUrl;
//            int dialogStyle = 0;
//            Beta.showUpgradeDialog(title, upgradeType, newFeature, publishTime, buildNo, versionCode, versionName,
//                   apkUrl, fileSize, apkMd5, imageUrl, dialogStyle, null, null, null, isManual);
//        }
//    }
//
//    public static VersionData translate(UpgradeInfo upgradeInfo){
//        VersionData versionData = null;
//        if (upgradeInfo != null){
//            versionData = new VersionData();
//            versionData.success = true;
//            versionData.code = "0";
//            versionData.msg = "成功";
//            versionData.detail = "fromBugly";
//            versionData.newResult();
//            versionData.result.currentVersion = upgradeInfo.versionName;
//            versionData.result.discrible = upgradeInfo.newFeature;
//            versionData.result.newest = false;
//            versionData.result.forceUpdate = upgradeInfo.upgradeType == 2 ? true : false;
//            versionData.result.newestDownloadUrl = upgradeInfo.apkUrl;
//            versionData.result.updateMessage = upgradeInfo.newFeature;
//        }
//        return versionData;
//    }
//
//    //埋点用
//    private static void createMdFile(String click_id) {
//        File cacheDirFile = JDApplication.getInstance().getCacheDir();//getFilesDir();//
//        String cacheDir = cacheDirFile.toString();
//        File file = new File(cacheDir, "jdcccmd_" + click_id);
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
