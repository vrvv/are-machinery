package com.are.rest_api;


import com.are.MyApp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestServiceFactory {

    private static OkHttpClient.Builder httpClientUser = new OkHttpClient.Builder();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit retrofit;
    private static Retrofit retrofitUser;
    private static ApiService apiServiceUser;
    private static ApiService apiService;

    private static <S> S createServiceUser(Class<S> serviceClass) {
        if (apiServiceUser == null) {

            setRetrofitLogLevel(httpClientUser);

            httpClientUser.readTimeout(30, TimeUnit.SECONDS);
            httpClientUser.connectTimeout(30, TimeUnit.SECONDS);

            httpClientUser.addInterceptor(new AuthenticationInterceptor());

            String baseUrl = AppConstants.Url.API_URL;

            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create());
            retrofitUser = builder.client(httpClientUser.build()).build();
            apiServiceUser = (ApiService) retrofitUser.create(serviceClass);
        }
        return (S) apiServiceUser;
    }

    public static ApiService createServiceUser() {
        return createServiceUser(ApiService.class);
    }

    private static void setRetrofitLogLevel(OkHttpClient.Builder httpClientUser) {
        if (MyApp.RETROFIT_SHOW_LOG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            httpClientUser.addInterceptor(logging);
        }
    }

    public static class AuthenticationInterceptor implements Interceptor {

        private String authToken;

        public AuthenticationInterceptor() {
            this.authToken = Credentials.basic(AppConstants.Auth.USERNAME, AppConstants.Auth.PASSWORD);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .header("Authorization", authToken);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }
}
