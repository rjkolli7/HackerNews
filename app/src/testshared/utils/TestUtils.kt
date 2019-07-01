package utils

import com.example.hackernews.presentation.model.CommentItem
import com.example.hackernews.presentation.model.NewsItem

var kids = listOf(
    20317392,
    20317738
)

var topStories = listOf(
    20316803,
    20317002
)

var newsList = listOf(
    NewsItem(id = 20316803, kids = listOf(20317392)),
    NewsItem(id = 20317002, kids = listOf(20317738))
)

var commentsList = listOf(
    CommentItem(id = 20317392),
    CommentItem(
        id = 20317738,
        kids = listOf(20317938),
        replies = listOf(CommentItem(id = 20317938, kids = listOf(20320136, 20321324)))
    )
)