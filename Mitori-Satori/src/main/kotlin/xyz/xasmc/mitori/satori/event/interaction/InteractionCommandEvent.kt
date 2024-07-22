package xyz.xasmc.mitori.satori.event.interaction

import xyz.xasmc.mitori.satori.datatype.interaction.Argv
import xyz.xasmc.mitori.satori.datatype.message.Message
import xyz.xasmc.mitori.satori.event.BaseEvent

class InteractionCommandEvent(
    override val argv: Argv?,
    override val message: Message?,
) : BaseEvent("interaction/command")