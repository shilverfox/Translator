package com.jbsx.customview.recyclerview;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by cundong on 2015/10/9.
 * <p/>
 * 继承自RecyclerView.OnScrollListener，可以监听到是否滑动到页面最低部
 */
public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements OnListLoadNextPageListener {
    private AppBarLayout appBarLayout;
    /**
     * 当前RecyclerView类型
     */
    protected LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;

    private int dy;

    private int mScrolledYDistance = 0;

    private int mnewScrolledYDistance=0;//专门为置顶按钮点击事件设置，表示距标题的真实距离


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

//        DLog.i("scroll","dy: "+dy);
        mScrolledYDistance+=dy;
        mnewScrolledYDistance+=dy;
        onFloating(mScrolledYDistance,dy);
        onReFloating(mnewScrolledYDistance,dy,lastVisibleItemPosition);
        this.dy=dy;
        if(appBarLayout!=null){
            appBarLayout.requestLayout();
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        switch (newState){
            case RecyclerView.SCROLL_STATE_DRAGGING:
                //  JDDJImageLoader.getInstance().pause();
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                //  JDDJImageLoader.getInstance().pause();
                break;
            case RecyclerView.SCROLL_STATE_IDLE:
                //  JDDJImageLoader.getInstance().resume();
                break;
        }

        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();


        if((lastVisibleItemPosition-visibleItemCount+1)>=1){
            if(totalItemCount>visibleItemCount){
                onBackTopSateChange(true,dy >= 0);
            }else{
                onBackTopSateChange(false,dy >= 0);

            }
        }else{
            onBackTopSateChange(false,dy >= 0);
            if(dy==0) {
                mnewScrolledYDistance = 0;
            }
        }
        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE&&dy>0 && (lastVisibleItemPosition) >= totalItemCount - 1)) {
            onLoadNextPage(recyclerView);
        }
    }

    /**
     * 取数组中最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    @Override
    public void onLoadNextPage(final View view) {
    }



    @Override
    public void onBackTopSateChange(boolean isBackTop,boolean upOrDown) {

    }

    @Override
    public void onFloating(int scrollerY,int dy) {

    }

    @Override
    public void onReFloating(int scrollerY, int dy,int lastVisibleItemPosition) {

    }

    public static enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }

    public void setView(AppBarLayout appBarLayout){
        this.appBarLayout=appBarLayout;
    }
}
