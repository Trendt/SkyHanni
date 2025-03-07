package at.hannibal2.skyhanni.features.mining

import at.hannibal2.skyhanni.SkyHanniMod
import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.events.SecondPassedEvent
import at.hannibal2.skyhanni.skyhannimodule.SkyHanniModule
import at.hannibal2.skyhanni.utils.ChatUtils
import at.hannibal2.skyhanni.utils.HypixelCommands
import at.hannibal2.skyhanni.utils.LorenzUtils
import at.hannibal2.skyhanni.utils.SkyBlockTime

@SkyHanniModule
object StarCult {

    private val config get() = SkyHanniMod.feature.mining

    private var notified = false

    @HandleEvent
    fun onSecondPassed(event: SecondPassedEvent) {
        if (!isEnabled()) return

        if (!isCultHappening()) {
            notified = false
        } else if (!notified) {
            sendNotificationMessage()
            notified = true
        }
    }

    private fun isCultHappening(): Boolean {
        val day = SkyBlockTime.now().day
        return day % 7 == 0 && day != 0 && SkyBlockTime.now().hour < 6
    }

    private fun sendNotificationMessage() {
        ChatUtils.clickToActionOrDisable(
            "Cult of the Fallen Star happening. Click to warp to the Forge!",
            config::starCult,
            actionName = "warp to forge",
            action = { HypixelCommands.warp("forge") },
        )
    }

    fun isEnabled() = config.starCult &&
        LorenzUtils.inSkyBlock
}
