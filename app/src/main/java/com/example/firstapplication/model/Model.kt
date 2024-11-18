data class TmdbResult(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

data class TvShowsResponse(
    val page: Int,
    val results: List<TvShow>,
    val total_pages: Int,
    val total_results: Int
)

data class TvShow(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val first_air_date: String,
    val name: String,
    val vote_average: Double,
    val vote_count: Int
)

data class PersonResponse(
    val page: Int,
    val results: List<Person>,
    val total_pages: Int,
    val total_results: Int
)
data class Person(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?,
    val known_for: List<KnownFor>
)

data class KnownFor(
    val backdrop_path: String?,
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String?,
    val media_type: String,
    val adult: Boolean,
    val original_language: String,
    val genre_ids: List<Int>,
    val popularity: Double,
    val release_date: String?,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)