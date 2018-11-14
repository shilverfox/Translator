package com.jbsx.customview.dialog;

import android.content.Context;

/**
 * 统一的对话框工厂类
 */
public class JDDJDialogFactory {

    /**
     * 默认样式
     */
    public static final int TYPE_DEFAULT = 0;
    /**
     * 操作按钮在顶部
     */
    public static final int TYPE_OPERATE_TOP = 1;

    /**
     * 根据type，创建差异的对话框(若没有设置按钮，默认带一个确定按钮用于取消对话框)
     *
     * @param ctx
     * @param type
     * @return
     */
    public final static JDDJDialog createDialog(Context ctx, int type) {
        if (type == TYPE_OPERATE_TOP) {
            return JDDJTopDialog.newInstance().init(ctx);
        } else {
            return JDDJBottomDialog.newInstance().init(ctx);
        }
    }

    /**
     * 创建默认对话框(若没有设置按钮，默认带一个确定按钮用于取消对话框)
     *
     * @param ctx
     * @return
     */
    public final static JDDJDialog createDialog(Context ctx) {
        return createDialog(ctx, TYPE_DEFAULT);
    }

    /**
     * 清理对话框，防止泄露
     *
     * @return
     */
    public final static void clearDialog() {
        JDDJDialog.instance = null;
    }

}
