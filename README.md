# Shaking-2048
This is an Android Game like 2048, but shake your phone to win the game!
This is a project I did with other two students Yuhan and Josip. In the app, you can move the blocks by sawying your phone to
the up, down, left and right directions. We used accelerometer to collect the raw data and developed a filter algorithm to 
minimize the noise (and other factors) so that the app can recognize user's gestures properly. Although we tried our best to 
attenuate the ''noise'', the app can only reach 97% accuracy to recognize users gestures, since there are 3 axes in real-life
(We live in 3-dimension world!), so app is rarely mistaken which axis user is moving on when crazily swaying :Lol
I am currently into this algorithm and trying to optimize it to make it perfectly recognize gestures. However, any insighs
crazy ideas are welcome! I would like to talk to you about any changes you may want to do on this app!

This is a prototype app, we did the limit of the game to 256 instead of 1024, for the theroy at the back is the same, we would 
finish this in the future and optimize the display and UI. We are currently using absolute pixels, this may cause display 
ambiguity，our next step is using responsive UI instead of absolute design so that our app can fit different screens on diverse
devices, not only phones but tablets!!

We uploaded all the source code to GitHub, to play the Game, you can download the whole thing into your Android Studio and run
on your Android devices!

Enjoy!
