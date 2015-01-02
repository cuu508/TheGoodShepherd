TheGoodShepherd
===============

An Android app that does a little bit of ad-hoc home automation. 
It does two things:

 * when phone is connected to external power, it sends Wake-on-LAN packet to hardcoded MAC address
 * when phone is disconnected from external power, it connects over SSH and shuts the machine down
 
Start Up
========

Startup is with Wake-on-LAN and is relatively easy: send a single specially crafted address. It's fire-and-forget, the app doesn't check if the PC actually booted or not. App needs to know IP and MAC of the target machine.

Shut Down
=========

The app connects to target machine using SSH. It then does "sudo shutdown -h now" and plays a short confirmation signal. For this, the app needs to know: IP of target machine, a key pair that would let it in, username and password of sudo-capable user on the target machine. 

Cool, now, WHY?
===============

My apartment happens to have an unused light switch and wiring right at the front door. Now add an AC socket, an AC charger, and finally a spare Android phone running this app. The light switch becomes a PC on/off switch. It's hacky, and it works.
