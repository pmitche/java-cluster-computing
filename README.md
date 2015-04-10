# CS290B Homework 1: Computer server

Kyrre Moe, Ingeborg Oftedal, Hallvard Christensen and Paul Mitchell  
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
```<property name="ip" value="ip-goes-here"/>```  

**Experimental results elapsed time in ms:**

Euclidean TSP (same machine different JVM):

91,
97,
85,
90,
93

Mandelbrot set (same machine different JVM):

418,
540,
538,
415,
521

