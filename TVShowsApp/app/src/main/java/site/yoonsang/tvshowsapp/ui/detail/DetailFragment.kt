package site.yoonsang.tvshowsapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import site.yoonsang.tvshowsapp.R
import site.yoonsang.tvshowsapp.databinding.FragmentDetailBinding

@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view)
        val args = DetailFragmentArgs.fromBundle(requireArguments())
        val show = args.show

        binding.apply {
            detailTextCountry.text = show.country
            detailTextName.text = show.name
            detailTextStartDate.text = show.start_date
            detailTextStatus.text = show.status
            Glide.with(binding.root)
                .load(show.image_thumbnail_path)
                .into(detailImageView)
        }
    }
}