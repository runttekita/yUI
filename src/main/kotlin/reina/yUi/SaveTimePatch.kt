package reina.yUi

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton
import com.megacrit.cardcrawl.screens.splash.SplashScreen

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

@SpirePatch(clz = SplashScreen::class, method = "update")
object SaveTimePatch {
    @JvmStatic
    fun Prefix(__instance: SplashScreen): SpireReturn<*> {
        __instance.isDone = true
        return SpireReturn.Return<Any>(null)
    }

    @SpirePatch(clz = MenuButton::class, method = "update")
    object fkmainmenu {
        @JvmStatic
        fun Prefix(__instance: MenuButton) {
            if (__instance.result == MenuButton.ClickResult.RESUME_GAME) {
                CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.NONE
                CardCrawlGame.mainMenuScreen.hideMenuButtons()
                CardCrawlGame.mainMenuScreen.darken()
                try {
                    val resumeGame = MenuButton::class.java.getDeclaredMethod("resumeGame")
                    resumeGame.isAccessible = true
                    resumeGame.invoke(__instance)
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }

            }
        }
    }
}
