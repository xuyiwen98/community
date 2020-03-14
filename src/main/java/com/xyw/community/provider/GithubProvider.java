package com.xyw.community.provider;

import com.alibaba.fastjson.JSON;
import com.xyw.community.dto.AccessTokenDTO;
import com.xyw.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        //大概是
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        //JSON.parseObject，是将Json字符串转化为相应的对象；JSON.toJSONString则是将对象转化为Json字符串。
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        //try()中的代码一般放的是对资源的申请,如果{}中的代码出项了异常,()中的资源就会被关闭
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //System.out.println(string);
            //access_token=63e85f3632be4d39ecf8d2641fe494ffdcd144f0&scope=user&token_type=bearer
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //string=" {"login":"xuyiwen98","id":48862030,"node_id":"MDQ6VXNlcjQ4ODYyMDMw","avatar_url":"https://avatars3.githubusercontent.com/u/48862030?v=4","gravatar_id":"","url":"https://api.github.com/users/xuyiwen98","html_url":"https://github.com/xuyiwen98","followers_url":"https://api.github.com/users/xuyiwen98/followers","following_url":"https://api.github.com/users/xuyiwen98/following{/other_user}","gists_url":"https://api.github.com/users/xuyiwen98/gists{/gist_id}","starred_url":"https://api.github.com/users/xuyiwen98/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/xuyiwen98/subscriptions","organizations_url":"https://api.github.com/users/xuyiwen98/orgs","repos_url":"https://api.github.com/users/xuyiwen98/repos","events_url":"https://api.github.com/users/xuyiwen98/events{/privacy}","received_events_url":"https://api.github.com/users/xuyiwen98/received_events","type":"User","site_admin":false,"name":"双人与余","company":"student","blog":"","location":null,"email":null,"hireable":null,"bio":"学习使用","public_repos":3,"public_gists":0,"followers":0,"following":0,"created_at":"2019-03-23T15:13:48Z","updated_at":"2020-03-13T04:32:56Z","private_gists":0,"total_private_repos":0,"owned_private_repos":0,"disk_usage":378,"collaborators":0,"two_factor_authentication":false,"plan":{"name":"free","space":976562499,"collaborators":0,"private_repos":10000}} "
            //将Json字符串转化为GithubUser对象,自动注入相应值
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;

    }
}
