package com.example.musicplayer.repository;

import android.app.Activity;
import android.content.ContentResolver;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.musicplayer.model.Album;
import com.example.musicplayer.model.Artist;
import com.example.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.List;

public class ListRepository {

    private static ListRepository instance;
    private List<Album> mAlbumList;
    private List<Artist> mArtistList;
    private List<Music> mMusicList;
    private Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    private ListRepository() {
        mAlbumList = new ArrayList<>();
        mArtistList = new ArrayList<>();
        mMusicList = new ArrayList<>();

    }

    public static ListRepository getInstance() {
        if (instance == null) {
            instance = new ListRepository();
        }
        return instance;
    }

    public List<Music> getListMusics(Activity activity) {

        ContentResolver contentResolver = activity.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        List<Music> musicList = new ArrayList<>();

        Cursor musicCursor = contentResolver.query(musicUri, null, null, null, null);
        Cursor albumCursor;

        if (musicCursor != null && musicCursor.moveToFirst()) {

            if (musicCursor != null && musicCursor.moveToFirst()) {

                int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

                do {
                    Long albumId = Long.valueOf(musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                    albumCursor = contentResolver.query(albumUri,
                            new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                            MediaStore.Audio.Albums._ID + "=" + albumId, null, null);

                    int pictureColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                    int uriColumn =musicCursor.getColumnIndex((MediaStore.Audio.Media.DATA));

                    if (albumCursor != null && albumCursor.moveToFirst()) {
                        long id = musicCursor.getLong(idColumn);
                        String title = musicCursor.getString(titleColumn);
                        String artist = musicCursor.getString(artistColumn);
                        String album = musicCursor.getString(albumColumn);
                        String pic = albumCursor.getString(pictureColumn);
                        int duration = musicCursor.getInt(musicCursor
                                .getColumnIndex(MediaStore.Audio.Media.DURATION));
                        String uri = musicCursor.getString(uriColumn);
                        // String idAlbum = albumCursor.getString(idAlbumColumn);
                        //  String idAlbumMedia = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                        musicList.add(new Music(id, title, artist, album, pic, duration,uri));
                    }

                } while (musicCursor.moveToNext());
                albumCursor.close();
            }
        }
        musicCursor.close();
        return musicList;

    }

    public List<Album> getListAlbum(Activity activity) {

        List<Album> albumList = new ArrayList<>();
        ContentResolver contentResolver = activity.getContentResolver();
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor albumCursor = contentResolver.query(albumUri, null, null, null, null);

        if (albumCursor != null && albumCursor.moveToFirst()) {

            do {
                long id = albumCursor.getLong(albumCursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String albumName = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String artist = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String pic = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                String musicCount = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));

                albumList.add(new Album(id, albumName, artist, pic, musicCount));

            } while (albumCursor.moveToNext());
        }
        albumCursor.close();
        return albumList;

    }

    public List<Artist> getListArtist(Activity activity) {

        List<Artist> artistList = new ArrayList<>();
        ContentResolver contentResolver = activity.getContentResolver();
        Uri artistUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

        Cursor artistCursor = contentResolver.query(artistUri, null, null, null, null);

        if (artistCursor != null && artistCursor.moveToFirst()) {

            do {
                long id = artistCursor.getLong(artistCursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String artistName = artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                String musicCount = artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));

                artistList.add(new Artist(id, artistName, musicCount));

            } while (artistCursor.moveToNext());
        }
        artistCursor.close();
        return artistList;

