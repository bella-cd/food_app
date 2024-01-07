package pt.ipg.food_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pt.ipg.food_app.databinding.MealItemBinding
import pt.ipg.food_app.dataclass.Meal

class FavoriteMealsAdapter : RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteMealsAdapterViewHolder>() {

    // View holder for the FavoriteMealsAdapter, holds the MealItemBinding.
    inner class FavoriteMealsAdapterViewHolder(val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root)
// DiffUtil callback for calculating the difference between two lists of meals.
    private val diffUtil = object  : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
          return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    // AsyncListDiffer instance using the defined diffUtil
    // callback for efficient RecyclerView updates.
    val differ = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMealsAdapterViewHolder {
       return FavoriteMealsAdapterViewHolder(
           MealItemBinding.inflate(
               LayoutInflater.from(parent.context),parent, false
           )
       )
    }

    // Inflates the layout and creates a new view holder when needed.
    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    // Binds the data to the view holder, loading meal thumbnail with Glide.
    override fun onBindViewHolder(holder: FavoriteMealsAdapterViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = meal.strMeal
    }

}
