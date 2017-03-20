package yml.android.example.com.yml.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import yml.android.example.com.yml.pojo.Follower;
import yml.android.example.com.yml.pojo.User;

/**
 * Created by payal.menon on 3/18/17.
 */

public interface GithubService {

    @GET("{username}/followers")
    Call<List<Follower>> getFollowers(@Path("username") String username);

    @GET("{username}")
    Call<User> getFollowerDetails(@Path("username") String username);
}
