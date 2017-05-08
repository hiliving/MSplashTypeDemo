package com.practice.dev.msplashtypedemo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.practice.dev.msplashtypedemo.R;
import com.practice.dev.msplashtypedemo.config.HttpAddress;
import com.practice.dev.msplashtypedemo.http.ApiService;
import com.practice.dev.msplashtypedemo.http.model.ConfigInfo;
import com.practice.dev.msplashtypedemo.services.DownSplashResService;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 此页面用于请求启动页类型，检查更新，然后将结果传给下个页面
 * Created by HY on 2017/5/8.
 */

public class LoadingActivity extends Activity {
    public static final int MODE_MOVIE = 2000;//视频
    public static final int MODE_PICTURE = 1000;//图片
    private final Handler mHandler = new Handler();
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        intent = new Intent(this, SplashActivity.class);

        initConfig();
    }

    private void initConfig() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpAddress.BASE_URL)
                .build();

        final ApiService mApi = retrofit.create(ApiService.class);
        Call<ConfigInfo> request = mApi.getType();

        //启动即发请求
        request.enqueue(new Callback<ConfigInfo>() {
            @Override
            public void onResponse(Call<ConfigInfo> call, Response<ConfigInfo> response) {

                ConfigInfo mInfo = response.body();//一行代码将返回结果完成封装

                String fileUrl = mInfo.getData().getFileUrl();
                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                String filePath = LoadingActivity.this.getCacheDir().getPath() + "/splash";//这里用的内置sd卡

                if (mInfo.getData().getFileType() == 2) {
                    //启动为视频
                    downRes(fileUrl, fileName, filePath, MODE_MOVIE);
                    intent.putExtra("type", MODE_MOVIE);
                } else {
                    //启动为图片
                    downRes(fileUrl, fileName, filePath, MODE_PICTURE);
                    intent.putExtra("type", MODE_PICTURE);
                }
                //3秒后跳转
                startAfterDelay();
            }

            @Override
            public void onFailure(Call<ConfigInfo> call, Throwable t) {
                Log.d("哈哈哈哈",t.getLocalizedMessage());
            }
        });
    }

    /**
     * 下载启动资源
     * @param url
     * @param fileName
     * @param path
     * @param type
     */
    private void downRes(String url, String fileName, String path, int type) {

        File file = new File(path + "/" + fileName);
        if (file.exists()) {
        } else {
            startService(new Intent(LoadingActivity.this, DownSplashResService.class).putExtra("fileUrl", url).putExtra("fileName", fileName).putExtra("fileType", type));
        }
    }

    public void startAfterDelay() {


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000l);

    }
}
