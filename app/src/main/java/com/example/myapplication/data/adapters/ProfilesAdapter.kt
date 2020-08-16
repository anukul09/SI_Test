package com.example.myapplication.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MvvmApplication
import com.example.myapplication.R
import com.example.myapplication.data.roomdb.AppDatabase
import com.example.myapplication.data.roomdb.ProfileEntity
import com.example.myapplication.util.Coroutins
import com.example.myapplication.util.loadImage

class ProfilesAdapter(var cProfileList: ArrayList<ProfileEntity>?) : RecyclerView.Adapter<ProfilesAdapter.Viewholder>(){

    override fun getItemCount(): Int {
        return cProfileList!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.res_single_profile, parent, false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

            holder.profileName.text = cProfileList!![position].profileName
            holder.profileAge.text = cProfileList!![position].profileAge.toString()
            holder.profileGender.text = cProfileList!![position].profileGender
            holder.profileLocationCity.text = cProfileList!![position].profileLocationCity
            holder.profileLocationState.text = cProfileList!![position].profileLocationState
            holder.profileImage.loadImage(cProfileList!![position].profileImageUrl)

            if(!cProfileList!![position].isDeclined && !cProfileList!![position].isAccepted){
                holder.llCheck.visibility = View.VISIBLE
                holder.tvProfileSelection.visibility = View.GONE
            }

            if (cProfileList!![position].isDeclined){
                holder.llCheck.visibility = View.GONE
                holder.tvProfileSelection.visibility = View.VISIBLE
                holder.tvProfileSelection.text = "Member Declined"
            }

            if (cProfileList!![position].isAccepted){
                holder.llCheck.visibility = View.GONE
                holder.tvProfileSelection.visibility = View.VISIBLE
                holder.tvProfileSelection.text = "Member Accepted"
            }


        holder.profileSelected.setOnClickListener {

            val profile = ProfileEntity(
                cProfileList!!.get(position).id,
                cProfileList!!.get(position).profileName,
                cProfileList!![position].profileAge,
                cProfileList!![position].profileGender,
                cProfileList!![position].profileLocationCity,
                cProfileList!![position].profileLocationState,
                cProfileList!!.get(position).profileImageUrl,
                false,
                true
            )

            Coroutins.io {
                AppDatabase(MvvmApplication.context).getProfileDao().updateProfile(profile)
            }

            cProfileList!!.set(position,profile)
            notifyDataSetChanged()

        }

        holder.profileDeclined.setOnClickListener {

            val profile = ProfileEntity(
                cProfileList!!.get(position).id,
                cProfileList!!.get(position).profileName,
                cProfileList!![position].profileAge,
                cProfileList!![position].profileGender,
                cProfileList!![position].profileLocationCity,
                cProfileList!![position].profileLocationState,
                cProfileList!!.get(position).profileImageUrl,
                true,
                false
            )

            Coroutins.io {
                AppDatabase(MvvmApplication.context).getProfileDao().updateProfile(profile)
            }

            cProfileList!!.set(position,profile)
            notifyDataSetChanged()
        }

    }

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage = itemView.findViewById<ImageView>(R.id.ivDisplayImg)
        val profileName = itemView.findViewById<TextView>(R.id.tvName)
        val profileGender = itemView.findViewById<TextView>(R.id.tvHeight)
        val profileAge = itemView.findViewById<TextView>(R.id.tvAge)
        val profileLocationCity = itemView.findViewById<TextView>(R.id.tvCast)
        val profileLocationState = itemView.findViewById<TextView>(R.id.tvLanguage)

        val profileSelected = itemView.findViewById<ImageView>(R.id.ivselected)
        val profileDeclined = itemView.findViewById<ImageView>(R.id.ivdeclined)
        val llCheck = itemView.findViewById<LinearLayout>(R.id.llCheck)

        val tvProfileSelection = itemView.findViewById<TextView>(R.id.tvProfileSelection)

    }

    interface SelectProfile{
        fun onprofileselected(position: Int)
    }
}