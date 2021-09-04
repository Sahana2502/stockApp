package com.pega.stockapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.pega.stockapp.R;
import com.pega.stockapp.model.Stock;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class StockDetailActivity extends AppCompatActivity {


    private ActionBar actionBar;
    private String currentCompany;
    private TextView currentStockPrice;
    private static final String STOCK_API = "https://71iztxw7wh.execute-api.us-east-1.amazonaws.com/interview/favorite-stocks";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
        setToolbar();

        //receives and displays the selected stock object.
        //The object is Parcelable object
        Intent intent = getIntent();
        displayStockDetails(intent);

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

    //The method customises the tool bar to add back button
    private void setToolbar() {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_left_chevron);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
                            if (!isFinishing() && hasWindowFocus())
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

    //The response object is looked up for the required company's current stock value and UI is updated.
    private void displayStocks(Response response) {
        try {
            JSONObject responseObject = new JSONObject(response.body().string());
            Double price = (double) responseObject.getJSONObject(currentCompany).getDouble("price");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    currentStockPrice.setText(String.format("%.2f", price));
                }
            });

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }


    }


    //The method displays the stock object details selected by the user. A parcelable object is received as an intent
    private void displayStockDetails(Intent intent) {
        TextView companySymbol = findViewById(R.id.text_detail_company_symbol);
        TextView companyName = findViewById(R.id.text_detail_company_name);
        currentStockPrice = findViewById(R.id.text_detail_current_price);
        TextView dailyHighPrice = findViewById(R.id.text_detail_high_price);
        TextView dailyLowPrice = findViewById(R.id.text_detail_low_price);

        Stock stock = (Stock) intent.getParcelableExtra("Stock Object");
        actionBar.setTitle(stock.getCompanySymbol());


        currentCompany = stock.getCompanySymbol();
        companySymbol.setText(stock.getCompanySymbol());
        companyName.setText(stock.getCompanyName());
        currentStockPrice.setText(String.format("%.2f", stock.getCurrentStockPrice()));
        dailyHighPrice.setText(String.format("%.2f", stock.getDailyHighPrice()));
        dailyLowPrice.setText(String.format("%.2f", stock.getDailyLowPrice()));

    }


    //If a response is not received, the user is alerted
    public void displayAlert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(StockDetailActivity.this);
        alertBuilder.setMessage(getResources().getString(R.string.error));
        alertBuilder.setCancelable(true);
        alertBuilder.setPositiveButton(
                "GOT IT",
                (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }


    //Implementing the up button feature for left chevron on the tool bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}