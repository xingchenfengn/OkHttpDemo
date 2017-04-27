package com.xingzhiqiao.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.get_btn)
    public void getData() {
        HttpManger.getInstance().getData(this, "https://www.baidu.com");
    }

    @OnClick(R.id.post_btn)
    public void postData() {
        HttpManger.getInstance().postData(this, "https://www.baidu.com");
    }

    @OnClick(R.id.post1_btn)
    public void postFile() {
        HttpManger.getInstance().uploadImg(this);
    }

    @OnClick(R.id.down_btn)
    public void downloadData() {
        HttpManger.getInstance().downLoadImg(this, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4245887956,100740587&fm=23&gp=0.jpg");
    }

}
