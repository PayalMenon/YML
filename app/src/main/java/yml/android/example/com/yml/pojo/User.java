package yml.android.example.com.yml.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by payal.menon on 3/18/17.
 */

public class User {

    @SerializedName("login")
    @Expose
    private String user;

    @Expose
    private int id;

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    @Expose
    private String noOfFollowers;

    @Expose
    private String noOfFollowing;

    @SerializedName("public_repos")
    @Expose
    private String noOfRepositories;

    @Expose
    private String location;

    @Expose
    private String email;

    @Expose
    private String name;


    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setNoOfFollowers(String followers) {
        this.noOfFollowers = followers;
    }

    public String getNoOfFollowers() {
        return noOfFollowers;
    }

    public void setNoOfFollowing(String following) {
        this.noOfFollowing = following;
    }

    public String getNoOfFollowing() {
        return noOfFollowing;
    }

    public void setNoOfRepositories(String repositories) {
        this.noOfRepositories = repositories;
    }

    public String getNoOfRepositories() {
        return noOfRepositories;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
