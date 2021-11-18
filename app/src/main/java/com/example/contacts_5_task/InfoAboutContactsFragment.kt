package com.example.contacts_5_task

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.contacts_5_task.databinding.FragmentInfoAboutContactsBinding
import com.example.contacts_5_task.model.ContactModel


class InfoAboutContactsFragment : Fragment() {

    private lateinit var binding: FragmentInfoAboutContactsBinding

    private val contact:ContactModel
        get()  = requireArguments().getSerializable(ARG_CONTACT) as ContactModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoAboutContactsBinding.inflate(inflater,container,false)
        binding.firstName.text = "First name: ${contact.firstName}"
        binding.lastName.text = "First name: ${contact.lastName}"
        binding.number.text = "Phone number: ${contact.phone}"

        return binding.root
    }


    companion object{
        private const val ARG_CONTACT = "ARG_CONTACT"

        fun newInstance(contact:ContactModel): InfoAboutContactsFragment {
            val fragment = InfoAboutContactsFragment()
            fragment.arguments = bundleOf(ARG_CONTACT to contact)
                return fragment
        }
    }
}