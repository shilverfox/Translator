package com.jbsx.view.search.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 搜索数据，用来被Event bus发出
 */
public class SearchEvent implements Parcelable {
    public final static int SEARCH_TYPE_TITLE_CELEBRITY = 0;
    public final static int SEARCH_TYPE_CELEBRITY = 2;
    public final static int SEARCH_TYPE_TITLE = 1;

    /** 排序：热门 */
    public final static int SEARCH_SORT_BY_HOT = 1;

    /** 排序：非热门 */
    public final static int SEARCH_SORT_BY_NOT_HOT = 2;

    /** 排序：集数 正序 */
    public final static int SEARCH_SORT_BY_COUNT_INCREASE = 3;

    /** 排序：集数 倒序 */
    public final static int SEARCH_SORT_BY_COUNT_DESCENT = 4;

    /** 排序：名称 正序 */
    public final static int SEARCH_SORT_BY_NAME_INCREASE = 5;

    /** 排序：名称 倒序 */
    public final static int SEARCH_SORT_BY_NAME_DESCENT = 6;

    private int celebrityId;
    private String searchKey;

    /** 0 标题+主讲 2主讲 1标题 (默认值：0) */
    private int searchType;

    /** 排序热门1热门2非热门 */
    private int sort;

    /** 默认选中的tab索引 */
    private int defaultFocus;

    public SearchEvent(int celebrityId, String searchKey, int searchType, int sort) {
        this.celebrityId = celebrityId;
        this.searchKey = searchKey;
        this.searchType = searchType;
        this.sort = sort;
    }

    public int getDefaultFocus() {
        return defaultFocus;
    }

    public void setDefaultFocus(int defaultFocus) {
        this.defaultFocus = defaultFocus;
    }

    public int getCelebrityId() {
        return celebrityId;
    }

    public void setCelebrityId(int celebrityId) {
        this.celebrityId = celebrityId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.celebrityId);
        dest.writeString(this.searchKey);
        dest.writeInt(this.searchType);
        dest.writeInt(this.sort);
        dest.writeInt(this.defaultFocus);
    }

    protected SearchEvent(Parcel in) {
        this.celebrityId = in.readInt();
        this.searchKey = in.readString();
        this.searchType = in.readInt();
        this.sort = in.readInt();
        this.defaultFocus = in.readInt();
    }

    public static final Parcelable.Creator<SearchEvent> CREATOR = new Parcelable.Creator<SearchEvent>() {
        @Override
        public SearchEvent createFromParcel(Parcel source) {
            return new SearchEvent(source);
        }

        @Override
        public SearchEvent[] newArray(int size) {
            return new SearchEvent[size];
        }
    };
}
