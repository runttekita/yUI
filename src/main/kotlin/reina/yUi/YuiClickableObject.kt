package reina.yUi

import basemod.ClickableUIElement
import com.badlogic.gdx.graphics.Texture
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.input.InputHelper

abstract class YuiClickableObject(private var texture: Texture) : ClickableUIElement(texture) {
    private var inMoveMode = false
    init{

    }

    override fun updateHitbox() {
        super.updateHitbox()
        moveMode()
        if (HitboxRightClick.rightClicked.get(this) && Settings.isDebug)
            inMoveMode = true
    }

    private fun moveMode() {
        if (!inMoveMode) return;
        x = InputHelper.mX.toFloat()
        y = InputHelper.mY.toFloat();
        if (InputHelper.justClickedLeft)
            inMoveMode = false
    }

}