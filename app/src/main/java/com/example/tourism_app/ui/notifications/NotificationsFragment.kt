package com.example.tourism_app.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tourism_app.databinding.FragmentNotificationsBinding

class NotificationsFragment(
    private val pseudo: String
) : Fragment() {


    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]


        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        notificationsViewModel.setupViews(binding, this, pseudo)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}