package com.ch.wchhuangya.baas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ch.wchhuangya.baas.model.Person;
import com.ch.wchhuangya.baas.util.LogHelper;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 测试数据服务能力的类
 * Created by wchya on 16/9/15.
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        findViewById(R.id.main_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                person.setName("First Person");
                person.setAddress("中国　@　甘肃　@　兰州 ");

                person.save(TestActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        LogHelper.i("添加数据成功!");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        LogHelper.e("添加数据失败!原因: " + s);
                    }
                });
            }
        });

        findViewById(R.id.main_get_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Person> query = new BmobQuery<Person>();

                query.getObject(TestActivity.this, "5010dda700", new GetListener<Person>() {
                    @Override
                    public void onSuccess(Person person) {
                        LogHelper.i("获取数据成功! 数据信息为: name -- " + person.getName() + ", address -- " + person.getAddress()
                                + ", createTime -- " + person.getCreatedAt() + ", updateTime -- " + person.getUpdatedAt());
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        LogHelper.e("获取数据失败! 原因: " + s);
                    }
                });
            }
        });

        findViewById(R.id.main_update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                person.setName("张三丰");
                person.update(TestActivity.this, "5010dda700", new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        LogHelper.i("修改数据成功!");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        LogHelper.e("修改数据失败! 错误码: " + i + ", 原因: " + s);
                    }
                });
            }
        });

        findViewById(R.id.main_del_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                person.setObjectId("5010dda700");
                person.delete(TestActivity.this, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        LogHelper.i("删除数据成功!");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        LogHelper.e("删除数据失败! 错误码: " + i + ", 原因: " + s);
                    }
                });
            }
        });
    }
}
