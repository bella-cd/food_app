package pt.ipg.food_app.retrofit

import pt.ipg.food_app.dataclass.CategoryList
import pt.ipg.food_app.dataclass.MealByCategoryList
import pt.ipg.food_app.dataclass.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {

// Interface defining a Retrofit endpoint to fetch a random meal.
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    // Retrofit GET request to fetch meal details by ID.
    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String) : Call<MealList>

    // Retrofit GET request to fetch popular food items filtered by category name.
    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName: String) : Call<MealByCategoryList>

    // Retrofit endpoint to fetch a list of meal categories.
    @GET ("categories.php")
    fun getCategories(): Call<CategoryList>

    // Retrofit GET request to fetch meals filtered by category name.
    @GET("filter.php")
    fun getMealByCategory(@Query ("c") categoryName: String) : Call <MealByCategoryList>
}