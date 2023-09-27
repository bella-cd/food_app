package pt.ipg.food_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pt.ipg.food_app.databinding.PopularItemsBinding
import pt.ipg.food_app.dataclass.CategoryMeals

// Adapter for displaying a list of most popular meals
class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {


    lateinit var onItemsClick:((CategoryMeals)-> Unit)
    private var  mealsList = ArrayList<CategoryMeals>()

    // Function to set a new list of meals and notify the adapter of the data change.
    fun setMeals(mealsList : ArrayList<CategoryMeals>){
        this.mealsList= mealsList
        notifyDataSetChanged()
    }

    // Create and return a PopularMealViewHolder by inflating its associated binding layout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {

        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    // Load an image into the ImageView using Glide for the given position in the adapter.
    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(mealsList[position].strMealThumb)
           .into(holder.binding.imgPopularMealItem)

// Set up a click listener for RecyclerView items and invoke a callback function when clicked.
        holder.itemView.setOnClickListener {
            onItemsClick.invoke(mealsList[position])
        }

    }

    // Get the count of items in the 'mealsList' to determine the RecyclerView's item count.
    override fun getItemCount(): Int {
      return mealsList.size
    }

    // ViewHolder class for holding views of individual items in a RecyclerView.
    class PopularMealViewHolder( var binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root)
}