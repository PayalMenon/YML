package yml.android.example.com.yml.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by payal.menon on 3/18/17.
 */

public class Follower {

    @SerializedName("login")
    @Expose
    private String name;

    @Expose
    private int id;

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAvatarUrl(String url) {
        this.avatarUrl = url;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

}
