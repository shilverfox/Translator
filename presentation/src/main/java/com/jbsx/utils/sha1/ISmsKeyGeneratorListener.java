package com.jbsx.utils.sha1;

/**
 * 返回sha加密结果及对应的参数
 */
public interface ISmsKeyGeneratorListener {
    void onShaComplete(String timeStamp, String randomInt, String resultKey);
}
