package de.hawk200014.serverdiscordnode;

import java.io.*;
import java.net.Socket;

public class SocketManager {
    private final Config config;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public SocketManager(Config config){
        this.config = config;
    }

    private boolean connect(){
        try {
            System.out.println("Starting Socket Connection");
            socket = new Socket(config.getControllerIP(), Integer.parseInt(config.getControllerPort()));
            System.out.println("Socket Connection established");
            return true;
        } catch (IOException | NumberFormatException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean initConnectionAndListener(){
        if(connect()){
            try {
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                System.out.println("Sending Servername: " + config.getServerName());
                sendMessage(config.getServerName());
                System.out.println("Sending Controller Secret");
                sendMessage(config.getControllerSecret());
                System.out.println("Listening for Messages");
                listenForMessages();
                return true;
            } catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
        return false;
    }

    public void listenForMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try{
                        ServerDiscordNodeServer.rp.processMessage(bufferedReader.readLine());
                    }
                    catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void sendMessage(String message){
        try{
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Servernode has disconnected");
    }
}
