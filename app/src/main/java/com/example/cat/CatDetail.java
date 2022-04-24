package com.example.cat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cat.view.HomeActivity;

public class CatDetail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_detail);

        String image=getIntent().getStringExtra("image");
        String name=getIntent().getStringExtra("name");
        String origin=getIntent().getStringExtra("origin");
        String description=getIntent().getStringExtra("description");

        ImageView back = findViewById(R.id.back2);
        ImageView imageView=findViewById(R.id.imageView);
        TextView txtname=findViewById(R.id.name);
        TextView txtorigin=findViewById(R.id.origin);
        TextView txtdescription=findViewById(R.id.description);

        Glide.with(this).load(image).centerCrop().into(imageView);
        txtname.setText(name);
        txtorigin.setText("Origin: "+origin);
        txtdescription.setText(description);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(CatDetail.this, HomeActivity.class);
                startActivity(i);
                finish();

            }
        });


    }


}