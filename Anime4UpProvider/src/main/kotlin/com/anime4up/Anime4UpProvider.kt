package com.anime4up

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class Anime4UpProvider : MainAPI() {
    override var mainUrl = "https://anime4up.tv"
    override var name = "Anime4Up"
    override val supportedTypes = setOf(TvType.Anime)
    override var lang = "ar"
    override val hasMainPage = true

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val document = app.get("/anime-list").document
        val home = document.select("div.episodes-card-container, div.col-anime").mapNotNull { element ->
            val title = element.select("h3 a, .anime-card-title a").text()
            val href = element.select("h3 a, .anime-card-title a").attr("href")
            val posterUrl = element.select("img").attr("data-src").ifEmpty { element.select("img").attr("src") }
            newAnimeSearchResponse(title, href, TvType.Anime) {
                this.posterUrl = posterUrl
            }
        }
        return newHomePageResponse(request.name, home)
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val document = app.get("/?s=").document
        return document.select("div.col-anime").mapNotNull { element ->
            val title = element.select(".anime-card-title a").text()
            val href = element.select(".anime-card-title a").attr("href")
            val posterUrl = element.select("img").attr(\> "data-src").ifEmpty { element.select("img").attr("src") }
            newAnimeSearchResponse(title, href, TvType.Anime) {
                this.posterUrl = posterUrl
            }
        }
    }

    override suspend fun load(url: String): LoadResponse {
        val document = app.get(url).document
        val title = document.select("h1.anime-details-title").text()
        val poster = document.select("div.anime-details-poster img").attr("src")
        val description = document.select("div.anime-details-des").text()
        
        return newAnimeLoadResponse(title, url, TvType.Anime) {
            this.posterUrl = poster
            this.plot = description
        }
    }

    override suspend fun loadLinks(data: String, isCasting: Boolean, subtitleCallback: (SubtitleFile) -> Unit, callback: (ExtractorLink) -> Unit): Boolean {
        val document = app.get(data).document
        val videoUrl = document.select("iframe").attr("src")
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
