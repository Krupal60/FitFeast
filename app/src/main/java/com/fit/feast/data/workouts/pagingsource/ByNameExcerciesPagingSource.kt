package com.fit.feast.data.workouts.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.network.workouts.FitnessApiService
import retrofit2.HttpException
import java.io.IOException

class ByNameExcerciesPagingSource(private val api: FitnessApiService,private val name: String) : PagingSource<Int, Exercises>() {
    override fun getRefreshKey(state: PagingState<Int, Exercises>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Exercises> {
        return try {
            val currentPage = params.key ?: 1
            val data = api.byName(name = name,limit = 15,page = currentPage)
                LoadResult.Page(
                  data = data.body()!!,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (data.body()!!.isEmpty()) null else currentPage + 1
              )
        }catch (e: HttpException){
           return LoadResult.Error(e)
        }catch (e : IOException){
            return LoadResult.Error(e)
        }
    }
}