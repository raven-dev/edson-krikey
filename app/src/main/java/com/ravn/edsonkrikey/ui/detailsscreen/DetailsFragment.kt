package com.ravn.edsonkrikey.ui.detailsscreen

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
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
    private var mediaPlayer: MediaPlayer? = null

    override fun initViewModel(viewModel: DetailsViewModel) {
        viewDataBinding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateView()
    }

    private fun updateView() = onUi {
        val itunesItem = data as ItunesItems
        kindOfItem.text = itunesItem.kind
        Glide.with(context!!)
            .load(itunesItem.artworkUrl100)
            .into(imageItem)
        artistName.inputBody.text = itunesItem.artistName
        trackName.inputBody.text = itunesItem.trackName
        genreName.inputBody.text = itunesItem.primaryGenreName
        trackPrice.inputBody.text = itunesItem.trackPrice.toString()
        releaseDate.inputBody.text = viewModel.formatDate(itunesItem.releaseDate)
        updateAdvisory(itunesItem.contentAdvisoryRating)
        updateDescription(itunesItem)
    }

    private fun updateAdvisory(contentAdvisoryRating: String?) {
        if (contentAdvisoryRating != null ) {
            advisory.inputBody.text = contentAdvisoryRating
        } else {
            advisory.visibility = View.GONE
        }
    }

    private fun updateDescription(itunesItem: ItunesItems) = onUi {
        if (itunesItem.description == null && itunesItem.longDescription == null) {
            description.visibility = View.GONE
        } else {
            description.inputBody.text = (itunesItem.description != null) then itunesItem.description ?: itunesItem.longDescription
        }
    }

    companion object {
        const val DATA = "Data"
        fun newInstance(item: ItunesItems?) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(DATA, item)
            }
        }
    }
}