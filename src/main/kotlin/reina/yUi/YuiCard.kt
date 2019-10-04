package reina.yUi

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.cards.AbstractCard

class YuiCard(private var card: AbstractCard, x: Float, y: Float) :
    YuiClickableObject(null, x, y) {
    
    override fun onUnhover() {
    }

    override fun onClick() {
    }

    override fun render(sb: SpriteBatch) {
        super.render(sb)
        card.render(sb)
    }

    override fun update() {
        card.current_x = x
        card.current_y = y
        card.update()
    }

}