/*
 * Nextcloud Talk - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Julius Linus <juliuslinus1@gmail.com>
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package com.nextcloud.talk.data.database.mappers

import com.nextcloud.talk.models.json.chat.ChatMessageJson
import com.nextcloud.talk.data.database.model.ChatMessageEntity
import com.nextcloud.talk.chat.data.model.ChatMessage
import com.nextcloud.talk.data.database.dao.ChatMessagesDao
import kotlinx.coroutines.flow.first

fun ChatMessageJson.asEntity(accountId: Long) =
    ChatMessageEntity(
        // accountId@token@messageId
        internalId = "$accountId@$token@$id",
        accountId = accountId,
        id = id,
        internalConversationId = "$accountId@$token",
        message = message,
        token = token,
        actorType = actorType,
        actorId = actorId,
        actorDisplayName = actorDisplayName,
        timestamp = timestamp,
        messageParameters = messageParameters,
        systemMessageType = systemMessageType,
        replyable = replyable,
        parentMessageId = parentMessage?.id,
        messageType = messageType,
        reactions = reactions,
        reactionsSelf = reactionsSelf,
        expirationTimestamp = expirationTimestamp,
        renderMarkdown = renderMarkdown,
        lastEditActorDisplayName = lastEditActorDisplayName,
        lastEditActorId = lastEditActorId,
        lastEditActorType = lastEditActorType,
        lastEditTimestamp = lastEditTimestamp
    )

fun ChatMessageEntity.asModel() =
    ChatMessage(
        jsonMessageId = id.toInt(),
        message = message,
        token = token,
        actorType = actorType,
        actorId = actorId,
        actorDisplayName = actorDisplayName,
        timestamp = timestamp,
        messageParameters = messageParameters,
        systemMessageType = systemMessageType,
        replyable = replyable,
        parentMessageId = parentMessageId,
        messageType = messageType,
        reactions = reactions,
        reactionsSelf = reactionsSelf,
        expirationTimestamp = expirationTimestamp,
        renderMarkdown = renderMarkdown,
        lastEditActorDisplayName = lastEditActorDisplayName,
        lastEditActorId = lastEditActorId,
        lastEditActorType = lastEditActorType,
        lastEditTimestamp = lastEditTimestamp
    )

fun ChatMessageJson.asModel() =
    ChatMessage(
        jsonMessageId = id.toInt(),
        message = message,
        token = token,
        actorType = actorType,
        actorId = actorId,
        actorDisplayName = actorDisplayName,
        timestamp = timestamp,
        messageParameters = messageParameters,
        systemMessageType = systemMessageType,
        replyable = replyable,
        parentMessageId = parentMessage?.id,
        messageType = messageType,
        reactions = reactions,
        reactionsSelf = reactionsSelf,
        expirationTimestamp = expirationTimestamp,
        renderMarkdown = renderMarkdown,
        lastEditActorDisplayName = lastEditActorDisplayName,
        lastEditActorId = lastEditActorId,
        lastEditActorType = lastEditActorType,
        lastEditTimestamp = lastEditTimestamp
    )
