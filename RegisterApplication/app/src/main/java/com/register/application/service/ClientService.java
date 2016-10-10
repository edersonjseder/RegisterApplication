package com.register.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.register.application.model.Clients;
import com.register.application.utils.Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ederson.js on 28/03/2016.
 */
public interface ClientService {

    @POST(Utils.END_POINT_CLIENTS)
    Call<Clients> createInfo(@Body Clients clients);

    @GET(Utils.END_POINT_CLIENTS + Utils.JSON)
    Call<List<Clients>> listAll();

    @PUT(Utils.END_POINT_CLIENTS + "/{clientsId}")
    Call<Clients> updateInfo(@Path("clientsId") int clientsId, @Body Clients clients);

    @DELETE(Utils.END_POINT_CLIENTS + "/{clientsId}")
    Call<ResponseBody> deleteInfo(@Path("clientsId") int clientId);

    class Factory {

        public static ClientService create() {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .header("Authorization", "Bearer 1457d5e4-fae3-439b-bd5e-0b20d505f97b")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.BASE_URL_BY_DEVICE)
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(httpClient.build()).build();

            return retrofit.create(ClientService.class);
        }
    }
}
