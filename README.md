UI in STS is a pain dude.

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
 ## Print:
 
  Press P while hovering over your yUi element in debug mode to print coordinates and texture width and height to console.


# How To Use

Extend `YuiClickableObject` to make your own UI element. 

Read the documentation:

https://github.com/velvet-halation/yUI/blob/master/src/main/kotlin/reina/yUi/Yui.kt

https://github.com/velvet-halation/yUI/blob/master/src/main/kotlin/reina/yUi/YuiClickableObject.kt

## Build this yourself
```
$ git clone
$ cd yUi
$ mvn package
```
