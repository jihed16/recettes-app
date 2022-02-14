package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.PlatRepository.Singleton.platList
import com.example.myapplication.R
import com.example.myapplication.adapter.PlatAdapter
import com.example.myapplication.adapter.PlatItemDecoration

class HomeFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

     //  val platList= arrayListOf<PlatModel>()

     /*   platList.add(
            PlatModel(
                 "couscous",
                "plat tunisien",
                "https://cdn.pixabay.com/photo/2018/05/29/20/47/couscous-3440042_960_720.jpg",
              true
        ))
        platList.add(
            PlatModel(
                "pizza",
                "plat italien",
                "https://cdn.pixabay.com/photo/2017/12/09/08/18/pizza-3007395_960_720.jpg",
                false
            ))
        platList.add(
            PlatModel(
                "couscouss",
                "plat tunisien",
                "https://cdn.pixabay.com/photo/2018/05/29/20/47/couscous-3440042_960_720.jpg",
                true
            ))
        platList.add(
            PlatModel(
                "pizzaa",
                "plat italien",
                "https://cdn.pixabay.com/photo/2017/12/09/08/18/pizza-3007395_960_720.jpg",
                false
            ))
            */

        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_Recycler_view)
       horizontalRecyclerView.adapter = PlatAdapter(context,platList,R.layout.item_horizontal_plat)

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_Recycler_view)
        verticalRecyclerView.adapter = PlatAdapter(context,platList,R.layout.item_vertical_plat)

        verticalRecyclerView.addItemDecoration(PlatItemDecoration())

        return view
    }

}