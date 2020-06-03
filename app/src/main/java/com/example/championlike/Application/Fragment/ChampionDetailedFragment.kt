package com.example.championlike.Application.Fragment


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.championlike.Data.Model.Champion
import com.example.championlike.Domain.ViewModel.ChampionDetailViewModel
import com.example.championlike.R
import org.koin.android.viewmodel.ext.android.viewModel

class ChampionDetailedFragment : Fragment(){

    private var championName:String? = null

    private val championDetailViewModel: ChampionDetailViewModel by viewModel()

    private lateinit var linearLayout:LinearLayout

    private var buttonPressedDrawable: Drawable? = null
    private var buttonNotPressedDrawable: Drawable? = null

    companion object{

        val CHAMPION_NAME_KEY:String = "CHAMPION_NAME_KEY"

        val NAME:String = ChampionDetailedFragment::class.java.simpleName

        fun newInstance(championName:String): ChampionDetailedFragment {
            val fragment = ChampionDetailedFragment()

            val bundle = Bundle()
            bundle.putString(CHAMPION_NAME_KEY,championName)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity?.let{
            buttonPressedDrawable = ContextCompat.getDrawable(it.baseContext,R.drawable.button_pressed_background)
            buttonNotPressedDrawable = ContextCompat.getDrawable(it.baseContext,R.drawable.button_not_pressed_background)
        }

        val view:View = inflater.inflate(R.layout.champion_detail_fragment,container,false)
        linearLayout= view.findViewById(R.id.linear_layout)
        championName = arguments?.getString(CHAMPION_NAME_KEY)

        if(savedInstanceState == null) {

            championName?.let {
                championDetailViewModel.bind(it)
            }

        }

        championDetailViewModel.championDetail.observe(this, Observer {

            it?.let{
                if(it.name.isEmpty()){
                    backToChampionSelect()
                }
                else{
                    addCardView(it)
                }
            }

        })

        championDetailViewModel.championSkin.observe(this, Observer{
            for(skin in it){
                val cardView:CardView? = getCardView(skin.skinName)

                cardView?.let{

                    val likeButton:Button = it.findViewById(R.id.like_button)
                    val dislikeButton:Button = it.findViewById(R.id.dislike_button)

                    if(skin.liked){
                        likeButton.setBackground(buttonPressedDrawable)
                        dislikeButton.setBackground(buttonNotPressedDrawable)
                    }
                    else if(skin.disliked){
                        likeButton.setBackground(buttonNotPressedDrawable)
                        dislikeButton.setBackground(buttonPressedDrawable)
                    }
                    else{
                        likeButton.setBackground(buttonNotPressedDrawable)
                        dislikeButton.setBackground(buttonNotPressedDrawable)
                    }
                }

            }
        })

        return view
    }

    fun getCardView(skinName:String):CardView?{

        for(i in 0 until linearLayout.childCount){
            if(linearLayout.getChildAt(i).tag.equals(skinName)){
                return linearLayout.getChildAt(i) as CardView
            }
        }

        return null

    }

    fun addCardView(champ:Champion){

        champ.skins?.let {

            for (key in it.keys) {

                val cardView: CardView =
                    activity?.layoutInflater?.inflate(R.layout.detail_card_view, linearLayout, false) as CardView
                linearLayout.addView(cardView)

                Glide.with(this)
                    .load(Champion.getSkinUrl(key,champ.name))
                    .centerCrop()
                    .into(cardView.findViewById<ImageView>(R.id.card_view_image))

                cardView.setTag(it.get(key))

                val likeButton = cardView.findViewById<Button>(R.id.like_button)
                val dislikeButton = cardView.findViewById<Button>(R.id.dislike_button)

                likeButton.setTag(key)
                dislikeButton.setTag(key)

                likeButton.setOnClickListener {
                    championDetailViewModel.likeOrDislikeButtonPressed(it.tag as Int,true)
                }
                dislikeButton.setOnClickListener {
                    championDetailViewModel.likeOrDislikeButtonPressed(it.tag as Int,false)
                }

                cardView.findViewById<TextView>(R.id.card_view_text).text = it.get(key)

                champ.liked.get(key)?.let{like->
                    if(like){
                        cardView.findViewById<Button>(R.id.like_button).setBackground(buttonPressedDrawable)
                    }
                }

                champ.disliked.get(key)?.let{dislike->
                    if(dislike){
                        cardView.findViewById<Button>(R.id.dislike_button).setBackground(buttonPressedDrawable)
                    }
                }



            }
        }

    }

    fun backToChampionSelect(){
        if(this.isVisible()){
            activity?.supportFragmentManager?.popBackStackImmediate()
        }
    }


}