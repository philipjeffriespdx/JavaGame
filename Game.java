/*
Philip Jeffries
CSC153
Game Lab Client
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.*;

public class Game extends JFrame
{
   private Board board;
   private JLabel score, highscore, moves, space, message1, message2, message3;
   private JButton reset, up, down, right, left;
   private JPanel pBoard, pNumbers, pControls, pMessage, buttonPanel;
   private Container contents;
   private Font scoreFont, messageFont1, messageFont2;

   //timer
   private Timer timer;
   private int time = 30;
   private int space2 = 1000;
   private boolean Continue = true, countdown = false;

   //Radio button group
   private JRadioButton [] gridButtons;
   private ButtonGroup gridGroup;
   private String [] grid = {"Level 1: 6x6","Level 2: 8x8","Level 3: 10x10","Level 4: 12x12","Level 5: 20x20"};
   private int numBoard = 6;
   
   //highscore
   private int HighScore;
   
   public Game(String name) throws IOException
   {
      
      super(name);
      
      contents = getContentPane();
      
      //highscore text file
      try {
      Scanner file = new Scanner(new File("highscore.txt"));
      String SHighScore = file.nextLine();
      HighScore = Integer.parseInt(SHighScore);}
      
      catch(FileNotFoundException fnfe) {
      System.out.println("File not found..");}
      
      
      //instantiate timer
      timer = new Timer(space2, new countListener());
      timer.start();

     // public void NumbersPanels()
      //{
      ButtonHandler bh = new ButtonHandler();
      pNumbers = new JPanel();
      pNumbers.setLayout(new GridLayout(5,1));
      pNumbers.setBounds(600,0,200,200);
      pNumbers.setOpaque(true);
      pNumbers.setBackground(Color.yellow);
         
      //inside of pNumbers
      score = new JLabel("Score:\n ");
      highscore = new JLabel("Highscore:\n ");
      moves = new JLabel("Time:\n ");
      space = new JLabel(" ");
      //reset button
      reset = new JButton("Reset");
      reset.setSize(3,1);
      reset.addActionListener(bh);
      
      pNumbers.add(score);
      pNumbers.add(highscore);
      pNumbers.add(moves);
      pNumbers.add(space);
      pNumbers.add(reset);
     
      pControls = new JPanel();
      pControls.setLayout(new BorderLayout());
      pControls.setBounds(600,400,200,100);
      pControls.setOpaque(true);
      pControls.setBackground(Color.blue);
         
      //inside of pControls
      up = new JButton("up");
      down = new JButton("down");
      right = new JButton("right");
      left = new JButton("left");
         
      up.setSize(1,1);
      down.setSize(1,1);
      right.setSize(1,1);
      left.setSize(1,1);
         
      up.addActionListener(bh);
      down.addActionListener(bh);
      right.addActionListener(bh);
      left.addActionListener(bh);
      
      pControls.add(up,BorderLayout.NORTH);
      pControls.add(down,BorderLayout.SOUTH);
      pControls.add(right,BorderLayout.EAST);
      pControls.add(left,BorderLayout.WEST);
     
      message1 = new JLabel("");
      message1.setBounds(0,600,800,75);
      message2 = new JLabel("");
      message2.setBounds(0,675,800,75);
     
      buttonPanel = new JPanel();
      buttonPanel.setBounds(600,200,200,200);
      buttonPanel.setLayout(new GridLayout(5,1));
      buttonPanel.setOpaque(true);
      buttonPanel.setBackground(Color.red);
      
      RadioButtonHandler rbh = new RadioButtonHandler();
      gridButtons = new JRadioButton[grid.length];
      
      gridGroup = new ButtonGroup();
      
      for(int i=0; i < grid.length; i++)
      {
         gridButtons[i]=new JRadioButton(grid[i]);
         gridGroup.add(gridButtons[i]);
         gridButtons[i].addItemListener(rbh);
         buttonPanel.add(gridButtons[i]);
      }
      
      //board
      board = new Board(numBoard);

      board.setBounds(0,0,600,600);
      

      //items in total contents
      contents.add(board);
      contents.add(pNumbers);
      contents.add(buttonPanel);
      contents.add(pControls);
      contents.add(message1);
      contents.add(message2);
      
      
      pack();
      
      setSize(800,800);
      setVisible(true);
   }
        
   private class ButtonHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent ae)
      {
         if(board.ghost()==true)
         {
            JOptionPane.showMessageDialog(null, "You Have Been Caught By the Ghost!");
            board.reset();
         }
         else
         {}
         
         if (ae.getSource() == reset){
            board.reset();
            countdown = false;
            time = 30;
            repaint();
            Continue=true;
         }
         if(Continue==true){
         if(ae.getSource() == up){
            countdown = true;
            board.up();
            repaint();
         }
         else if(ae.getSource() == down){
            countdown = true;
            board.down();
            repaint();
         }
         else if(ae.getSource() == right){
            countdown = true;
            board.right();
            repaint();
            
         }
         else if(ae.getSource() == left){
            countdown = true;
            board.left();
            repaint();
         }}
      }   
   }
   
   private class countListener implements ActionListener
   {
      public void actionPerformed (ActionEvent event)
      {
         if(time>=1 && countdown==true)
         {
            time=time-1;
         }
         else if(time<=0 && countdown==true && Continue==true)
         {
            Continue = false; 
            if (HighScore<=board.getScore())
            {
               try{
               FileOutputStream fos = new FileOutputStream("highscore.txt",false);
               PrintWriter pw = new PrintWriter(fos);
               pw.print(""+board.getScore());
               System.out.println("the new highsore is: " + highscore);
               pw.close();}
               catch(FileNotFoundException fnfe){
               System.out.println("File not found.");}               
            }
         }
         
         if(board.ghost()==true)
         {
            JOptionPane.showMessageDialog(null, "You Have Been Caught By the Ghost, you just lost all your points.");
            board.reset();
         }
         else
         {}
         
         repaint();
      }
   }
   
   private class RadioButtonHandler implements ItemListener
   {
      public void itemStateChanged(ItemEvent ie)
      {  
         if(ie.getSource() == gridButtons[0])
            board.resetBoard(6);
         else if(ie.getSource() == gridButtons[1])
            board.resetBoard(8);
         else if(ie.getSource() == gridButtons[2])
            board.resetBoard(10);
         else if(ie.getSource() == gridButtons[3])
            board.resetBoard(12);
         else if(ie.getSource() == gridButtons[4])
            board.resetBoard(20);
      }
   }

   //paint method
   public void paint(Graphics g)
   {
      super.paint(g);
      scoreFont = new Font("Arial", Font.BOLD,30);
      g.setFont(scoreFont);
      
      g.drawString(""+time,700,140);
      g.drawString(""+HighScore,700,100);
      g.drawString(""+board.getScore(),700,60);
      
      messageFont1 = new Font("Arial", Font.BOLD,40);
      g.setFont(messageFont1);
      g.drawString("COLOR QUEST",100,675);
      
      messageFont2 = new Font("Arial", Font.PLAIN,15);
      g.setFont(messageFont2);
      g.drawString("The goal is to go back over the tiles that become colored to recieve point values from 1-4. You only have 30 seconds. ",10,700);
      g.drawString("Look out for the ghost, if he touches you he will take away all your points.",10,716);
   }

   //public void 
   public static void main (String [] args) throws IOException
   {
      Game boardgame = new Game("Game name");
      boardgame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
}