package Helpers;

/**
 * Created by Ali on 12/16/2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private  final LruCache<String, Bitmap> mCache;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mCache = new LruCache<>(200);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    public void clearCache(){
        mCache.resize(1);
        mCache.resize(50);
    }
}