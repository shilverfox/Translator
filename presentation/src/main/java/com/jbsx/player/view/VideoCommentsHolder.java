package com.jbsx.player.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.model.BaseDomainData;
import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.PushFromBottomDialog;
import com.jbsx.player.util.AlbumDetailUtil;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.UiTools;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.data.UserComments;
import com.jbsx.view.myinfo.view.detail.CommentDetailListHolder;

/**
 * 视频中的评论列表
 */

public class VideoCommentsHolder extends CommentDetailListHolder {
    private final static String[] BAD_COMMENTS_MENU = {"举报", "取消"};

    private ImageView mIvBadComment;
    private Context mContext;
    private PushFromBottomDialog mBadCommentDialog;

    public VideoCommentsHolder(Context context, View view) {
        super(context, view);
        mContext = context;
    }

    @Override
    public void findViews(View rootView) {
        super.findViews(rootView);

        if (rootView != null) {
            mIvBadComment = rootView.findViewById(R.id.iv_video_bad_comment);
        }
    }

    private void handleBadCommentSelect() {
        ProgressBarHelper.addProgressBar(mBadCommentDialog.getContentView());

        mUserCase.requestBadComment(LoginHelper.getInstance().getUserToken(),
                LoginHelper.getInstance().getUserId(), mData.getSpecialAlbumId(),
                mData.getSpecialSingleId(), mData.getId(),
                new BaseRequestCallback() {
                    @Override
                    public void onRequestFailed(BaseDomainData data) {
                        ShowTools.toast("举报失败，请重试");
                        handleBadCommentResult();
                    }

                    @Override
                    public void onRequestSuccessful(String data) {
                        ShowTools.toast("举报成功");
                        handleBadCommentResult();
                    }

                    @Override
                    public void onNetError() {

                    }
                });
    }

    private void handleBadCommentResult() {
        dissBadCommentDialog();
        ProgressBarHelper.removeProgressBar(mBadCommentDialog.getContentView());
    }

    @Override
    public void registerEvent() {
        super.registerEvent();

        mIvBadComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBadCommentDialog();
            }
        });
    }

    @Override
    public void drawViews(UserComments data, final int position) {
        super.drawViews(data, position);
    }

    /**
     * 举报菜单点击事件
     */
    private View.OnClickListener mMenuItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view != null) {
                Object tag = view.getTag();
                if (tag != null && tag instanceof Integer) {
                    handleItemClickByIndex((Integer)tag);
                }
            }
        }
    };

    private void handleItemClickByIndex(int itemIndex) {
        switch (itemIndex) {
            case 0:
                // 举报
                handleBadCommentSelect();
                break;
            case 1:
                // 取消
                dissBadCommentDialog();
                break;
        }
    }

    private void dissBadCommentDialog() {
        if (mBadCommentDialog != null && mBadCommentDialog.isShowing()) {
            mBadCommentDialog.dismiss();
        }
    }

    private void showBadCommentDialog() {
        LinearLayout dialogView = new LinearLayout(mContext);
        dialogView.setOrientation(LinearLayout.VERTICAL);
        int itemHeight = 1;

        for (int i = 0; i < BAD_COMMENTS_MENU.length; i++) {
            View itemView = LayoutInflater.from(MainApplicationLike.getAppContext())
                    .inflate(R.layout.menu_item_text_view, null);
            TextView textView = itemView.findViewById(R.id.tv_menu_item_text);
            textView.setText(BAD_COMMENTS_MENU[i]);
            textView.setTag(i);
            textView.setOnClickListener(mMenuItemClick);

            dialogView.addView(itemView);

            // 动态测量控件大小以设置对话框高度
            UiTools.measureForWrap(itemView);
            itemHeight = itemView.getMeasuredHeight();
        }

        mBadCommentDialog = new PushFromBottomDialog(mContext, dialogView);
        mBadCommentDialog.setSize(WindowManager.LayoutParams.MATCH_PARENT,
                BAD_COMMENTS_MENU.length * itemHeight);
        mBadCommentDialog.show();
    }

    /**
     * 是否可被交互（评论，点赞，点击item）
     * 评论详情页中的item不能继续评论
     *
     * @return
     */
    public boolean canBeInteracted() {
        return true;
    }
}
