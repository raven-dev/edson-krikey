package com.ravn.edsonkrikey.ui.detailsscreen

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ravn.edsonkrikey.R
import com.ravn.edsonkrikey.core.BaseFragment
import com.ravn.edsonkrikey.core.FragmentLayout
import com.ravn.edsonkrikey.databinding.FragmentDetailsBinding
import com.ravn.edsonkrikey.extensions.onUi
import com.ravn.edsonkrikey.extensions.then
import com.ravn.edsonkrikey.repository.remote.ItunesItems
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.view_information_input.view.*

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@FragmentLayout(R.layout.fragment_details)
class DetailsFragment : BaseFragment<DetailsViewModel, FragmentDetailsBinding>() {
    private val data by lazy { arguments?.getSerializable(DATA) }

    override fun initViewModel(viewModel: DetailsViewModel) {
        viewDataBinding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateView()
    }

    private fun updateView() = onUi {
        val itunesItem = data as ItunesItems
        Glide.with(context!!)
            .load(itunesItem.artworkUrl100)
            .apply(RequestOptions().centerCrop())
            .into(imageItem)
        artistName.text = itunesItem.artistName
        titleTrack.text = itunesItem.trackName
        dateTrack.text = viewModel.formatDate(itunesItem.releaseDate)
        timeTrack.text = viewModel.millisecondToMinutes(itunesItem.trackTimeMillis)
        genreTrack.text = itunesItem.primaryGenreName
        btnBuy.text = getString(R.string.button_text, itunesItem.trackPrice)
        descriptionTrack.text = (itunesItem.description != null) then itunesItem.description ?: itunesItem.longDescription
    }

    companion object {
        const val DATA = "Data"
        fun newInstance(item: ItunesItems?) = DetailsFragment().apply {
            enterTransition = Fade()
            exitTransition = Fade()
            arguments = Bundle().apply {
                putSerializable(DATA, item)
            }
        }
    }
}