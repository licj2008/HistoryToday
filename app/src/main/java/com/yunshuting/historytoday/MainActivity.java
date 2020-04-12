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
import com.yunshuting.historytoday.util.historyBean;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
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
    @BindView(R.id.tv_cur_day)
    TextView tvToday;

    private int m;
    private int d;

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
        initDate();
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
        Call<HistoryDto> call = api.getListData(m, d, PAGESIZE);

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
                requestListData(m,d);
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

    private void initDate() {
        Calendar c = Calendar.getInstance();
        m = c.get(c.MONTH) +1;
        d = c.get(c.DAY_OF_MONTH);
        showDate();
    }

    private void setOffset(int offset) {
        if (offset > 0) {
            if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
                if(d<31) {
                    d = d+1;
                } else {
                    d = 1;
                    if(m == 12) {
                        m = 1;
                    } else {
                        m = m + 1;
                    }
                }

            } else if (m == 4 || m == 6 || m == 9 || m == 11) {
                if(d<30) {
                    d = d+1;
                } else {
                    d = 1;
                    m = m + 1;
                }
            } else if (m == 2) {
                if(d<29) {
                    d = d+1;
                } else {
                    d = 1;
                    m = m + 1;
                }
            }
        } else {
            if (d>1) {
                d = d-1;
            } else if(d ==1) {
                if(m ==1 ) {
                    m = 12;
                    d = 31;
                } else if (m == 3) {
                    m = m-1;
                    d=29;
                } else if (m ==2 ||m == 4 || m == 6 || m == 9 || m == 11) {
                    m = m-1;
                    d =31;
                } else if ( m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
                    m = m -1;
                    d =30;
                }
            }
        }
        showDate();
        request();
    }
    private void showDate() {
        tvToday.setText(m + "月" + d + "日");
    }

    @OnClick({R.id.tv_pre_day,R.id.tv_next_day}) //多个控件可以一起发在里面进行监听
    public void sayHi(View view) {
        switch (view.getId()) {
            case R.id.tv_pre_day:
                setOffset(-1);
                break;
            case R.id.tv_next_day:
                setOffset(1);
                break;

            default:
                break;
        }
    }



}
