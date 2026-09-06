package com.akwam

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class AkwamProvider : MainAPI() {
    override var mainUrl = "https://akwam.cx"
    override var name = "Akwam"
    override val supportedTypes = setOf(TvType.Movie, TvType.TvSeries)
    override var lang = "ar"
    override val hasMainPage = true

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val document = app.get("/movies").document
        val home = document.select("div.entry-box").mapNotNull { element ->
            val title = element.select("h3 a").text()
            val href = element.select("h3 a").attr("href")
            val posterUrl = element.select("img").attr("data-src").ifEmpty { element.select("img").attr("src") }
            newMovieSearchResponse(title, href, TvType.Movie) {
                this.posterUrl = posterUrl
            }
        }
        return newHomePageResponse(request.name, home)
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val document = app.get("/search?q=").document
        return document.select("div.entry-box").mapNotNull { element ->
            val title = element.select("h3 a").text()
            val href = element.select("h3 a").attr("href")
            val posterUrl = element.select("img").attr("data-src").ifEmpty { element.select("img").attr("src") }
            newMovieSearchResponse(title, href, TvType.Movie) {
                this.posterUrl = posterUrl
            }
        }
    }

    override suspend fun load(url: String): LoadResponse {
        val document = app.get(url).document
        val title = document.select("h1.entry-title").text()
        val poster = document.select("div.poster img").attr("src")
        val description = document.select("div.story").text()
        
        return newMovieLoadResponse(title, url, TvType.Movie, url) {
            this.posterUrl = poster
            this.plot = description
        }
    }

    override suspend fun loadLinks(data: String, isCasting: Boolean, subtitleCallback: (SubtitleFile) -> Unit, callback: (ExtractorLink) -> Unit): Boolean {
        val document = app.get(data).document
        val videoUrl = document.select("video source").attr("src")
        if (videoUrl.isNotEmpty()) {
            callback.invoke(
                ExtractorLink(
                    source = name,
                    name = name,
                    url = videoUrl,
                    referer = mainUrl,
                    quality = Qualities.Unknown.value,
                    isM3u8 = videoUrl.contains(".m3u8")
                )
            )
        }
        return true
    }
}
