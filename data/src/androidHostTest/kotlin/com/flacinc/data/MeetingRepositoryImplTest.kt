package com.flacinc.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isSuccess
import com.flacinc.data.meeting.MeetingRepositoryImpl
import com.flacinc.domain.meeting.entity.CreateMeetingEntity
import com.flacinc.domain.meeting.entity.MeetingEntity
import com.flacinc.todok_mp.data.database.sql.AppDatabase
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
            meetingPlace = "ROOM_200",
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
            meetingPlace = "ROOM_200",
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
                meetingPlace = "ROOM_200",
                participants = listOf("Alice", "Bob"),
            )
            expectedResults.add(
                MeetingEntity(
                    id = i.toLong(),
                    title = "Daily+$i",
                    subject = "Standup+$i",
                    timestamp = 123456L,
                    place = "ROOM_200",
                    participants = "Alice,Bob",
                )
            )
            repository.save(entity)
        }

        val result = repository.getMeetings().first()

        assertThat(result).isEqualTo(expectedResults)
    }

    @Test
    fun `nominal case - delete meeting by id`() = runTest(standardTestDispatcher) {
        for (i in 1..3) {
            val entity = CreateMeetingEntity(
                title = "Daily+$i",
                subject = "Standup+$i",
                timestamp = 123456L,
                meetingPlace = "ROOM_200",
                participants = listOf("Alice", "Bob"),
            )
            repository.save(entity)
        }

        repository.delete(1)

        assertThat(repository.getMeetings().first().size).isEqualTo(2)
    }
}