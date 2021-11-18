package com.example.contacts_5_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contacts_5_task.databinding.ActivityMainBinding
import com.example.contacts_5_task.model.ContactModel

class MainActivity : AppCompatActivity(),AppContract {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contactList = ContactsListFragment.newInstance()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, contactList)
                .commit()
        }
    }

    override fun launchInfoContacts(contact: ContactModel) {
        val contactsInfoFragment = InfoAboutContactsFragment.newInstance(contact)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, contactsInfoFragment)
            .addToBackStack(null)
            .commit()
    }

}