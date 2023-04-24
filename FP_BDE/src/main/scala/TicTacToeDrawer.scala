import TicTacToeController.{isValid}
import AbstractGameEngine._
import java.awt.Color
import java.awt.Font
import javax.swing.{BorderFactory, ImageIcon}
import scala.swing._
import scala.swing.event._

object TicTacToeDrawer extends SimpleSwingApplication {
  // Icon set
  val xIcon = new ImageIcon("C:/Users/moham/Desktop/Board-Drawing-Engine/icons set/x.png")
  val oIcon = new ImageIcon("C:/Users/moham/Desktop/Board-Drawing-Engine/icons set/o.png")

  // Board array
  var board = Array.ofDim[Char](3, 3)

  // User input
  var input = ""

  // The GUI components
  val statusLabel = new Label(s"Player $currentPlayer's turn")  // shows current turn
  val boardLabels = Array.ofDim[Label](3, 3)  // to update the board and show players' moves
  def top = new MainFrame {
    title = "Tic Tac Toe"
    updateStatusLabel("Valid")
    contents = new BorderPanel {
      // The game board panel
      val boardPanel = new GridPanel(3, 3) {
        for (i <- 0 until 3; j <- 0 until 3) {
          val label = new Label("")
          label.border = BorderFactory.createMatteBorder(1, 1, 0, 0, Color.BLACK)
          label.preferredSize = new Dimension(100, 100)
          label.name = s"$i,$j"
          boardLabels(i)(j) = label
          contents += label
        }
      }
      // The horizontal letters panel
      val lettersPanel = new GridPanel(1, 3) {
        for (i <- 0 until 3) {
          val label = new Label(
            i match {
              case 0 => "a"
              case 1 => "b"
              case 2 => "c"
              case _ => ""
            }
          )
          label.border = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK)
          label.preferredSize = new Dimension(100, 20)
          contents += label
        }
      }

      // Input text area
      val takeInput = new TextField(2)
      takeInput.preferredSize = new Dimension(100, 70)
      takeInput.peer.setFont(new Font("Arial", Font.PLAIN, 50))
      takeInput.listenTo(takeInput)
      takeInput.reactions += {
        case EditDone(`takeInput`) =>
          input = takeInput.text
          board = Control(board, input)
          isValid match {
            case 0 => updateStatusLabel("Valid")
            case 1 => updateStatusLabel("Invalid")
            case _ =>
          }
          updateBoard()
          takeInput.text = ""
      }

      // Board reset button
      val resetButton =  new Button("Reset Board")
      resetButton.reactions += {
        case ButtonClicked(`resetButton`) =>
          board = TicTacToeController.Controller(board, "reset")
          for (i <- 0 until 3; j <- 0 until 3) {
              boardLabels(i)(j).icon = null
          }
          updateStatusLabel("reset")
          updateBoard()
          takeInput.text = ""
      }
      val flowButton = new FlowPanel(FlowPanel.Alignment.Center)(resetButton)

      // Back to main menu button
      val backToMain = new Button("Back to Main Menu")
      backToMain.reactions += {
        case ButtonClicked(`backToMain`) =>
          board = TicTacToeController.Controller(board, "reset")
          close()
          Main.main(Array.empty)
      }
      val exitButton = new FlowPanel(FlowPanel.Alignment.Center)(backToMain)

      // The vertical numbers panel
      val numbersPanel = new GridPanel(3, 1) {
        for (i <- 1 until 4) {
          val label = new Label(s"$i")
          label.preferredSize = new Dimension(10, 100)
          contents += label
        }
      }

      // The status label panel
      val statusPanel = new FlowPanel(FlowPanel.Alignment.Center)(statusLabel)
      statusPanel.border = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK)

      // The input panel containing both input text area and reset button
      val inputPanel = new BoxPanel(Orientation.Vertical) {
        contents += takeInput
        contents += flowButton
        contents += exitButton
      }

      // The left panel containing both the input panel and the numbers panel
      val leftPanel = new FlowPanel(FlowPanel.Alignment.Center)(inputPanel, numbersPanel)

      // The center panel containing both the board panel and the letters panel
      val centerPanel = new BoxPanel(Orientation.Vertical) {
        contents += boardPanel
        contents += lettersPanel
      }

      // Adjusting layout
      layout(centerPanel) = BorderPanel.Position.Center
      layout(statusPanel) = BorderPanel.Position.South
      layout(leftPanel) = BorderPanel.Position.West
    }

    // Adjusting game window
    pack()
    centerOnScreen()
    open()
  }

  // Updates the game board buttons with icons to match the current board
  private def updateBoard(): Unit = {
    for (i <- 0 until 3; j <- 0 until 3) {
      board(i)(j).toString.toLowerCase match {
        case "x" => boardLabels(i)(j).icon = xIcon
        case "o" => boardLabels(i)(j).icon = oIcon
        case _ =>
      }
    }
  }

  // Updates the status label with the current player's turn and indicates invalid inputs
  private def updateStatusLabel(validity: String): Unit = {
    validity match {
      case "Valid" => statusLabel.foreground = Color.BLACK ; statusLabel.text = s"Player $currentPlayer's turn"
      case "Invalid" => statusLabel.foreground = Color.RED ; statusLabel.text = s"Invalid Move! Player $currentPlayer's turn"
      case "reset" => statusLabel.foreground = Color.GREEN ; statusLabel.text = s"Board Reset, Player $currentPlayer's turn"
      case _ =>
    }
  }

  // A drawer function to set the initial board and call the GUI top method
  def Drawer(newBoard: Array[Array[Char]]): Unit = {
    board = newBoard
    TicTacToeDrawer.main(Array.empty)
  }
}
