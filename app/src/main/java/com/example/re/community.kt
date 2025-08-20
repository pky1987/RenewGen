package com.example.re

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.re.databinding.FragmentCommunityBinding

class community : Fragment() {

    private lateinit var binding: FragmentCommunityBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter
    private lateinit var db: DB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)

        db = DB(requireContext())

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = PostAdapter(emptyList())
        recyclerView.adapter = adapter

        setupButtons()

        return binding.root
    }

    private fun setupButtons() {
        binding.fab.setOnClickListener {
            val intent = Intent(requireContext(), New_Post::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val updatedPosts = db.allPosts

        adapter.updatePosts(updatedPosts)
    }
}
