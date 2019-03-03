package com.okusr.music.musicbox3;

import android.os.Parcel;
import android.os.Parcelable;

/**
* @date :2017/10/24
* @author lixiang
* @Description:
*/

public class MusicInfo implements Parcelable {
    private long id;
    private String title;//歌曲
    private String album;//音乐专辑
    private int duration; //播放时间长
    private long size;//歌曲大小
    private String artist; //歌手
    private String url; //路径

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected MusicInfo(Parcel in) {
        id = in.readLong();
        title = in.readString();
        album = in.readString();
        duration = in.readInt();
        size = in.readLong();
        artist = in.readString();
        url = in.readString();
    }

    protected MusicInfo(long pId, String pTitle) {
        this.id = pId;
        this.title = pTitle;
    }

    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel in) {
            return new MusicInfo(in);
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(album);
        dest.writeInt(duration);
        dest.writeLong(size);
        dest.writeString(artist);
        dest.writeString(url);
    }
}
