package pt.ipg.food_app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.ipg.food_app.dataclass.Category
import pt.ipg.food_app.dataclass.CategoryList
import pt.ipg.food_app.dataclass.MealByCategoryList
import pt.ipg.food_app.dataclass.MealByCategory
import pt.ipg.food_app.dataclass.Meal
import pt.ipg.food_app.dataclass.MealList
import pt.ipg.food_app.db.MealDatabase
import pt.ipg.food_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (
    // Declaration of a MealDatabase instance to handle database operations.
    private var mealDatabase: MealDatabase
): ViewModel(){
    // MutableLiveData to hold a random meal
    private var randomMealLiveData = MutableLiveData<Meal>()

    // MutableLiveData to hold a popular meal
    private var popularItemsLiveData = MutableLiveData<List<MealByCategory>>()

    // MutableLiveData to hold a  meals categories
    private var  categoriesLiveData = MutableLiveData<List<Category>>()

    // Initializing a LiveData object to observe all favorite
// meals from the local database.
    private var favoriteMealsLiveData = mealDatabase.MealDao().getAllMeals()
    // Function to fetch a random meal from the network
    fun getRandomMeal(){
        RetrofitInstance.foodApi.getRandomMeal().enqueue(object : Callback<MealList> {

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    // Extract the random meal from the response
                    val randomMeal: Meal = response.body()!!.meals[0]
                    // Function to observe the MutableLiveData for random meal data
                    randomMealLiveData.value = randomMeal

                }else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    // Fetch popular food items using Retrofit and update the LiveData with the result.
    fun getPopularItems(){
        RetrofitInstance.foodApi.getPopularItems("Seafood").enqueue(object : Callback<MealByCategoryList>{

            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
              if(response.body()!= null){
                  // Update the LiveData with the list of popular items
                  popularItemsLiveData.value = response.body()!!.meals
              }
            }
            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                // Log any errors for debugging purposes.
              Log.d("HomeFragment", t.message.toString())
            }

        })

    }
    // Fetch food categories using Retrofit and update the LiveData with the result.
 fun getCategories(){

     RetrofitInstance.foodApi.getCategories().enqueue(object : Callback<CategoryList>{
         override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
             // Check if the response body is not null and update LiveData with categories.
            response.body()?.let {  categoryList ->
                categoriesLiveData.postValue(categoryList.categories)
            }
         }

         override fun onFailure(call: Call<CategoryList>, t: Throwable) {
             // Log an error message if the network request fails.
          Log.e("HomeViewModel", t.message.toString())
         }

     })

 }
    // Asynchronously inserts a Meal into the Room database using coroutines and viewModelScope.
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.MealDao().upsert(meal)
        }
    }

    // Asynchronously deletes a Meal from the Room database using coroutines and viewModelScope.
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.MealDao().delete(meal)
        }
    }

    // Function to observe the MutableLiveData for random meal data
    fun observeRandomMealLivedata(): LiveData<Meal>{
        return randomMealLiveData
    }

    // Function to provide LiveData for observing changes in the list of popular food items.
    fun observerPopularItemsLiveData () :LiveData<List<MealByCategory>>{
        return popularItemsLiveData
    }

    // Function to provide LiveData for observing changes in the list of categories.
    fun observerCategoriesLiveData(): LiveData<List<Category>>{
        return categoriesLiveData
    }

    // Function to observe changes in the LiveData representing favorite meals.
    fun observeFavoriteMealsLiveData(): LiveData<List<Meal>>{
        return favoriteMealsLiveData
    }
}