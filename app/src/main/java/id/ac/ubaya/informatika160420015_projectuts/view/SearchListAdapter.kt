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

class SearchListAdapter(val searchList:ArrayList<Kost>) : RecyclerView.Adapter<SearchListAdapter.searchViewHolder>() {
        class searchViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): searchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.kost_list_item, parent, false)
        return searchViewHolder(view)
    }

    override fun onBindViewHolder(holder: searchViewHolder, position: Int) {
        val searchPosition = searchList[position]
        holder.view.findViewById<Button>(R.id.btnTipeGenderKost).text = searchPosition.jenisKost
        holder.view.findViewById<Button>(R.id.btnTagLineKost).text = searchPosition.tagLineKost

        val countFasilitas = searchPosition.fasilitasKost?.count()
        var fasilitas = ""
        if (countFasilitas != null) {
            for (i in 0..(countFasilitas.toInt()-1)) {
                fasilitas = if(fasilitas == "") (fasilitas + "Fasilitas : " + searchPosition.fasilitasKost[i]) else (fasilitas + ", " + searchPosition.fasilitasKost[i])
            }
        }

        holder.view.findViewById<TextView>(R.id.textFasilitasKost).text = fasilitas
        holder.view.findViewById<TextView>(R.id.textAlamatKost).text = searchPosition.alamatKost
        holder.view.findViewById<TextView>(R.id.textKostTitle).text = searchPosition.namaKost
        holder.view.findViewById<TextView>(R.id.textHargaSewa).text = "Rp. " + searchPosition.harga.toString()

        var imageView = holder.view.findViewById<ImageView>(R.id.imageKost)
        var progressBar = holder.view.findViewById<ProgressBar>(R.id.progressBarLoadImage)
        imageView.loadImage(searchPosition.urlPhoto, progressBar)

        holder.view.findViewById<TextView>(R.id.btnDetail).setOnClickListener{
            val action = SearchFragmentDirections.actionItemSearchToDetailKostFragment(searchPosition.id.toString())
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return  searchList.size
    }

    fun updateKostList(newKostList: ArrayList<Kost>) {
        searchList.clear()
        searchList.addAll(newKostList)
        notifyDataSetChanged()
    }
}