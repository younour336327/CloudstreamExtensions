plugins {
    id("com.android.library")
    id("kotlin-android")
    id("cloudstream.plugin")
}

cloudstream {
    setID("akwam")
    setName("Akwam")
    setAuthor("Younour")
    setDescription("Akwam provider for Cloudstream")
    setVersion(1)
    setTvTypes(listOf("Movie", "TvSeries"))
}
