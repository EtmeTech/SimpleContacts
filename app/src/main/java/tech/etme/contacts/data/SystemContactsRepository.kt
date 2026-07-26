package tech.etme.contacts.data

import android.content.ContentProviderOperation
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import tech.etme.contacts.model.Contact

// access the phone contacs direct using permissions
class SystemContactsRepository(private val context: Context) {

    fun loadAllContacts(): List<Contact> {
        val resolver = context.contentResolver
        val result = mutableListOf<Contact>()

        val cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.STARRED
            ),
            null, null,
            "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} ASC"
        ) ?: return emptyList()

        cursor.use {
            val idIndex = it.getColumnIndexOrThrow(ContactsContract.Contacts._ID)
            val starredIndex = it.getColumnIndexOrThrow(ContactsContract.Contacts.STARRED)
            while (it.moveToNext()) {
                val contactId = it.getLong(idIndex)
                val starred = it.getInt(starredIndex) == 1
                val contact = loadContactDetails(contactId, starred) ?: continue
                result.add(contact)
            }
        }
        return result
    }

    private fun loadContactDetails(contactId: Long, starred: Boolean): Contact? {
        val resolver = context.contentResolver
        var firstName = ""
        var lastName = ""
        var phone = ""
        var phone2 = ""
        var email = ""
        var street = ""
        var houseNumber = ""
        var postalCode = ""
        var city = ""
        var region = ""
        var country = ""
        var company = ""
        var birthday = ""
        var nickname = ""

        val dataCursor = resolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            "${ContactsContract.Data.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )

        dataCursor?.use { c ->
            val mimeIndex = c.getColumnIndexOrThrow(ContactsContract.Data.MIMETYPE)
            while (c.moveToNext()) {
                when (c.getString(mimeIndex)) {
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE -> {
                        firstName = c.getStringOrEmpty(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)
                        lastName = c.getStringOrEmpty(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)
                    }
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                        val number = c.getStringOrEmpty(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        if (phone.isBlank()) phone = number else if (phone2.isBlank()) phone2 = number
                    }
                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE -> {
                        if (email.isBlank()) email = c.getStringOrEmpty(ContactsContract.CommonDataKinds.Email.ADDRESS)
                    }
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE -> {
                        street = c.getStringOrEmpty(ContactsContract.CommonDataKinds.StructuredPostal.STREET)
                        postalCode = c.getStringOrEmpty(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)
                        city = c.getStringOrEmpty(ContactsContract.CommonDataKinds.StructuredPostal.CITY)
                        region = c.getStringOrEmpty(ContactsContract.CommonDataKinds.StructuredPostal.REGION)
                        country = c.getStringOrEmpty(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)
                    }
                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE -> {
                        company = c.getStringOrEmpty(ContactsContract.CommonDataKinds.Organization.COMPANY)
                    }
                    ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE -> {
                        nickname = c.getStringOrEmpty(ContactsContract.CommonDataKinds.Nickname.NAME)
                    }
                    ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE -> {
                        val type = c.getInt(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Event.TYPE))
                        if (type == ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY) {
                            birthday = c.getStringOrEmpty(ContactsContract.CommonDataKinds.Event.START_DATE)
                        }
                    }
                }
            }
        }

        if (firstName.isBlank() && lastName.isBlank() && nickname.isBlank()) return null

        return Contact(
            id = "sys_$contactId",
            systemContactId = contactId,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            phone2 = phone2,
            email = email,
            street = street,
            houseNumber = houseNumber,
            postalCode = postalCode,
            city = city,
            region = region,
            country = country,
            company = company,
            birthday = birthday,
            nickname = nickname,
            favorite = starred
        )
    }

    //inserts the new contacts into the system contacts
    fun insertContact(contact: Contact): Long? {
        val ops = ArrayList<ContentProviderOperation>()
        ops.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )
        addDataOps(ops, 0, contact)

        val results = try {
            context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
        } catch (e: Exception) {
            return null
        }
        val rawContactUri = results.firstOrNull()?.uri ?: return null
        val rawContactId = ContentUris.parseId(rawContactUri)
        setStarred(rawContactToContactId(rawContactId), contact.favorite)
        return rawContactToContactId(rawContactId)
    }

    // updates the existing contact in the system contacts
    fun updateContact(contact: Contact) {
        val systemId = contact.systemContactId ?: return
        val rawContactId = getRawContactId(systemId) ?: return

        val ops = ArrayList<ContentProviderOperation>()
        ops.add(
            ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
                .withSelection(
                    "${ContactsContract.Data.RAW_CONTACT_ID} = ?",
                    arrayOf(rawContactId.toString())
                )
                .build()
        )
        addDataOps(ops, 0, contact, rawContactId = rawContactId)

        try {
            context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
        } catch (_: Exception) {
        }
        setStarred(systemId, contact.favorite)
    }

    fun deleteContact(contact: Contact) {
        val systemId = contact.systemContactId ?: return
        val uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, systemId)
        context.contentResolver.delete(uri, null, null)
    }

    fun setStarred(systemContactId: Long, starred: Boolean) {
        val uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, systemContactId)
        val values = ContentValues().apply {
            put(ContactsContract.Contacts.STARRED, if (starred) 1 else 0)
        }
        context.contentResolver.update(uri, values, null, null)
    }

    private fun getRawContactId(systemContactId: Long): Long? {
        val cursor = context.contentResolver.query(
            ContactsContract.RawContacts.CONTENT_URI,
            arrayOf(ContactsContract.RawContacts._ID),
            "${ContactsContract.RawContacts.CONTACT_ID} = ?",
            arrayOf(systemContactId.toString()),
            null
        )
        cursor.use {
            if (it != null && it.moveToFirst()) {
                return it.getLong(0)
            }
        }
        return null
    }

    private fun rawContactToContactId(rawContactId: Long): Long {
        val cursor = context.contentResolver.query(
            ContactsContract.RawContacts.CONTENT_URI,
            arrayOf(ContactsContract.RawContacts.CONTACT_ID),
            "${ContactsContract.RawContacts._ID} = ?",
            arrayOf(rawContactId.toString()),
            null
        )
        cursor.use {
            if (it != null && it.moveToFirst()) {
                return it.getLong(0)
            }
        }
        return rawContactId
    }

    private fun addDataOps(
        ops: ArrayList<ContentProviderOperation>,
        rawContactBackRef: Int,
        contact: Contact,
        rawContactId: Long? = null
    ) {
        fun builder(): ContentProviderOperation.Builder {
            val b = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            return if (rawContactId != null) {
                b.withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            } else {
                b.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactBackRef)
            }
        }

        ops.add(
            builder()
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.firstName)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, contact.middleName)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contact.lastName)
                .build()
        )

        if (contact.phone.isNotBlank()) {
            ops.add(
                builder()
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phone)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build()
            )
        }
        if (contact.phone2.isNotBlank()) {
            ops.add(
                builder()
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phone2)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build()
            )
        }
        if (contact.email.isNotBlank()) {
            ops.add(
                builder()
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, contact.email)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                    .build()
            )
        }
        if (listOf(contact.street, contact.city, contact.postalCode, contact.region, contact.country).any { it.isNotBlank() }) {
            ops.add(
                builder()
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.STREET, listOf(contact.street, contact.houseNumber).filter { it.isNotBlank() }.joinToString(" "))
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE, contact.postalCode)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.CITY, contact.city)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.REGION, contact.region)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY, contact.country)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME)
                    .build()
            )
        }
        if (contact.company.isNotBlank()) {
            ops.add(
                builder()
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, contact.company)
                    .build()
            )
        }
        if (contact.nickname.isNotBlank()) {
            ops.add(
                builder()
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Nickname.NAME, contact.nickname)
                    .build()
            )
        }
        if (contact.birthday.isNotBlank()) {
            ops.add(
                builder()
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Event.START_DATE, contact.birthday)
                    .withValue(ContactsContract.CommonDataKinds.Event.TYPE, ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY)
                    .build()
            )
        }
    }
}

private fun android.database.Cursor.getStringOrEmpty(column: String): String {
    val index = getColumnIndex(column)
    if (index < 0) return ""
    return getString(index) ?: ""
}
