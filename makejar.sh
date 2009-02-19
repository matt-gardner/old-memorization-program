#!/bin/bash

# This is broken!  The code isn't written to handle reading files from and 
# writing files to jars.  So, the jar won't really work yet.  To be fixed.

mv bin/memorizing .
jar cfm Memorization\ Program.jar manifest memorizing memorizingfiles classes
mv memorizing bin/memorizing


