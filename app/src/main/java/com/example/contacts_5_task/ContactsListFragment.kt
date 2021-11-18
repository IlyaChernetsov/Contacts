package com.example.contacts_5_task

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract.*
import android.provider.ContactsContract.CommonDataKinds.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import com.example.contacts_5_task.databinding.FragmentContactsListBinding
import com.example.contacts_5_task.model.ContactModel


class ContactsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle:Bundle
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                Array(1) { android.Manifest.permission.READ_CONTACTS },
                111
            )
        } else {
            readContacts()
            val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,
                sortedContactList)
            binding.listContactsFragment.adapter = adapter
            binding.listContactsFragment.setOnItemClickListener { _, _, position, _ ->
                val currentItem = adapter.getItem(position)
                if (currentItem != null) {
                    contract().launchInfoContacts(currentItem)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun readContacts(){
        val rs = requireActivity().contentResolver.query(
            Data.CONTENT_URI,
            null,
            "${Data.MIMETYPE} IN (${Phone.MIMETYPE}, ${StructuredName.MIMETYPE})",
            null,
            null
        ).use { contacts ->
            if (contacts != null) {
                while (contacts.moveToNext()) {
                    val rawContactId =
                        contacts.getLong(contacts.getColumnIndexOrThrow(Data.RAW_CONTACT_ID))
                    when (contacts.getString(contacts.getColumnIndexOrThrow(Data.MIMETYPE))) {
                        Phone.CONTENT_ITEM_TYPE -> {
                            val phone = contacts.getString(
                                contacts.getColumnIndexOrThrow(Phone.NUMBER)
                            )
                            contactMap[rawContactId] =
                                contactMap[rawContactId]?.copy(
                                    phone = phone
                                ) ?: ContactModel(
                                    rawContactId.toString(),
                                    phone = phone
                                )
                        }
                        StructuredName.CONTENT_ITEM_TYPE -> {
                            val firstName = contacts.getString(
                                contacts.getColumnIndexOrThrow(StructuredName.GIVEN_NAME)
                            )
                            val lastName = contacts.getString(
                                contacts.getColumnIndexOrThrow(StructuredName.FAMILY_NAME)
                            )
                            contactMap[rawContactId] =
                                contactMap[rawContactId]?.copy(
                                    firstName = firstName,
                                    lastName = lastName
                                ) ?: ContactModel(
                                    rawContactId.toString(),
                                    firstName = firstName,
                                    lastName = lastName

                                )
                        }
                    }
                }
            }
        }
    }

    companion object {
        val sortedContactList by lazy { contactMap.map { it.value }.sortedBy { it.fullName } }
        val contactMap = hashMapOf<Long, ContactModel>()
        lateinit var binding: FragmentContactsListBinding
        @JvmStatic
        fun newInstance() =
            ContactsListFragment().apply {
            }
    }
}