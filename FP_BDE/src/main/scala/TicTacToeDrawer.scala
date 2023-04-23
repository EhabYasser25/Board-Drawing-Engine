
import javax.swing.ImageIcon
import scala.swing._
import scala.swing.event._

object TicTacToeDrawer extends SimpleSwingApplication {
  var board = Array.ofDim[Char](3, 3)

  // The current player (X or O)
  var currentPlayer = 1

  // The GUI components
  val statusLabel = new Label(s"Player $currentPlayer's turn")
  val boardButtons = Array.ofDim[Button](3, 3)

  def top = new MainFrame {
    title = "Tic Tac Toe"
    contents = new BorderPanel {
      // The game board panel
      val boardPanel = new GridPanel(3, 3) {
        for (i <- 0 until 3; j <- 0 until 3) {
          val button = new Button("")
          button.preferredSize = new Dimension(100, 100)
          button.peer.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 50))
          button.name = s"$i,$j"
          button.reactions += {
            case ButtonClicked(_) =>
              val Array(row, col) = button.name.split(",").map(_.toInt)
              makeMove(row, col)
              updateBoard()
              updateStatusLabel()
          }
          boardButtons(i)(j) = button
          contents += button
        }
      }

      // The status label panel
      val statusPanel = new FlowPanel(FlowPanel.Alignment.Center)(statusLabel)

      layout(boardPanel) = BorderPanel.Position.Center
      layout(statusPanel) = BorderPanel.Position.South
    }

    pack()
    centerOnScreen()
    open()
  }

  // Makes a move on the game board
  private def makeMove(row: Int, col: Int): Unit = {
    if (board(row)(col) == '\u0000') {
      currentPlayer match {
        case 1 => board(row)(col) = 'X'
        case 2 => board(row)(col) = 'O'
        case _ =>
      }
      currentPlayer = if (currentPlayer == 1) 2 else 1
    }
  }

  // Updates the game board buttons with the current state of the game board
  private def updateBoard(): Unit = {
    for (i <- 0 until 3; j <- 0 until 3) {
      boardButtons(i)(j).text = board(i)(j).toString
      boardButtons(i)(j).enabled = (board(i)(j) == '\u0000')
    }
  }

  private def updateStatusLabel(): Unit = {
      statusLabel.text = s"Player $currentPlayer's turn"
  }

//  def update(newBoard: Array[Array[Char]], turn: Int): Unit = {
//    board = newBoard
//    currentPlayer = turn
//  }
}
