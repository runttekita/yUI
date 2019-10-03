package reina.yUi

import basemod.BaseMod
import basemod.interfaces.PostInitializeSubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer

@SpireInitializer
class Yui() :
    PostInitializeSubscriber{

    init {
        BaseMod.subscribe(this)
        println("dab")
    }

    companion object {
        @JvmStatic
        fun initialize() {
            Yui()
        }
    }

    override fun receivePostInitialize() {
    }

}