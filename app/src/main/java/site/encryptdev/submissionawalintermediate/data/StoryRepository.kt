package site.encryptdev.submissionawalintermediate.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import site.encryptdev.submissionawalintermediate.data.remote.response.ListStoryItem

class StoryRepository(token: String) {

    private val tokens = token
    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(token = tokens)
            }
        ).liveData
    }
}