package pt.ipg.food_app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pt.ipg.food_app.dataclass.MealByCategory
import pt.ipg.food_app.dataclass.MealByCategoryList
import pt.ipg.food_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel(){

    // LiveData to hold the list of meals for a specific category.
    val mealsLiveData = MutableLiveData<List<MealByCategory>>()

    // Function to fetch meals by category name from the API.
    fun getMealByCategory(categoryName : String){
        RetrofitInstance.foodApi.getMealByCategory(categoryName).enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(
                call: Call<MealByCategoryList>,
                response: Response<MealByCategoryList>
            ) {
                response.body()?.let { mealsList->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                // Log error message in case of API request failure.
             Log.e("CategoryMealsViewModel", t.message.toString())
            }

        })
    }

    // Function to observe changes in the mealsLiveData.
    fun observeMealsLiveData(): LiveData<List<MealByCategory>>{
        return mealsLiveData
    }
}