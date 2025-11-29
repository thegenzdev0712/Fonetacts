package com.thegenzdev.contactsprototype.mapper

import com.thegenzdev.contactsprototype.data.ContactItem
import com.thegenzdev.contactsprototype.data.ContactEntity

fun ContactEntity.toContactItem(): ContactItem {
    return ContactItem(
        id = id,
        name = name,
        number = number,
        email = email,
        favourite = isFavourite
    )
}

fun ContactItem.toEntity(): ContactEntity {
    return ContactEntity(
        id = id,
        name = name,
        number = number,
        email = email,
        isFavourite = favourite,
    )
}
