# Sample Code

A couple of projects done previously, one written in Kotlin that uses GPS, another written in Java that uses a REST API.

## MQTT Geofence

This project uses GPS and a fairly short update interval to keep track of where a user is and keep track if they are inside of or outside of a predefined Geofence.  This uses the MQTT protocol to connect to a a server and push updates to clients when the fence boundary gets crossed along with the direction the user is headed (into the fence or out of).  In this particular case, the only listener was the Android device doing the sending so the device itself got the updates.  The server was running on a home desktop so that part won't work unless you install it and update the IP being used.  Server side code has not been included but can be if you want to play.

## ScoreKeeper

This app uses a simple GUI to keep score and then pushes the updates to a REST API (this was done in Azure and may still be up).  It also pulls games down from the server using the same API.  There are a number of similar apps out there but most do stupid things like change the score when you shake the phone, use swiping etc. all of which are very unhandy when you are coaching a game and wanting to quicly update the score without adding 5 points.