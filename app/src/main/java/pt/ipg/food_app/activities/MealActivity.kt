package pt.ipg.food_app.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import pt.ipg.food_app.HomeFragment
import pt.ipg.food_app.R
import pt.ipg.food_app.databinding.ActivityMealBinding
import pt.ipg.food_app.dataclass.Meal
import pt.ipg.food_app.db.MealDatabase
import pt.ipg.food_app.viewModel.MealViewModel
import pt.ipg.food_app.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    // Late-initialized properties to store meal-related information and binding for the MealActivity.
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var youtubeLink:String
    private lateinit var mealMvvm: MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the MealDatabase and create a ViewModelFactory to instantiate the MealViewModel.
        val mealDatabase =  MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        // Initialize the MealViewModel using ViewModelProvider.
       // mealMvvm = ViewModelProvider(this).get(MealViewModel::class.java)

        // Retrieve meal information from the intent.
        getMealInformationFromIntent()
        // Populate the view with meal information.
        getInformationInView()

        loadingCase()

        // Fetch meal details and observe LiveData for updates.
        mealMvvm.getMealDetails(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()
        onFavoriteClick()
    }

    // Handles the click event for adding a meal to favorites,
    // invoking the insertMeal method from the ViewModel.
    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let { 
                mealMvvm.insertMeal(it)
                Toast.makeText(this,"Meal is save", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to Handle when click on the YouTube image, opening the video link in the default media player.
    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent (Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave:Meal?=null
    // this function Observe changes in the LiveData for meal details.
    private fun observerMealDetailsLiveData() {
        mealMvvm.observerDetailsLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(t: Meal) {
                onResponseCase()
                // Assign the observed 'Meal' data to a local variable 'meal'
                val meal = t

                mealToSave = meal

                // Update UI text views with meal category, area, and instructions.
                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstruction1.text = meal.strInstructions

                youtubeLink = meal.strYoutube

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

    //This function is used to hide various UI elements
    // (e.g., buttons, text views) and display a progress bar to indicate that the app is in a loading state.
    private  fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    //After a successful response, this function makes UI elements (buttons, text views, etc.)
    // visible again while hiding the progress bar to display the retrieved content to the user.
    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}