import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;



public class Server implements Protocol
{
   public static void main(String[] args) throws IOException
   {
      ServerSocket server = new ServerSocket(Protocol.PORT);
      while(true){
         System.out.println("Waiting for clients to connect...");
         Socket s = server.accept();
         System.out.println("Client  connected.");
         Runnable game = new GameHandler(s); // s: socket
         Thread t = new Thread(game);
         t.start();
      }
           

   }
}

//----
class GameHandler implements Runnable, Protocol
{
  private Socket client;
  private DataInputStream in;
  private DataOutputStream out;
  private int mystryNumber;

   public GameHandler(Socket s) 
   {  
     this.client = s;
     mystryNumber = (int) (Math.random() * (99 - 1 + 1) + 1);
     System.out.println(mystryNumber);
   }
   public void run()
   {
      try{
         in = new DataInputStream(client.getInputStream());
         out = new DataOutputStream(client.getOutputStream());
         runGame();
      }
      catch(IOException exception){
         System.out.println("Something went wrong");
      }
      finally{
         try{
            client.close();
         }catch(IOException exception){}
      }
 
   }

   private void runGame() throws IOException {
      int score = 0;
      int guessedNumber;
      boolean matched = false;
      while(matched == false){
         guessedNumber = in.readInt();

         if(guessedNumber==mystryNumber){
            out.writeInt(Protocol.SCORE);
            out.writeInt(score);
            out.flush();
         }
         else if(guessedNumber<mystryNumber){
            score++;
            out.writeInt(Protocol.LOW);
            out.flush();
         }
         else if(guessedNumber>mystryNumber){
            score++;
            out.writeInt(Protocol.HIGHT);
            out.flush();
         }

      }
   }






}
