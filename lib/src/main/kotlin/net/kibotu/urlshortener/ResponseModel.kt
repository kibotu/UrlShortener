package net.kibotu.urlshortener

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

data class ResponseModel(
        var kind: String? = null,
        var id: String? = null,
        var longUrl: String? = null
)