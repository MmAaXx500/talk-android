/*
 * Nextcloud Talk - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Sowjanya Kota <sowjanya.kch@gmail.com>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package com.nextcloud.talk.conversationcreation

import com.nextcloud.talk.models.json.conversations.ConversationEnums
import com.nextcloud.talk.models.json.conversations.RoomOverall
import com.nextcloud.talk.models.json.generic.GenericOverall
import com.nextcloud.talk.models.json.participants.AddParticipantOverall

interface ConversationCreationRepository {

    fun allowGuests(token: String, allow: Boolean): ConversationCreationRepositoryImpl.AllowGuestsResult
    suspend fun renameConversation(roomToken: String, roomNameNew: String?): GenericOverall
    suspend fun setConversationDescription(roomToken: String, description: String?): GenericOverall
    suspend fun addParticipants(conversationToken: String?, userId: String, sourceType: String): AddParticipantOverall
    suspend fun createRoom(roomType: ConversationEnums.ConversationType?, conversationName: String?): RoomOverall
    fun getImageUri(avatarId: String, requestBigSize: Boolean): String
}
