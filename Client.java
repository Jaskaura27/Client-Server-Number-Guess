import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.net.Socket;
public class Client implements Protocol
{
   public static void main(String[] args)
   {
      new Client("localhost"); 
   }
   public Client(String host)
   {  
     
      try (Socket socket = new Socket(host, PORT))
      {  
         DataInputStream in = new DataInputStream(socket.getInputStream());
         DataOutputStream out=new DataOutputStream(socket.getOutputStream());
         playGame(in, out);
      }
      catch (IOException e) 
      {
         System.err.println(e.getMessage());
      }
   } 
   public void playGame(DataInputStream in,DataOutputStream out) throws IOException
   {  
      Scanner fromUser = new Scanner(System.in);
      boolean done = false;
      System.out.println("guess a number from 1 (inclusive) up to and including 99");
      do 
      {  
         System.out.print("> your guess: ");
         int guessNumber = fromUser.nextInt();
         out.writeInt(guessNumber);
         out.flush();
         switch (in.readInt()){  
            case LOW:
               System.out.println(guessNumber+ " was lower than mysteryNum"); 
               break;
            case HIGHT:
               System.out.println(guessNumber+ " was higher than mysteryNum");
               break;
            case SCORE:
               int score = in.readInt();
               System.out.println("game over with a score of " + score);
               done = true;
         } 
      } while (!done);
      
   } 
} 