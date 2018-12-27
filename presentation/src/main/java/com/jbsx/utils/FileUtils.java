package com.jbsx.utils;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 文件操作类
 * Created by Nereo on 2015/4/8.
 */
public class FileUtils {

    public static File createTmpFile(Context context) {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 已挂载
//            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            //如果需要照片存在本地，则注释上面的代码，打开下面的代码--start
            File pic = null;
            pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+ "/Camera");
            if (!pic.exists()) {
                pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            }
            //-------end
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_" + timeStamp + "";
            File tmpFile = new File(pic, fileName + ".jpg");
            return tmpFile;
        } else {
            File cacheDir = context.getCacheDir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_" + timeStamp + "";
            File tmpFile = new File(cacheDir, fileName + ".jpg");
            return tmpFile;
        }

    }

    /**
     * 计算缓存大小,包括内部、外部缓存
     *
     * @param
     */
    public static String totalCache(Context mContext) {
        File file = mContext.getCacheDir();
        long size = getFolderSize(file);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            size += getFolderSize(mContext.getExternalCacheDir());
        }
        return getFormatSize(size);
    }

    /**
     * 计算文件夹大小
     *
     * @param
     */
    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] filelist = file.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                if (filelist[i].isDirectory()) {
                    size = size + getFolderSize(filelist[i]);
                } else {
                    size = size + filelist[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 缓存大小以正确的格式显示
     *
     * @param size
     * @return
     */

    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1)
            return "0K";
        else if (kiloByte < 1024) {
            BigDecimal result1 = new BigDecimal(kiloByte);
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1024) {
            BigDecimal result2 = new BigDecimal(megaByte);
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1024) {
            BigDecimal result3 = new BigDecimal(gigaByte);
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1024) {
            BigDecimal result4 = new BigDecimal(teraBytes);
            return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
        }
        BigDecimal result5 = new BigDecimal(teraBytes);
        return result5.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    /**
     * 清空缓存
     */
    public static boolean deleteCache(File dir) {
        if (dir == null) {
            return true;
        } else if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteCache(new File(dir, children[i]));
                if (!success)
                    return false;
            }
            return true;
        } else {
            return dir.delete();
        }
    }

    public static byte[] getBytesFromFile(File file) {
        byte[] data = null;
        if (file != null) {
            FileInputStream fis = null;
            ByteArrayOutputStream bos = null;

            try {
                fis = new FileInputStream(file);
                bos = new ByteArrayOutputStream();

                byte[] b = new byte[1024];
                int n;

                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }

                data = bos.toByteArray();
            } catch (FileNotFoundException ex) {
                LogTools.e(ex.toString());
            } catch (IOException ex) {
                LogTools.e(ex.toString());
            } finally {
                try {
                    if (null != bos) {
                        bos.close();
                    }
                } catch (IOException ex) {
                    LogTools.e(ex.toString());
                } finally{
                    try {
                        if(null!=fis){
                            fis.close();
                        }
                    } catch (IOException ex) {
                        LogTools.e(ex.toString());
                    }
                }
            }
        }

        return data;
    }
}
