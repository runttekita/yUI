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

/**
 * @param texture The image you want to draw.
 * @param x The x value of where you want to draw it.
 * @param y The y value of where you want to draw it.
 *
 * Features:
 * Coordinates:
 *  In debug mode, you yUi element will render its coordinates before being multiplied by Settings.scale
 * Move Mode:
 *  Press J while hovering over your yUi Element in debug mode to make it snap to your mouse
 * Nudge Mode:
 *  Press K while hovering over your yUi element in debug mode to be able to press the arrow
 *  keys to slightly nudge your element.
 */
abstract class YuiClickableObject(private val texture: Texture, x: Float, y: Float) :
    ClickableUIElement(texture, x, y, texture.width.toFloat(), texture.height.toFloat()) {
    public val inputMove = InputAction(Input.Keys.J)
    public val inputNudge = InputAction(Input.Keys.K)
    public val inputUp = InputAction(Input.Keys.UP)
    public val inputRight = InputAction(Input.Keys.RIGHT)
    public val inputLeft = InputAction(Input.Keys.LEFT)
    public val inputDown = InputAction(Input.Keys.DOWN)
    private var waitTimer = 0.5f
    private var xValue = x / Settings.scale
    private var yValue = y / Settings.scale

    public fun getX(): Float {
        return x / Settings.scale
    }

    public fun getY(): Float {
        return y / Settings.scale
    }

    public fun getWidth(): Float {
        return texture.width.toFloat()
    }

    public fun getHeight(): Float {
        return texture.height.toFloat()
    }

    init {
        this.x = x
        this.y = y
        hitbox.x = x
        hitbox.y = y
    }

    enum class Mode(var on: Boolean) {
        MOVE(false),
        NUDGE(false)
    }

    private fun enterMode(enteredMode: Mode) {
        Mode.values().forEach { it.on = false }
        enteredMode.on = true
    }

    override fun onHover() {
        if (Settings.isDebug) {
            if (inputMove.isJustPressed) {
                waitTimer = 0.5f
                enterMode(Mode.MOVE)
            }
            if (inputNudge.isJustPressed) {
                enterMode(Mode.NUDGE)
            }
        }


    }

    override fun update() {
        super.update()
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
        if (Mode.MOVE.on) {
            x = InputHelper.mX.toFloat()
            y = InputHelper.mY.toFloat()
            moveHitboxes()
            waitTimer -= Gdx.graphics.deltaTime
            if (inputMove.isPressed && waitTimer < 0)
                Mode.MOVE.on = false
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