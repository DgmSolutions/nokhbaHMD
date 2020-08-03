package com.nokhba.nokhbahmd.Notifications;

import com.nokhba.nokhbahmd.Model.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Service {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAQyXH7Ho:APA91bF9NkOBGozD0rHrJ7osvElh3ZfHyd4SLNeEmYcX-Xn1Ik29HqdwQZFyS5UgyoPgWqn9UfDc6pOVujvT8OS71FEdtzUChU3ERhxGoOZyWP5kOHgHFUmTnSo5TfdK2elppiXf7L-A"
            }
    )
    @POST("/fcm/send")
    Call<respance> sendNotifcation(@Body Notification body);
}
