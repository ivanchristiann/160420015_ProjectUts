package id.ac.ubaya.informatika160420015_projectuts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.ac.ubaya.informatika160420015_projectuts.R
import id.ac.ubaya.informatika160420015_projectuts.viewmodel.ListSearchViewModel

class SearchFragment : Fragment() {
    private lateinit var viewModel: ListSearchViewModel
    private val searchListAdapter = SearchListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showKost("All")

        view.findViewById<Button>(R.id.btnSearchPria).setOnClickListener{
            showKost("Putra")
        }
        view.findViewById<Button>(R.id.btnSearchWanita).setOnClickListener{
            showKost("Putri")
        }
        view.findViewById<Button>(R.id.btnSearcCampur).setOnClickListener{
            showKost("Putra/Putri")
        }
    }

    private fun showKost(jenis :String){
        viewModel = ViewModelProvider(this).get(ListSearchViewModel::class.java)
        viewModel.refresh(jenis)

        val recyclerViewList = view?.findViewById<RecyclerView>(R.id.recViewSearch);

        recyclerViewList?.layoutManager = LinearLayoutManager(context)
        recyclerViewList?.adapter = searchListAdapter
        observeViewModel()

        view?.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutSearch)?.setOnRefreshListener {
            recyclerViewList?.visibility = View.GONE
            view?.findViewById<TextView>(R.id.txtErrorLoadSearch)?.visibility = View.GONE
            view?.findViewById<ProgressBar>(R.id.progressLoadSearch)?.visibility = View.VISIBLE

            viewModel.refresh(jenis)
            view?.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutSearch)?.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.kostSearchLiveData.observe(viewLifecycleOwner, Observer {
            searchListAdapter.updateKostList(it)
        })
        viewModel.kostSearchLoadErrorLD.observe(viewLifecycleOwner, Observer {
            val errorTxt = view?.findViewById<TextView>(R.id.txtErrorLoadSearch);
            if(it == true) {
                errorTxt?.visibility = View.VISIBLE
            } else {
                errorTxt?.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            val recyclerViewList = view?.findViewById<RecyclerView>(R.id.recViewSearch);
            val progressBar = view?.findViewById<ProgressBar>(R.id.progressLoadSearch);
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
