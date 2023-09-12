package pt.ipg.food_app.retrofit

import pt.ipg.food_app.dataclass.MealList
import retrofit2.Call
import retrofit2.http.GET

interface FoodApi {

// Interface defining a Retrofit endpoint to fetch a random meal.
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}