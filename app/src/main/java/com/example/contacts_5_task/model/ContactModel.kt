package com.example.contacts_5_task.model

import java.io.Serializable

data class ContactModel(
    val id: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val phone: String? = null
) : Serializable{
    val fullName: String
        get() = "${firstName ?: ""} ${lastName ?: ""}".trim()

    override fun toString(): String {
        return "$firstName $lastName\n$phone"
    }
}