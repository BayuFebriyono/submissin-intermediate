package site.encryptdev.submissionawalintermediate

import site.encryptdev.submissionawalintermediate.data.remote.response.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                photoUrl = "photo + $i",
                createdAt = "createdAt + $i",
                name = "name + $i",
                description = "description + $i",
                id = i.toString()
            )
            items.add(quote)
        }
        return items
    }
}