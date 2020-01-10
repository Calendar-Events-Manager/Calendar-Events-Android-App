package com.mymeetings.android

import android.content.Context
import android.support.test.runner.AndroidJUnit4
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mymeetings.android.db.MeetingsDao
import com.mymeetings.android.db.MyMeetingsDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MeetingDaoTest {
//
//    private lateinit var meetingsDao: MeetingsDao
//    private lateinit var db: MyMeetingsDatabase
//
//    @Before
//    fun createDb() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(
//            context, MyMeetingsDatabase::class.java).build()
//        meetingsDao = db.
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun writeUserAndReadInList() {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
//        meetingsDao.insert(user)
//        val byName = meetingsDao.findUsersByName("george")
//        assertThat(byName.get(0), equalTo(user))
//    }
}