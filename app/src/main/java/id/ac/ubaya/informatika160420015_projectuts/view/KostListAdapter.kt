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

class KostListAdapter(val kostList:ArrayList<Kost>) : RecyclerView.Adapter<KostListAdapter.KostViewHolder>() {
        class KostViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.kost_list_item, parent, false)
        return KostViewHolder(view)
    }

    override fun onBindViewHolder(holder: KostViewHolder, position: Int) {
        val kostPosition = kostList[position]
        holder.view.findViewById<Button>(R.id.btnTipeGenderKost).text = kostPosition.jenisKost
        holder.view.findViewById<Button>(R.id.btnTagLineKost).text = kostPosition.tagLineKost

        val countFasilitas = kostPosition.fasilitasKost?.count()
        var fasilitas = ""
        if (countFasilitas != null) {
            for (i in 0..(countFasilitas.toInt()-1)) {
                fasilitas = if(fasilitas == "") (fasilitas + "Fasilitas : " + kostPosition.fasilitasKost[i]) else (fasilitas + ", " + kostPosition.fasilitasKost[i])
            }
        }

        holder.view.findViewById<TextView>(R.id.textFasilitasKost).text = fasilitas
        holder.view.findViewById<TextView>(R.id.textAlamatKost).text = kostPosition.alamatKost
        holder.view.findViewById<TextView>(R.id.textKostTitle).text = kostPosition.namaKost
        holder.view.findViewById<TextView>(R.id.textHargaSewa).text = "Rp. " + kostPosition.harga.toString()

        var imageView = holder.view.findViewById<ImageView>(R.id.imageKost)
        var progressBar = holder.view.findViewById<ProgressBar>(R.id.progressBarLoadImage)
        imageView.loadImage(kostPosition.urlPhoto, progressBar)

        holder.view.findViewById<TextView>(R.id.btnDetail).setOnClickListener{
            val action = HomeFragmentDirections.actionDetailKost(kostList[position].id.toString())
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return  kostList.size
    }

    fun updateKostList(newKostList: ArrayList<Kost>) {
        kostList.clear()
        kostList.addAll(newKostList)
        notifyDataSetChanged()
    }
}