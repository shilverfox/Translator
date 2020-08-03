package com.jbsx.hotfix;

import android.os.Environment;

import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.LogTools;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.SystemUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Hot fix
 *
 * Created by lijian15 on 2017/3/8.
 */

public class HotFixHelper {
    private final static String TAG = "HotFixHelper";

    public final static String PATCH_NAME_IN_SD = "patch_signed_7zip.apk";

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
                File file = new File(SDPath, PATCH_NAME_IN_SD);
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
                + PATCH_NAME_IN_SD;
        File patchFile = new File(patchPath);

        if (patchFile.exists()) {
            ShowTools.toast("Found patch file!");
            TinkerInstaller.onReceiveUpgradePatch(MainApplicationLike.getAppContext(), patchPath);
        } else {
            ShowTools.toast("No patch file!!!");
        }
    }

    public static void checkHotFix() {
//        Func1 dataAction = new Func1() {
//            @Override
//            public Object call(Object o) {
//                return TaskManager.getTaskManager().getHotFixPatch();
//            }
//        };
//
//        Action1 viewAction = new Action1<BaseDominData>() {
//
//            @Override
//            public void call(BaseDominData response) {
//                if (response != null) {
//                    // Download the patch and save to sd card
//                    downloadPatch(response.getBaseResponse().getByteData());
//                    executePatch();
//                }
//            }
//        };
//
//        Observable.just("").observeOn(Schedulers.io())
//                .map(dataAction)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(viewAction);
    }
}
