package com.example.teamprojectsolocode

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

// 할 일을 보여주는 Recycler View의 각 item의 top마다 margin을 넣게 해주는 클래스
class RecyclerViewDecoration(private val divHeight: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = divHeight
    }
}