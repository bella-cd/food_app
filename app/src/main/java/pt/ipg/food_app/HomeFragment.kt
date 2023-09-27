package pt.ipg.food_app


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import pt.ipg.food_app.activities.MealActivity
import pt.ipg.food_app.adapters.MostPopularAdapter
import pt.ipg.food_app.databinding.FragmentHomeBinding
import pt.ipg.food_app.dataclass.CategoryMeals
import pt.ipg.food_app.dataclass.Meal
import pt.ipg.food_app.dataclass.MealList
import pt.ipg.food_app.retrofit.RetrofitInstance
import pt.ipg.food_app.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeMvvm : HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter:MostPopularAdapter

    // Companion object containing keys for passing meal-related data between components.
    companion object{
        const val MEAL_ID ="pt.ipg.food_app.fragments.idMeal"
        const val MEAL_NAME ="pt.ipg.food_app.fragments.nameMeal"
        const val MEAL_THUMB ="pt.ipg.food_app.fragments.thumbMeal"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)

        popularItemsAdapter = MostPopularAdapter()

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

        preparePopularItemsRecycleView()

         homeMvvm.getRandomMeal()
         observerRandomMeal()
         onRandomMealClick()

        homeMvvm.getPopularItems()
        observerPopularItemsLiveData()


    }

    // Configure and prepare the RecyclerView to display popular food items horizontally.
    private fun preparePopularItemsRecycleView() {
     binding.recViewMealsPopular.apply {
         layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
         adapter = popularItemsAdapter
     }
    }

    // Observe changes in the LiveData for popular food items and update the adapter.
    private fun observerPopularItemsLiveData() {
       homeMvvm.observerPopularItemsLiveData().observe(viewLifecycleOwner,
           { mealList->
               // Update the adapter with the list of popular food items.
               popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<CategoryMeals>)

           })
    }

    // This function handles the click event of the "randomMeal" button.
    private fun onRandomMealClick() {
       binding.randomMeal.setOnClickListener {
           val intent = Intent(activity,MealActivity::class.java)
           //These lines of code to prepare an intent with meal-related data as extras for launching another activity.
           intent.putExtra(MEAL_ID,randomMeal.idMeal)
           intent.putExtra(MEAL_NAME,randomMeal.strMeal)
           intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
           startActivity(intent)
       }
    }

    // Observes changes in LiveData for a random meal and updates the UI with the meal's thumbnail image using Glide.
    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner,
            {meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imgRandomMeal)
                this.randomMeal = meal

        })

    }


}

