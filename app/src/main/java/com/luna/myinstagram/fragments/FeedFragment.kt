package com.luna.myinstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luna.myinstagram.MainActivity
import com.luna.myinstagram.Post
import com.luna.myinstagram.PostAdapter
import com.luna.myinstagram.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

class FeedFragment : Fragment() {

    lateinit var postRecyclerView: RecyclerView

    lateinit var adapter: PostAdapter
    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    private fun queryPosts() {
        //specify which class to query
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)

        //return posts in descending order based on posted time
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching posts")
                }
                else {
                    if (posts != null) {
                        for (post in posts) {
                            Log.i(TAG, "Post: " + post.getDescription() + " , username: " + post.getUser()?.username)
                        }
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }
    companion object {
        const val TAG = "FeedFragment"
    }
}