       /* List<Artist> artistList = new ArrayList<>();
        ContentResolver contentResolver = activity.getContentResolver();
        Uri artistUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        Cursor artistCursor = contentResolver.query(artistUri, null, null, null, null);
        Cursor albumCursor;

        if (artistCursor != null && artistCursor.moveToFirst()) {
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                Long albumId = Long.valueOf(artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Artists.Albums.ALBUM_ID)));
                albumCursor = contentResolver.query(albumUri,
                        new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID + "=" + albumId, null, null);

                //   int pictureColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);

                int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                long id = artistCursor.getLong(artistCursor.getColumnIndex(MediaStore.Audio.Artists._ID));
               // String albumName = artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Artists.Albums.ALBUM));
                String artistName = artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                String pic = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                String musicCount = artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));

                artistList.add(new Artist(id,artistName,pic ,musicCount));

            } while (artistCursor.moveToNext());
        }
        artistCursor.close();
        return artistList;*/

    }

    public List<Music> getMusicListByAlbum(Activity activity, Long albumId) {

        ContentResolver contentResolver = activity.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        List<Music> albumList = new ArrayList<>();

        String where = MediaStore.Audio.Media.IS_MUSIC + "!=0" + " AND " + MediaStore.Audio.Media.ALBUM_ID + "=" + albumId;

        Cursor musicCursor = contentResolver.query(musicUri, null, where, null, null);
        Cursor albumCursor;
       /* final Cursor cursor = activity.getContentResolver().query(uri
                , null, where, null, null);*/

        if (musicCursor != null && musicCursor.moveToFirst()) {


            do {
                albumCursor = contentResolver.query(albumUri,
                        new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID + "=" + albumId, null, null);

                int pictureColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                int uriColumn =musicCursor.getColumnIndex((MediaStore.Audio.Media.DATA));

                if (albumCursor != null && albumCursor.moveToFirst()) {
                    long id = musicCursor.getLong(musicCursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String title = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String artist = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String album = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String pic = albumCursor.getString(pictureColumn);
                    int duration = musicCursor.getInt(musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    String uri = musicCursor.getString(uriColumn);

                    // String idAlbum = albumCursor.getString(idAlbumColumn);
                    //  String idAlbumMedia = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                    albumList.add(new Music(id, title, artist, album, pic, duration,uri));
                }

            } while (musicCursor.moveToNext());
            albumCursor.close();
            musicCursor.close();
        }
        return albumList;
    }

    public List<Music> getMusicListByArtist(Activity activity, Long artistId) {

        ContentResolver contentResolver = activity.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        List<Music> artistList = new ArrayList<>();

        String where = MediaStore.Audio.Media.IS_MUSIC + "!=0" + " AND " + MediaStore.Audio.Media.ARTIST_ID + "=" + artistId;

        Cursor musicCursor = contentResolver.query(musicUri, null, where, null, null);
        Cursor albumCursor;

        if (musicCursor != null && musicCursor.moveToFirst()) {

            do {
                albumCursor = contentResolver.query(albumUri,
                        new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums.ARTIST_ID + "=" + artistId, null, null);

                int pictureColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                int uriColumn =musicCursor.getColumnIndex((MediaStore.Audio.Media.DATA));

                if (albumCursor != null && albumCursor.moveToFirst()) {
                    long id = musicCursor.getLong(musicCursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String title = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String artist = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String album = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String pic = albumCursor.getString(pictureColumn);
                    int duration = musicCursor.getInt(musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    String uri = musicCursor.getString(uriColumn);

                    // String idAlbum = albumCursor.getString(idAlbumColumn);
                    //  String idAlbumMedia = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                    artistList.add(new Music(id, title, artist, album, pic, duration,uri));
                }

            } while (musicCursor.moveToNext());
            albumCursor.close();
            musicCursor.close();
        }
        return artistList;

    }

}



/*ContentResolver contentResolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
       // Uri musicsIntUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        Cursor musicCursor = contentResolver.query(musicUri ,null,null,null,null);
        Cursor albumCursor = contentResolver.query(albumUri,null,null,null,null);

        if(musicCursor != null && musicCursor.moveToFirst()){

            if (albumCursor != null && albumCursor.moveToFirst()) {

                int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int pictureColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                int idAlbumColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums._ID);


                do {
                    long id = musicCursor.getLong(idColumn);
                    String title = musicCursor.getString(titleColumn);
                    String artist = musicCursor.getString(artistColumn);
                    String album = musicCursor.getString(albumColumn);
                    String pic = albumCursor.getString(pictureColumn);
                    String idAlbum = albumCursor.getString(idAlbumColumn);

                    mListTracks.add(new Music(id, title, artist, album , pic));

                } while (musicCursor.moveToNext()&& albumCursor.moveToNext());
            }
        }*/

        /* public static ArrayList<CommonModel> getAllMusicPathList(Context context) {
    ArrayList<CommonModel> musicPathArrList = new ArrayList<>();
    Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    Cursor cursorAudio = context.getContentResolver().query(songUri, null, null, null, null);
    if (cursorAudio != null && cursorAudio.moveToFirst()) {

        Cursor cursorAlbum;
        if (cursorAudio != null && cursorAudio.moveToFirst()) {

            do {
                Long albumId = Long.valueOf(cursorAudio.getString(cursorAudio.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                cursorAlbum = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID + "=" + albumId, null, null);

                    if(cursorAlbum != null && cursorAlbum.moveToFirst()){
                        String albumCoverPath = cursorAlbum.getString(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                        String data = cursorAudio.getString(cursorAudio.getColumnIndex(MediaStore.Audio.Media.DATA));
                        musicPathArrList.add(new CommonModel(data,albumCoverPath ,false));
                    }

                 } while (cursorAudio.moveToNext());
        }
    }
    return musicPathArrList;
}*/