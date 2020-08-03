package com.jbsx.weixin;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.jbsx.app.BaseActivity;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.LogTools;
import com.jbsx.utils.ShowTools;
import com.jbsx.view.login.thirdapi.LoginThirdResultEvent;

/**
 * 微信返回请求结果，分享、登录公用
 * 微信app会默认调用此类，官方要求类名包名均按这个要求来命名
 *
 * @author Li Jian
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private IWXAPI mWxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 当接收微信返回情况时用三个参数的
        mWxApi = WXAPIFactory.createWXAPI(this, MainApplicationLike.APP_ID, false);
        mWxApi.registerApp(MainApplicationLike.APP_ID);
        mWxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        mWxApi.handleIntent(intent, this);
    }

    /**
     * 微信返回的相应码
     */
    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp == null) {
            return;
        }

        LogTools.e("WXEntryActivity", "  code = " +
                baseResp.errCode + "  ,msg = " +
                baseResp.errStr + ", type = " +
                baseResp.getType());

        // 根据type类型来区分消息属性
        int type = baseResp.getType();

        switch (type) {
            case ConstantsAPI.COMMAND_SENDAUTH:
                // 来自于授权登录的返回
                handleResOfLogin(baseResp);
                break;
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                // 分享
                handleResOfShare(baseResp.errCode);
                break;
        }

        finish();
    }

    /**
     * 处理授权登录返回
     *
     * @param resp
     */
    private void handleResOfLogin(BaseResp resp) {
        if (resp == null) {
            return;
        }

        String result = "";
        int errCode = resp.errCode;
        String rpsCode = "";

        switch (errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "授权成功";

                rpsCode = ((SendAuth.Resp) resp).code;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "拒绝授权";
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                result = "授权失败";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消授权";
                break;
            default:
                result = "未知错误";
                break;
        }

        if (eventBus != null) {
            eventBus.post(new LoginThirdResultEvent(rpsCode));
        }
        ShowTools.toast(result);
    }

    /**
     * 分享
     *
     * @param code
     */
    private void handleResOfShare(int code) {
        String result = "";

        switch (code) {
            case BaseResp.ErrCode.ERR_OK:
                WXShareHelper.getInstance().handleResOfShareSuccess();
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "用户拒绝";
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                result = "分享失败";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                break;
            default:
                result = "未知错误";
                break;
        }
        if (eventBus != null) {
            eventBus.post(new WechatEvent(result));
        }
        ShowTools.toast(result);
    }

    @Override
    public void onReq(BaseReq arg0) {
//        if (arg0.getType() == ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX) {
//            if (DataCore.getsInstance().isStarted) {
//                ActivityManager manager = (ActivityManager) this
//                        .getSystemService(Context.ACTIVITY_SERVICE);
//                List<ActivityManager.RunningTaskInfo> task_info = manager
//                        .getRunningTasks(20);
//
//                String className = "";
//                for (int i = 0; i < task_info.size(); i++) {
//                    if (this.getPackageName().equals(task_info
//                            .get(i).topActivity.getPackageName())) {
//                        manager.moveTaskToFront(task_info.get(i).id, ActivityManager.MOVE_TASK_WITH_HOME);//关键
//                        className = task_info.get(i).topActivity.getClassName();
//                        LogTools.e("onReq", "packagename  " + task_info
//                                .get(i).topActivity.getPackageName());
//                        LogTools.e("onReq", "className  " + className);
//
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_MAIN);
//                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                        try {
//                            intent.setComponent(new ComponentName(this, Class.forName(className)));//
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        }
//
//                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//                                | Intent.FLAG_ACTIVITY_NEW_TASK
//                                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                        this.startActivity(intent);
//                    }
//                }
//            } else {
//                LogTools.e("onReq", "start");
//                Intent intent = new Intent(this, NewStartActivity.class);
//                this.startActivity(intent);
//            }
//            finish();
//        }
    }


}
