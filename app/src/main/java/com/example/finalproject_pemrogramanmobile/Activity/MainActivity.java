package com.example.finalproject_pemrogramanmobile.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.finalproject_pemrogramanmobile.Activity.Adapter.NewsAdapter;
import com.example.finalproject_pemrogramanmobile.Activity.model.HomepageModel;
import com.example.finalproject_pemrogramanmobile.Activity.rest.ApiClient;
import com.example.finalproject_pemrogramanmobile.Activity.rest.ApiInterface;
import com.example.finalproject_pemrogramanmobile.R;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.DefaultSliderView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout sidebar_view_container;
    private NavigationView sidebar_view_item;
    private ActionBarDrawerToggle drawerToggle;
    SliderLayout sliderLayout;

    NewsAdapter newsAdapter;
    List<HomepageModel.News> news;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitiateViews();
        AddImagesToSlider();
        getHomeData();

        sidebar_view_container = findViewById(R.id.sidebar_view_container);
        sidebar_view_item = findViewById(R.id.sidebar_view_item);
        drawerToggle = new ActionBarDrawerToggle(this, sidebar_view_container, R.string.open, R.string.close);

        sidebar_view_container.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sidebar_view_item.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
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

    private void getHomeData() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Map<String, String> params = new HashMap<>();
        params.put("page", 1+"");
        params.put("posts", 10+"");

        Call<HomepageModel> call = apiInterface.getHomepageApi(params);
        call.enqueue(new Callback<HomepageModel>() {
            @Override
            public void onResponse(Call<HomepageModel> call, Response<HomepageModel> response) {
                UpdateDataOnHomePage(response.body());
            }

            @Override
            public void onFailure(Call<HomepageModel> call, Throwable t) {

            }
        });

    }

    private void UpdateDataOnHomePage(HomepageModel body) {

        for (int i = 0; i < body.getBanners().size(); i++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            defaultSliderView.setRequestOption(new RequestOptions().centerCrop());
            defaultSliderView.image(body.getBanners().get(i).getImage());
            sliderLayout.addSlider(defaultSliderView);
            int finalI = i;
            defaultSliderView.setOnSliderClickListener(slider -> {
                Toast.makeText(this, "Slider " + finalI + " Clicked", Toast.LENGTH_SHORT).show();
            });
        }
        sliderLayout.startAutoCycle();
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setDuration(3000);

        for(int i = 0; i < body.getNews().size(); i++){
            news.add(body.getNews().get(i));
        }
        recyclerView.setAdapter(newsAdapter);
    }

    //SUCCESS

    private void InitiateViews() {
        sliderLayout = findViewById(R.id.carousel);

        recyclerView = findViewById(R.id.recy_news);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        news = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, news);

    }
    private void AddImagesToSlider(){
        //Add images to slider using ArrayList
        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.slider1);
        images.add(R.drawable.slider2);
        images.add(R.drawable.slider3);


    }
    @Override
    protected void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
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