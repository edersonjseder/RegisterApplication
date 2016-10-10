package com.register.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.register.application.model.BillsToPay;
import com.register.application.utils.Utils;

import java.util.List;

import okhttp3.OkHttpClient;
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
 * Created by ederson on 24/04/16.
 */
public interface BillsToPayService {

    @POST(Utils.END_POINT_BILLSTOPAY)
    Call<BillsToPay> createInfo(@Body BillsToPay billsToPay);

    @GET(Utils.END_POINT_BILLSTOPAY + Utils.JSON)
    Call<List<BillsToPay>> listAll();

    @PUT(Utils.END_POINT_BILLSTOPAY + "/{billsToPayId}")
    Call<BillsToPay> updateInfo(@Path("billsToPayId") int billsToPayId, @Body BillsToPay billsToPay);

    @DELETE(Utils.END_POINT_BILLSTOPAY + "/{billsToPayId}")
    Call<ResponseBody> deleteInfo(@Path("billsToPayId") int billsToPayId);


    class Factory {

        public static BillsToPayService create() {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.BASE_URL_BY_DEVICE)
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(httpClient.build()).build();

            return retrofit.create(BillsToPayService.class);
        }
    }

}
