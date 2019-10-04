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

abstract class YuiClickableObject(private val texture: Texture, x: Float, y: Float) :
    ClickableUIElement(texture, x, y, texture.width.toFloat(), texture.height.toFloat()) {
    public var inMoveMode= false
    private val inputMove = InputAction(Input.Keys.Q)
    private var waitTimer = 0.5f
    private var xValue = x / Settings.scale
    private var yValue = x / Settings.scale

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
    }

    private fun moveMode() {
        if (inMoveMode) {
            x = InputHelper.mX.toFloat()
            y = InputHelper.mY.toFloat()
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
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontPurple, "y: $yValue", x, y + 50 * Settings.scale)
        }
    }

}