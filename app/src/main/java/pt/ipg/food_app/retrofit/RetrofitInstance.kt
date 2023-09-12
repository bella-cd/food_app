package pt.ipg.food_app.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton object for creating and configuring Retrofit instance to access the Food API.
object RetrofitInstance {
    val foodApi:FoodApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApi::class.java)
    }
}