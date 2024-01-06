package pt.ipg.food_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import pt.ipg.food_app.HomeFragment
import pt.ipg.food_app.R
import pt.ipg.food_app.adapters.CategoryMealAdapter
import pt.ipg.food_app.databinding.ActivityCategoryMealsBinding
import pt.ipg.food_app.databinding.ActivityMealBinding
import pt.ipg.food_app.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    // View binding instance for the activity layout.
    lateinit var  binding : ActivityCategoryMealsBinding

    // ViewModel instance for handling data related to category meals.
    lateinit var  categoryMealsViewModel: CategoryMealsViewModel

    lateinit var categoryMealAdapter: CategoryMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inflating the layout using view binding.
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        // Initializing the ViewModel for CategoryMealsActivity.
        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        // Observing changes in the mealsLiveData and logging each meal's name.
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList->
            //Sets the text of the 'tvCategoryCount' TextView to display the count of meals.
            //  Updates the CategoryMealAdapter with the new list of meals.
           binding.tvCategoryCount.text = mealsList.size.toString()
          //  Updates the CategoryMealAdapter with the new list of meals.
            categoryMealAdapter.setMealsList(mealsList)
        })
    }

    private fun prepareRecyclerView() {
        // Initialize the CategoryMealAdapter
        categoryMealAdapter = CategoryMealAdapter()

        // Set the layout manager and adapter for the RecyclerView
        binding.tvMeals.apply {
            layoutManager = GridLayoutManager(context, 2,GridLayoutManager.VERTICAL, false)
            adapter = categoryMealAdapter
        }

    }
}