package com.dajeong.chatbot.dajeongbot.manager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.dajeong.chatbot.dajeongbot.model.GalleryImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// 이미지를 가져오기 위한 클래스
public class GalleryManager {
    private final String TAG = "GalleryManager";

    private Context mContext;

    public GalleryManager(Context context) {
        mContext = context;
    }


    /**
     * 갤러리 이미지 반환
     *
     * @return
     */

    /**
     * 갤러리 이미지 반환
     */
    public List<GalleryImage> getDatePhotoPathList() {



        String completePath = Environment.getExternalStorageDirectory() + "/";

        ArrayList<GalleryImage> photoList = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };


        //이미지를 사진 찍은 날짜대로 (최신순으로) 정렬
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
        int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            GalleryImage galleryImage = new GalleryImage(cursor.getString(columnIndexData),false);
//            Log.e(TAG, galleryImage.toString());

            photoList.add(galleryImage);

        }
        cursor.close();
        return photoList;
    }


    public List<GalleryImage> getDirectoryPhotoPathList(String diriectoryName) {



        String completePath = Environment.getExternalStorageDirectory() + "/";

        ArrayList<GalleryImage> photoList = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };


        //이미지를 사진 찍은 날짜대로 (최신순으로) 정렬
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
        int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        if(diriectoryName.equals("모든 사진")){
            while (cursor.moveToNext()) {
                GalleryImage galleryImage = new GalleryImage(cursor.getString(columnIndexData),false);
                photoList.add(galleryImage);

            }
        }
        else{
            while (cursor.moveToNext()) {
                GalleryImage galleryImage = new GalleryImage(cursor.getString(columnIndexData),false);
                if(galleryImage.toString().contains("/"+diriectoryName+"/")){
                    photoList.add(galleryImage);
                }
        }


        }
        cursor.close();
        return photoList;
    }
}
