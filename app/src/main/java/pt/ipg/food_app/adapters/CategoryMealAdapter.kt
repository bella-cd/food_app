package pt.ipg.food_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pt.ipg.food_app.databinding.MealItemBinding
import pt.ipg.food_app.dataclass.MealByCategory

// Adapter for displaying meals in a RecyclerView
class CategoryMealAdapter : RecyclerView.Adapter<CategoryMealAdapter.CategoryMealViewModel> (){
    // List to store the meals
    private var mealsList = ArrayList<MealByCategory> ()

    // Set the list of meals and notify the adapter
    fun setMealsList(mealsList: List<MealByCategory>){
        this.mealsList = mealsList as ArrayList<MealByCategory>
        notifyDataSetChanged()
}

    // ViewHolder class for holding the view components
    inner class CategoryMealViewModel(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    // Create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewModel {
       return CategoryMealViewModel(
           MealItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    // Get the number of items in the list
    override fun getItemCount(): Int {
        return mealsList.size
    }

    // Bind the data to the ViewHolder
    override fun onBindViewHolder(holder: CategoryMealViewModel, position: Int) {
        // Load meal image using Glide
     Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeal)
        // Set the meal name
        holder.binding.tvMealName.text = mealsList[position].strMeal
    }
}