package xyz.xasmc.mitori.satori.event

import xyz.xasmc.mitori.satori.datatype.interaction.Argv
import xyz.xasmc.mitori.satori.datatype.interaction.Button
import xyz.xasmc.mitori.satori.datatype.message.Message


fun interactionButtonEvent(
    button: Button, block: BaseEvent.() -> Unit = {}
) = BaseEvent("interaction/button", button = button).apply(block)

fun interactionCommandEvent(
    argv: Argv?, message: Message?, block: BaseEvent.() -> Unit = {}
) = BaseEvent("interaction/command", argv = argv, message = message).apply(block)