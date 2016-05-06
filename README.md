# mini-project
A simple LAN game completely written in java

This project has been done as a mini-project for semester 6.The project consists of a simple game which is written 
completely in java.The game consists of four players where each of them would start from four corners of grid.Grid in the game is
much similar to grid we have seen in minesweeper instead all of the numbers will be shown to players.Each player will able to track
down all the moves of the other player in the same grid.Every player should compete each other to reach the center of the grid
illuminated in yellow color first and become the winner.However there will be mines hidden on grid which can be found by analysing the numbers on the grid as in same fashion we do for minesweeper.Whenever a player steps on mine he will be given 10 
second hault penalty from others.

Project focusses on the core concepts of Networking,Concurrency in programming.We have used UDP as our networking protocol.
Project uses server-client as its networking model in which server hosts a network,to which all the clients join and play the game.

----WORKING----

Basically the server maintains a simple array containing x and y cords of 4 players.Whenever a player(client) makes a move by arrow keys hes going to send a request to server to move to a specific destiation,server then accepts the request and updates the current position array and sends the array to all the clients who are connected with the server.Clients maintains 2 threads to  send requests and recieve updates.Server will always be listening to connected clients and also plays as a client in the game.
Network should be hosted by player who will become the server.
Project was done inspired from the well known android app MiniMilitia.The project is still in its testing phase.

