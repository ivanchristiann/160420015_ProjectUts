package id.ac.ubaya.informatika160420015_projectuts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import id.ac.ubaya.informatika160420015_projectuts.R
import id.ac.ubaya.informatika160420015_projectuts.model.Kost
import id.ac.ubaya.informatika160420015_projectuts.util.loadImage

class RatingKostAdapter(val kostRatingList:ArrayList<Kost>) : RecyclerView.Adapter<RatingKostAdapter.ratingKostViewHolder>() {
        class ratingKostViewHolder(var view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ratingKostViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.kost_list_rating, parent, false)
                return ratingKostViewHolder((view))
        }

        override fun onBindViewHolder(holder: ratingKostViewHolder, position: Int) {
                val kostRatingPosition = kostRatingList[position]
                holder.view.findViewById<TextView>(R.id.textNamaKostRating).text = kostRatingPosition.namaKost
                holder.view.findViewById<TextView>(R.id.textAlamatKostRating).text = kostRatingPosition.alamatKost
                holder.view.findViewById<TextView>(R.id.textHargaKostRating).text = "Rp. " + kostRatingPosition.harga.toString()
                holder.view.findViewById<TextView>(R.id.textJumlahRating).text = kostRatingPosition.rating.toString()

                var imageView = holder.view.findViewById<ImageView>(R.id.imageKostRaitng)
                var progressBar = holder.view.findViewById<ProgressBar>(R.id.progressBarLoadImageRating)
                imageView.loadImage(kostRatingPosition.urlPhoto, progressBar)

                var imageViewStar = holder.view.findViewById<ImageView>(R.id.imageViewStarRating)
                imageViewStar.setOnClickListener {
                        val action = RatingFragmentDirections.actionItemRatingToDetailRatingFragment(kostRatingPosition.id.toString())
                        Navigation.findNavController(it).navigate(action)
                }
        }

        override fun getItemCount(): Int {
                return  kostRatingList.size
        }

        fun updateKostList(newKostList: ArrayList<Kost>) {
                kostRatingList.clear()
                kostRatingList.addAll(newKostList)
                notifyDataSetChanged()
        }

}