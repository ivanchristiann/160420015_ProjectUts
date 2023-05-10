package id.ac.ubaya.informatika160420015_projectuts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import id.ac.ubaya.informatika160420015_projectuts.R
import id.ac.ubaya.informatika160420015_projectuts.model.Kost
import id.ac.ubaya.informatika160420015_projectuts.util.loadImage

class FavoriteListAdapter(val favoriteList:ArrayList<Kost>) : RecyclerView.Adapter<FavoriteListAdapter.favoriteViewHolder>() {
        class favoriteViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.kost_list_item, parent, false)
        return favoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: favoriteViewHolder, position: Int) {
        val favoritePosition = favoriteList[position]
        holder.view.findViewById<Button>(R.id.btnTipeGenderKost).text = favoritePosition.jenisKost
        holder.view.findViewById<Button>(R.id.btnTagLineKost).text = favoritePosition.tagLineKost

        val countFasilitas = favoritePosition.fasilitasKost?.count()
        var fasilitas = ""
        if (countFasilitas != null) {
            for (i in 0..(countFasilitas.toInt()-1)) {
                fasilitas = if(fasilitas == "") (fasilitas + "Fasilitas : " + favoritePosition.fasilitasKost[i]) else (fasilitas + ", " + favoritePosition.fasilitasKost[i])
            }
        }

        holder.view.findViewById<TextView>(R.id.textFasilitasKost).text = fasilitas
        holder.view.findViewById<TextView>(R.id.textAlamatKost).text = favoritePosition.alamatKost
        holder.view.findViewById<TextView>(R.id.textKostTitle).text = favoritePosition.namaKost
        holder.view.findViewById<TextView>(R.id.textHargaSewa).text = "Rp. " + favoritePosition.harga.toString()

        var imageView = holder.view.findViewById<ImageView>(R.id.imageKost)
        var progressBar = holder.view.findViewById<ProgressBar>(R.id.progressBarLoadImage)
        imageView.loadImage(favoritePosition.urlPhoto, progressBar)

        holder.view.findViewById<TextView>(R.id.btnDetail).setOnClickListener{
            val action = FavoriteFragmentDirections.actionToDetailKost(favoritePosition.id.toString())
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return  favoriteList.size
    }

    fun updateKostList(newKostList: ArrayList<Kost>) {
        favoriteList.clear()
        favoriteList.addAll(newKostList)
        notifyDataSetChanged()
    }
}