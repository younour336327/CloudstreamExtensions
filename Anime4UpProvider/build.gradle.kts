plugins {
    id("com.android.library")
    id("kotlin-android")
    id("cloudstream.plugin")
}

cloudstream {
    setID("anime4up")
    setName("Anime4Up")
    setAuthor("Younour")
    setDescription("Anime4Up provider for Cloudstream")
    setVersion(1)
    setTvTypes(listOf("Anime"))
}
