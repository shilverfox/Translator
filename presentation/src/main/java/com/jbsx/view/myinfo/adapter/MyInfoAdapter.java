package com.jbsx.view.myinfo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.adapter.UniversalAdapter2;
import com.jbsx.adapter.UniversalViewHolder2;
import com.jbsx.utils.Router;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.entity.MyInfoItem;

/**
 * 我的适配器
 */
public class MyInfoAdapter extends UniversalAdapter2<MyInfoItem> {
    private OnMyItemClickListener onMyItemClickListener;

    public MyInfoAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(UniversalViewHolder2 holder, final MyInfoItem myInfoItem, final int position) {
        if (myInfoItem == null) {
            return;
        }

        // 标题
        holder.setText(R.id.tv_myinfo_item_title, myInfoItem.getTitle());

        // 右边提示
        TextView tvHint = (TextView) holder.getViewById(R.id.tv_myinfo_item_hint);
        boolean isString = String.class.isInstance(myInfoItem.getHint());
        if (isString) {
            String hint = myInfoItem.getHint().toString();
            if (TextUtils.isEmpty(hint)) {
                tvHint.setVisibility(View.GONE);
            } else {
                tvHint.setText(hint);
                tvHint.setTextColor(Color.parseColor(myInfoItem.getHintColor()));
                tvHint.setVisibility(View.VISIBLE);
            }
        } else {
            boolean isSpannableString = SpannableString.class.isInstance(myInfoItem.getHint());
            if (isSpannableString) {
                SpannableString hint2 = (SpannableString) myInfoItem.getHint();
                if (hint2.length() == 0) {
                    tvHint.setVisibility(View.GONE);
                } else {
                    tvHint.setText(hint2);
                    tvHint.setVisibility(View.VISIBLE);
                }
            } else {
                tvHint.setVisibility(View.GONE);
            }
        }

        // 图片
        int imgId = myInfoItem.getImgId();
        ImageView iv = (ImageView) holder.getViewById(R.id.iv_myinfo_item_icon);
        if (imgId != 0) {
            iv.setImageResource(imgId);
            iv.setVisibility(View.VISIBLE);
        } else {
            iv.setVisibility(View.GONE);
        }

        // 顶部空白
        if (myInfoItem.isTopDeliver()) {
            holder.getViewById(R.id.myinfo_item_diliver).setVisibility(View.VISIBLE);
        } else {
            holder.getViewById(R.id.myinfo_item_diliver).setVisibility(View.GONE);
        }

        // 底部线
        if (myInfoItem.isBottomDeliver()) {
            holder.getViewById(R.id.myinfo_item_bottom_divider).setVisibility(View.VISIBLE);
        } else {
            holder.getViewById(R.id.myinfo_item_bottom_divider).setVisibility(View.GONE);
        }

        // 红点
        if (myInfoItem.isNotice()) {
            holder.getViewById(R.id.iv_myinfo_item_notice).setVisibility(View.VISIBLE);
        } else {
            holder.getViewById(R.id.iv_myinfo_item_notice).setVisibility(View.INVISIBLE);
        }

        // 右箭头
        boolean hasNav = (myInfoItem.getTo() != null);
        holder.getViewById(R.id.iv_myinfo_item_forward).setVisibility(hasNav ? View.VISIBLE : View.INVISIBLE);

        holder.getViewById(R.id.rl_myinfo_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 进入到相应界面
                if (onMyItemClickListener != null) {
                    onMyItemClickListener.onClick(position);
                }

                boolean needLogin = myInfoItem.isNeedLogin();
                if (!needLogin || (needLogin && LoginHelper.getInstance().isLogin())) {
                    Class toView = myInfoItem.getTo();
                    if (toView != null) {
                        Router.getInstance().open(myInfoItem.getTo(), (Activity) mContext);
                    }
                } else {
                    LoginHelper.getInstance().showLoginDialog(mContext);
                }
            }
        });
    }

    public interface OnMyItemClickListener {
        void onClick(int id);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }
}
