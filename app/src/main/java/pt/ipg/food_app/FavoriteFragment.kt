package pt.ipg.food_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import pt.ipg.food_app.activities.MainActivity
import pt.ipg.food_app.adapters.FavoriteMealsAdapter
import pt.ipg.food_app.databinding.FragmentFavoriteBinding
import pt.ipg.food_app.viewModel.HomeViewModel


class FavoriteFragment : Fragment() {
    // View binding and ViewModel initialization.
    private lateinit var binding : FragmentFavoriteBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var favoriteAdapter : FavoriteMealsAdapter

    // Fragment initialization.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeMvvm = (activity as MainActivity).viewModel
    }

    // Inflate the layout for this fragment.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

// Set up the RecyclerView and observe the list of favorite meals.
        prepareFavoriteRecyclerView()
        observeFavorites()

// Set up an ItemTouchHelper to handle swipe gestures for deleting favorite meals.
        val itemTouchHelper = object  : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Handle swipe to delete and show a Snackbar with the option to undo.
                val position  = viewHolder.adapterPosition
                homeMvvm.deleteMeal(favoriteAdapter.differ.currentList[position])

                Snackbar.make(requireView(), "Recipe Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo", View.OnClickListener {
                      homeMvvm.insertMeal(favoriteAdapter.differ.currentList[position])
                    }
                ).show()

            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.tvFavorite)
    }

    // Set up RecyclerView for displaying
    // favorite meals and observe changes in the LiveData.
    private fun prepareFavoriteRecyclerView() {
      favoriteAdapter = FavoriteMealsAdapter()
        binding.tvFavorite.apply {
            layoutManager = GridLayoutManager(context, 2,GridLayoutManager.VERTICAL,false)
            adapter = favoriteAdapter
        }
    }

    // Observe changes in the LiveData for favorite meals and update the adapter.
    private fun observeFavorites() {
        homeMvvm.observeFavoriteMealsLiveData().observe(viewLifecycleOwner, Observer { meals->
          favoriteAdapter.differ.submitList(meals)
        })
    }

}