package reina.yUi

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.FontHelper

class YuiCard(private var card: AbstractCard, x: Float, y: Float, private var isSmall: Boolean) :
    YuiClickableObject(Yui.assetManager.getTexture("reina/yUi/images/uwu.png"), x, y) {

    override fun getWidth(): Float {
        return if (isSmall) {
            AbstractCard.IMG_WIDTH_S
        } else {
            AbstractCard.IMG_WIDTH
        }
    }


    override fun getHeight(): Float {
        return if (isSmall) {
            AbstractCard.IMG_HEIGHT_S
        } else {
            AbstractCard.IMG_HEIGHT
        }
    }

    override fun onUnhover() {
    }

    override fun onClick() {
    }

    override fun render(sb: SpriteBatch) {
        card.render(sb)
        if (Settings.isDebug) {
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontPurple, "x: $xValue", x, y)
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontPurple, "y: $yValue", x, y - 50 * Settings.scale)
        }
    }

    override fun update() {
        card.current_x = x
        card.current_y = y
        card.update()
    }

}