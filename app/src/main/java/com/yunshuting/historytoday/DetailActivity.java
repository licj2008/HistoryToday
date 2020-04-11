package com.yunshuting.historytoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yunshuting.historytoday.util.BizApi;
import com.yunshuting.historytoday.util.HistoryDetailDto;
import com.yunshuting.historytoday.util.HistoryDto;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tvContent)
    TextView tvContent;


    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("detailId");
        requestData(id);

    }


    private void requestData(String infoId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Retrofit retrofit = new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl("http://www.seme.me/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                BizApi api = retrofit.create(BizApi.class);
                Call<HistoryDetailDto> call = api.getDetail(infoId);

                call.enqueue(new Callback<HistoryDetailDto>() {
                    @Override
                    public void onResponse(@NonNull Call<HistoryDetailDto> call, @NonNull Response<HistoryDetailDto> response) {
                        try {
                            if(response.isSuccessful()){
                                HistoryDetailDto dto = response.body();
                                if (dto.status == 0) {
                                    showContent(dto.data.title, dto.data.date, dto.data.content);
                                } else {

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            //callBack.onFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoryDetailDto> call, Throwable t) {
                        //callBack.onFailure();
                    }
                });
            }
        }).start();
    }

    private void showContent(String title, String date, String info) {
        setTitle(title + "["+ date+"]");
        tvContent.setText(info.replace("<br>","\n") + "\n\n");
    }
}
