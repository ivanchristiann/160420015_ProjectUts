package id.ac.ubaya.informatika160420015_projectuts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.ubaya.informatika160420015_projectuts.R
import id.ac.ubaya.informatika160420015_projectuts.util.loadImage
import id.ac.ubaya.informatika160420015_projectuts.viewmodel.DetailUserProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var viewModel: DetailUserProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel = ViewModelProvider(this).get(DetailUserProfileViewModel::class.java)
        viewModel.fetch()
        viewModel.userLoadData.observe(viewLifecycleOwner, Observer {
            val txtNama = view?.findViewById<EditText>(R.id.inputNama)
            val txtNomorTelpon = view?.findViewById<EditText>(R.id.inputNomorTelepon)
            val txtAlamat = view?.findViewById<EditText>(R.id.inputAlamat)
            val txtDomisili = view?.findViewById<EditText>(R.id.inputDomisili)
            val photoImageUser = view?.findViewById<ImageView>(R.id.imageProfilePhoto)

            val progressBarImage = view?.findViewById<ProgressBar>(R.id.progressBarLoadUserImage)
            if (progressBarImage != null) {
                photoImageUser?.loadImage(viewModel.userLoadData.value?.urlPhoto, progressBarImage)
            }

            txtNama?.setText(viewModel.userLoadData.value?.nama)
            txtNomorTelpon?.setText(viewModel.userLoadData.value?.nomorTelepon)
            txtAlamat?.setText(viewModel.userLoadData.value?.alamat)
            txtDomisili?.setText(viewModel.userLoadData.value?.domisili)
        })
    }
}