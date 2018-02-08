package com.smart.interview.network;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangPeng on 2-8-0008.
 */
//网络请求控制类
public class RetrofitManager {

    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    private static final long CACHE_AGE_SEC = 0;
    private static final String BASE_URL = "http://api.nohttp.net/";

    //云端响应头拦截器，用来配置缓存策略
    private static Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Request request = chain.request();

            // 在这里统一配置请求头缓存策略以及响应头缓存策略
            if (NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC)
                        .build();
                okhttp3.Response response = chain.proceed(request);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC)
                        .build();
            } else {
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .build();
                okhttp3.Response response = chain.proceed(request);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .build();
            }
        }
    };

    //打印返回的json数据拦截器
    private static Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            //默认不过滤请求接口
            boolean filter = false;
            okhttp3.Request request = chain.request();
            okhttp3.Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            request = requestBuilder.build();
            String requestUrl = request.url().toString();

            LogUtils.v("请求网址: " + requestUrl);
            String method = request.method();
            if ("POST".equals(method)) {
                LogUtils.v("------------开始打印请求参数------------");
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                        LogUtils.v(body.encodedName(i) + " = " + body.encodedValue(i));
                    }
                    LogUtils.v("------------结束打印请求参数------------");
                    sb.delete(sb.length() - 1, sb.length());
                }
            }

            final okhttp3.Response response = chain.proceed(request);
            final ResponseBody responseBody = (ResponseBody) response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    LogUtils.d("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
                if (filter) {
                    LogUtils.v("------------开始打印返回数据------------");
                    LogUtils.v(buffer.clone().readString(charset));
                    LogUtils.v("------------结束打印返回数据------------");
                } else {
                    LogUtils.d("------------开始打印返回数据------------");
                    LogUtils.d(buffer.clone().readString(charset));
                    LogUtils.d("------------结束打印返回数据------------");
                }
            }
            return response;
        }
    };

    private static ApiService mApiService;

    public static ApiService getApiService() {
        if (mApiService == null) {
            Retrofit builder = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient().build())
                    .build();
            mApiService = builder.create(ApiService.class);
        }
        return mApiService;
    }

    public static <T> Observable.Transformer<T, T> applyScheduler() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static OkHttpClient.Builder getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(mLoggingInterceptor);
        builder.addNetworkInterceptor(mRewriteCacheControlInterceptor);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        return builder;
    }


}
