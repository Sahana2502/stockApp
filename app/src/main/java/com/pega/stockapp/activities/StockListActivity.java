package com.pega.stockapp.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pega.stockapp.R;
import com.pega.stockapp.adapter.StockAdapter;
import com.pega.stockapp.model.Stock;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class StockListActivity extends AppCompatActivity {
    private OkHttpClient client;
    private ArrayList<Stock> stocks;
    private StockAdapter stockAdapter;
    private RecyclerView recyclerView;
    private static final String STOCK_API = "https://71iztxw7wh.execute-api.us-east-1.amazonaws.com/interview/favorite-stocks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);
        setUpRecyclerView();

    }


    @Override
    public void onResume() {
        super.onResume();
        scheduleTimerTask();
    }
    //The method polls the API every 10 seconds.
    private void scheduleTimerTask() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                getStocks();
            }
        }, 0, 10000);
    }

    //The method sends the API request to Stocks API and recieves response
    private void getStocks() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(STOCK_API)
                .build();
        try {
            client.newCall(request).enqueue(new Callback() {
                //If response is not received due to network failure/error
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!isFinishing() && hasWindowFocus())
                                displayAlert();
                        }
                    });
                }
                //On successfully receiving of data from API, the updated stock value is displayed
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    displayStocks(response);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //The response object is parsed and is displayed in a recycler View
    public void displayStocks(Response response) {
        String companySymbol, companyName = null;
        double currentPrice = 0, lowPrice = 0, highPrice = 0;
        Stock stock;
        stocks = new ArrayList<>();
        JSONObject responseObject = null;
        try {
            responseObject = new JSONObject(response.body().string());
        } catch (JSONException | IOException jsonException) {
            jsonException.printStackTrace();
        }
        Iterator keys = responseObject.keys();
        while (keys.hasNext()) {
            companySymbol = (String) keys.next();
            try {
                companyName = (String) responseObject.getJSONObject(companySymbol).get("name");
                currentPrice = (double) responseObject.getJSONObject(companySymbol).getDouble("price");
                lowPrice = (double) responseObject.getJSONObject(companySymbol).getDouble("low");
                highPrice = (double) responseObject.getJSONObject(companySymbol).getDouble("high");
            } catch (Exception e) {
                e.printStackTrace();
            }

            stock = new Stock(companySymbol, companyName, currentPrice, lowPrice, highPrice);
            stocks.add(stock);
        }
        runOnUiThread(new Runnable() {
            //Update the UI
            @Override
            public void run() {
                updateRecords();
            }
        });
    }

    public void displayAlert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(StockListActivity.this);
        alertBuilder.setMessage(getResources().getString(R.string.error));
        alertBuilder.setCancelable(true);

        alertBuilder.setPositiveButton(
                "GOT IT",
                (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    //set the recycler view and attach the adapter
    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.rv_stocks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(stockAdapter);
    }

    //The method updates the UI to display the current stock price of the company
    private void updateRecords() {
        stockAdapter = new StockAdapter(stocks);
        recyclerView.setAdapter(stockAdapter);
    }


}