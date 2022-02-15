package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.PlatRepository.Singleton.platList
import com.example.myapplication.R
import com.example.myapplication.adapter.PlatAdapter
import com.example.myapplication.adapter.PlatItemDecoration

class CollectionFragment(
    private val context: MainActivity
) : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    //    return super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater?.inflate(R.layout.fragment_collection, container, false)

        // recuperer recyclerview
        val collectionRecycleView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecycleView.adapter = PlatAdapter(context,platList.filter{it.liked}, R.layout.item_vertical_plat )
        collectionRecycleView.layoutManager = LinearLayoutManager(context)
        collectionRecycleView.addItemDecoration(PlatItemDecoration())
        return view
    }

}