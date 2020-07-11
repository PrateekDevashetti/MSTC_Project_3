package com.example.project001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1 ;
    MyRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
// Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // sees the explanation, try again to request the permission.
            } else {
                // request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        // this method is used to get the path of the file
        public ArrayList<String> getFilePaths(){
            Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Images.ImageColumns.DATA};
            Cursor c = null;
            SortedSet<String> dirList = new TreeSet<String>();
            ArrayList<String> resultIAV = new ArrayList<String>();
            String[] directories = null;
            if (u != null)
            {
                c = managedQuery(u, projection, null, null, null);
            }
            if ((c != null) && (c.moveToFirst()))
            {
                do
                {
                    String tempDir = c.getString(0);
                    tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                    try{
                        dirList.add(tempDir);
                    }
                    catch(Exception e)
                    {
                    }
                }
                while (c.moveToNext());
                directories = new String[dirList.size()];
                dirList.toArray(directories);
            }
            for(int i=0;i<dirList.size();i++)
            {
                File imageDir = new File(directories[i]);
                File[] imageList = imageDir.listFiles();
                if(imageList == null)
                    continue;
                for (File imagePath : imageList) {
                    try {
                        if(imagePath.isDirectory())
                        {
                            imageList = imagePath.listFiles();
                        }
                        if ( imagePath.getName().contains(".jpg")|| imagePath.getName().contains(".JPG")
                                || imagePath.getName().contains(".jpeg")|| imagePath.getName().contains(".JPEG")
                                || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                                || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                                || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                        )
                        {
                            String path= imagePath.getAbsolutePath();
                            resultIAV.add(path);
                        }
                    }
                    //  }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            return resultIAV;
        }
//fetching all image paths
        Object utils;
        String[] imagePath = utils.getFilePaths();
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, imagePath);
        recyclerView.setAdapter(adapter);
    }
}
