package reina.yui

import com.megacrit.cardcrawl.helpers.input.InputHelper

class SimpleYuiObject:
    YuiClickableObject
        (Yui.assetManager.getTexture("reina/yui/images/yui.jpg"), getMx(), getMy()) {

    override fun onUnhover() {
    }

    override fun onClick() {
    }
}

private fun getMx(): Float {
    return InputHelper.mX.toFloat()
}

private fun getMy(): Float {
    return InputHelper.mY.toFloat()
}