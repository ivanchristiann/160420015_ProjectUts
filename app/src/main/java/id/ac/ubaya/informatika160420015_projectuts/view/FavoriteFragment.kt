package id.ac.ubaya.informatika160420015_projectuts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.ac.ubaya.informatika160420015_projectuts.R
import id.ac.ubaya.informatika160420015_projectuts.viewmodel.ListFavoriteViewModel
import id.ac.ubaya.informatika160420015_projectuts.viewmodel.ListKostViewModel

class FavoriteFragment : Fragment() {
    private lateinit var viewModel: ListFavoriteViewModel
    private val favoriteListAdapter = FavoriteListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListFavoriteViewModel::class.java)
        viewModel.refresh()

        val recyclerViewList = view.findViewById<RecyclerView>(R.id.recViewFavorite);

        recyclerViewList.layoutManager = LinearLayoutManager(context)
        recyclerViewList.adapter = favoriteListAdapter
        observeViewModel()

        view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutFavorite).setOnRefreshListener {
            recyclerViewList.visibility = View.GONE
            view.findViewById<TextView>(R.id.txtErrorLoadFavorite).visibility = View.GONE
            view.findViewById<ProgressBar>(R.id.progressLoadFavorite).visibility = View.VISIBLE

            viewModel.refresh()
            view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutFavorite).isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.favoriteLiveData.observe(viewLifecycleOwner, Observer {
            favoriteListAdapter.updateKostList(it)
        })
        viewModel.favoriteLoadErrorLD.observe(viewLifecycleOwner, Observer {
            val errorTxt = view?.findViewById<TextView>(R.id.txtErrorLoadFavorite);

            if(it == true) {
                errorTxt?.visibility = View.VISIBLE
            } else {
                errorTxt?.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            val recyclerViewList = view?.findViewById<RecyclerView>(R.id.recViewFavorite);
            val progressBar = view?.findViewById<ProgressBar>(R.id.progressLoadFavorite);

            if(it == true) {
                recyclerViewList?.visibility = View.GONE
                progressBar?.visibility = View.VISIBLE
            } else {
                recyclerViewList?.visibility = View.VISIBLE
                progressBar?.visibility = View.GONE
            }
        })
    }
}