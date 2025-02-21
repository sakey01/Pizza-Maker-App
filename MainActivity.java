package com.example.pizzaapp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private double total = 5.0;
    private TextView tot;
    private final Map<CheckBox, Topping> toppings = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tot = findViewById(R.id.totalNum);
        tot.setText("£" + total);

        initToppings();

        findViewById(R.id.submitb).setOnClickListener(v -> {
            if (total == 5) {
                Toast.makeText(MainActivity.this, "Please select at least one ingredient", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Order confirmed. Your total is: £" + total, Toast.LENGTH_LONG).show();
            }
        });
        
        findViewById(R.id.clearb).setOnClickListener(v -> resetSelections());
    }

    private void initToppings() {
        addTopping(R.id.sweetcorn, R.id.sweetcorn_img, 0.50);
        addTopping(R.id.pepperoni, R.id.pepperoni_img, 2.00);
        addTopping(R.id.mushroom, R.id.mushroom_img, 0.50);
        addTopping(R.id.chicken, R.id.chicken_img, 1.00);
        addTopping(R.id.tomato, R.id.tomato_img, 0.50);
        addTopping(R.id.onion, R.id.onion_img, 0.50);
    }

    private void addTopping(int checkBoxId, int imageViewId, double price) {
        CheckBox checkBox = findViewById(checkBoxId);
        ImageView imageView = findViewById(imageViewId);
        Topping topping = new Topping(imageView, price);

        toppings.put(checkBox, topping);

        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                total += topping.getPrice();
                topping.getImageView().setVisibility(View.VISIBLE);
            } else {
                total -= topping.getPrice();
                topping.getImageView().setVisibility(View.INVISIBLE);
            }
            tot.setText("£" + total);
        });
    }

    private void resetSelections() {
        total = 5;
        tot.setText("£" + total);
        for (Map.Entry<CheckBox, Topping> entry : toppings.entrySet()) {
            entry.getKey().setChecked(false);
            entry.getValue().getImageView().setVisibility(View.INVISIBLE);
        }
        Toast.makeText(MainActivity.this, "Selection cleared", Toast.LENGTH_SHORT).show();
    }

    private static class Topping {
        private final ImageView imageView;
        private final double price;

        Topping(ImageView imageView, double price) {
            this.imageView = imageView;
            this.price = price;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public double getPrice() {
            return price;
        }
    }
}
