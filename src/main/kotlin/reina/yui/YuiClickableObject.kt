package reina.yui

import basemod.ClickableUIElement
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.Hitbox
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
 * Delete:
 *  Press X while hovering over your yUi Element in debug mode to delete it.
 * Priority:
 *   Press TAB while hovering over your yUi Element in debug mode to make it be rendered on top
 * Deprioritize:
 *   Press L-SHIFT while hovering over your yUi Element in debug mode to make it be rendered on the bottom
 */
abstract class YuiClickableObject(private val texture: Texture?, x: Float, y: Float) :
    ClickableUIElement(texture, x, y, texture!!.width.toFloat(), texture.height.toFloat()) {
    private val inputFile = InputAction(Input.Keys.H)
    private val inputMove = InputAction(Input.Keys.J)
    private val inputNudge = InputAction(Input.Keys.K)
    private val inputExit = InputAction(Input.Keys.L)
    private val inputUp = InputAction(Input.Keys.UP)
    private val inputRight = InputAction(Input.Keys.RIGHT)
    private val inputLeft = InputAction(Input.Keys.LEFT)
    private val inputDown = InputAction(Input.Keys.DOWN)
    private val inputPrint = InputAction(Input.Keys.P)
    private val inputPriority = InputAction(Input.Keys.TAB)
    private val inputDeprioritize = InputAction(Input.Keys.SHIFT_LEFT)
    private val inputDelete = InputAction(Input.Keys.X)
    protected var xValue = x / Settings.scale
    protected var yValue = y / Settings.scale
    private var currentMode: Mode = Mode.NONE

    public fun getX(): Float {
        return x
    }

    public fun getY(): Float {
        return y
    }

    public open fun getWidth(): Float {
        return image.width.toFloat() * Settings.scale
    }

    public open fun getHeight(): Float {
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
        NUDGE(false),
        NONE(false)
    }

    private fun enterMode(enteredMode: Mode) {
        this.currentMode = enteredMode
    }

    override fun onHover() {
        if (Settings.isDebug) {
            if (inputPriority.isJustPressed) {
                if (Yui.isRegular(this)) {
                    Yui.prioritize(this)
                } else if (Yui.isPost(this)) {
                    Yui.prioritizePost(this)
                }
            }
            if (inputDeprioritize.isJustPressed) {
                if (Yui.isRegular(this)) {
                    Yui.deprioritize(this)
                } else if (Yui.isPost(this)) {
                    Yui.deprioritizePost(this)
                }
            }
            if (inputDelete.isJustPressed) {
                if (Yui.isRegular(this)) {
                    Yui.remove(this)
                } else if (Yui.isPost(this)) {
                    Yui.removePost(this)
                }
            }
            if (inputMove.isJustPressed) {
                enterMode(Mode.MOVE)
            }
            if (inputNudge.isJustPressed) {
                enterMode(Mode.NUDGE)
            }
            if (inputFile.isJustPressed) {
                enterMode(Mode.FILE)
                if (this.currentMode == Mode.FILE) {
                    val fc = JFileChooser();
                    val returnVal = fc.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        val file = fc.selectedFile
                        image = Yui.assetManager.getTexture(file.absolutePath)
                        hb_w = image.width.toFloat()
                        hb_h = image.width.toFloat()
                        hitbox = Hitbox(x, y, hb_w, hb_h)
                        currentMode == Mode.NONE
                    }
                }
            }
            if (inputPrint.isJustPressed) {
                xValue = x / Settings.scale
                yValue = y / Settings.scale
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
        if (inputExit.isJustPressed) {
            this.currentMode = Mode.NONE
        }
        moveMode()
        nudgeMode()
        xValue = x / Settings.scale
        yValue = y / Settings.scale
    }

    private fun moveHitboxes() {
        hitbox.x = x
        hitbox.y = y
    }

    private fun moveMode() {
        if (this.currentMode == Mode.MOVE) {
            x = InputHelper.mX.toFloat()
            y = InputHelper.mY.toFloat()
            moveHitboxes()
        }
    }

    private fun nudgeMode() {
        if (this.currentMode == Mode.NUDGE) {
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

    override fun render(sb: SpriteBatch) {
        super.render(sb)
        if (Settings.isDebug) {
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, "x: $xValue", x, y)
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, "y: $yValue", x, y - 50 * Settings.scale)
        }
    }

}