package com.translatmaster.hotfix;

import android.os.Environment;

import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.translatmaster.app.MainApplicationLike;
import com.translatmaster.data.ConstData;
import com.translatmaster.data.HttpRequestPool;
import com.translatmaster.net.BaseResponse;
import com.translatmaster.net.RequestManager;
import com.translatmaster.utils.LogTools;
import com.translatmaster.utils.ShowTools;
import com.translatmaster.utils.SystemUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Hot fix
 *
 * Created by lijian15 on 2017/3/8.
 */

public class HotFixHelper {
    private final static String TAG = "HotFixHelper";

    /**
     * Download patch file from server
     *
     * @param data
     */
    private static void downloadPatch(byte[] data) {
        FileOutputStream fos = null;
        String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        byte[] byteData = data;

        if (byteData != null && byteData.length > 0) {
            try {
                File file = new File(SDPath, ConstData.PATCH_NAME_IN_SD);
                fos = new FileOutputStream(file);

                fos.write(byteData, 0, byteData.length);

                LogTools.e(TAG, "Patch of hot-fix download successful."
                        + " | total length: "+ byteData.length
                        + " | version: " + SystemUtil.getAppVersion());
            } catch (IOException e) {
                LogTools.e(TAG, "Patch of hot-fix download failed.");
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    LogTools.e(TAG, "An Exception has been thrown");
                }
            }
        }
    }

    /**
     * Do the patch
     */
    private static void executePatch() {
        String patchPath  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                + ConstData.PATCH_NAME_IN_SD;
        File patchFile = new File(patchPath);

        if (patchFile.exists()) {
            ShowTools.toast("Found patch file!");
            TinkerInstaller.onReceiveUpgradePatch(MainApplicationLike.getAppContext(), patchPath);
        } else {
            ShowTools.toast("No patch file!!!");
        }
    }

    public static void checkHotFix() {
        Func1 dataAction = new Func1() {
            @Override
            public Object call(Object o) {
                return RequestManager.setRequestForRx(HttpRequestPool.getHotFixPatch());
            }
        };

        Action1 viewAction = new Action1<BaseResponse>() {

            @Override
            public void call(BaseResponse response) {
                if (response != null) {
                    // Download the patch and save to sd card
                    downloadPatch(response.getByteData());
                    executePatch();
                }
            }
        };

        Observable.just("").observeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewAction);
    }
}