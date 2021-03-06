package com.example.onlineshop.data.entity.customProduct



import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("body_html")
    val bodyHtml: String,
    @SerializedName("created_at")
    val createdAt: String,
    val handle: String,
    val id: Double,
    val image: Image?,
    val images: List<Image>?,
    val options: List<Option>?,
    @SerializedName("product_type")
    val productType: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("published_scope")
    val publishedScope: String,
    val tags: String,
    @SerializedName("template_suffix")
    val templateSuffix: Any,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val vendor: String
) {
    constructor(title: String, vendor: String) : this(
        "",
        "", "", "", 0.0, null, null, null, "", "", "", "", "",title,"",vendor

    )
}