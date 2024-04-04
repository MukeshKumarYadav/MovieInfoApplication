package com.mukesh.movieinfoapplication.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mukesh.movieinfoapplication.BR
import com.mukesh.movieinfoapplication.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Movie : BaseObservable, Parcelable {
    @SerializedName("popularity")
    @Expose
    private var popularity: Double? = null

    @SerializedName("vote_count")
    @Expose
    private var voteCount: Int? = null

    @SerializedName("video")
    @Expose
    private var video: Boolean? = null

    @SerializedName("poster_path")
    @Expose
    private var posterPath: String? = null

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("adult")
    @Expose
    private var adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
    private var backdropPath: String? = null

    @SerializedName("original_language")
    @Expose
    private var originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    private var originalTitle: String? = null

    @SerializedName("genre_ids")
    @Expose
    private var genreIds: List<Int> = ArrayList()

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("vote_average")
    @Expose
    private var voteAverage: Double? = null

    @SerializedName("overview")
    @Expose
    private var overview: String? = null

    @SerializedName("release_date")
    @Expose
    private var releaseDate: String? = null

    constructor(`in`: Parcel) {
        popularity = `in`.readValue(Double::class.java.classLoader) as Double?
        voteCount = `in`.readValue(Int::class.java.classLoader) as Int?
        video = `in`.readValue(Boolean::class.java.classLoader) as Boolean?
        posterPath = `in`.readValue(String::class.java.classLoader) as String?
        id = `in`.readValue(Int::class.java.classLoader) as Int?
        adult = `in`.readValue(Boolean::class.java.classLoader) as Boolean?
        backdropPath = `in`.readValue(String::class.java.classLoader) as String?
        originalLanguage = `in`.readValue(String::class.java.classLoader) as String?
        originalTitle = `in`.readValue(String::class.java.classLoader) as String?
        `in`.readList(genreIds, Int::class.java.classLoader)
        title = `in`.readValue(String::class.java.classLoader) as String?
        voteAverage = `in`.readValue(Double::class.java.classLoader) as Double?
        overview = `in`.readValue(String::class.java.classLoader) as String?
        releaseDate = `in`.readValue(String::class.java.classLoader) as String?
    }

    constructor()

    @Bindable
    fun getPosterPath(): String? {
        return posterPath
    }

    fun setPosterPath(posterPath: String?) {
        this.posterPath = posterPath
        notifyPropertyChanged(BR.posterPath)
    }

    @Bindable
    fun getOriginalTitle(): String? {
        return originalTitle
    }

    fun setOriginalTitle(originalTitle: String?) {
        this.originalTitle = originalTitle
        notifyPropertyChanged(BR.originalTitle)
    }


    @Bindable
    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
        notifyPropertyChanged(BR.title)
    }

    @Bindable
    fun getVoteAverage(): Double? {
        return voteAverage
    }

    fun setVoteAverage(voteAverage: Double?) {
        this.voteAverage = voteAverage
        notifyPropertyChanged(BR.voteAverage)
    }

    @Bindable
    fun getOverview(): String? {
        return overview
    }

    fun setOverview(overview: String?) {
        this.overview = overview
        notifyPropertyChanged(BR.overview)
    }

    @Bindable
    fun getReleaseDate(): String? {
        return releaseDate
    }

    fun setReleaseDate(releaseDate: String?) {
        this.releaseDate = releaseDate
        notifyPropertyChanged(BR.releaseDate)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(popularity)
        dest.writeValue(voteCount)
        dest.writeValue(video)
        dest.writeValue(posterPath)
        dest.writeValue(id)
        dest.writeValue(adult)
        dest.writeValue(backdropPath)
        dest.writeValue(originalLanguage)
        dest.writeValue(originalTitle)
        dest.writeList(genreIds)
        dest.writeValue(title)
        dest.writeValue(voteAverage)
        dest.writeValue(overview)
        dest.writeValue(releaseDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Movie?> = object : Parcelable.Creator<Movie?> {
            override fun createFromParcel(`in`: Parcel): Movie {
                return Movie(`in`)
            }

            override fun newArray(size: Int): Array<Movie?> {
                return arrayOfNulls(size)
            }
        }

        @JvmStatic
        @BindingAdapter("posterPath")
        fun loadImage(imageView: ImageView, imageUrl: String) {
            val imagePath = "https://image.tmdb.org/t/p/w500$imageUrl"
            Glide.with(imageView.context)
                .load(imagePath)
                .placeholder(R.drawable.loading)
                .into(imageView)
        }
    }
}