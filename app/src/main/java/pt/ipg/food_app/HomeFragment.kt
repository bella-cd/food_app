package pt.ipg.food_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import pt.ipg.food_app.databinding.FragmentHomeBinding
import pt.ipg.food_app.dataclass.Meal
import pt.ipg.food_app.dataclass.MealList
import pt.ipg.food_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

// Perform a network request to fetch a random meal using Retrofit.
// Handle the response in the onResponse and onFailure callbacks.
// If the response is successful, load the meal's image using Glide into the ImageView.
        RetrofitInstance.foodApi.getRandomMeal().enqueue(object : Callback<MealList>{

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
               if(response.body() != null){
                   val randomMeal: Meal = response.body()!!.meals[0]
                  Glide.with(this@HomeFragment)
                      .load(randomMeal.strMealThumb)
                      .into(binding.imgRandomMeal)

               }else {
                   return
               }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }


         }

