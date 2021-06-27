package com.example.nasadatasetdemo.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nasadatasetdemo.R
import com.example.nasadatasetdemo.databinding.FragmentDetailBinding
import com.example.nasadatasetdemo.view.base.BaseFragment
import com.example.nasadatasetdemo.view.bean.NasaItemBean

/**
 * A simple [Fragment] subclass.
 */
private const val DATE_ORIGINAL_SYMBOL = "-"
private const val DATE_REPLACE_SYMBOL = " "

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    private val args: DetailFragmentArgs by navArgs()
    private val nasaItemBean by lazy { args.nasaItemBean }

    private val mapMonth by lazy {
        HashMap<String, String>().apply {
            this["1"] = "Jan."
            this["2"] = "Feb."
            this["3"] = "Mar."
            this["4"] = "Apr."
            this["5"] = "May."
            this["6"] = "Jun."
            this["7"] = "Jul."
            this["8"] = "Aug."
            this["9"] = "Sep."
            this["10"] = "Oct."
            this["11"] = "Nov."
            this["12"] = "Dec."
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNasaBitmap()
        setView()
        setListener()
        setObserver()
    }

    private fun getNasaBitmap() {
        viewModel.getNasaBitmap(nasaItemBean.hdUrl)
    }

    private fun setView() {
        binding.date.text = transformDate(nasaItemBean.date)
        binding.name.text = nasaItemBean.title
        binding.copyright.text = String.format(getString(R.string.credit_copyright), nasaItemBean.copyright)
        binding.description.text = nasaItemBean.description
    }

    private fun transformDate(date: String): String {
        val firstIndex = date.indexOf(DATE_ORIGINAL_SYMBOL)
        val secIndex = date.indexOf(DATE_ORIGINAL_SYMBOL, firstIndex + 1)
        val strMonth = date.substring(firstIndex + 1, secIndex)

        mapMonth[strMonth]?.let {
            val result = date.replace(strMonth, it)
            return result.replace(DATE_ORIGINAL_SYMBOL, DATE_REPLACE_SYMBOL)
        }

        return date
    }

    private fun setListener() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setObserver() {
        viewModel.getNasaBitmapResponse.observe(viewLifecycleOwner) {
            binding.image.setImageBitmap(it)
            binding.progress.visibility = GONE
        }
    }

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentDetailBinding.inflate(inflater, container, false)

    override fun getViewModel() = DetailViewModel::class.java
}