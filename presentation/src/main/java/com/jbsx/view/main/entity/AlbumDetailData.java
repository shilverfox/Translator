package com.jbsx.view.main.entity;

import java.util.List;

/**
 * 专辑播放页面数据
 */
public class AlbumDetailData {
    private boolean status;
    private int code;
    private String msg;
    private Result body;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result getBody() {
        return body;
    }

    public void setBody(Result body) {
        this.body = body;
    }

    public class Result {
        private String albumName;
        private String deviceCode;
        private String preview;
        private List<SongInfo> sideA;
        private List<SongInfo> sideB;
        private RepertoryData.MetaData metadata;

        public List<SongInfo> getSideA() {
            return sideA;
        }

        public void setSideA(List<SongInfo> sideA) {
            this.sideA = sideA;
        }

        public List<SongInfo> getSideB() {
            return sideB;
        }

        public void setSideB(List<SongInfo> sideB) {
            this.sideB = sideB;
        }

        public String getAlbumName() {
            return albumName;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(String deviceCode) {
            this.deviceCode = deviceCode;
        }

        public RepertoryData.MetaData getMetadata() {
            return metadata;
        }

        public void setMetadata(RepertoryData.MetaData metadata) {
            this.metadata = metadata;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }
    }

    public class SongInfo {
        private String songFilePath;
        private String songName;
        private String songTime;

        public String getSongFilePath() {
            return songFilePath;
        }

        public void setSongFilePath(String songFilePath) {
            this.songFilePath = songFilePath;
        }

        public String getSongName() {
            return songName;
        }

        public void setSongName(String songName) {
            this.songName = songName;
        }

        public String getSongTime() {
            return songTime;
        }

        public void setSongTime(String songTime) {
            this.songTime = songTime;
        }
    }


}
