package com.example.hackernews.ui.newsdetails

import android.graphics.Typeface
import android.view.View
import com.example.hackernews.R
import com.example.hackernews.databinding.ItemCommentsListBinding
import com.example.hackernews.presentation.model.CommentItem
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.databinding.BindableItem

open class ReplyListItem(private val commentItem: CommentItem) : BindableItem<ItemCommentsListBinding>(), ExpandableItem {

    private var expandableGroup: ExpandableGroup? = null

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    override fun getLayout(): Int {
        return R.layout.item_comments_list
    }

    override fun bind(viewBinding: ItemCommentsListBinding, position: Int) {
        viewBinding.commentItem = commentItem
        viewBinding.executePendingBindings()
        viewBinding.title.isEnabled = false
        viewBinding.title.typeface = Typeface.DEFAULT
        viewBinding.replies.visibility = View.GONE
        viewBinding.line.visibility = View.VISIBLE
        viewBinding.lineSeparator.visibility = View.VISIBLE
    }
}