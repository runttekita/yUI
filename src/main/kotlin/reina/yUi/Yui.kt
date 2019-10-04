package reina.yUi

import basemod.BaseMod
import basemod.interfaces.PostRenderSubscriber
import basemod.interfaces.PostUpdateSubscriber
import basemod.interfaces.RenderSubscriber
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.blue.Strike_Blue
import com.megacrit.cardcrawl.cards.green.Strike_Green
import com.megacrit.cardcrawl.cards.red.Strike_Red
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.input.InputAction
import java.util.*

@SpireInitializer
class Yui() :
    RenderSubscriber {
    var cardOne: YuiCard? = null
    var cardTwo: YuiCard? = null
    var cardThree: YuiCard? = null

    init {
        BaseMod.subscribe(this)
    }

    override fun receiveRender(sb: SpriteBatch) {
        if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null) {
            if (cardOne == null) {
                cardOne = YuiCard(Strike_Red().makeStatEquivalentCopy(), Settings.WIDTH / 2f, Settings.HEIGHT / 2f, false)
            }
            if (cardTwo == null) {
                cardTwo = YuiCard(Strike_Green().makeStatEquivalentCopy(), Settings.WIDTH / 2f, Settings.HEIGHT / 2f, false)
            }
            if (cardThree == null) {
                cardThree = YuiCard(Strike_Blue().makeStatEquivalentCopy(), Settings.WIDTH / 2f, Settings.HEIGHT / 2f, false)
            }
            autoPlaceHorizontallyWithVerticalOffset(cardOne!!, cardTwo!!, 50f)
            autoPlaceVerticallyWithHorizontalOffset(cardTwo!!, cardThree!!, 50f)
            cardOne!!.render(sb)
            cardThree!!.render(sb)
            cardTwo!!.render(sb)
            cardOne!!.update()
            cardTwo!!.update()
            cardThree!!.update()
        }
    }

    companion object {
        /**
         * @param anchorElement The element you want to be placed nearby
         * @param placedElement The element being moved
         *
         * This places placedElement to the *right* of the anchor element.
         */
        public fun autoPlaceHorizontally(anchorElement: YuiClickableObject, placedElement: YuiClickableObject) {
            val anchorX = anchorElement.getX()
            val anchorY = anchorElement.getY()
            placedElement.setX(anchorX + anchorElement.getWidth())
            placedElement.setY(anchorY)
        }

        /**
         * @param padding The amount of space between anchorElement and placedElement
         */
        public fun autoPlaceHorizontally(anchorElement: YuiClickableObject, placedElement: YuiClickableObject, padding: Float) {
            val anchorX = anchorElement.getX()
            val anchorY = anchorElement.getY()
            placedElement.setX(anchorX + anchorElement.getWidth() + padding)
            placedElement.setY(anchorY)
        }

        /**
         * This places placedElement *above* the anchorElement.
         * So if you're making a big complex relationship of yUI elements,
         * work from the bottom-left and expand out from there.
         */
        public fun autoPlaceVertically
                    (anchorElement: YuiClickableObject, placedElement: YuiClickableObject) {
            val anchorX = anchorElement.getX()
            val anchorY = anchorElement.getY()
            placedElement.setX(anchorX)
            placedElement.setY(anchorY + anchorElement.getHeight())
        }

        public fun autoPlaceVertically
                    (anchorElement: YuiClickableObject, placedElement: YuiClickableObject, padding: Float) {
            val anchorX = anchorElement.getX()
            val anchorY = anchorElement.getY()
            placedElement.setX(anchorX)
            placedElement.setY(anchorY + anchorElement.getHeight() + padding)
        }

        /**
         * @param offset Places placedElement above anchorElement then moves it to the right
         * by your offset
         */
        public fun autoPlaceVerticallyWithHorizontalOffset
                    (anchorElement: YuiClickableObject, placedElement: YuiClickableObject, offset: Float) {
            val anchorX = anchorElement.getX()
            val anchorY = anchorElement.getY()
            placedElement.setX(anchorX + offset)
            placedElement.setY(anchorY + anchorElement.getHeight())
        }

        /**
         * Same as above but places the placedElement to the right then moves it up
         * by your offset
         */
        public fun autoPlaceHorizontallyWithVerticalOffset
                    (anchorElement: YuiClickableObject, placedElement: YuiClickableObject, offset: Float) {
            val anchorX = anchorElement.getX()
            val anchorY = anchorElement.getY()
            placedElement.setX(anchorX + anchorElement.getWidth())
            placedElement.setY(anchorY + offset)
        }

        @JvmStatic
        fun initialize() {
            Yui()
        }

        public val assetManager = AssetLoader()

        class AssetLoader {
            private val assets = AssetManager()

            fun getTexture(fileName: String): Texture {
                if (!assets.isLoaded(fileName, Texture::class.java)) {
                    val param = TextureLoader.TextureParameter()
                    param.minFilter = Texture.TextureFilter.Linear
                    param.magFilter = Texture.TextureFilter.Linear
                    assets.load(fileName, Texture::class.java, param)
                    try {
                        assets.finishLoadingAsset(fileName)
                    } catch (e: GdxRuntimeException) {
                        println("Cant find texture???")
                    }

                }
                return assets.get(fileName, Texture::class.java)
            }

            fun loadAtlas(fileName: String): TextureAtlas {
                if (!assets.isLoaded(fileName, TextureAtlas::class.java)) {
                    assets.load(fileName, TextureAtlas::class.java)
                    assets.finishLoadingAsset(fileName)
                }
                return assets.get(fileName, TextureAtlas::class.java)
            }
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