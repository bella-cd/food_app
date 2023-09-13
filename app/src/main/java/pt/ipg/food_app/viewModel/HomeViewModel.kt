package pt.ipg.food_app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pt.ipg.food_app.dataclass.Meal
import pt.ipg.food_app.dataclass.MealList
import pt.ipg.food_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (): ViewModel(){
    // MutableLiveData to hold a random meal
    private var randomMealLiveData = MutableLiveData<Meal>()
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
    // Function to observe the MutableLiveData for random meal data
    fun observeRandomMealLivedata(): LiveData<Meal>{
        return randomMealLiveData
    }
}