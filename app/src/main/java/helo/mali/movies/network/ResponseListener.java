package helo.mali.movies.network;

public abstract class ResponseListener<T> {
    public abstract void onResponse(T response);

    public abstract void onErrorResponse();
}
