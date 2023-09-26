package pt.ipg.food_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import pt.ipg.food_app.HomeFragment
import pt.ipg.food_app.R
import pt.ipg.food_app.databinding.ActivityMealBinding
import pt.ipg.food_app.dataclass.Meal
import pt.ipg.food_app.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    // Late-initialized properties to store meal-related information and binding for the MealActivity.
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the MealViewModel using ViewModelProvider.
        mealMvvm = ViewModelProvider(this).get(MealViewModel::class.java)

        // Retrieve meal information from the intent.
        getMealInformationFromIntent()
        // Populate the view with meal information.
        getInformationInView()

        // Fetch meal details and observe LiveData for updates.
        mealMvvm.getMealDetails(mealId)
        observerMealDetailsLiveData()
    }

    // this function Observe changes in the LiveData for meal details.
    private fun observerMealDetailsLiveData() {
        mealMvvm.observerDetailsLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(t: Meal) {
                // Assign the observed 'Meal' data to a local variable 'meal'
                val meal = t

                // Update UI text views with meal category, area, and instructions.
                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstruction1.text = meal.strInstructions
            }

        })
    }

    // Populates the view with meal information, including an image, title, and title text colors.
    private fun getInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.g_black))
    }

    // This function Retrieves meal information from the intent and assigns it to corresponding properties.
    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
}