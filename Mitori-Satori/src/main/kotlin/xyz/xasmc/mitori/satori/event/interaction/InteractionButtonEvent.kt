package xyz.xasmc.mitori.satori.event.interaction

import xyz.xasmc.mitori.satori.datatype.interaction.Button
import xyz.xasmc.mitori.satori.event.BaseEvent

class InteractionButtonEvent(
    override val button: Button,
) : BaseEvent("interaction/button")