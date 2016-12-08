package net.kibotu.urlshortener

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */
data class ResponseModel(
        var kind: String = "",
        var id: String = "",
        var longUrl: String = ""
)