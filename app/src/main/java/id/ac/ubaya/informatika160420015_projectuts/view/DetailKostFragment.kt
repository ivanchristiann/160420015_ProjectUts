package id.ac.ubaya.informatika160420015_projectuts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.ubaya.informatika160420015_projectuts.R
import id.ac.ubaya.informatika160420015_projectuts.util.loadImage
import id.ac.ubaya.informatika160420015_projectuts.viewmodel.DetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [DetailKostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailKostFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_kost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(DetailKostFragmentArgs.fromBundle(requireArguments()).idKost)
        viewModel.kostLoadData.observe(viewLifecycleOwner, Observer {
            val progressBarDetail =
                view?.findViewById<ProgressBar>(R.id.progressBarLoadImageDetailKost)
            val photoImage = view?.findViewById<ImageView>(R.id.imageViewDetailKost)
            val txtNama = view?.findViewById<TextView>(R.id.txtDetailNamaKost)
            val txtAlamat = view?.findViewById<TextView>(R.id.txtDetailAlamatKost)
            val txtHarga = view?.findViewById<TextView>(R.id.txtDetailHargaKost)
            val jenisKost = view?.findViewById<TextView>(R.id.txtDetailJenisKost)
            val txtFasilitas = view?.findViewById<TextView>(R.id.txtDetailFasilitasKost)

            if (progressBarDetail != null) {
                photoImage?.loadImage(viewModel.kostLoadData.value?.urlPhoto, progressBarDetail)
            }

            jenisKost?.setText("Kost ini untuk : " + viewModel.kostLoadData.value?.jenisKost)
            txtNama?.setText(viewModel.kostLoadData.value?.namaKost)
            txtAlamat?.setText(viewModel.kostLoadData.value?.alamatKost)
            txtHarga?.setText(viewModel.kostLoadData.value?.harga.toString())
            val countFasilitas = viewModel.kostLoadData.value?.fasilitasKost?.count()
            var fasilitas = ""
            if (countFasilitas != null) {
                for (i in 0..(countFasilitas.toInt() - 1)) {
                    fasilitas =
                        if (fasilitas == "") (fasilitas + "Fasilitas : " + viewModel.kostLoadData.value?.fasilitasKost!![i]) else (fasilitas + ", " + viewModel.kostLoadData.value?.fasilitasKost!![i])
                }
            }
            txtFasilitas?.setText(fasilitas)
        })
    }
}