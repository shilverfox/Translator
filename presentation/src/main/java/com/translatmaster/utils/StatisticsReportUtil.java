package com.translatmaster.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.translatmaster.app.MainApplicationLike;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;

public class StatisticsReportUtil {

    static String uuidMd5 = "";
    static String versionName = "";
    static String showVersionName = "";
    static String versionCode = "";
    static String deviceModel = "";
    static String screen = "";
    static BigInteger sUUIDMD5Decimal = null;
    static String netString = "";
    static String mImei = "";
    static String mProvidersName = "";
    static String mTraceid = "";

    /**
     * 获取device Id 失败后的默认值
     */
    private final static String DEFAULT_DEVICE_ID = "daojia";

    public static void destroyDeviceInfoStr() {
        netString = "";
        uuidMd5 = "";
        versionName = "";
        deviceModel = "";
    }

    public static String getdeviceModel() {
        if (TextUtils.isEmpty(deviceModel)) {
            deviceModel = spilitSubString((Build.MODEL), 12);
        }
        return deviceModel;
    }

    /**
     * 获取手机的IP
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            return null;
        }
        return null;
    }


    /**
     * 取得DeviceId
     */
    public static String getUUIDMD5() {
        if (TextUtils.isEmpty(uuidMd5)) {
            uuidMd5 = MD5Calculator.calculateMD5(getUUID());
        }
        return uuidMd5;
    }

    /**
     * 取得DeviceId
     */
    public static String getUUIDMD5Decimal() {
        if (sUUIDMD5Decimal != null) {
            sUUIDMD5Decimal = new BigInteger(getUUIDMD5(), 16);
        }

        return sUUIDMD5Decimal.toString(10);
    }

