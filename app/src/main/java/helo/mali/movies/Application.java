package helo.mali.movies;

import helo.mali.movies.network.RequestMaker;

public class Application extends android.app.Application {
    private RequestMaker requestMaker;

    private static Application sInstance;
    public static synchronized Application getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        requestMaker = RequestMaker.getInstance(this);
    }

    public RequestMaker getRequestMaker(){
        return requestMaker;
    }
}
