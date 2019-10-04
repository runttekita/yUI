package reina.yUi

import basemod.BaseMod
import basemod.interfaces.PostRenderSubscriber
import basemod.interfaces.PostUpdateSubscriber
import basemod.interfaces.RenderSubscriber
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import java.util.*

@SpireInitializer
class Yui() :
    RenderSubscriber {
    var test: Test? = null

    init {
        BaseMod.subscribe(this)
    }

    override fun receiveRender(sb: SpriteBatch) {
        if (test == null)
            test = Test()
        if (AbstractDungeon.player != null) {
            test!!.update()
            test!!.render(sb)
        }
    }

    companion object {
        @JvmStatic
        fun initialize() {
            Yui()
        }
    }

    /**
     * Idea taken from Alchyr
     * ASCII Art made from
     * https://asciiart.club/
     */
    object ASCII_ART {

        @SpireEnum
        @JvmStatic
        var iLoveReina: AbstractCard.CardTags? = null

        init {
            init()
        }

        private fun init() {
            println("\n" +
                    "    ╫╫╫╫╫╫╫╫╫╫╫╫╫╫╫╫▓██████▓███████████▓▓▓▓▓▓▓▓▓▓▓▓▌▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓█████████▓██\n" +
                    "    ░╫░╫╫╫╠▀███▓▄▄▄▓████████▓██████▌║▓▓▓▓▓▓▓▓▓▓▓█▓▓ ║▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓███████████\n" +
                    "    ░░░░░░░░╠████████████████▓▀▓▓▌`H║▓▓▌╙▓▓▓▓▓▓▓▓▓▌  ╢▓▓▓▓▓▓▓▓▓▓▓▓█▓▓▓▓▓▓▓██████████\n" +
                    "    ░░░░░░░░╫██████████████▓▓▓▓▓▀ ╒  ▓▓M╙▀▓▓▓▓▓▓▓▓H  ╓▓▓▓▓▓▓▓▓▓▓▓▓▓█▓▓▓▓▓▓▓█████████\n" +
                    "    ░░░░░░╠▄▓██████████▓▓▓▓▓▓▓█░` Γ  ║▓░` ╙▓▓▓▓▓▓▌     ╙╨▀▓▓▓▓▓▓▓▓▓█▓▓▓▓▓▓▓█████████\n" +
                    "    ░░░µ▄▓████████████▓▓▒▓▓▓▓▌`   ⌐  *╟Ω\"─ ╢▓▓▓▓▓M   .»-«≈╙▀▓▓▓▓▓▓▓▓█▓▓▓▓▓▓▓▓███████\n" +
                    "    ░▄███████████████▓▓▓▓▓Å█▓`    ,▄██▀▀█⌂  ▓▓▓▓▓    \"▄██▄▄ `╨▓▓▓▓▓▓█▓▓▓▓▓▓▓▓▓▓▓▓██▓\n" +
                    "    ████████████████▓▓▓▓▓▓▓▓▌    ▄█▀`▄█M.╙  ╙█▓▓M    ╙▐▄▄░▀█▄ ║▓▓▓▓▓██▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n" +
                    "    ▀║█████████████▓▓▓▓██▓▓▓▌   ║█` █████▄   `▓▌▌    ╓██▌▄µ╙█⌐ ▓▓▓▓▓██▓▓▓▓▓▓▓▓▓▓▓▓▓█\n" +
                    "    ░█████████████▓▓▓▓██▓▓▓▓█⌐  `╙  █████▌     \"`    █████▌ ╨  ▓▓▓▓▓█▓▓▓█▓▓▓▓▓█▓▓▓▓▓\n" +
                    "    ░█████▀██████▓▓▓▓██▓▓▓▓M¿*  ` » ╙▓██▀           `╙████ »   █▓▓▓██▓▓▓▓██▓▓▓▓▓█▓▓▓\n" +
                    "    ░██▀░░░░░░░▄▓▓▓▓▓█▓▓▓▓▌ ╫`     .  `.             `` ``    »▓█▓███▓▓▓▓▓███▓▓▓▓▓▓▓\n" +
                    "    ░▀░░░░░░░▄▓▓▓▓▓▓██▓▓▓▓ ╙w⌂.   ` `                  ''    ;`╙█████▓▓▓█▓▓███████▀╠\n" +
                    "    ░░░░░░░░▀█▓▓▓▓▓▓█▓▓▓▓██▄µ` \\      ╓▄⌂                    ╨ ╓▓███▓▓▓▓▓█▓▓▓╠╣░░░░░\n" +
                    "    ░░░µ▄▄▄▓▓███▓▓▓▓▓▓▓▓████████▌  ╓╓╓╓µ╓╦▄µ       ╓▄╦╥╥╓╓¿ ▄███▓██▓▓▓▓▓▓██▓▓▓████▓█\n" +
                    "    ▓████████████▓▓▓▓▓▓███████████▄╫╫╫▓██▓▓▓▓▓▓▓▓▓▓▓▓▓█▓╫╫▌▄████▓▓▓▓▓▓▓▓▓██▓▓███████\n" +
                    "    ██████████████▓▓▓▓████████████▒╫╫╫▓██▓▓▓██▓▓▓▓▓▓▓▓▓▌╫╫███████▓▓▓▓▓▓▓▓█▓▓████████\n" +
                    "    █████████████▓█▓▓▓████████████╫╫█▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▌╫╫██████▓▓▓▓▓▓▓▓██▓█████████\n" +
                    "    ░╠▀█████████████▓▓███████████▌╫▓███▓▓▓▓▓▓▓▓▓███▓▓▓▄╫╫╫╫████▓▓▓▓▓▓███████████████\n" +
                    "    ░░░░░╠╠██████████████████████╫╫╫╫▓█▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓╫╫▓╫██▓▓▀░░║███████████████\n" +
                    "    ░░░░░░░░╠▀███████████████╫██▌╫╫╫▓████▓▓▓▓█▓▓▓▀▀▀▀▀▓▓▓▓╫╫▓╫█▌░░░░╠███████████████\n" +
                    "    ░░░░░░░░░░╫▀█████████████▌▓█╫╫╫╫█████▓╫╫▀▀▀▀╫╫╫╫╫╫╫╫╫╫╫╫▓▓██░░░░░███████████████\n" +
                    "    ░░░░░░░░░╫╫╫╠▓█████████████▌╫╫╫╫╫╫╫╫▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓███M░░░░███████████████\n" +
                    "    ░░░░░░░░░░╠▓████▓▓█████████▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓███▌░░░░███████████████\n" +
                    "    ░░░░░░░░▄▓████▓▓▓▓▓▓▓██████▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▀▓▓█▓▓██████████░░░░▓██████████████\n" +
                    "    ░░░░░░▄▓████▓▓▓▓▓▓▓▓▓▓▓██╫▓██▓▓▓██████⌐     ,▄███▌╫██████████▒░░░║██████████████\n" +
                    "    ░░░░▄█████▓▓▓▓▓▓▓▓▓▓▓▓▓▓███████████████⌐   ▄██████╫██████████▒░░░░▀█████████▀▀▀▓ Powered by yUi")
        }
    }

}