    public static String getDeviceId() {
        String deviceId = null;
        try {
            TelephonyManager tm = (TelephonyManager) MainApplicationLike.getAppContext().getSystemService(
                    Context.TELEPHONY_SERVICE);

            if (Build.VERSION.SDK_INT >= 26) {
                deviceId = tm.getImei();
            } else {
                deviceId = tm.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(deviceId)) {
            // 获取失败，使用“默认值+时间戳”
            deviceId = DEFAULT_DEVICE_ID + System.currentTimeMillis();
        }

        return deviceId;
    }


    /**
     * 取得UUID
     */
    private static String getUUID() {
        return getDeviceId() + Build.SERIAL;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取SIM国家码
     *
     * @return
     */
    public static String getSimCountryCode() {
        String code = "";
        try {
            TelephonyManager telManager = (TelephonyManager) MainApplicationLike.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
            code = telManager.getSimCountryIso();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return code;
    }

    public static String getNetworkTypeName(Context myContext) {
        if (myContext == null) {
            return "UNKNOWN";
        }
        if (TextUtils.isEmpty(netString)) {

            ConnectivityManager cm = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            TelephonyManager tm = (TelephonyManager) myContext.getSystemService(Context.TELEPHONY_SERVICE);

            NetworkInfo[] netinfo = cm.getAllNetworkInfo();
            try {
                for (int i = 0; i < netinfo.length; i++) {
                    if (netinfo[i].isConnected()) {
                        if (netinfo[i].getTypeName().toUpperCase().contains("MOBILE")) {
                            netString = tm.getNetworkType() + "";
                        } else if (netinfo[i].getTypeName().toUpperCase().contains("WIFI")) {
                            netString = "WIFI";
                        } else {
                            netString = "UNKNOWN";
                        }
                    }
                }
            } catch (Exception e) {
                netString = "UNKNOWN";
            }
            if (netString == null) {
                netString = "UNKNOWN";
            }
        }

        return netString;

    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isGpsOPen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 按指定长度截取串
     *
     * @param value
     * @param length
     * @return
     */
    private static String spilitSubString(String value, int length) {
        try {
            if (value != null && value.length() > length) {
                value = value.substring(0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    private static String macAddress;// 保存得到的Mac地址

    /**
     * 判断deivceUUID是否有效
     */
    private static boolean isValidDeviceUUID(String deivceUUID) {
        if (TextUtils.isEmpty(deivceUUID)) {// 整个都是空那肯定无效
            return false;
        }
        String[] split = deivceUUID.split("-");
        if (split.length > 1) {// 如果参数量不足2认为无效
            return !TextUtils.isEmpty(split[1]);// 如果没有MAC那么认为无效
        }
        return false;
    }

    /**
     * 得到当前版本信息 3位
     */
    public static String getSimpleVersionName(Context cxt) {
        if (!TextUtils.isEmpty(versionName)) {
            return versionName;
        }
        PackageInfo packageInfo = getPackageInfo(cxt);
        if (null == packageInfo) {
            return "";
        }
        versionName = packageInfo.versionName;

        return versionName;
    }

    /**
     * 得到当前版本信息 3位
     */
    public static String getSimpleVersionName() {
        return getSimpleVersionName(MainApplicationLike.getAppContext());
    }


    /**
     * 全版本号
     */
    public static String getVersionName() {
        return getVersionName(MainApplicationLike.getAppContext());
    }

    /**
     * 全版本号
     */
    public static String getVersionName(Context Context) {
        return getSimpleVersionName(Context);
    }

    /**
     * 得到当前版本信息
     */
    public static int getSoftwareVersionCode() {
        PackageInfo packageInfo = getPackageInfo();
        if (null == packageInfo) {
            return 0;
        }
        return packageInfo.versionCode;
    }

    private static PackageInfo getPackageInfo() {
        try {
            Context cxt = MainApplicationLike.getAppContext();
            return getPackageInfo(cxt);
        } catch (Exception e) {
            return null;
        }
    }


    private static PackageInfo getPackageInfo(Context cxt) {
        try {
            PackageInfo packageInfo = cxt.getPackageManager().getPackageInfo(cxt.getPackageName(), 0);
            return packageInfo;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取制造商信息
     *
     * @return
     */
    public static String getBrand() {
        String brand = "";
        try {
            brand = spilitSubString(Build.MANUFACTURER, 12).replaceAll(" ", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return brand;
    }

    /**
     * 获取机器的型号
     *
     * @return
     */
    public static String getModel() {
        String model = "";
        try {
            model = spilitSubString((Build.MODEL), 12).replaceAll(" ", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        Display display = ((WindowManager) MainApplicationLike.getAppContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        return display.getWidth();
    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        Display display = ((WindowManager) MainApplicationLike.getAppContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        return display.getHeight();
    }

    /**
     * 获取进程名字
     */
    public static String getProcessName(Context ctx) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) ctx
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /*
     * 得到签名
     */
    public static String getSign() {
        try {
            String packageName = MainApplicationLike.getAppContext().getPackageName();
            PackageInfo packageInfo = MainApplicationLike.getAppContext().getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            byte[] signature = sign.toByteArray();
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signature));
            String ss = StringToolBox.getHexString(cert.getPublicKey().getEncoded());
            return ss;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getImei() {
        if (!TextUtils.isEmpty(mImei)) {
            return mImei;
        }

        mImei = getDeviceId();
        return mImei;
    }

    public static String getTranceid() {
        if (!TextUtils.isEmpty(mTraceid)) {
            return mTraceid;
        }
        mTraceid = getUUIDMD5() + System.currentTimeMillis();
        return mTraceid;
    }

    public static String getMacAddress() {
        if (!TextUtils.isEmpty(macAddress)) {
            return macAddress;
        }
        try {
            WifiManager wifi = (WifiManager) MainApplicationLike.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            macAddress = info.getMacAddress();
            ;
        } catch (Exception e) {
            macAddress = "00:00:00:00:00:00";
        }
        return macAddress;
    }

    /**
     * 返回手机运营商
     *
     * @return
     */
    public static String getProvidersName() {
        if (!TextUtils.isEmpty(mProvidersName)) {
            return mProvidersName;
        }
        String ProvidersName = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) MainApplicationLike.getAppContext().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            String IMSI = telephonyManager.getSubscriberId();
            if (IMSI == null) {
                return "unknow";
            }

            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                ProvidersName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                ProvidersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                ProvidersName = "中国电信";
            }
            ProvidersName = URLEncoder.encode("" + ProvidersName, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ProvidersName;
    }
}
