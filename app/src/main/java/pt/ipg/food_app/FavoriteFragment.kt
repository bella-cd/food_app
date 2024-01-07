package pt.ipg.food_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
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

        prepareFavoriteRecyclerView()
        observeFavorites()
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