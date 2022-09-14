>## Threads & Lock
>
>The program is fully automated, run the App.java file in the THREADS folder. The App will go over all the threads and array sizes and sort them.
>
>Inside the App.java file
>
>Edit the N_THREADS array to change the number of threads
>
>edit the SIZES array to change the size of the integer array 

>## Nodes
>Without working servers this program will not work. So first the servers need to be setup and working. rent or buy a ubuntu server version 18.04 or above.
> 
> The program works inside ubuntu server with the apache2 http server 
> 
> having this dependency installed creates a folder in the location var/www/html from the root folder.
> 
> this is te place where the .jar file will be placed of the Server.java
>
>### Server
>run these commands inside the new server:
>
>``sudo apt update``
>
>``sudo apt install openjdk-11-jdk``
>
>``sudo apt install apache2``
>
>On your own dekstop inside the Server.java file located in the RMI folder. 
> 
> Change the HOSTNAME variable to the IP of your server. Compile the Server.java file to a .jar.
> 
> The code for the HOSTNAME variable.
> 
> ``final static String HOSTNAME = "95.179.133.188";``
>
>run this new .jar file in the command promt to test it with:
>
>``java -jar YOURFILE.jar``
>
>If and only if the .jar file from the compiled Server.java class is working AND the HOSTNAME variable is changed to your current server IP. 
>
>Then put the .jar file in the location: ``var/www/html``. 
>
>The run the file with:
>
>``java -jar YOURFILE.jar.``
>
>You can put the transfer files via FileZilla with your server that is how i did it.


>### Desktop
>Inside the nodes array in the client.java file that is located in the RMI folder. Change the nodes array holding the IP adressed of your servers. After this is done and your servers are working the app should work.
>
>Inside the client.java file
>
>Changing the ``N_NODES`` variable affects how many nodes will be used
>
>Changing the ``N_SIZE`` variable affects the array size.


