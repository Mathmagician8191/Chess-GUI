package io.github.mathmagician8191.chessgui;

import io.github.mathmagician8191.chessgame.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Arrays;

public class ChessStart {
  /*
  Makes a chess game gui and gives options to play the game
  */
  
  //JFrame and JPanels
  private static JFrame window;
  private static JPanel cards;
  private static JPanel pane;
  
  //elements in gui
  private static JComboBox presets;
  private static JButton submitButton;
  private static JButton setPreset;
  
  //options for the game
  private static JTextField fen;
  private static JSpinner pawnRow;
  private static JSpinner pawnSquares;
  private static JSpinner leftRook;
  private static JSpinner rightRook;
  private static JTextField promotionOptions;
  private static JCheckBox aiEnabled;
  private static JCheckBox aiWhite;
  private static JSpinner depth;
  private static JSpinner quiescenceDepth;
  private static JCheckBox friendlyFire;
  
  //menu
  private static JPanel menu;
  private static JComboBox promotionChoices;
  
  //squares
  private static Square[][] boardSquares;
  
  //the state of the board
  private static Engine gameState;
  
  //state to undo to
  private static Engine undoState;
  
  //selected square
  private static int[] selectedSquare;
  
  //engine settings
  private static boolean enginePlays;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        makeGui();
      }
    });
  }
  public static void makeGui() {
    window = new JFrame("Totally Normal Chess - setup game");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setIconImage(Square.whiteNightrider);

    cards = new JPanel(new CardLayout());

    //set up border for padding
    Border border = BorderFactory.createEmptyBorder(20,20,20,20);

    //set up BoxLayout
    pane = new JPanel();
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    pane.setBorder(border);
    
    String[] presetNames = {
      "Standard (8x8)",
      "Capablanca (10x8)",
      "Capablanca (10x10)",
      "Mega chess (12x12)",
      "Mongol chess (8x8)",
      "Loaded Board (8x8)",
      "Totally Normal Chess (8x8)",
      "Buffed chess (8x8)",
      "Nerfed chess (8x8)",
      "Mini chess (5x6)",
    };
    presets = new JComboBox(presetNames);
    presets.setAlignmentX(Component.LEFT_ALIGNMENT);
    presets.setMaximumSize(presets.getPreferredSize());
    pane.add(presets);
    
    //add gap
    pane.add(Box.createRigidArea(new Dimension(0,20)));
    
    //button to submit
    submitButton = new JButton("Start Game");
    submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ChessStart.startGame();
      }
    });
    pane.add(submitButton);
    
    //add gap
    pane.add(Box.createRigidArea(new Dimension(0,20)));
    
    //button to apply the selected preset
    setPreset = new JButton("Apply preset");
    setPreset.setAlignmentX(Component.LEFT_ALIGNMENT);
    setPreset.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ChessStart.setPreset();
      }
    });
    pane.add(setPreset);
    
    //add gap
    pane.add(Box.createRigidArea(new Dimension(0,20)));
    
    //set up fen input as editable text field
    fen = new JTextField(60);
    fen.setAlignmentX(Component.LEFT_ALIGNMENT);
    //make combo box only expand horizontally
    fen.setMaximumSize(new Dimension(Integer.MAX_VALUE, fen.getPreferredSize().height));
    pane.add(fen);

    //add gap
    pane.add(Box.createRigidArea(new Dimension(0,20)));
    
    //add label for max n-move row
    pawnRow = ChessStart.createRow("Max row pawns can n-move from:",2,0,2);
    
    //add max number of squares a pawn can move
    pawnSquares = ChessStart.createRow("Max squares pawns can move first move:",2,1,2);
    
    //left rook column
    leftRook = ChessStart.createRow("Left Rook column:",1,1,2);
    
    //right rook column
    rightRook = ChessStart.createRow("Right Rook column:",8,1,2);
    
    promotionOptions = ChessStart.createInput("Promotion Pieces:");
    
    aiEnabled = ChessStart.checkBox("AI");
    
    aiWhite = ChessStart.checkBox("AI is white");
    
    depth = ChessStart.createRow("Depth (higher is better but slower)",3,1,2);
    
    quiescenceDepth = ChessStart.createRow(
        "Extra depth (can make very slow sometimes, too low makes AI bad)",4,1,1);
    
    friendlyFire = ChessStart.checkBox("Friendly Fire");
    
    pane.add(new JLabel("Source code and copyright: https://github.com/Mathmagician8191/Totally-Normal-Chess"));
    
    //add cards to CardLayout
    cards.add(pane);
    
    //set up default settings
    ChessStart.setPreset();

    //Display
    window.add(cards);
    window.pack();
    window.setVisible(true);
  }
  
  public static JSpinner createRow(String text,int defaultValue,int minimumValue,
      int columns) {
    JPanel row = ChessStart.getBoxWithLabel(text);
    
    SpinnerNumberModel validNumbers = new SpinnerNumberModel(defaultValue,minimumValue,
        Integer.MAX_VALUE,1);
    
    JSpinner spinner = new JSpinner(validNumbers);
    JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
    JFormattedTextField spinnerText = editor.getTextField();
    spinnerText.setColumns(columns);
    
    spinner.setMaximumSize(spinner.getPreferredSize());
    
    row.add(spinner);
    
    pane.add(row);
    
    return spinner;
  }
  
  public static JTextField createInput(String text) {
    JPanel row = ChessStart.getBoxWithLabel(text);
    
    JTextField input = new JTextField();
    input.setAlignmentX(Component.LEFT_ALIGNMENT);
    input.setMaximumSize(new Dimension(Integer.MAX_VALUE, input.getPreferredSize().height));
    
    row.add(input);
    
    pane.add(row);
    
    return input;
  }
  
  public static JCheckBox checkBox(String text) {
    JPanel row = ChessStart.getBoxWithLabel(text);
    
    JCheckBox checkBox = new JCheckBox();
    checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    row.add(checkBox);
    
    pane.add(row);
    
    return checkBox;
  }
  
  public static JPanel getBoxWithLabel(String text) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    JLabel label = new JLabel(text);
    panel.add(label);
    
    return panel;
  }

  public static void startGame() {
    //get game info
    String gameFen = fen.getText();
    int pawnStartRow = (Integer) pawnRow.getValue();
    int pawnSquaresMovable = (Integer) pawnSquares.getValue();
    int leftRookColumn = (Integer) leftRook.getValue();
    int rightRookColumn = (Integer) rightRook.getValue();
    String promotionPieces = promotionOptions.getText();
    boolean aiPlaysWhite = aiWhite.isSelected();
    int aiDepth = (Integer) depth.getValue();
    int quietDepth  = (Integer) quiescenceDepth.getValue();
    boolean friendlyFireEnabled = friendlyFire.isSelected();
    
    enginePlays = aiEnabled.isSelected();

    gameState = new Engine(gameFen,pawnStartRow,pawnSquaresMovable,leftRookColumn,
        rightRookColumn,promotionPieces,friendlyFireEnabled,aiDepth,quietDepth,aiPlaysWhite);
    
    ChessStart.getMenu();
    
    ChessStart.renderBoard();
    
    ChessStart.engineMove();
    
    //resize window if not maximized
    if (window.getExtendedState() == JFrame.NORMAL) {
      int defaultSize = 72;
      Board position = gameState.position;
      
      Insets border = window.getInsets();
      int width = (defaultSize*position.width) + border.left + border.right +
          menu.getPreferredSize().width;
      int height = (defaultSize*position.height) + border.top + border.bottom;
      
      //get current window size
      int currentWidth = window.getWidth();
      int currentHeight = window.getHeight();
      
      Rectangle currentScreen = window.getGraphicsConfiguration().getBounds();
      int screenWidth = currentScreen.width;
      int screenHeight = currentScreen.height;
      width = Math.min(width,screenWidth);
      height = Math.min(height, screenHeight);
      
      //update width/height to set
      width = Math.max(width,currentWidth);
      height = Math.max(height,currentHeight);
      window.setSize(width,height);
    }
    
    undoState = new Engine(gameState);
  }
  
  public static void changeTitle() {
    if (gameState.position.gameOver) {
      window.setTitle("Game Over. " + gameState.endCause);
    }
    else {
      window.setTitle("Totally Normal Chess - " + (gameState.position.toMove ? "White" : "Black") + " to move");
    }
  }
  
  public static void move(int[] square) {
    if (enginePlays && (gameState.side == gameState.position.toMove)) {
      return;
    }
    if (Arrays.equals(selectedSquare, new int[] {-1,-1})) {
      Board position = gameState.position;
      if (position.boardstate[square[0]][square[1]].side==(position.toMove ? 1 : -1)) {
        selectedSquare = square;
        
        //colour the selected piece
        boardSquares[square[0]][square[1]].select(Square.selected);
      }
    }
    else if (gameState.position.isMoveValid(selectedSquare, square)) {
      //save game state to undo to
      undoState = new Engine(gameState);
      
      gameState.makeMove(selectedSquare, square);
      
      ChessStart.renderBoard();
      
      //remove the last board state to prevent using up lots of memory
      cards.remove(1);
      
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          ChessStart.engineMove();
        }
      });
      thread.start();
      
      ChessStart.testCheck();
    }
    else {
      boardSquares[selectedSquare[0]][selectedSquare[1]].deSelect();
      selectedSquare = new int[] {-1,-1};
      ChessStart.testCheck();
    }
  }
  
  public static void engineMove() {
    //moves if it is the engine's turn
    if (enginePlays && (gameState.side == gameState.position.toMove)
        && !gameState.position.gameOver) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          window.setTitle("Thinking");
        }
      });
      gameState = Engine.makeMove(gameState);
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          ChessStart.changeTitle();
          ChessStart.renderBoard();
          //remove the last board state to prevent using up lots of memory
          cards.remove(1);
        }
      });
    }
  }
  
  public static void renderBoard() {
    ChessStart.changeTitle();
    
    final int rowCount = gameState.position.height;
    final int columnCount = gameState.position.width;

    //draw board
    JPanel board = new JPanel(new GridLayout(rowCount,columnCount)) {
      //Make board squares as large as possible while still being squares
      @Override
      public final Dimension getPreferredSize() {
        Component parent = getParent();
        int availableWidth = parent.getWidth();
        int availableHeight = parent.getHeight();
        int maxSquareWidth = availableWidth/columnCount;
        int maxSquareHeight = availableHeight/rowCount;
        int maxSquareSize = Math.min(maxSquareWidth,maxSquareHeight);
        return new Dimension(maxSquareSize*columnCount,maxSquareSize*rowCount);
      }
    };
    Piece[][] boardState = gameState.position.boardstate;
    boardSquares = new Square[columnCount][rowCount];
    for (int i=rowCount-1;i>=0;i--) {
      for (int j=0;j<columnCount;j++) {
        //find the piece being represented
        Piece piece = boardState[j][i];

        //switch the case back to how it would be output in a FEN
        Character letter = piece.side==1 ? Character.toUpperCase(piece.letter) :
            piece.letter;

        //make a Customized JButton to represent the square
        Square square = new Square(new int[] {j,i}, letter);
        boardSquares[j][i] = square;
        
        board.add(square);
      }
    }
    
    if (gameState.position.startSquare != null && gameState.position.endSquare != null) {
      int[] startSquare = gameState.position.startSquare;
      int[] endSquare = gameState.position.endSquare;
      boardSquares[startSquare[0]][startSquare[1]].select(Square.moved);
      boardSquares[endSquare[0]][endSquare[1]].select(Square.moved);
    }
    
    ChessStart.testCheck();
    
    //initialise selected square
    selectedSquare = new int[] {-1,-1};
    
    //Add board to container panel
    JPanel gameBoard = new JPanel(new GridBagLayout());
    gameBoard.add(board,new GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER,
        GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));
    
    JPanel gameLayout = new JPanel(new GridBagLayout());
    gameLayout.add(gameBoard,new GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER,
        GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
    
    gameLayout.add(menu);
    
    //add panel to CardLayout
    cards.add(gameLayout);
    CardLayout cardLayout = (CardLayout) cards.getLayout();
    cardLayout.next(cards);
  }
  
  public static void getMenu() {
    //add menu side bar
    menu = new JPanel();
    menu.setLayout(new BoxLayout(menu,BoxLayout.Y_AXIS));
    
    JButton newGame = new JButton("New Game");
    newGame.setAlignmentX(Component.LEFT_ALIGNMENT);
    newGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ChessStart.newGame();
      }
    });
    menu.add(newGame);
    
    JButton copyFen = new JButton("Copy FEN to clipboard");
    copyFen.setAlignmentX(Component.LEFT_ALIGNMENT);
    copyFen.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ChessStart.copyFen();
      }
    });
    menu.add(copyFen);
    
    String promotionPieces = gameState.promotionOptions;
    int length = promotionPieces.length();
    if (length>1) {
      String[] promotions = new String[length];
      for (int i=0;i<length;i++) {
        promotions[i] = promotionPieces.substring(i,i+1);
      }
      promotionChoices = new JComboBox(promotions);
      promotionChoices.setAlignmentX(Component.LEFT_ALIGNMENT);
      menu.add(promotionChoices);

      JButton promote = new JButton("Promote");
      promote.setAlignmentX(Component.LEFT_ALIGNMENT);
      promote.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          ChessStart.promote();
        }
      });
      menu.add(promote);
    }
    
    JButton undo = new JButton("Undo last move");
    undo.setAlignmentX(Component.LEFT_ALIGNMENT);
    undo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ChessStart.undo();
      }
    });
    menu.add(undo);
  }
  
  public static void newGame() {
    window.setTitle("Choose game FEN");
    CardLayout cardLayout = (CardLayout) cards.getLayout();
    cardLayout.next(cards);
    cards.remove(1);
  }
  
  public static void setPreset() {
    switch ((String) presets.getSelectedItem()) {
      case "Standard (8x8)":
        //standard chess
        fen.setText("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        pawnRow.setValue(2);
        pawnSquares.setValue(2);
        leftRook.setValue(1);
        rightRook.setValue(8);
        promotionOptions.setText("qrbn");
        friendlyFire.setSelected(false);
        break;
      case "Capablanca (10x8)":
        //Capablanca's chess on a 10x8 board without triple move
        fen.setText("rnabqkbcnr/pppppppppp/10/10/10/10/PPPPPPPPPP/RNABQKBCNR w KQkq - 0 1");
        pawnRow.setValue(2);
        pawnSquares.setValue(2);
        leftRook.setValue(1);
        rightRook.setValue(10);
        promotionOptions.setText("qcarbn");
        friendlyFire.setSelected(false);
        break;
      case "Capablanca (10x10)":
        //Capablanca's chess on a 10x0 with triple move
        fen.setText("rnabqkbcnr/pppppppppp/10/10/10/10/10/10/PPPPPPPPPP/RNABQKBCNR w KQkq - 0 1");
        pawnRow.setValue(2);
        pawnSquares.setValue(3);
        leftRook.setValue(1);
        rightRook.setValue(10);
        promotionOptions.setText("qcarbn");
        friendlyFire.setSelected(false);
        break;
      case "Mega chess (12x12)":
        //12x12 chess
        fen.setText("rhbicqkaibhr/lfwnxzzxnwfl/pppppppppppp/12/12/12/12/12/12/PPPPPPPPPPPP/LFWNXZZXNWFL/RHBICQKAIBHR w KQkq - 0 1");
        pawnRow.setValue(3);
        pawnSquares.setValue(3);
        leftRook.setValue(1);
        rightRook.setValue(12);
        promotionOptions.setText("qcahirbzxnlwf");
        friendlyFire.setSelected(false);
        break;
      case "Mongol chess (8x8)":
        //interesting custom position
        fen.setText("nnnnknnn/pppppppp/8/8/8/8/PPPPPPPP/NNNNKNNN w - - 0 1");
        pawnRow.setValue(2);
        pawnSquares.setValue(2);
        leftRook.setValue(1);
        rightRook.setValue(8);
        promotionOptions.setText("iznl");
        friendlyFire.setSelected(false);
        break;
      case "Loaded Board (8x8)":
        //interesting custom position
        fen.setText("rrrqkrrr/bbbbbbbb/nnnnnnnn/pppppppp/PPPPPPPP/NNNNNNNN/BBBBBBBB/RRRQKRRR w KQkq - 0 1");
        pawnRow.setValue(2);
        pawnSquares.setValue(2);
        leftRook.setValue(1);
        rightRook.setValue(8);
        promotionOptions.setText("qrbn");
        friendlyFire.setSelected(false);
        break;
      case "Totally Normal Chess (8x8)":
        //interesting custom position
        fen.setText("hixakxih/pppppppp/8/8/8/8/PPPPPPPP/HIXAKXIH w - - 0 1");
        pawnRow.setValue(2);
        pawnSquares.setValue(2);
        leftRook.setValue(1);
        rightRook.setValue(8);
        promotionOptions.setText("ahix");
        friendlyFire.setSelected(false);
        break;
      case "Buffed chess (8x8)":
        //interesting custom position
        fen.setText("ciamkaic/pppppppp/8/8/8/8/PPPPPPPP/CIAMKAIC w - - 0 1");
        pawnRow.setValue(2);
        pawnSquares.setValue(2);
        leftRook.setValue(1);
        rightRook.setValue(8);
        promotionOptions.setText("mcai");
        friendlyFire.setSelected(false);
        break;
      case "Nerfed chess (8x8)":
        //interesting custom position
        fen.setText("wlfxkflw/pppppppp/8/8/8/8/PPPPPPPP/WLFXKFLW w - - 0 1");
        pawnRow.setValue(2);
        pawnSquares.setValue(2);
        leftRook.setValue(1);
        rightRook.setValue(8);
        promotionOptions.setText("xlwf");
        friendlyFire.setSelected(false);
        break;
      case "Mini chess (5x6)":
        //miniature board
        fen.setText("qkbnr/ppppp/5/5/PPPPP/QKBNR w Kk - 0 1");
        pawnRow.setValue(2);
        pawnSquares.setValue(1);
        leftRook.setValue(1);
        rightRook.setValue(5);
        promotionOptions.setText("qrbn");
        friendlyFire.setSelected(false);
        break;
    }
  }
  
  public static void copyFen() {
    String currentFen = ChessStart.gameState.position.toString();
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(new StringSelection(currentFen),null);
  }
  
  public static void promote() {
    char piece = ((String) promotionChoices.getSelectedItem()).charAt(0);
    if (gameState.promotePiece(piece)) {
      ChessStart.renderBoard();
      ChessStart.engineMove();
    }
  }
  
  public static void undo() {
    gameState = undoState;
    ChessStart.renderBoard();
    
    //remove the last board state to prevent using up lots of memory
    cards.remove(1);
  }
  
  public static void testCheck() {
    //colour the king if in check
    if (gameState.position.inCheck) {
      int[] kingLocation;
      if (gameState.position.toMove) {
        //white is in check
        kingLocation = gameState.position.whiteKingLocation;
      }
      else {
        //black is in check
        kingLocation = gameState.position.blackKingLocation;
      }
      Color checkColour = new Color(192,0,0);
      boardSquares[kingLocation[0]][kingLocation[1]].select(checkColour);
    }
  }
}