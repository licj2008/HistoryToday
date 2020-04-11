package com.yunshuting.historytoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yunshuting.historytoday.util.BizApi;
import com.yunshuting.historytoday.util.HistoryAdapter;
import com.yunshuting.historytoday.util.HistoryDto;
import com.yunshuting.historytoday.util.MyIntercepter;
import com.yunshuting.historytoday.util.historyBean;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_root_list)
    RecyclerView reView;

    @BindView(R.id.tv_info)
    TextView tvMsg;

    final int PAGESIZE = 90;
    HistoryAdapter adapter;
    List<historyBean> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (Build.VERSION.SDK_INT >= 11) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads    ().detectDiskWrites().detectNetwork().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
//        }

        ButterKnife.bind(this);
        //必须指定一个适配器Adapter和一个布局管理器LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        reView.setLayoutManager(layoutManager);
        adapter = new HistoryAdapter(MainActivity.this);
        reView.setAdapter(adapter);

        tvMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });
        request();
    }



    private void requestListData(int m, int d){
        OkHttpClient okHttpClient = new OkHttpClient();
        //okHttpClient.interceptors().add(new MyIntercepter());

        Retrofit retrofit = new Retrofit.Builder()
               .client(okHttpClient)
                .baseUrl("http://www.seme.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BizApi api = retrofit.create(BizApi.class);
        Call<HistoryDto> call = api.getListData(4, 9, PAGESIZE);

            call.enqueue(new Callback<HistoryDto>() {
                @Override
                public void onResponse(@NonNull Call<HistoryDto> call, @NonNull Response<HistoryDto> response) {
                    Log.d("LOG_TAG", "Reached this place:onResponse");
                    try {
                        if(response.isSuccessful()){
                            Log.d("LOG_TAG", "Reached this place:onResponse1111");
                            HistoryDto dto = response.body();
                            if (dto.status == 0) {
                                fetchListData(dto);
                            } else {
                                showErrorMsg(dto.msg);
                            }


                        }

                    } catch (Exception e) {
                        Log.d("LOG_TAG", "Reached this place:onResponse2222");
                        e.printStackTrace();
                        //callBack.onFailure();
                    }
                }

                @Override
                public void onFailure(Call<HistoryDto> call, Throwable t) {
                    //callBack.onFailure();
                }
            });

    }

    private void request() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                requestListData(4,5);
            }
        }).start();
    }
    private void fetchListData(HistoryDto list) {
        adapter.setData(list.data);
        adapter.notifyDataSetChanged();
    }

    private void  showErrorMsg(String str) {
        tvMsg.setText("获取数据出错:" + str);

    }



}
