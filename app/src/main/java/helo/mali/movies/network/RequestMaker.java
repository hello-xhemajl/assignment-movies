package helo.mali.movies.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;



public class RequestMaker {
    private static final String VOLLEY_TAG = "Volley RequestMaker";

    private static RequestMaker instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context ctx;



    private RequestMaker(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized RequestMaker getInstance(Context context) {
        if (instance == null) {
            instance = new RequestMaker(context);
        }
        return instance;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds the specified request to the global queue using the Default VOLLEY_TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default VOLLEY_TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req, boolean shouldUpdateToken) {
        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default VOLLEY_TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? VOLLEY_TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }


    /**
     * Cancels all pending requests by the specified VOLLEY_TAG, it is important
     * to specify a VOLLEY_TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
