package com.xingzhiqiao.okhttpdemo;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static android.R.string.ok;

/**
 * Created by xingzhiqiao on 2017/4/27.
 */

public class HttpManger {

    public String TAG = HttpManger.class.getName();
    private OkHttpClient mHttpClient;
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型


    private volatile static HttpManger mInstance;

    private HttpManger() {

        //设置缓存
        Cache cache = new Cache(new File(OkHttpApplication.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);//添加logInterceptor,这里可以根据需要添加其他Interceptor;
        builder.connectTimeout(30, TimeUnit.SECONDS);//设置超时时间
        builder.cache(cache);
        mHttpClient = builder.build();

    }

    public static HttpManger getInstance() {
        if (mInstance == null) {
            synchronized (HttpManger.class) {
                if (mInstance == null) {
                    mInstance = new HttpManger();
                }
            }
        }
        return mInstance;
    }


    public void getData(final Activity activity, String url) {
        Request.Builder builder = new Request.Builder();
        //设置请求方法，默认是Get请求
        builder.method("GET", null);
        builder.url(url);
        Request request = builder.build();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure");
                Log.d(TAG, "currentThread   is " + Thread.currentThread().getName());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "请求成功" + response.toString(), 1).show();
                    }
                });
                Log.d(TAG, "onResponse" + response.toString());
                Log.d(TAG, "currentThread   is " + Thread.currentThread().getName());
            }
        });
    }

    public void postData(final Activity activity, String url) {


        FormBody.Builder builder = new FormBody.Builder();

        builder.add("startNum", 0 + "");
        builder.add("perNum", 10 + "");
        FormBody formBody = builder.build();

        Request request = new Request.Builder().
                url(url).
                post(formBody).
                build();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "请求成功" + response.toString(), 1).show();
                    }
                });
            }
        });
    }

    public void uploadImg(final Activity activity) {
        File file = new File("/sdcard/test.jpg");
        Request request = new Request.Builder().
                url("https://api.github.com/").
                post(RequestBody.create(MediaType.parse(CONTENT_TYPE), file)).
                build();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "上传图片成功", 1).show();
                    }
                });
            }
        });
    }

    public void downLoadImg(final Activity activity, String imgUrl) {

        Request request = new Request.Builder().url(imgUrl).build();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(new File("/sdcard/test.jpg"));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "文件下载成功", 1).show();
                    }
                });
            }
        });

    }

}
