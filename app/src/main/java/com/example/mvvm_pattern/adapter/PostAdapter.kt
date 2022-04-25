package com.example.mvvm_pattern.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_pattern.viewmodel.MainActivity
import com.example.mvvm_pattern.R
import com.example.mvvm_pattern.model.Post
import com.example.mvvm_pattern.utils.Utils

class PostAdapter(var activity: MainActivity, var items: ArrayList<Post>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post: Post = items[position]
        if (holder is PosterViewHolder){
            val ll_poster = holder.ll_poster
            val tv_title = holder.tv_title
            val tv_body = holder.tv_body

            tv_title.text = post.title.toUpperCase()
            tv_body.text = post.body

            ll_poster.setOnLongClickListener {
                deletePostDialog(post)
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PosterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ll_poster: LinearLayout
        val tv_title: TextView
        val tv_body: TextView

        init {
            ll_poster = view.findViewById(R.id.ll_poster)
            tv_title = view.findViewById(R.id.tv_title)
            tv_body = view.findViewById(R.id.tv_body)
        }
    }

    private fun deletePostDialog(post: Post) {
        val title = "Delete"
        val body = "Do you want to delete"
        Utils.customDialog(activity, title, body, object : Utils.DialogListener{
            override fun onPositiveClick() {
                activity.viewModel.apiPostDelete(post).observe(activity, Observer {
                    activity.viewModel.apiPostList()
                })
            }

            override fun onNegativeClick() {

            }

        })
    }
}