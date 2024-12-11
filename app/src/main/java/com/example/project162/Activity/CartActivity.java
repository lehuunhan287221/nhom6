package com.example.project162.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project162.Adapter.CartAdapter;
import com.example.project162.Helper.ChangeNumberItemsListener;
import com.example.project162.Helper.ManagmentCart;
import com.example.project162.R;
import com.example.project162.databinding.ActivityCartBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariable();
        calculateCart();
        initList();

        binding.buttonOder.setOnClickListener(v -> {
            managmentCart.clearCart(() -> {
                initList();     // Cập nhật danh sách
                calculateCart(); // Cập nhật tổng phí
            });
        });

    }

    private void initList() {
        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, () -> calculateCart());
        binding.cardView.setAdapter(adapter);
    }

    private void calculateCart() {
        double percentTax = 0.02; //percent 2% tax
        double delivery = 10000; // 10000 VND

        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        binding.totalFeeTxt.setText(formatCurrency(itemTotal));
        binding.taxTxt.setText(formatCurrency(tax));
        binding.deliveryTxt.setText(formatCurrency(delivery));
        binding.totalTxt.setText(formatCurrency(total));
    }

    private String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedAmount = df.format(amount); // Định dạng số tiền
        return formattedAmount + " VND"; // Thêm "VND" vào cuối
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }

}