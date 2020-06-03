package com.example.championlike.Application.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.championlike.Data.Model.Champion
import com.example.championlike.R


class ChampionSelectionAdapter(private val glide:RequestManager): ListAdapter<Champion, ChampionSelectionAdapter.ItemViewHolder>(Champion.DIFF_CALLBACK){

    interface ItemClickListener{
        fun itemClicked(name:String)
    }

    private lateinit var itemClickListener: ItemClickListener

    override fun onBindViewHolder(p0: ItemViewHolder, p1: Int) {
        p0.bind(getItem(p1))
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {

        val view = LayoutInflater.from(p0.context).inflate(R.layout.champion_select_item,p0,false)
        return ItemViewHolder(view)
    }

    fun setOnItemClickListener(listener: ItemClickListener){
        itemClickListener = listener
    }

    inner class ItemViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{

        private val imageView:ImageView = view.findViewById(R.id.champion_splash_image)
        private val textView = view.findViewById<TextView>(R.id.champion_name_textview)

        fun bind(champ:Champion){

            textView.text = champ.name

            glide
                .load(champ.imageUrl)
                .centerCrop()
                .into(imageView)

            view.setOnClickListener(this)
        }

        override fun onClick(view:View?) {
            view?.let{
                itemClickListener.itemClicked(it.findViewById<TextView>(R.id.champion_name_textview).text.toString())
            }
        }
    }
}