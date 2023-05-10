package id.ac.ubaya.informatika160420015_projectuts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ubaya.informatika160420015_projectuts.R
import id.ac.ubaya.informatika160420015_projectuts.model.Rating
import id.ac.ubaya.informatika160420015_projectuts.util.loadImage

class DetailRatingAdapter(val ratingList:ArrayList<Rating>) : RecyclerView.Adapter<DetailRatingAdapter.ratingViewHolder>() {
    class ratingViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ratingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rating_list_kost, parent, false)
        return DetailRatingAdapter.ratingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ratingViewHolder, position: Int) {
        val ratingPosition = ratingList[position]
        var imageViewReview = holder.view.findViewById<ImageView>(R.id.imageProfileReview)
        var progressBar = holder.view.findViewById<ProgressBar>(R.id.progressBarLoadImageUserReview)
        imageViewReview.loadImage(ratingPosition.imageUser, progressBar)

        holder.view.findViewById<TextView>(R.id.tanggalReview).text = ratingPosition.tanggalRating
        holder.view.findViewById<TextView>(R.id.namaUserReview).text = ratingPosition.namaUser
        holder.view.findViewById<TextView>(R.id.review).text = ratingPosition.review
        holder.view.findViewById<TextView>(R.id.rating).text = ratingPosition.rating.toString()
    }

    override fun getItemCount(): Int {
        return  ratingList.size
    }

    fun updateDetailRatingKostList(newRatingList: ArrayList<Rating>) {
        ratingList.clear()
        ratingList.addAll(newRatingList)
        notifyDataSetChanged()
    }

}