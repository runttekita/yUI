package reina.yUi

import basemod.ClickableUIElement
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.input.InputAction
import com.megacrit.cardcrawl.helpers.input.InputHelper
import javax.swing.JFileChooser

/**
 * @param texture The image you want to draw.
 * @param x The x value of where you want to draw it.
 * @param y The y value of where you want to draw it.
 *
 * Features:
 * Coordinates:
 *  In debug mode, you yUi element will render its coordinates before being multiplied by Settings.scale
 * File Mode:
 *  Press H while hovering over your yUi element in debug mode to open up your file browser.
 *  You can select any picture to replace your texture with.
 *  This mode exits automatically on file choose.
 * Move Mode:
 *  Press J while hovering over your yUi Element in debug mode to make it snap to your mouse
 * Nudge Mode:
 *  Press K while hovering over your yUi element in debug mode to be able to press the arrow
 *  keys to slightly nudge your element.
 * Exit Mode:
 *  Press L while in Move or Nudge mode to exit mode.
 * Print:
 *  Prints coordinates and texture width and height to console.
 */
abstract class YuiClickableObject(private val texture: Texture, x: Float, y: Float) :
    ClickableUIElement(texture, x, y, texture.width.toFloat(), texture.height.toFloat()) {
    private val inputFile = InputAction(Input.Keys.H)
    private val inputMove = InputAction(Input.Keys.J)
    private val inputNudge = InputAction(Input.Keys.K)
    private val inputExit = InputAction(Input.Keys.L)
    private val inputUp = InputAction(Input.Keys.UP)
    private val inputRight = InputAction(Input.Keys.RIGHT)
    private val inputLeft = InputAction(Input.Keys.LEFT)
    private val inputDown = InputAction(Input.Keys.DOWN)
    private val inputPrint = InputAction(Input.Keys.P)
    private var xValue = x / Settings.scale
    private var yValue = y / Settings.scale

    public fun getX(): Float {
        return x
    }

    public fun getY(): Float {
        return y
    }

    public fun getWidth(): Float {
        return image.width.toFloat() * Settings.scale
    }

    public fun getHeight(): Float {
        return image.height.toFloat() * Settings.scale
    }

    init {
        this.x = x
        this.y = y
        hitbox.x = x
        hitbox.y = y
    }

    enum class Mode(var on: Boolean) {
        FILE(false),
        MOVE(false),
        NUDGE(false)
    }

    private fun enterMode(enteredMode: Mode) {
        Mode.values().forEach {
            if (it != enteredMode) {
                it.on = false
            }
        }
        enteredMode.on = true
    }

    override fun onHover() {
        if (Settings.isDebug) {
            if (inputMove.isJustPressed) {
                enterMode(Mode.MOVE)
            }
            if (inputNudge.isJustPressed) {
                enterMode(Mode.NUDGE)
            }
            if (inputFile.isJustPressed) {
                enterMode(Mode.FILE)
                val fc = JFileChooser();
                val returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    val file = fc.selectedFile
                    image = Yui.assetManager.getTexture(file.absolutePath)
                    hb_w = image.width.toFloat()
                    hb_h = image.width.toFloat()
                    hitbox.update()
                    Mode.FILE.on = false
                }
            }
            if (inputPrint.isJustPressed) {
                println("" +
                        "x: $xValue\n" +
                        "y: $yValue\n" +
                        "width: ${image.width}\n" +
                        "height: ${image.height}"
                )
            }
        }


    }

    override fun update() {
        super.update()
        moveMode()
        nudgeMode()
        if (inputExit.isJustPressed) {
            Mode.values().forEach { it.on = false }
        }
        xValue = x / Settings.scale
        yValue = y / Settings.scale
    }

    private fun moveHitboxes() {
        hitbox.x = x
        hitbox.y = y
    }

    private fun moveMode() {
        if (Mode.MOVE.on) {
            x = InputHelper.mX.toFloat()
            y = InputHelper.mY.toFloat()
            moveHitboxes()
        }
    }

    private fun nudgeMode() {
        if (Mode.NUDGE.on) {
            moveHitboxes()
            if (inputUp.isPressed) {
                y += 1 * Settings.scale
            }
            if (inputDown.isPressed) {
                y -= 1 * Settings.scale
            }
            if (inputRight.isPressed) {
                x += 1 * Settings.scale
            }
            if (inputLeft.isPressed) {
                x -= 1 * Settings.scale
            }
        }
    }

    override fun render(sb: SpriteBatch?) {
        super.render(sb)
        if (Settings.isDebug) {
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontPurple, "x: $xValue", x, y)
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontPurple, "y: $yValue", x, y - 50 * Settings.scale)
        }
    }

}