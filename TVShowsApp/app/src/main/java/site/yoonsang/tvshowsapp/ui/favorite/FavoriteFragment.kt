package site.yoonsang.tvshowsapp.ui.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import site.yoonsang.tvshowsapp.R
import site.yoonsang.tvshowsapp.data.model.Show
import site.yoonsang.tvshowsapp.databinding.FragmentFavoriteBinding

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val viewModel by viewModels<FavoriteViewModel>()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavoriteBinding.bind(view)

        val adapter = FavoriteAdapter(FavoriteAdapter.ShowClickListener { favoriteShow ->
            val show = Show(
                favoriteShow.id,
                favoriteShow.name,
                favoriteShow.start_date,
                favoriteShow.country,
                favoriteShow.status,
                favoriteShow.image_thumbnail_path
            )
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(
                    show
                )
            )
        })

        binding.apply {
            this!!.favoriteList.setHasFixedSize(true)
            favoriteList.adapter = adapter
        }

        viewModel.shows.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val show = adapter.currentList[position]
                viewModel.delete(show)

                Snackbar.make(binding!!.root, "deleted!", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.insert(show)
                    }
                    show()
                }
            }
        }).attachToRecyclerView(binding!!.favoriteList)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.favorite_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_all -> viewModel.deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}