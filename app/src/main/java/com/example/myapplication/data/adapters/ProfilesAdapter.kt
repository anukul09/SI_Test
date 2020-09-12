package com.example.myapplication.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.roomdb.Team1
import com.example.myapplication.data.roomdb.Team2

class ProfilesAdapter(var cProfileList: ArrayList<Team1>?, var cProfileList1: ArrayList<Team2>?) : RecyclerView.Adapter<ProfilesAdapter.Viewholder>(){

    override fun getItemCount(): Int {

        if(!cProfileList.isNullOrEmpty()){
            return cProfileList!!.size
        }else{
            return cProfileList1!!.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.res_team, parent, false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        if(!cProfileList.isNullOrEmpty()){

            val playerName = StringBuilder(cProfileList!![position].PlaynerName)
            if (cProfileList!![position].isCaption){
                playerName.append(" [C] ")
            }
            if (cProfileList!![position].isKeepner){
                playerName.append(" [WK] ")
            }
            holder.tvPlayName.text = playerName
        }else{
            val playerName = StringBuilder(cProfileList1!![position].PlaynerName)
            if (cProfileList1!![position].isCaption){
                playerName.append(" [C] ")
            }
            if (cProfileList1!![position].isKeepner){
                playerName.append(" [WK] ")
            }

            holder.tvPlayName.text = playerName
        }
    }

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvPlayName = itemView.findViewById<TextView>(R.id.tvPlayerName)
    }

}