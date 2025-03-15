package com.matheus.movieapp.data.paging

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.matheus.movieapp.data.model.Movie
import com.matheus.movieapp.data.model.toDomain
import com.matheus.movieapp.data.remote.MovieApi
import kotlinx.coroutines.runBlocking

class MoviePagingSource(
    private val api: MovieApi,
    private val apiKey: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.getPopularMovies(apiKey, page)
            val movies = response.movies.map { it.toDomain() }

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}