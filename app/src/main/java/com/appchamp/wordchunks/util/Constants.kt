package com.appchamp.wordchunks.util


object Constants {

    const val JSON_FILE_NAME = "data.json"
    const val WORDS_SEPARATOR = " "
    const val CHUNKS_SEPARATOR = ","

    const val WORD_CHUNKS_PREFS = "WORD_CHUNKS_PREFS"
    const val PREFS_REALM_CREATE_OBJECTS = "PREFS_REALM_CREATE_OBJECTS"
    const val PREFS_HOW_TO_PLAY = "PREFS_HOW_TO_PLAY"

    const val EXTRA_PACK_ID = "EXTRA_PACK_ID"
    const val LEVEL_ID_KEY = "LEVEL_ID_KEY"
    const val COLOR_ID_KEY = "COLOR_ID_KEY"
    const val FACT_ID_KEY = "FACT_ID_KEY"
    const val LEFT_ID_KEY = "LEFT_ID_KEY"
    const val CLUE_ID_KEY = "CLUE_ID_KEY"

    const val REALM_FIELD_ID = "id"
    const val REALM_FIELD_PACK_ID = "packId"
    const val REALM_FIELD_LEVEL_ID = "levelId"
    const val REALM_FIELD_STATE = "state"

    const val WORDS_GRID_NUM = 2
    const val CHUNKS_GRID_NUM = 4

    // States of chunks
    const val CHUNK_STATE_NORMAL = 0L
    const val CHUNK_STATE_GONE = -1L

    // States of words
    const val WORD_STATE_NOT_SOLVED = 0
    const val WORD_STATE_SOLVED = 1

    // States of packs and levels
    const val STATE_LOCKED = 0
    const val STATE_CURRENT = 1
    const val STATE_SOLVED = 2
}