/*
 * Nextcloud Talk - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Tim Krüger <t@timkrueger.me>
 * SPDX-FileCopyrightText: 2022 Nextcloud GmbH
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package com.nextcloud.talk.repositories.conversations

import com.nextcloud.talk.models.json.generic.GenericOverall
import com.nextcloud.talk.models.json.participants.TalkBan
import io.reactivex.Observable

interface ConversationsRepository {

    suspend fun allowGuests(token: String, allow: Boolean): GenericOverall

    data class ResendInvitationsResult(
        val successful: Boolean
    )
    fun resendInvitations(token: String): Observable<ResendInvitationsResult>

    suspend fun archiveConversation(credentials: String, url: String): GenericOverall

    suspend fun unarchiveConversation(credentials: String, url: String): GenericOverall

    fun setConversationReadOnly(credentials: String, url: String, state: Int): Observable<GenericOverall>

    suspend fun banActor(
        credentials: String,
        url: String,
        actorType: String,
        actorId: String,
        internalNote: String
    ): TalkBan

    suspend fun setPassword(password: String, token: String): GenericOverall

    suspend fun setConversationReadOnly(roomToken: String, state: Int): GenericOverall

    suspend fun clearChatHistory(apiVersion: Int, roomToken: String): GenericOverall
}
