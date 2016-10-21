/*
Philip Jeffries
CSC153
Game lab Methods
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.math.*;
import javax.sound.sampled.*;
import java.io.*;

public class Board extends JPanel
{
   private Container contents;
   
   //location of game piece
   private int pieceX, pieceY;
   private int ghostX, ghostY;
   
   //2D int array.   
   private int [][] boardColors;
   
   //int for size of game board (width and height same)
   private int boardNumber = 6;
   
   private int boardSize = 600;
   //int for # of squares (4x4 or 6x6...)
   private int numberSquares;
   
   protected int numMoves = 0;
   protected int numScore = 0;
   
   private int k=0, l=0;
   
   protected boolean up = false, down = false, right = false, left = false, reset = false;
   //random points
   private int point1,point2,point3,point4;
      
   private String direction = "right";
   
   //timer
   private Timer timer;
   private int time = 25;
   private int space2 = 1000;
   private boolean Continue = true, countdown = false;
   
   
   //Methods:
   public Board(int Size)
   {
      boardNumber = Size;
            
      //instantiate or initialize instance data
      boardColors = new int[boardNumber][boardNumber];
      
      for(int i=0; i<=boardNumber-1; i++)
      {
         for(int j=0; j<=boardNumber-1; j++)
         {
            boardColors[i][j]=0;
         }
      }
      
      pieceX = 0;
      pieceY = 0;
      
      //ghost
      timer = new Timer(space2, new countListener());
      timer.start();
      
      Random rand = new Random();
      ghostX = (rand.nextInt(2)+4)*(boardSize/boardNumber);
      ghostY = (rand.nextInt(2)+4)*(boardSize/boardNumber);
      
      //random
      point1 = rand.nextInt(3)+1;
      point2 = rand.nextInt(3)+1;
      point3 = rand.nextInt(3)+1;
      point4 = rand.nextInt(3)+1;
         
      setVisible(true);
      setPreferredSize(new Dimension(Size,Size));
   }
   
   //method to color the square by passing in one int as a paramater
   public void setColor(int newColor)
   {
      //uses the int paramater to change the color in the 2D array and repaints
      boardColors[pieceX/(boardSize/boardNumber)][pieceY/(boardSize/boardNumber)] = newColor;
   }   
   
   //method which returns the color is for the location where the game piece is
   public int getColor()
   {  
      return boardColors[pieceX/(boardSize/boardNumber)][pieceY/(boardSize/boardNumber)];  
   }
   
   //plays sound
   public void playSound(String name) 
   {
      try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(new File(name));
            
            Clip clip = AudioSystem.getClip();
            
            clip.open(sound);
            clip.start();
        }    
        catch(UnsupportedAudioFileException uae) {
            System.out.println("uae");
        }
        catch(IOException ioe) {
            System.out.println("uae");
        }
        catch(LineUnavailableException lua) {
            System.out.println("lua");
        }

   }
   //up 
   public void up(){
      countdown=true;

      if(pieceY>=(boardSize/boardNumber)-1)
      {
         pieceY = pieceY - (boardSize/boardNumber);
         if(pieceX>0){
            k=(boardNumber*pieceX)/boardSize;}
         else{
            k=1-1;}
         
         if(pieceY>0){
            l=(boardNumber*pieceY)/boardSize;}
         else{
            l=1-1;}

         //the point system is random
         
         if(boardColors[k][l]==1){
            numScore=numScore+point1;}
         else if(boardColors[k][l]==2){
            numScore=numScore+point2;}
         else if(boardColors[k][l]==3){
            numScore=numScore+point3;}
         else if(boardColors[k][l]==4){
            numScore=numScore+point4;}
            
         boardColors[k][l]=2;
      }
      numMoves++;
      repaint();
      direction = "up";
      //playSound("Paceman.wav");
   }
   //down
   public void down(){
      countdown=true;

      if(pieceY<=(boardSize-boardSize/boardNumber)-1)
      {
         pieceY = pieceY + (boardSize/boardNumber);
         if(pieceX>0){
            k=(boardNumber*pieceX)/boardSize;}
         else{
            k=1-1;}
         
         if(pieceY>0){
            l=(boardNumber*pieceY)/boardSize;}
         else{
            l=1-1;}

         if(boardColors[k][l]==1){
            numScore=numScore+point1;}
         else if(boardColors[k][l]==2){
            numScore=numScore+point2;}
         else if(boardColors[k][l]==3){
            numScore=numScore+point3;}
         else if(boardColors[k][l]==4){
            numScore=numScore+point4;}
            
         boardColors[k][l]=4;
      }
      numMoves++;
      repaint();
      direction = "down";
      //playSound("Paceman.wav");
   }
   //left 
   public void left(){
      countdown=true;

      if(pieceX>=(boardSize/boardNumber)-1)
      {
         pieceX = pieceX - (boardSize/boardNumber);
         if(pieceX>0){
            k=(boardNumber*pieceX)/boardSize;}
         else{
            k=1-1;}
         
         if(pieceY>0){
            l=(boardNumber*pieceY)/boardSize;}
         else{
            l=1-1;}

         if(boardColors[k][l]==1){
            numScore=numScore+point1;}
         else if(boardColors[k][l]==2){
            numScore=numScore+point2;}
         else if(boardColors[k][l]==3){
            numScore=numScore+point3;}
         else if(boardColors[k][l]==4){
            numScore=numScore+point4;}
            
         boardColors[k][l]=3;
      }
      numMoves++;
      repaint();
      direction = "left";
      //playSound("Paceman.wav");
   }
   //right 
   public void right(){
      countdown=true;
      if(pieceX<=(boardSize - boardSize/boardNumber)-1)
      {
         pieceX = pieceX + (boardSize/boardNumber);
         if(pieceX>0){
            k=(boardNumber*pieceX)/boardSize;}
         else{
            k=1-1;}
         
         if(pieceY>0){
            l=(boardNumber*pieceY)/boardSize;}
         else{
            l=1-1;}

         if(boardColors[k][l]==1){
            numScore=numScore+point1;}
         else if(boardColors[k][l]==2){
            numScore=numScore+point2;}
         else if(boardColors[k][l]==3){
            numScore=numScore+point3;}
         else if(boardColors[k][l]==4){
            numScore=numScore+point4;}

         boardColors[k][l]=1;
      }
      numMoves++;
      repaint();
      direction = "right";
      //playSound("Paceman.wav");
   }
   
   //reset method to clear the board
   public void reset(){
      countdown=false;
      
      time =30;
      
      pieceX = 0;
      pieceY = 0;
      numMoves = 0;
      numScore = 0;
      
      for(int i=0; i<=boardNumber-1; i++)
      {
         for(int j=0; j<=boardNumber-1; j++)
         {
            boardColors[i][j]=0;
         }
      }
      repaint();
      direction = "right";
      
      //random
      Random rand = new Random();
      
      ghostX = (rand.nextInt(2)+4)*(boardSize/boardNumber);
      ghostY = (rand.nextInt(2)+4)*(boardSize/boardNumber);
      
      point1 = rand.nextInt(3)+1;
      point2 = rand.nextInt(3)+1;
      point3 = rand.nextInt(3)+1;
      point4 = rand.nextInt(3)+1;

   }
   
   //returns the number of moves
   public int getMoves(){
      return numMoves;
   }

   //returns the score
   public int getScore(){
      return numScore;
   }

   public void doRepaint(){
      repaint();
   }
   
   public void resetBoard(int Size)
   {
      boardNumber = Size;
      countdown=false;
      
      if (boardNumber==6) 
         space2 = 2000;
      else if(boardNumber==8) 
         space2 = 1000; 
      else if(boardNumber==10)
         space2 = 800;
      else if(boardNumber==12)
         space2 = 500;
      else if(boardNumber==20)
         space2 = 50;
      else
         space2 = 1000;
      
      //instantiate or initialize instance data
      boardColors = new int[boardNumber][boardNumber];
      
      for(int i=0; i<=boardNumber-1; i++)
      {
         for(int j=0; j<=boardNumber-1; j++)
         {
            boardColors[i][j]=0;
         }
      }
      
      pieceX = 0;
      pieceY = 0;
      
      
      //random
      Random rand = new Random();
      
      ghostX = (rand.nextInt(2)+4)*(boardSize/boardNumber);
      ghostY = (rand.nextInt(2)+4)*(boardSize/boardNumber);
      
      point1 = rand.nextInt(3)+1;
      point2 = rand.nextInt(3)+1;
      point3 = rand.nextInt(3)+1;
      point4 = rand.nextInt(3)+1;
         
      setVisible(true);
      setPreferredSize(new Dimension(Size,Size));
   }
   
   public boolean ghost() 
   {
      if(pieceX==ghostX && pieceY==ghostY)
         return true;
      else
         return false;
   }
   
   private class countListener implements ActionListener
   {
      public void actionPerformed (ActionEvent event)
      {
         
         if(time>1 && countdown==true)
         {
            //change ghost position relative to piece
            if(pieceX > ghostX)
               ghostX = ghostX + (boardSize/boardNumber);
            else if(pieceX < ghostX)
               ghostX = ghostX - (boardSize/boardNumber);
            if(pieceY > ghostY)
               ghostY = ghostY + (boardSize/boardNumber);
            else if(pieceY < ghostY)
               ghostY = ghostY - (boardSize/boardNumber);
            repaint();
            time--;
         }
         else if(time<=1)
         {
            repaint();
            countdown = false;
         }
       }
   }
   
   //paint method, draws grid with current coloring and game piece location
   public void paint(Graphics g)
   {
      super.paint(g);
      
      for(int i=0; i<=boardNumber-1; i++)
      {
         for(int j=0; j<=boardNumber-1; j++)
         {
            if(boardColors[i][j]==0){
               g.setColor(Color.BLACK);}
            else if(boardColors[i][j]==1){
               g.setColor(Color.RED);}
            else if(boardColors[i][j]==2){
               g.setColor(Color.YELLOW);}
            else if(boardColors[i][j]==3){
               g.setColor(Color.GREEN);}
            else if(boardColors[i][j]==4){
               g.setColor(Color.BLUE);}
            
            //g.drawRect((i*(boardSize/boardNumber) + i),(j*(boardSize/boardNumber) + j),(boardSize/boardNumber),(boardSize/boardNumber));
            g.fillOval((i*(boardSize/boardNumber) + (3*boardSize/(8*boardNumber))),(j*(boardSize/boardNumber) + (3*boardSize/(8*boardNumber))),(boardSize/boardNumber)/4,(boardSize/boardNumber)/4);
         }
      }
      
      //ghost
      g.setColor(Color.WHITE);
      g.fillOval(ghostX,ghostY,(int)((boardSize/boardNumber)),(int)((boardSize/boardNumber)));
      g.fillRect(ghostX,(ghostY+(int)(0.5*boardSize/boardNumber)),(int)((boardSize/boardNumber)),(int)((0.5*boardSize/boardNumber)));
      
      //paint game piece
      g.setColor(Color.YELLOW);     
      g.fillOval(pieceX,pieceY,(int)((boardSize/boardNumber)),(int)((boardSize/boardNumber)));
      g.setColor(Color.BLACK);
      int r = (boardSize/(boardNumber))/2;
      double side = r/Math.sqrt(2);
      if(direction.equals("right")){
         g.drawLine((pieceX+r),(pieceY+r),(int)(pieceX+r+side),(int)(pieceY+r-side));
         g.drawLine((pieceX+r),(pieceY+r),(int)(pieceX+r+side),(int)(pieceY+r+side));
      }
      else if(direction.equals("left")){
         g.drawLine((pieceX+r),(pieceY+r),(int)(pieceX+r-side),(int)(pieceY+r-side));
         g.drawLine((pieceX+r),(pieceY+r),(int)(pieceX+r-side),(int)(pieceY+r+side));
      }
      else if(direction.equals("down")){
         g.drawLine((pieceX+r),(pieceY+r),(int)(pieceX+r-side),(int)(pieceY+r+side));
         g.drawLine((pieceX+r),(pieceY+r),(int)(pieceX+r+side),(int)(pieceY+r+side));
      }
      else if(direction.equals("up")){
         g.drawLine((pieceX+r),(pieceY+r),(int)(pieceX+r+side),(int)(pieceY+r-side));
         g.drawLine((pieceX+r),(pieceY+r),(int)(pieceX+r-side),(int)(pieceY+r-side));
      }
      
   }
   
}
