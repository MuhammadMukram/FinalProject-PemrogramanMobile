package com.example.finalproject_pemrogramanmobile.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.finalproject_pemrogramanmobile.Activity.Adapter.CategoryAdapter;
import com.example.finalproject_pemrogramanmobile.Activity.Adapter.GridCategoryAdapter;
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
    CategoryAdapter categoryAdapter;
    RecyclerView recyclerView;
    RecyclerView categoryGroupRv;

    List<HomepageModel.CategoryBotton> categoryBottons;
    int posts = 2;
    int page = 1;
    boolean isFromStart = true;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitiateViews();
        AddImagesToSlider();
        getHomeData();
        //Initial Views
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener(){
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY){

                if(scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
                    isFromStart = false;
                    progressBar.setVisibility(View.VISIBLE);
                    page++;
                    getHomeData();
                }
            }
        });

    }

    private void getHomeData() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Map<String, String> params = new HashMap<>();
        params.put("page", page+"");
        params.put("posts", posts+"");

        Call<HomepageModel> call = apiInterface.getHomepageApi(params);
        call.enqueue(new Callback<HomepageModel>() {
            @Override
            public void onResponse(Call<HomepageModel> call, Response<HomepageModel> response) {
                UpdateDataOnHomePage(response.body());
            }

            @Override
            public void onFailure(Call<HomepageModel> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void UpdateDataOnHomePage(HomepageModel body) {
        progressBar.setVisibility(View.GONE);
            if(isFromStart){
            categoryBottons.clear();
        }
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

        int beforeNewsSize = news.size();
        for(int i = 0; i < body.getNews().size(); i++){
            news.add(body.getNews().get(i));
        }
        categoryBottons.addAll(body.getCategoryBotton());
        if(isFromStart){
            recyclerView.setAdapter(newsAdapter);
            categoryGroupRv.setAdapter(categoryAdapter);
        }else{
            newsAdapter.notifyItemRangeInserted(beforeNewsSize, body.getNews().size());
        }
    }

    //SUCCESS

    private void InitiateViews() {
        categoryBottons = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryBottons);
        categoryGroupRv = findViewById(R.id.categoryGroupRv);
        categoryGroupRv.setHasFixedSize(true);
        categoryGroupRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        sliderLayout = findViewById(R.id.carousel);
        recyclerView = findViewById(R.id.recy_news);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        nestedScrollView = findViewById(R.id.nested);
        progressBar = findViewById(R.id.progressBar);

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