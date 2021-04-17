package io.github.mathmagician8191.chessgui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Square extends JButton implements ActionListener {
  /*
  Represents a square on the board. Is styled to look like a coloured square
  rather than a button.
  */
  
  static Image blackPawn = Square.createImage("/images/BlackPawn.png");
  static Image blackKnight = Square.createImage("/images/BlackKnight.png");
  static Image blackBishop = Square.createImage("/images/BlackBishop.png");
  static Image blackRook = Square.createImage("/images/BlackRook.png");
  static Image blackQueen = Square.createImage("/images/BlackQueen.png");
  static Image blackKing = Square.createImage("/images/BlackKing.png");
  static Image blackArchbishop = Square.createImage("/images/BlackArchbishop.png");
  static Image blackChancellor = Square.createImage("/images/BlackChancellor.png");
  static Image blackNightrider = Square.createImage("/images/BlackNightrider.png");
  static Image blackZebra = Square.createImage("/images/BlackZebra.png");
  static Image blackChampion = Square.createImage("/images/BlackChampion.png");
  static Image blackCamel = Square.createImage("/images/BlackCamel.png");
  static Image blackAmazon = Square.createImage("/images/BlackAmazon.png");
  static Image blackMann = Square.createImage("/images/BlackMann.png");
  static Image blackObstacle = Square.createImage("/images/BlackObstacle.png");
  static Image blackFerz = Square.createImage("/images/BlackFerz.png");
  static Image blackWazir = Square.createImage("/images/BlackWazir.png");
  static Image whitePawn = Square.createImage("/images/WhitePawn.png");
  static Image whiteKnight = Square.createImage("/images/WhiteKnight.png");
  static Image whiteBishop = Square.createImage("/images/WhiteBishop.png");
  static Image whiteRook = Square.createImage("/images/WhiteRook.png");
  static Image whiteQueen = Square.createImage("/images/WhiteQueen.png");
  static Image whiteKing = Square.createImage("/images/WhiteKing.png");
  static Image whiteArchbishop = Square.createImage("/images/WhiteArchbishop.png");
  static Image whiteChancellor = Square.createImage("/images/WhiteChancellor.png");
  static Image whiteNightrider = Square.createImage("/images/WhiteNightrider.png");
  static Image whiteZebra = Square.createImage("/images/WhiteZebra.png");
  static Image whiteChampion = Square.createImage("/images/WhiteChampion.png");
  static Image whiteCamel = Square.createImage("/images/WhiteCamel.png");
  static Image whiteAmazon = Square.createImage("/images/WhiteAmazon.png");
  static Image whiteMann = Square.createImage("/images/WhiteMann.png");
  static Image whiteObstacle = Square.createImage("/images/WhiteObstacle.png");
  static Image whiteFerz = Square.createImage("/images/WhiteFerz.png");
  static Image whiteWazir = Square.createImage("/images/WhiteWazir.png");
  
  static Color dark = new Color(160,128,96);
  static Color light = new Color(240,217,181);
  static Color selected = new Color(192,192,0);
  static Color moved = new Color(64,192,0);
  
  int[] coordinates;
  char letter;
  Color colour;
  Image image;
  
  public Square(int[] coordinates, char letter) {
    super(String.valueOf(letter));
    this.coordinates = coordinates;
    this.letter = letter;
    
    //set up a colour for the square
    this.colour = (coordinates[0]+coordinates[1])%2 == 1 ? light : dark;
    
    this.addActionListener(this);
    
    this.setBackground(this.colour);
    
    //set opaque so background is visible
    this.setOpaque(true);
    
    //style button like label
    this.setFocusPainted(false);
    this.setMargin(new Insets(0,0,0,0));
    this.setBorderPainted(false);
    
    switch (this.letter) {
      case 'p':
        image = blackPawn;
        this.setText("");
        break;
      case 'n':
        image = blackKnight;
        this.setText("");
        break;
      case 'b':
        image = blackBishop;
        this.setText("");
        break;
      case 'r':
        image = blackRook;
        this.setText("");
        break;
      case 'q':
        image = blackQueen;
        this.setText("");
        break;
      case 'k':
        image = blackKing;
        this.setText("");
        break;
      case 'a':
        image = blackArchbishop;
        this.setText("");
        break;
      case 'c':
        image = blackChancellor;
        this.setText("");
        break;
      case 'i':
        image = blackNightrider;
        this.setText("");
        break;
      case 'z':
        image = blackZebra;
        this.setText("");
        break;
      case 'h':
        image = blackChampion;
        this.setText("");
        break;
      case 'l':
        image = blackCamel;
        this.setText("");
        break;
      case 'm':
        image = blackAmazon;
        this.setText("");
        break;
      case 'x':
        image = blackMann;
        this.setText("");
        break;
      case 'o':
        image = blackObstacle;
        this.setText("");
        break;
      case 'f':
        image = blackFerz;
        this.setText("");
        break;
      case 'w':
        image = blackWazir;
        this.setText("");
        break;
      case 'P':
        image = whitePawn;
        this.setText("");
        break;
      case 'N':
        image = whiteKnight;
        this.setText("");
        break;
      case 'B':
        image = whiteBishop;
        this.setText("");
        break;
      case 'R':
        image = whiteRook;
        this.setText("");
        break;
      case 'Q':
        image = whiteQueen;
        this.setText("");
        break;
      case 'K':
        image = whiteKing;
        this.setText("");
        break;
      case 'A':
        image = whiteArchbishop;
        this.setText("");
        break;
      case 'C':
        image = whiteChancellor;
        this.setText("");
        break;
      case 'I':
        image = whiteNightrider;
        this.setText("");
        break;
      case 'Z':
        image = whiteZebra;
        this.setText("");
        break;
      case 'H':
        image = whiteChampion;
        this.setText("");
        break;
      case 'L':
        image = whiteCamel;
        this.setText("");
        break;
      case 'M':
        image = whiteAmazon;
        this.setText("");
        break;
      case 'X':
        image = whiteMann;
        this.setText("");
        break;
      case 'O':
        image = whiteObstacle;
        this.setText("");
        break;
      case 'F':
        image = whiteFerz;
        this.setText("");
        break;
      case 'W':
        image = whiteWazir;
        this.setText("");
        break;
      default:
        image=null;
    }
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    //ChessStart.engineMove();
    Square button = (Square) e.getSource();
    int[] square = button.coordinates;
    ChessStart.move(square);
  }
  
  @Override
  protected void paintComponent(Graphics graphics) {
    Graphics2D twoDGraphics = (Graphics2D) graphics;
    super.paintComponent(graphics);
    
    twoDGraphics.drawImage(this.image,0,0,this.getWidth(),this.getHeight(),this);
  }
  
  public static Image createImage(String location) {
    java.net.URL imageURL = Square.class.getResource(location);
    if (imageURL != null) {
      ImageIcon image =  new ImageIcon(imageURL);
      return image.getImage();
    }
    else {
      System.err.println("Can't find: " + location);
      return null;
    }
  }
  
  public void select(Color colour) {
    this.setBackground(colour);
  }
  
  public void deSelect() {
    this.setBackground(this.colour);
  }
}
