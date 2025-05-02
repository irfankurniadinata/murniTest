package com.test.murni.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    var id: Int? = 0,
    var title: String? = null,
    var authors: List<Author>? = null,
    var url: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null,
    @SerializedName("news_site")
    var newsSite: String? = null,
    var summary: String? = null,
    @SerializedName("published_at")
    var publishedAt: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    var featured: Boolean? = null,
)
