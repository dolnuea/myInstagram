package com.luna.myinstagram.fragments

import android.util.Log
import com.luna.myinstagram.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment: FeedFragment() {

    override fun queryPosts() {
        // SwipeRefresh: Clear posts first
        allPosts.clear()

        // SwipeRefresh: Populate again
        //specify which class to query
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        //only return posts from current signed in user
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        //return posts in descending order based on posted time
        query.addDescendingOrder("createdAt")

        query.findInBackground { posts, e ->
            if (e != null) {
                Log.e(TAG, "Error fetching posts")
            } else {
                var count = 0
                if (posts != null) {
                    for (post in posts) {
                        if (count < 20)
                            allPosts.add(post)
                        Log.i(
                            TAG,
                            "Post: " + post.getDescription() + " , username: " + post.getUser()?.username
                        )
                        count++
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}