package mockData

import android.content.Context
import com.example.hackernews.presentation.model.CommentItem
import com.example.hackernews.presentation.model.NewsItem
import io.reactivex.Observable

import java.util.Arrays

object MockDataObserver {

    private fun getMockTopStories(context: Context, path: String): Observable<MutableList<Int?>>? {
        val list = getTopStories(context, path) ?: return null
        return Observable.just(list)
    }

    private fun getTopStories(context: Context, path: String): MutableList<Int?>? {
        var ids: Array<Int?> = arrayOfNulls(0)
        try {
            ids = ParseMockData().parseMockData(context, path, Array<Int?>::class.java)!!
        } catch (e: Exception) {
        }
        return Arrays.asList(*ids)
    }

    private fun getMockResultItem(context: Context, path: String): Observable<NewsItem>? {
        val item = getResulItem(context, path) ?: return null
        return Observable.just(item)
    }

    private fun getResulItem(context: Context, path: String): NewsItem? {
        var resultItem: NewsItem? = null
        try {
            resultItem = ParseMockData().parseMockData(context, path, NewsItem::class.java)
        } catch (e: Exception) {
        }
        return resultItem
    }

    fun getMockCommentItem(context: Context, path: String): Observable<CommentItem>? {
        val comment = getCommentItem(context, path) ?: return null
        return Observable.just(comment)
    }

    fun getCommentItem(context: Context, path: String): CommentItem? {
        var commentItem: CommentItem? = null
        try {
            commentItem = ParseMockData().parseMockData(context, path, CommentItem::class.java)
        } catch (e: Exception) {
        }
        return commentItem
    }

    fun getTopStoriesMockDataObserver(context: Context): Observable<NewsItem>? {
        return getMockTopStories(context, "topstories.json")
            ?.flatMap { ids -> Observable.fromIterable(ids) }
            ?.flatMap { id -> getMockResultItem(context, "parentItem/$id.json") }
    }

    fun getTopStoriesMockDataObserver(context: Context, path: String): Observable<NewsItem>? {
        if (path.isEmpty())
            return null
        return getMockTopStories(context, path)
            ?.flatMap { ids -> Observable.fromIterable(ids) }
            ?.flatMap { id -> getMockResultItem(context, "parentItem/$id.json") }
    }

    fun getCommentsMockDataObserver(context: Context, kids: List<Int>): Observable<CommentItem>? {
        return Observable.just(kids)
            .flatMap { ids -> Observable.fromIterable(ids) }
            .flatMap { id -> getMockCommentItem(context, "commentItem/$id.json") }
    }
}
