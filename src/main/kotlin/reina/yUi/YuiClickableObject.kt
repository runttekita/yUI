package reina.yUi

import basemod.ClickableUIElement
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.input.InputHelper

/**
 * @param texture The image you want to draw.
 * @param x The x value of where you want to draw it.
 * @param y The y value of where you want to draw it.
 *
 * Features:
 * Move Mode:
 *  Press J while hovering over your yUi Element in debug mode to make it snap to your mouse
 * Nudge Mode:
 *  Press K while hovering over your yUi element in debug mode to be able to press the arrow
 *  keys to slightly nudge your element.
 */
abstract class YuiClickableObject(private val texture: Texture, x: Float, y: Float) :
    ClickableUIElement(texture, x, y, texture.width.toFloat(), texture.height.toFloat()) {
    private var waitTimer = 0.5f
    private var xValue = x / Settings.scale
    private var yValue = y / Settings.scale

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
            if (Yui.inputMove.isJustPressed) {
                enterMode(Mode.MOVE)
                waitTimer = 0.5f
            }
            if (Yui.inputNudge.isJustPressed) {
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
            if (Yui.inputMove.isPressed && waitTimer < 0)
                Mode.MOVE.on = false
        }
    }

    private fun nudgeMode() {
        if (Mode.NUDGE.on) {
            moveHitboxes()
            if (Yui.inputUp.isPressed) {
                y += 1 * Settings.scale
            }
            if (Yui.inputDown.isPressed) {
                y -= 1 * Settings.scale
            }
            if (Yui.inputRight.isPressed) {
                x += 1 * Settings.scale
            }
            if (Yui.inputLeft.isPressed) {
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