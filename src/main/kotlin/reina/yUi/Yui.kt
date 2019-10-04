package reina.yUi

import basemod.BaseMod
import basemod.interfaces.PostRenderSubscriber
import basemod.interfaces.PostUpdateSubscriber
import basemod.interfaces.RenderSubscriber
import com.badlogic.gdx.Input
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
import com.megacrit.cardcrawl.helpers.input.InputAction
import java.util.*

@SpireInitializer
class Yui() :
    RenderSubscriber {
    private var test: Test? = null
    private var test2: Test? = null
    init {
        BaseMod.subscribe(this)
    }

    override fun receiveRender(sb: SpriteBatch) {
        if (test == null)
            test = Test(Settings.WIDTH/2F, Settings.HEIGHT/2F)
        if (test2 == null)
            test2 = Test(0f, 0f)
        if (AbstractDungeon.player != null) {
            test!!.update()
            test!!.render(sb)
            test2!!.update()
            test2!!.render(sb)
            autoPlaceVertically(test!!, test2!!, 30f)
        }
    }

    class Test(x: Float, y: Float): YuiClickableObject(Texture("reina/yUi/images/journey.png"), x, y) {

        override fun onClick() {
        }

        override fun onUnhover() {
        }

    }


    companion object {
        public fun autoplaceHorizontally(anchorElement: YuiClickableObject, placedElement: YuiClickableObject) {
            val anchorX = anchorElement.getX()
            val anchorY = anchorElement.getY()
            placedElement.setX(anchorX + anchorElement.getWidth())
            placedElement.setY(anchorY)
        }

        public fun autoplaceHorizontally(anchorElement: YuiClickableObject, placedElement: YuiClickableObject, padding: Float) {
            val anchorX = anchorElement.getX()
            val anchorY = anchorElement.getY()
            placedElement.setX(anchorX + anchorElement.getWidth() + padding)
            placedElement.setY(anchorY)
        }

        public fun autoPlaceVertically(anchorElement: YuiClickableObject, placedElement: YuiClickableObject) {
            val anchorX = anchorElement.getX()
            val anchorY = anchorElement.getY()
            placedElement.setX(anchorX)
            placedElement.setY(anchorY - anchorElement.getHeight())
        }

        public fun autoPlaceVertically(anchorElement: YuiClickableObject, placedElement: YuiClickableObject, padding: Float) {
            val anchorX = anchorElement.getX()
            val anchorY = anchorElement.getY()
            placedElement.setX(anchorX)
            placedElement.setY(anchorY - anchorElement.getHeight() + padding)
        }

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