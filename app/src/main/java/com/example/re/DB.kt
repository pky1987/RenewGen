package com.example.re

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Date

class DB(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_POSTS_TABLE = "CREATE TABLE $TABLE_POSTS (" +
                "$KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_TITLE TEXT," +
                "$KEY_CONTENT TEXT," +
                "$KEY_AUTHOR TEXT," +
                "$KEY_CREATED_AT INTEGER)"
        db.execSQL(CREATE_POSTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_POSTS")
        onCreate(db)
    }

    fun insertPost(post: Post) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, post.title)
        values.put(KEY_CONTENT, post.content)
        values.put(KEY_AUTHOR, post.author)
        values.put(KEY_CREATED_AT, post.createdAt.time)
        db.insert(TABLE_POSTS, null, values)
        db.close()
    }

    val allPosts: List<Post>
        get() {
            val postList: MutableList<Post> = ArrayList()
            val selectQuery = "SELECT * FROM $TABLE_POSTS"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val post = Post()
                    post.id = cursor.getInt(0)
                    post.title = cursor.getString(1)
                    post.content = cursor.getString(2)
                    post.author = cursor.getString(3)
                    post.createdAt = Date(cursor.getLong(4))
                    postList.add(post)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return postList
        }

    fun deletePost(postId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_POSTS, "$KEY_ID = ?", arrayOf(postId.toString()))
        db.close()
    }

    fun updatePost(post: Post) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, post.title)
        values.put(KEY_CONTENT, post.content)
        values.put(KEY_AUTHOR, post.author)
        values.put(KEY_CREATED_AT, post.createdAt.time)

        db.update(TABLE_POSTS, values, "$KEY_ID = ?", arrayOf(post.id.toString()))
        db.close()
    }

    fun dropTable() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_POSTS")
        db.close()
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "community"
        private const val TABLE_POSTS = "posts"
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_CONTENT = "content"
        private const val KEY_AUTHOR = "author"
        private const val KEY_CREATED_AT = "createdAt"
    }
}
