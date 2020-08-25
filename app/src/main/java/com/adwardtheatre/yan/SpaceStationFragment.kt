package com.adwardtheatre.yan

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_space_station.view.*


class SpaceStationFragment(private val mAstrosList: MutableList<Person>, private val context: Context) : Fragment(), RecyclerView.Adapter<SpaceStationFragment.MyViewHolder>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_space_station, container, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_space_station, parent, false)
        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int = mAstrosList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //which called each time rows refreshed with the data object
        val mPeople = mAstrosList[position]
        holder.itemView.astroTv.text = mPeople.name
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}