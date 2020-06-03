package com.example.championlike.Application.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.championlike.Application.Adapter.ChampionSelectionAdapter
import com.example.championlike.Domain.ViewModel.ChampionViewModel
import com.example.championlike.R
import org.koin.android.viewmodel.ext.android.viewModel

class ChampionSelectionFragment : Fragment(),
    ChampionSelectionAdapter.ItemClickListener {

    private lateinit var recyclerView: RecyclerView

    val champViewModel: ChampionViewModel by viewModel()

    companion object{
        val NAME = ChampionSelectionFragment.javaClass.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.champion_selection_fragment,container,false)

        recyclerView = view.findViewById(R.id.champ_select_recyclerview)

        val layoutManager: LinearLayoutManager = GridLayoutManager(activity,2)
        recyclerView.layoutManager = layoutManager


        val adapter = ChampionSelectionAdapter(Glide.with(this))

        adapter.setOnItemClickListener(this)

        recyclerView.adapter = adapter

        champViewModel.bind(this.context?.filesDir)

        champViewModel.champions.observe(this, Observer {
            adapter.submitList(it)
        })

        return view
    }

    override fun itemClicked(name: String) {

        activity?.let{
            it.supportFragmentManager
                .beginTransaction()
                .replace(R.id.rootLayout,
                    ChampionDetailedFragment.newInstance(name),
                    ChampionDetailedFragment.NAME)
                .addToBackStack(null)
                .commit()
        }
    }
}