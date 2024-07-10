package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val address: String,
    val telNumber: String,
    val status: String // Menambahkan status
) : Parcelable {
    init {
        require(status == "user" || status == "admin") { "Status must be either 'user' or 'admin'" }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "" // Membaca status dari Parcel
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(address)
        parcel.writeString(telNumber)
        parcel.writeString(status) // Menulis status ke Parcel
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
