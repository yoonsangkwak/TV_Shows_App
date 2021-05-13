package site.yoonsang.tvshowsapp.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import site.yoonsang.tvshowsapp.R
import site.yoonsang.tvshowsapp.database.FavoriteShow
import site.yoonsang.tvshowsapp.databinding.FragmentDetailBinding

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel by viewModels<DetailViewModel>()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)
        val args = DetailFragmentArgs.fromBundle(requireArguments())
        val show = args.show

        binding.apply {
            this!!.detailTextCountry.text = show.country
            detailTextName.text = show.name
            detailTextStartDate.text = show.start_date
            detailTextStatus.text = show.status
            Glide.with(binding!!.root)
                .load(show.image_thumbnail_path)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(detailImageView)

            var _isChecked = false
            CoroutineScope(Dispatchers.Main).launch {
                val count = viewModel.checkShow(show.id.toString())

                withContext(Dispatchers.Main) {
                    if (count > 0) {
                        toggleButton.isChecked = true
                        _isChecked = true
                    } else {
                        toggleButton.isChecked = false
                        _isChecked = false
                    }
                }
            }

            toggleButton.setOnClickListener {
                _isChecked = !_isChecked
                if (_isChecked) {
                    viewModel.addToFavorite(
                        FavoriteShow(
                            show.id,
                            show.name,
                            show.start_date,
                            show.country,
                            show.status,
                            show.image_thumbnail_path
                        )
                    )
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.removeFromFavorite(show.id.toString())
                    Toast.makeText(requireContext(), "Removed!", Toast.LENGTH_SHORT).show()
                }
                toggleButton.isChecked = _isChecked
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}