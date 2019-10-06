package reina.yui

import basemod.BaseMod
import basemod.ReflectionHacks
import basemod.interfaces.*
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.GdxRuntimeException
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.Hitbox
import com.megacrit.cardcrawl.helpers.input.InputAction
import com.megacrit.cardcrawl.ui.panels.DiscardPilePanel
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

@SpireInitializer
class Yui() :
    RenderSubscriber,
    PostRenderSubscriber,
    PostInitializeSubscriber,
    PreUpdateSubscriber,
    PostUpdateSubscriber {

    private var inputSpawnYui: InputAction? = null

    init {
        BaseMod.subscribe(this)
    }

    override fun receivePostInitialize() {
        inputSpawnYui = InputAction(Input.Keys.N)
    }

    /**
     * Press N in debug mode to spawn a Simple Yui Object at your mouse location.
     */
    override fun receiveRender(sb: SpriteBatch) {
        if (AbstractDungeon.player != null && inputSpawnYui!!.isJustPressed) {
            add(SimpleYuiObject())
        }
        for (yui in listOfYui) {
            yui.render(sb)
        }
    }

    /**
     * Renders over everything
     */
    override fun receivePostRender(sb: SpriteBatch) {
        for (yui in listOfPostYui) {
            yui.render(sb)
        }
    }

    /**
     * Boring behind the scenes library stuff
     */
    override fun receivePreUpdate() {
        for (yui in listOfYui) {
            yui.update()
        }
        for (yui in toPrioritize) {
            listOfYui.remove(yui)
            listOfYui.add(yui)
        }
        toPrioritize.clear()
        for (yui in toDeprioritize) {
            listOfYui.remove(yui)
            listOfYui.add(0, yui)
        }
        toDeprioritize.clear()
        for (yui in yuiToAdd) {
            listOfYui.add(yui)
        }
        yuiToAdd.clear()
        for (yui in yuiToRemove) {
            listOfYui.remove(yui)
        }
        yuiToRemove.clear()
    }

    /**
     * Same as above
     */
    override fun receivePostUpdate() {
        for (yui in listOfPostYui) {
            yui.update()
        }
        for (yui in toPrioritizePost) {
            listOfPostYui.remove(yui)
            listOfPostYui.add(yui)
        }
        toPrioritizePost.clear()
        for (yui in toDeprioritizePost) {
            listOfPostYui.remove(yui)
            listOfPostYui.add(0, yui)
        }
        toDeprioritizePost.clear()
        for (yui in yuiToAddPost) {
            listOfPostYui.add(yui)
        }
        yuiToAddPost.clear()
        for (yui in yuiToRemovePost) {
            listOfPostYui.remove(yui)
        }
        yuiToRemovePost.clear()
    }

    companion object {
        private var listOfYui = ArrayList<YuiClickableObject>()
        private var listOfPostYui = ArrayList<YuiClickableObject>()
        private var toPrioritize = ArrayList<YuiClickableObject>()
        private var toDeprioritize = ArrayList<YuiClickableObject>()
        private var toPrioritizePost = ArrayList<YuiClickableObject>()
        private var toDeprioritizePost = ArrayList<YuiClickableObject>()
        private var yuiToRemove = ArrayList<YuiClickableObject>()
        private var yuiToRemovePost = ArrayList<YuiClickableObject>()
        private var yuiToAdd = ArrayList<YuiClickableObject>()
        private var yuiToAddPost = ArrayList<YuiClickableObject>()
        private val discardWidth = ReflectionHacks.getPrivateStatic(DiscardPilePanel::class.java, "HITBOX_W") as Float
        private val discardHeight = ReflectionHacks.getPrivateStatic(DiscardPilePanel::class.java, "HITBOX_W") as Float
        private val drawWidth = ReflectionHacks.getPrivateStatic(DrawPilePanel::class.java, "HITBOX_W") as Float
        private val drawHeight = ReflectionHacks.getPrivateStatic(DrawPilePanel::class.java, "HITBOX_W") as Float
        private val discardX = CardGroup.DISCARD_PILE_X
        private val discardY = CardGroup.DISCARD_PILE_Y
        private val drawX = CardGroup.DRAW_PILE_X
        private val drawY = CardGroup.DRAW_PILE_Y

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

        public fun autoPlaceHorizontallyHitbox(anchorElement: Hitbox, placedElement: YuiClickableObject) {
            placedElement.setX(anchorElement.x + anchorElement.width)
            placedElement.setY(anchorElement.y)
        }

        public fun autoPlaceHorizontallyHitbox(anchorElement: Hitbox, placedElement: YuiClickableObject, padding: Float) {
            placedElement.setX(anchorElement.x + anchorElement.width + padding)
            placedElement.setY(anchorElement.y)
        }

        public fun autoPlaceHorizontallyHitboxWithVerticalOffset(anchorElement: Hitbox, placedElement: YuiClickableObject, offset: Float) {
            placedElement.setX(anchorElement.x + anchorElement.width)
            placedElement.setY(anchorElement.y + offset)
        }

        public fun autoPlaceVerticallyHitbox(anchorElement: Hitbox, placedElement: YuiClickableObject) {
            placedElement.setX(anchorElement.x)
            placedElement.setY(anchorElement.y + anchorElement.height)
        }

        public fun autoPlaceVerticallyHitbox(anchorElement: Hitbox, placedElement: YuiClickableObject, padding: Float) {
            placedElement.setX(anchorElement.x)
            placedElement.setY(anchorElement.y + anchorElement.height + padding)
        }

        public fun autoPlaceVerticallyHitboxWithHorizontalOffset(anchorElement: Hitbox, placedElement: YuiClickableObject, offset: Float) {
            placedElement.setX(anchorElement.x + offset)
            placedElement.setY(anchorElement.y + anchorElement.height)
        }

        /**
         * These methods are all for automatically rendering and updating your yUi elements
         * So you don't have to
         * You can also change the priority or the order in which things render.
         */
        public fun add(yuiElement: YuiClickableObject) {
            yuiToAdd.add((yuiElement))
        }

        public fun remove(yuiElement: YuiClickableObject) {
            yuiToRemove.add(yuiElement)
        }

        public fun prioritize(yuiElement: YuiClickableObject) {
            toPrioritize.add(yuiElement)
        }

        public fun deprioritize(yuiElement: YuiClickableObject) {
            toDeprioritize.add(yuiElement)
        }

        public fun addPost(yuiElement: YuiClickableObject) {
            yuiToAddPost.add(yuiElement)
        }

        public fun removePost(yuiElement: YuiClickableObject) {
            yuiToRemovePost.add(yuiElement)
        }

        public fun prioritizePost(yuiElement: YuiClickableObject) {
            toPrioritizePost.add(yuiElement)
        }

        public fun deprioritizePost(yuiElement: YuiClickableObject) {
            toDeprioritizePost.add(yuiElement)
        }

        public fun isRegular(yuiElement: YuiClickableObject): Boolean {
            return listOfYui.contains(yuiElement)
        }

        public fun isPost(yuiElement: YuiClickableObject): Boolean {
            return listOfPostYui.contains(yuiElement)
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