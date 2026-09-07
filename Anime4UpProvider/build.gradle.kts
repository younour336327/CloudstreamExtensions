plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.lagradost.cloudstream3.plugin")
}

cloudstream {
    setID("anime4up")
    setName("Anime4Up")
    setAuthor("Younour")
    setDescription("Anime4Up provider for Cloudstream")
    setVersion(1)
    setTvTypes(listOf("Anime"))
}
