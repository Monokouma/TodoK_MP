package com.flacinc.todok_mp.data.meeting.repository

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isSuccess
import com.flacinc.todok_mp.data.database.sql.AppDatabase
import com.flacinc.todok_mp.data.meeting.MeetingRepositoryImpl
import com.flacinc.todok_mp.domain.meeting.entity.CreateMeetingEntity
import com.flacinc.todok_mp.domain.meeting.entity.MeetingEntity
import com.flacinc.todok_mp.ui.utils.model.MeetingPlace
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class MeetingRepositoryImplTest {
    private val standardTestDispatcher = StandardTestDispatcher()

    private lateinit var driver: JdbcSqliteDriver
    private lateinit var database: AppDatabase
    private lateinit var repository: MeetingRepositoryImpl

    @Before
    fun setup() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        AppDatabase.Schema.create(driver)
        database = AppDatabase(driver)
        repository = MeetingRepositoryImpl(database.meetingQueries)
    }

    @After
    fun tearDown() {
        driver.close()
    }

    @Test
    fun `nominal case - save should return success`() = runTest(standardTestDispatcher) {
        val entity = CreateMeetingEntity(
            title = "Daily",
            subject = "Standup",
            timestamp = 123456L,
            meetingPlace = MeetingPlace.ROOM_200.name,
            participants = listOf("Alice", "Bob"),
        )

        val result = repository.save(entity)

        assertThat(result).isSuccess()
        result.onSuccess {
            assertThat(it).isEqualTo(Unit)
        }
    }

    @Test
    fun `error case - save should return failure`() = runTest(standardTestDispatcher) {

        driver.close()

        val entity = CreateMeetingEntity(
            title = "Daily",
            subject = "Standup",
            timestamp = 123456L,
            meetingPlace = MeetingPlace.ROOM_200.name,
            participants = listOf("Alice", "Bob"),
        )

        val result = repository.save(entity)

        assertThat(result).isFailure()
        result.onFailure {
            assertThat(it.message).isEqualTo("database connection closed")
        }
    }

    @Test
    fun `nominal case - get meetings should return meeting`() = runTest(standardTestDispatcher) {
        val expectedResults = mutableListOf<MeetingEntity>()

        for (i in 1..3) {
            val entity = CreateMeetingEntity(
                title = "Daily+$i",
                subject = "Standup+$i",
                timestamp = 123456L,
                meetingPlace = MeetingPlace.ROOM_200.name,
                participants = listOf("Alice", "Bob"),
            )
            expectedResults.add(
                MeetingEntity(
                    id = i.toLong(),
                    title = "Daily+$i",
                    subject = "Standup+$i",
                    timestamp = 123456L,
                    place = MeetingPlace.ROOM_200.name,
                    participants = "Alice,Bob",
                )
            )
            repository.save(entity)
        }

        val result = repository.getMeetings().first()

        assertThat(result).isEqualTo(expectedResults)
    }
}