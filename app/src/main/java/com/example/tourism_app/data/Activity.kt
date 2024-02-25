package com.example.tourism_app.data

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// class representing the "Lieu" object in our Firebase database
data class Activity(
    var address: String? = null,
    var category: String? = null,
    var condition_free: String? = null,
    var hours: Hours? = null,
    var name: String? = null,
    var reason: String? = null,
    var transport: Transport? = null,
    var url: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Hours::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Transport::class.java.classLoader),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(category)
        parcel.writeString(condition_free)
        parcel.writeParcelable(hours, flags)
        parcel.writeString(name)
        parcel.writeString(reason)
        parcel.writeParcelable(transport, flags)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Activity> {
        override fun createFromParcel(parcel: Parcel): Activity {
            return Activity(parcel)
        }

        override fun newArray(size: Int): Array<Activity?> {
            return arrayOfNulls(size)
        }
    }

    fun getTodaySchedule(): String? {

        return when (getCurrentDay()) {
            "Monday" -> hours?.monday
            "Tuesday" -> hours?.tuesday
            "Wednesday" -> hours?.wednesday
            "Thursday" -> hours?.thursday
            "Friday" -> hours?.friday
            "Saturday" -> hours?.saturday
            "Sunday" -> hours?.sunday
            else -> null
        }
    }

    private fun getCurrentDay(): String {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return simpleDateFormat.format(calendar.time)
    }

    fun getArrondissement(): String? {
        val pattern = Regex("\\b750(\\d{2})\\b")
        val matchResult = pattern.find(address.toString())

        val arrondissementNumber = matchResult?.groupValues?.get(1)?.toIntOrNull()

        return when {
            arrondissementNumber != null && arrondissementNumber in 1..20 -> {
                "$arrondissementNumber${getOrdinalSuffix(arrondissementNumber)}"
            }
            else -> null
        }
    }

    private fun getOrdinalSuffix(number: Int): String {
        return when {
            number in 11..13 -> "th"
            number % 10 == 1 -> "st"
            number % 10 == 2 -> "nd"
            number % 10 == 3 -> "rd"
            else -> "th"
        }
    }
}

data class Hours(
    var friday: String? = null,
    var monday: String? = null,
    var saturday: String? = null,
    var sunday: String? = null,
    var thursday: String? = null,
    var tuesday: String? = null,
    var wednesday: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(friday)
        parcel.writeString(monday)
        parcel.writeString(saturday)
        parcel.writeString(sunday)
        parcel.writeString(thursday)
        parcel.writeString(tuesday)
        parcel.writeString(wednesday)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Hours> {
        override fun createFromParcel(parcel: Parcel): Hours {
            return Hours(parcel)
        }

        override fun newArray(size: Int): Array<Hours?> {
            return arrayOfNulls(size)
        }
    }
}

data class Transport(
    var bus: String? = null,
    var metro: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bus)
        parcel.writeString(metro)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transport> {
        override fun createFromParcel(parcel: Parcel): Transport {
            return Transport(parcel)
        }

        override fun newArray(size: Int): Array<Transport?> {
            return arrayOfNulls(size)
        }
    }
}