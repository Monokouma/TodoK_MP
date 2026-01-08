package com.flacinc.domain.error_manager


enum class TodokError(val message: String) {
    MEETING_SUBJECT_EMPTY_VALUE("MEETING_SUBJECT_EMPTY_VALUE"),
    MEETING_TITLE_EMPTY_VALUE("MEETING_TITLE_EMPTY_VALUE"),
    MEETING_PARTICIPANTS_EMPTY_VALUE("MEETING_PARTICIPANTS_EMPTY_VALUE"),

}

class TodokException(val error: TodokError) : Exception(error.message)