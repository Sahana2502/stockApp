package com.pega.stockapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import android.util.Log;

import androidx.annotation.NonNull;

import com.pega.stockapp.util.AppConstants;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StockAppTest {
    @Test
    public void whenResponseReceivedByGetRequest_thenCorrect() {
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url(AppConstants.STOCK_API)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                fail("Encountered an error");
            }

            public void onResponse(Call call, Response response)
                    throws IOException {
                assertEquals(response.code(),200);
                Log.d("TEST","Success");

            }


        });
    }
}
