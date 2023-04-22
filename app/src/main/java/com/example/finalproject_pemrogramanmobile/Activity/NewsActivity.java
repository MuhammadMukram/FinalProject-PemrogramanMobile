package com.example.finalproject_pemrogramanmobile.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject_pemrogramanmobile.R;
import com.google.android.material.navigation.NavigationView;

public class NewsActivity extends AppCompatActivity {

    private TextView newsTitleTv, newsContentTv, newsDateTv, writerTv;
    private ImageView newsImageIv;

    private DrawerLayout sidebar_view_container;
    private NavigationView sidebar_view_item;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initiateView();

        String news_title = getIntent().getStringExtra("news_title");
        String news_desc = getIntent().getStringExtra("news_desc");
        String news_image = getIntent().getStringExtra("news_image");
        String news_date = getIntent().getStringExtra("news_date");
        String news_category = getIntent().getStringExtra("news_category");

        newsTitleTv.setText(news_title);
        newsContentTv.setText(news_desc);
        newsDateTv.setText(news_date);
        writerTv.setText(news_category);
        Glide.with(this).load(news_image).into(newsImageIv);


        sidebar_view_container = findViewById(R.id.sidebar_view_container);
        sidebar_view_item = findViewById(R.id.sidebar_view_item);
        drawerToggle = new ActionBarDrawerToggle(this, sidebar_view_container, R.string.open, R.string.close);

        sidebar_view_container.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sidebar_view_item.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    finish();
                    break;
                case R.id.kesehatanCategory:
                    Toast.makeText(this, "Kesehatan Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.olahragaCategory:
                    Toast.makeText(this, "Olahraga Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.livestyleCategory:
                    Toast.makeText(this, "Lifestyle Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.politikCategory:
                    Toast.makeText(this, "Politik Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ekonomiCategory:
                    Toast.makeText(this, "Ekonomi Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.teknologiCategory:
                    Toast.makeText(this, "Teknologi Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_login:
                    Toast.makeText(this, "Login Selected", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }

    private void initiateView() {
        newsTitleTv = findViewById(R.id.newsTitleTv);
        newsContentTv = findViewById(R.id.newsContentTv);
        newsDateTv = findViewById(R.id.newsDateTv);
        writerTv = findViewById(R.id.writerTv);
        newsImageIv = findViewById(R.id.newsImageIv);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (sidebar_view_container.isDrawerOpen(GravityCompat.START)) {
            sidebar_view_container.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}