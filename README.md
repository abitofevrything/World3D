# World3D

World3D is a java game engine based on the LWJGL game library.
It provides simple functionality for rendering, input, audio and events.

For any problems or questions, contact Abitofevrything#1422 on Discord or submit an issue / pull request on github.

##Running the engine
The main class for interacting with the engine is <code>me.abitofevrything.world3d.World3D</code>.

Calling <code>World3D.init()</code> will start the engine, and calling <code>World3D.update()</code> will update and close the engine if necessary.
Please note that <code>World3D.exit()</code> will terminate the process if the users requests it, so any cleanup code should be implemented as such :
```
new GameCloseEventListener() {

	@Override
	public void onEvent(GameCloseEvent event) {
		//Code to run here
	}

}.listen();
```

THe above code will be run when the user requests an engine close.