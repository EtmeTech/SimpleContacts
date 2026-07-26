package tech.etme.contacts.model

import java.util.UUID

data class Contact(
    val id: String = UUID.randomUUID().toString(),
    // links app-contact to a row in system contacts provider
    val systemContactId: Long? = null, //null = !synconiced yet
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val street: String = "",
    val houseNumber: String = "",
    val postalCode: String = "",
    val city: String = "",
    val region: String = "",
    val country: String = "",
    val phone: String = "",
    val favorite: Boolean = false,
    // extra things
    val middleName: String = "",
    val nickname: String = "",
    val company: String = "",
    val birthday: String = "",
    val phone2: String = ""
) {
    val fullName: String
        get() = listOf(firstName, middleName, lastName)
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .ifBlank { nickname }

    val initialLetter: String
        get() = (fullName.firstOrNull()?.uppercaseChar() ?: '#').toString()
}
