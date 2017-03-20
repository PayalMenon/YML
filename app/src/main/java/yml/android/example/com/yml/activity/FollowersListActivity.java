package yml.android.example.com.yml.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yml.android.example.com.yml.R;
import yml.android.example.com.yml.pojo.Follower;
import yml.android.example.com.yml.services.GithubService;

/**
 * Created by payal.menon on 3/19/17.
 */

public class FollowersListActivity extends AppCompatActivity {

    private List<Follower> list;
    RecyclerView recyclerView;
    RecyclerViewAdapter listViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String username = intent.getStringExtra(SearchManager.QUERY);
            fetchFollowerData(username);
        }
    }

    private static String GIT_BASE_URL = "https://api.github.com/users/";

    private void fetchFollowerData(String username) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GIT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubService gitService = retrofit.create(GithubService.class);
        Call<List<Follower>> call = gitService.getFollowers(username);
        call.enqueue(new Callback<List<Follower>>() {
            @Override
            public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
                list = response.body();
                if(null != list && list.size() > 0) {
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerListView);
                    recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
                    recyclerView.setLayoutManager(recyclerViewLayoutManager);
                    listViewAdapter = new RecyclerViewAdapter(getApplicationContext());
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(listViewAdapter);
                }
                else {
                    TextView noItems = (TextView) findViewById(R.id.noItemsText);
                    noItems.setVisibility(View.VISIBLE);
                    noItems.setText(R.string.noFollowers);
                }
            }

            @Override
            public void onFailure(Call<List<Follower>> call, Throwable t) {

            }
        });
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


        public RecyclerViewAdapter(Context mContext) {
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView cardImageView;

            public ViewHolder(View v) {
                super(v);
                cardImageView = (ImageView) v.findViewById(R.id.cardImage);

            }
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item, parent, false);
            view.setOnClickListener(onClickListener);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {

            Follower user = list.get(position);
            Glide.with(getApplicationContext()).load(user.getAvatarUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.cardImageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.cardImageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = recyclerView.getChildLayoutPosition(view);
            String userName = list.get(position).getName();
            Intent intent = new Intent(getApplicationContext(), FollowerDetailsActivity.class);
            intent.putExtra("username", userName);
            startActivity(intent);
        }
    };
}
