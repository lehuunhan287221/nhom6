package com.example.project162.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.project162.Domain.Foods;
import com.example.project162.Helper.ManagmentCart;
import com.example.project162.R;
import com.example.project162.databinding.ActivityDetailBinding;

import java.text.DecimalFormat;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private Foods object;
    private int num = 1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentCart = new ManagmentCart(this);

        binding.backBtn.setOnClickListener(v -> finish());

        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);

        // Format price
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedPrice = df.format(object.getPrice()) + " VND";
        binding.priceTxt.setText(formattedPrice);

        // Set other details
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar() + " Sao");
        binding.ratingBar.setRating((float) object.getStar());

        // Format total price
        updateTotalPrice();

        binding.plusBtn.setOnClickListener(v -> {
            num = num + 1;
            binding.numTxt.setText(String.valueOf(num));
            updateTotalPrice();
        });

        binding.minusBtn.setOnClickListener(v -> {
            if (num > 1) {
                num = num - 1;
                binding.numTxt.setText(String.valueOf(num));
                updateTotalPrice();
            }
        });

        binding.addBtn.setOnClickListener(v -> {
            object.setNumberInCart(num);
            managmentCart.insertFood(object);
        });
    }
    private void updateTotalPrice() {
        // Format total price with thousands separator
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedTotal = df.format(num * object.getPrice()) + " VND";
        binding.totalTxt.setText(formattedTotal);
    }
    private void getIntentExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
}