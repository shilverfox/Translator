package com.jbsx.view.main.datapoint;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.MainPageUserCase;
import com.jbsx.app.MainApplicationLike;

/**
 * 数据埋点
 */
public class DataPointManager {
    private static DataPointManager mInstance;

    private int resourceType;
    private String resourceCode;
    private String classifyCode;

    private MainPageUserCase mUserCase;

    private DataPointManager() {
        mUserCase = new MainPageUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    public static DataPointManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataPointManager();
        }
        return mInstance;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
    }

    public void sendPlayRecord() {
        mUserCase.sendPlayAccord(classifyCode, resourceCode, resourceType);
    }
}
