package com.luna.myinstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.luna.myinstagram.MainActivity
import com.luna.myinstagram.Post
import com.luna.myinstagram.PostAdapter
import com.luna.myinstagram.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

open class FeedFragment : Fragment() {

    lateinit var postRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter
    lateinit var swipeContainer: SwipeRefreshLayout
    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeContainer = view.findViewById(R.id.swipeContainer)
        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            queryPosts()
            swipeContainer.isRefreshing = false
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        postRecyclerView = view.findViewById(R.id.postRecyclerView)

        // populate recyclerview:
        // create layout for each row in list
        // create data source for each row
        // create adapter that will bridge data and row layout (post adapter class)
        // set adapter on recyclerview
        adapter = PostAdapter(requireContext(), allPosts)
        postRecyclerView.adapter = adapter
        // set layout manager on recyclerview
        postRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        queryPosts()
    }

    // query for all posts in our server
    open fun queryPosts() {

        // SwipeRefresh: Clear posts first
        allPosts.clear()

        // SwipeRefresh: Populate again
        //specify which class to query
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)

        //return posts in descending order based on posted time
        query.addDescendingOrder("createdAt")

        query.findInBackground { posts, e ->
            if (e != null) {
                Log.e(TAG, "Error fetching posts")
            } else {
                var count = 0
                if (posts != null) {
                    // get last 20 posts
                    for (post in posts) {
                        if(count < 20)
                            allPosts.add(post)
                        Log.i(TAG, "Post: " + post.getDescription() + " , username: " + post.getUser()?.username)
                        count++
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
    companion object {
        const val TAG = "FeedFragment"
    }
}