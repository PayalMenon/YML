package yml.android.example.com.yml.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yml.android.example.com.yml.R;
import yml.android.example.com.yml.pojo.Follower;
import yml.android.example.com.yml.pojo.User;
import yml.android.example.com.yml.services.GithubService;

/**
 * Created by payal.menon on 3/19/17.
 */

public class FollowerDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follower_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (null != getIntent()) {
            String userName = getIntent().getStringExtra("username");
            getFollowerDetails(userName);
        }
    }

    private static String BASE_URL = "https://api.github.com/users/";

    private void getFollowerDetails(String userName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubService gitService = retrofit.create(GithubService.class);
        Call<User> call = gitService.getFollowerDetails(userName);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User followerDetails = response.body();
                showDetails(followerDetails);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
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

    private void showDetails(User followerDetails) {

        final ImageView followerImageView = (ImageView) findViewById(R.id.follower_details_image);
        Glide.with(getApplicationContext()).load(followerDetails.getAvatarUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(followerImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                followerImageView.setImageDrawable(circularBitmapDrawable);
            }
        });

        TextView followerUsername = (TextView) findViewById(R.id.follower_username);
        if (null != followerDetails.getUser()) {
            followerUsername.setText(followerDetails.getUser());
        } else {
            followerUsername.setVisibility(View.GONE);
        }

        TextView name = (TextView) findViewById(R.id.follower_name);
        if (null != followerDetails.getName()) {
            name.setText(followerDetails.getName());
        } else {
            name.setVisibility(View.GONE);
        }

        TextView followersCount = (TextView) findViewById(R.id.followers_count);
        if (null != followerDetails.getNoOfFollowers()) {
            followersCount.setText(followerDetails.getNoOfFollowers());
        } else {
            followersCount.setText("0");
        }

        TextView followingCount = (TextView) findViewById(R.id.following_count);
        if (null != followerDetails.getNoOfFollowing()) {
            followingCount.setText(followerDetails.getNoOfFollowing());
        } else {
            followingCount.setText("0");
        }

        TextView reposCount = (TextView) findViewById(R.id.repos_count);
        if (null != followerDetails.getNoOfRepositories()) {
            reposCount.setText(followerDetails.getNoOfRepositories());
        } else {
            reposCount.setText("0");
        }

        TextView location = (TextView) findViewById(R.id.follower_location);
        if (null != followerDetails.getLocation()) {
            location.setText(followerDetails.getLocation());
        } else {
            location.setText("NA");
        }

        TextView email = (TextView) findViewById(R.id.follower_email);
        if (null != followerDetails.getEmail()) {
            email.setText(followerDetails.getEmail());
        } else {
            email.setText("NA");
        }
    }

}
