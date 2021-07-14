package com.gmail.sangckm93.superchat.Fragments;

import com.gmail.sangckm93.superchat.Notifications.MyReponse;
import com.gmail.sangckm93.superchat.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA8ppZqnw:APA91bH1lp4N1BDxtKR0-2hmY6tVZQYyflTkO_QkPxLrrqG87yxvFRJfFr6XdAzj6PImDoy4230c7wqoYyqjhn0MJDEpxOJdTHwzi7gqfCgfMCsYV1q0Dxq4VInC3ePDXPR-04vLl23Z"
            }
            )

    @POST("fcm/send")
    Call<MyReponse> sendNotification(@Body Sender body);

}
