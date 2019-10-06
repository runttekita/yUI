Adding in a GUI to make UI creation easier in Slay The Spire.

yUi doesnt stand for anything.

The name Yui just sounds like UI.

# Features:

 ## Coordinates:
 
  In debug mode, you yUi element will render its coordinates before being multiplied by Settings.scale
  
 ## File Mode:
 
  Press H while hovering over your yUi element in debug mode to open up your file browser.
  
  You can select any picture to replace your texture with.
  
  This mode exits automatically on file choose.
  
 ## Move Mode:
 
  Press J while hovering over your yUi Element in debug mode to make it snap to your mouse
  
 ## Nudge Mode:
 
  Press K while hovering over your yUi element in debug mode to be able to press the arrow
  
  keys to slightly nudge your element.
  
 ## Exit Mode:
 
  Press L while in Move or Nudge mode to exit mode.
  
 ## Autoplace
 
  You can call `autoPlaceHorizontally()` or `autoPlaceVertically()` to automatically place an element relative to another element.
  
  You can also pass those methods a Hitbox to position relative to those if you're trying to position to base game elements. 
  
 ## Print:
 
  Press P while hovering over your yUi element in debug mode to print coordinates and texture width and height to console.
  
  ## Spawn Yui
  
   Spawn a simple yUi element at your cursor in debug mode by pressing N.
   
  ## Delete:
  
  Press X while hovering over your yUi Element in debug mode to delete it.
 
 ## Priority:
 
   Press TAB while hovering over your yUi Element in debug mode to make it be rendered on top
   
 ## Deprioritize:
 
   Press L-SHIFT while hovering over your yUi Element in debug mode to make it be render on the bottom
  

# How To Use

Extend `YuiClickableObject` to make your own UI element. 

To render your yUi element call `Yui.Companion.add()` and pass it an instance of your element.
All the various methods you can use on your element can be found in [Yui.kt](https://github.com/velvet-halation/yUI/blob/master/src/main/kotlin/reina/yUi/Yui.kt#L134) in the `companion object`

Read the documentation:

https://github.com/velvet-halation/yUI/blob/master/src/main/kotlin/reina/yui/Yui.kt

https://github.com/velvet-halation/yUI/blob/master/src/main/kotlin/reina/yui/YuiClickableObject.kt

An example Yui element:

https://github.com/velvet-halation/yUI/blob/master/src/main/kotlin/reina/yui/SimpleYuiObject.kt

## Build this yourself
```
$ git clone
$ cd yUi
$ mvn package
```
