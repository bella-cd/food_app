package pt.ipg.food_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pt.ipg.food_app.databinding.CategoryItemsBinding
import pt.ipg.food_app.dataclass.Category

// RecyclerView adapter for displaying a list of categories.
class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private  var categoriesList = ArrayList<Category>()

    // Set the list of categories and notify data changes.
    fun setCategoriesList (categoriesList: List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    // ViewHolder class for holding views of individual category items.
    inner class CategoryViewHolder(val binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Inflate and return a ViewHolder for a category item view.
      return CategoryViewHolder(
          CategoryItemsBinding.inflate(
              LayoutInflater.from(parent.context), parent, false
          )
      )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        // Bind data to views within the ViewHolder.
     Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory
    }


    override fun getItemCount(): Int {
        // Return the number of categories in the list.
        return  categoriesList.size
    }

}
