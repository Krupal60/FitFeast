package com.fit.feast.data.workouts

import android.os.Parcel
import android.os.Parcelable


data class Exercises(
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val id: String,
    val instructions: ArrayList<String>?,
    val name: String,
    val secondaryMuscles: ArrayList<String>?,
    val target: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList(),
        parcel.readString().toString(),
        parcel.createStringArrayList(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bodyPart)
        parcel.writeString(equipment)
        parcel.writeString(gifUrl)
        parcel.writeString(id)
        parcel.writeStringList(instructions)
        parcel.writeString(name)
        parcel.writeStringList(secondaryMuscles)
        parcel.writeString(target)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercises> {
        override fun createFromParcel(parcel: Parcel): Exercises {
            return Exercises(parcel)
        }

        override fun newArray(size: Int): Array<Exercises?> {
            return arrayOfNulls(size)
        }
    }
}
