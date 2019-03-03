package com.okusr.music.musicbox3;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiang
 * @date :2017/10/23
 * @Description:
 */

public class MusicLoader {
    private static final String TAG = "MusicLoader";
    public static List<MusicInfo> musicList = new ArrayList<MusicInfo>();

    private static MusicLoader musicLoader;

    private static ContentResolver contentResolver;
    //Uri，指向external的database
    private Uri contentUri = Media.EXTERNAL_CONTENT_URI;
    /**
     * 查询返回内容
     */
    private String[] projection = {
            Media._ID,
            Media.DISPLAY_NAME,
            Media.DATA,
            Media.ALBUM,
            Media.ARTIST,
            Media.DURATION,
            Media.SIZE
    };
    private String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') and bucket_display_name <> 'audio' and is_music > 0";
    private String sortOrder = Media.DATA;

    public static MusicLoader instance(ContentResolver pContentResolver) {
        if (musicLoader == null) {
            contentResolver = pContentResolver;
            musicLoader = new MusicLoader();
        }
        return musicLoader;
    }

    private MusicLoader() {
        //利用ContentResolver的query函数来查询数据，然后将得到的结果放到MusicInfo对象中，最后放到数组中
        Cursor cursor = contentResolver.query(contentUri, projection, where, null, sortOrder);
        if (cursor == null) {
            Log.v(TAG, "Line(37  )   Music Loader cursor == null.");
        } else if (!cursor.moveToFirst()) {
            Log.v(TAG, "Line(39  )   Music Loader cursor.moveToFirst() returns false.");
        } else {
            int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);
            int albumCol = cursor.getColumnIndex(Media.ALBUM);
            int idCol = cursor.getColumnIndex(Media._ID);
            int durationCol = cursor.getColumnIndex(Media.DURATION);
            int sizeCol = cursor.getColumnIndex(Media.SIZE);
            int artistCol = cursor.getColumnIndex(Media.ARTIST);
            int urlCol = cursor.getColumnIndex(Media.DATA);
            do {
                String title = cursor.getString(displayNameCol);
                String album = cursor.getString(albumCol);
                long id = cursor.getLong(idCol);
                int duration = cursor.getInt(durationCol);
                long size = cursor.getLong(sizeCol);
                String artist = cursor.getString(artistCol);
                String url = cursor.getString(urlCol);

                MusicInfo musicInfo = new MusicInfo(id, title);
                musicInfo.setAlbum(album);
                musicInfo.setDuration(duration);
                musicInfo.setSize(size);
                musicInfo.setArtist(artist);
                musicInfo.setUrl(url);
                musicList.add(musicInfo);

            } while (cursor.moveToNext());
        }
    }
}
