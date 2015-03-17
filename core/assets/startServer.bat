::Can be called by Java to load JS in the background and start the
::drone hardware console first (which is a must for it to work) -- 
::final path can be fixed relative to the location where we package
::the files as we near project completion


::Run Once, Leave Command Console Open
	::cd %~dp0\..\..\node_modules\drone
	::cmd.exe /K "node drone 0"

::Use this for final application
::Run Once, Close When JS Closes
	cd %~dp0\droneHardware\node_modules\drone
	node drone 0
	exit

::Run Once, Pause Before Closing Completely
	::cd %~dp0\..\..\node_modules\drone
	::node drone 0
	::pause
	
