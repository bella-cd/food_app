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

// ViewModel for handling meal details data retrieval and observation.
class MealViewModel:ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    // Function to fetch meal details by ID using Retrofit.
    fun getMealDetails(id: String){
        RetrofitInstance.foodApi.getMealDetails(id).enqueue(object : Callback<MealList> {

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!= null){
                    // Set the meal details LiveData value to the first meal in the response.
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }else{
                    return
                }
            }
            // Callback for handling a network request failure.
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity",t.message.toString())
            }

        })
    }
    // Function to observe the meal details LiveData.
    fun observerDetailsLiveData(): LiveData<Meal>{
        return mealDetailsLiveData
    }
}