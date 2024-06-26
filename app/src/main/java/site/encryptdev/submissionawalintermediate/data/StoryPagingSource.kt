package site.encryptdev.submissionawalintermediate.data


import androidx.paging.PagingSource
import androidx.paging.PagingState
import site.encryptdev.submissionawalintermediate.data.remote.response.ListStoryItem
import site.encryptdev.submissionawalintermediate.data.remote.retrofit.ApiConfig

class StoryPagingSource( private val token: String) : PagingSource<Int, ListStoryItem>() {
    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData= ApiConfig.getService().getStories(token="Bearer $token", page = position, size = params.loadSize).listStory


            LoadResult.Page(
                data =responseData,
                prevKey =if(position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey =if(responseData.isNullOrEmpty()) null else position +1

            )
        }catch (exception  : Exception){
            return LoadResult.Error(exception)
        }
    }
}
