package ru.kpfu.itis

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kpfu.itis.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() =  _binding!!

    private var adapter: MyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        adapter = MyAdapter(
            onFirstItemClick = ::onFirstItemClick,
            onThirdItemClick = ::onThirdItemClick,

        )
        adapter?.differ?.submitList(Repository.getDataList())
      //  adapter?.setData(Repository.getDataList())
        binding.recyclerView.adapter = adapter
    }

    private fun onFirstItemClick(position: Int){
        (Repository.getDataList()[position] as DataModel.FirstType).url = resources.getStringArray(R.array.url_array)[(0..1).random()]
        adapter?.notifyItemChanged(position)

    }

    private fun onThirdItemClick(position: Int) {
       // Log.d("value", Repository.getDataList().size.toString())
       // Repository.addRandomItem()
        adapter?.differ?.submitList(Repository.getDataList())
        //adapter?.setData(Repository.getDataList())
        adapter?.notifyItemInserted(Repository.addRandomItem())
       // adapter?.notifyDataSetChanged()
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
        @JvmStatic
        fun newInstance(bundle: Bundle) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putAll(bundle)
                }
            }
    }
}