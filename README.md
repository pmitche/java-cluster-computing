# CS290B Homework 1: Computer server

Kyrre Laugerud Moe, Ingeborg Ødegård Oftedal, Hallvard Jore Christensen and Paul Philip Mitchell
University of California, Santa Barbara
Spring 2015

**Build:**
cd HW1
ant clean
ant

**Run:**
ant runComputer (on server)
ant runClient (on client)

**Change IP:**
By default, the client will connect to localhost. To connect to a different domain, modify "build.xml" on line 8:
<property name="ip"  value="ip-goes-here"/>
