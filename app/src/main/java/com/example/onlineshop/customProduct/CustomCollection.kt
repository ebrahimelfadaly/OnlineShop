package com.example.onlineshop.customProduct

data class CustomCollection(
    val admin_graphql_api_id: String,
    val body_html: Any,
    val handle: String,
    val id: Long,
    val image: Image,
    val published_at: String,
    val published_scope: String,
    val sort_order: String,
    val template_suffix: Any,
    val title: String,
    val updated_at: String
)