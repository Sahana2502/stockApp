package com.pega.stockapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.pega.stockapp.R;
import com.pega.stockapp.activities.StockDetailActivity;
import com.pega.stockapp.model.Stock;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private final ArrayList<Stock> stocks;

    public StockAdapter(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.companySymbol.setText(stock.getCompanySymbol());
        holder.companyName.setText(stock.getCompanyName());
        holder.currentStockPrice.setText(String.format("%.2f", stock.getCurrentStockPrice()));

    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {
        private final TextView companySymbol;
        private final TextView companyName;
        private final TextView currentStockPrice;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            companySymbol = itemView.findViewById(R.id.text_company_symbol);
            companyName = itemView.findViewById(R.id.text_company_name);
            currentStockPrice = itemView.findViewById(R.id.text_stock_price);

            //Handles click action on recycler view element
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), StockDetailActivity.class);
                intent.putExtra("Stock Object", stocks.get(getAdapterPosition()));
                itemView.getContext().startActivity(intent);
            });

        }


    }
}
