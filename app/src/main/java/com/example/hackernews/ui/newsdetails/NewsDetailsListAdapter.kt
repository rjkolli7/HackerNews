package com.example.hackernews.ui.newsdetails

import com.example.hackernews.presentation.model.CommentItem
import com.xwray.groupie.*

class NewsDetailsListAdapter : GroupAdapter<ViewHolder>(), ExpandableItem {

    fun notifyAdapter(comments: List<CommentItem>?) {
        comments?.let {
            for (comment in it) {
                ExpandableGroup(CommentListItem(comment), false).apply {
                    for (reply in comment.replies) {
                        reply?.let { re ->
                            add(Section(ReplyListItem(re)))
                        }
                    }
                    this@NewsDetailsListAdapter.add(this)
                }
            }
        }
    }

    private lateinit var expandableGroup: ExpandableGroup

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }
}