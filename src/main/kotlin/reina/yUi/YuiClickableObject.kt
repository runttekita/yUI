package reina.yUi

import basemod.ClickableUIElement
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick
import com.megacrit.cardcrawl.core.CardCrawlGame
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
 * Move Mode:
 *  Press L while hovering over your yUi Element in debug mode to make it snap to your mouse
 *  and display the x, y values you want to put into x/
 *  You may also use the arrow keys to nudge it ever so slightly.
 */
abstract class YuiClickableObject(private val texture: Texture, x: Float, y: Float) :
    ClickableUIElement(texture, x, y, texture.width.toFloat(), texture.height.toFloat()) {
    public var inMoveMode= false
    private val inputMove = InputAction(Input.Keys.L)
    private val inputUp = InputAction(Input.Keys.UP)
    private val inputRight = InputAction(Input.Keys.RIGHT)
    private val inputLeft = InputAction(Input.Keys.LEFT)
    private val inputDown = InputAction(Input.Keys.DOWN)
    private var waitTimer = 0.5f
    private var xValue = x / Settings.scale
    private var yValue = y / Settings.scale

    init {
        this.x = x
        this.y = y
        hitbox.x = x
        hitbox.y = y
    }

    override fun onHover() {
        if (inputMove.isJustPressed && Settings.isDebug) {
            inMoveMode = !inMoveMode;
            waitTimer = 0.5f
        }
    }

    override fun update() {
        super.update()
        moveMode()
        xValue = x / Settings.scale
        yValue = y / Settings.scale
    }

    private fun moveMode() {
        if (inMoveMode) {
            x = InputHelper.mX.toFloat()
            y = InputHelper.mY.toFloat()
            if (inputUp.isPressed) {
                y += 10 * Settings.scale
            }
            if (inputDown.isPressed) {
                y -= 10 * Settings.scale
            }
            if (inputRight.isPressed) {
                x += 10 * Settings.scale
            }
            if (inputLeft.isPressed) {
                x -= 10 * Settings.scale
            }
            hitbox.x = x
            hitbox.y = y
            waitTimer -= Gdx.graphics.deltaTime
            if (inputMove.isPressed && waitTimer < 0)
                inMoveMode = !inMoveMode
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