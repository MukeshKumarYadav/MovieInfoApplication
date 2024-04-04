package com.mukesh.movieinfoapplication.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieDbResponse : Parcelable {
    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("total_results")
    @Expose
    var totalMovies: Int? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("results")
    @Expose
    var movies: List<Movie>? = null

    constructor(`in`: Parcel) {
        page = `in`.readValue(Int::class.java.classLoader) as Int?
        totalMovies = `in`.readValue(Int::class.java.classLoader) as Int?
        totalPages = `in`.readValue(Int::class.java.classLoader) as Int?
        `in`.readList(
            movies!!,
            Movie::class.java.classLoader
        )
    }

    constructor() {}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(page)
        dest.writeValue(totalMovies)
        dest.writeValue(totalPages)
        dest.writeList(movies)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Creator<MovieDbResponse?> = object : Creator<MovieDbResponse?> {
            override fun createFromParcel(`in`: Parcel): MovieDbResponse? {
                return MovieDbResponse(`in`)
            }

            override fun newArray(size: Int): Array<MovieDbResponse?> {
                return arrayOfNulls(size)
            }
        }
    }
}