package reina.yUi

import basemod.ClickableUIElement
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.input.InputHelper

abstract class YuiClickableObject(private val texture: Texture, x: Float, y: Float) :
    ClickableUIElement(texture, x, y, texture.width.toFloat(), texture.height.toFloat()) {
    private var inMoveMode: Boolean = false

    init {
        this.x = x
        this.y = y
    }

    override fun updateHitbox() {
        super.updateHitbox()
        moveMode()
        if (HitboxRightClick.rightClicked.get(this.hitbox) && Settings.isDebug)
            inMoveMode = true
    }

    private fun moveMode() {
        if (!inMoveMode) return;
        x = InputHelper.mX.toFloat()
        y = InputHelper.mY.toFloat();
        if (InputHelper.justClickedLeft)
            inMoveMode = false
    }

    override fun render(sb: SpriteBatch?) {
        super.render(sb)
        if (Settings.isDebug)
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontPurple, x.toString(),
                texture.width.toFloat() / Settings.scale, texture.height.toFloat() / Settings.scale)
    }

}