package com.example.contacts_5_task

import androidx.fragment.app.Fragment
import com.example.contacts_5_task.model.ContactModel

fun Fragment.contract(): AppContract = requireActivity() as AppContract

interface AppContract{
    fun launchInfoContacts(contact: ContactModel);
}