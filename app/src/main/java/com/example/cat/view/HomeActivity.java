package com.example.cat.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cat.CatAdapter;
import com.example.cat.CatDetail;

import com.example.cat.IRecyclerView;
import com.example.cat.R;
import com.example.cat.model.Cat;
import com.example.cat.retrofit.ApiClient;
import com.example.cat.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements IRecyclerView {
RecyclerView recyclerView;
LinearLayoutManager layoutManager;
CatAdapter adapter;
SearchView searchView;
List<Cat> catList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        searchView = findViewById(R.id.search_bar);
        searchView.clearFocus();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CatAdapter(HomeActivity.this, catList, this);
        recyclerView.setAdapter(adapter);


        RetrofitService retrofitService = new RetrofitService();
        ApiClient apiService=retrofitService.getRetrofit().create(ApiClient.class);

        Call<List<Cat>> call = apiService.getBreeds();

        call.enqueue(new Callback<List<Cat>>() {
            @Override
            public void onResponse(Call<List<Cat>> call, Response<List<Cat>> response) {
                catList = response.body();
                Log.d("TAG","Response = "+catList);
                adapter.setBreedsList(getApplicationContext(),catList);
            }

            @Override
            public void onFailure(Call<List<Cat>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });

        fetchCat();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem search = menu.findItem(R.id.search_bar);

       /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        SetupSearchView();*/

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_bar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void fetchCat(){
        RetrofitService retrofitService = new RetrofitService();
        ApiClient apiClient=retrofitService.getRetrofit().create(ApiClient.class);


        Call<List<Cat>> call = apiClient.getBreeds();

        call.enqueue(new Callback<List<Cat>>() {
            @Override
            public void onResponse(Call<List<Cat>> call, Response<List<Cat>> response) {

                if (response.isSuccessful()) {
                    catList.addAll(response.body());
                    adapter.notifyDataSetChanged();


                } else {
                    Log.d("err","Error!");
                }

            }

            @Override
            public void onFailure(Call<List<Cat>> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(HomeActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }

        });
    }


    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(HomeActivity.this, CatDetail.class);
        intent.putExtra("image",catList.get(position).getImage().getUrl());
        intent.putExtra("name",catList.get(position).getName());
        intent.putExtra("origin",catList.get(position).getOrigin());
        intent.putExtra("description",catList.get(position).getDescription());
        startActivity(intent);

    }


   /* private void SetupSearchView(){
        final SearchView searchView=findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RetrofitService retrofitService = new RetrofitService();
                ApiClient apiClient=retrofitService.getRetrofit().create(ApiClient.class);
                apiClient.searchBreed(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }*/
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

}
