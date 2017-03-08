package com.translatmaster.net;

import com.translatmaster.utils.LogTools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Response data of http resuqst.
 *
 * Created by lijian15 on 2017/1/13.
 */

public class BaseResponse {
    private boolean mIsSuccessful;
    private String mContent;

    /** Can be used for download some stuff */
    private byte[] mByteData;

    public boolean isSuccessful() {
        return mIsSuccessful;
    }

    public void setSuccessful(boolean successful) {
        mIsSuccessful = successful;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

//    public InputStream getByteStream() {
//        return mByteStream;
//    }

    public byte[] getByteData() {
        return mByteData;
    }

    public void setByteData(byte[] byteData) {
        mByteData = byteData;
    }

    public void setByteStream(InputStream is) {
        if (is == null) {
            return;
        }

        byte buf[] = new byte[256];
        int len;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            baos.flush();

            baos.write(mByteData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {

            }

            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {

            }
        }
    }
}
