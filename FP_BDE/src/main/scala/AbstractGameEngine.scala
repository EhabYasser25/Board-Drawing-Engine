import scala.swing.SimpleSwingApplication
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import scala.swing._
import scala.swing.event._

// Define a game engine
object AbstractGameEngine extends SimpleSwingApplication{

  // Define a function type for the game controller and drawer
  private var Controller: (Array[Array[Char]], String, Int) => Array[Array[Char]] = null
  private var Drawer: Array[Array[Char]] => FlowPanel = null

  def playGame(drawer: Array[Array[Char]] => FlowPanel,
               controller: (Array[Array[Char]], String, Int) => Array[Array[Char]]): Unit = {
    Controller = controller
    Drawer = drawer
    AbstractGameEngine.main(Array.empty)
  }

  def top = new MainFrame {
    title = "Game"

    // Track the current player (1 or 2)
    var currentPlayer: Integer = 1

    // A label to show the current status
    val statusLabel = new Label(s"Player $currentPlayer's turn") // shows current turn

    // User input
    var input = ""

    // Game board
    var Board = Array.ofDim[Char](20, 20)

    updateStatusLabel("Valid", statusLabel, currentPlayer)
    contents = new BorderPanel {

      // The game board panel
      val centerPanel = Drawer(Board)

      // Input text area
      val takeInput = new TextField(2)
      takeInput.preferredSize = new Dimension(100, 70)
      takeInput.peer.setFont(new Font("Arial", Font.PLAIN, 50))
      takeInput.listenTo(takeInput)
      takeInput.reactions += {
        case EditDone(`takeInput`) =>
          input = takeInput.text
          Board = Controller(Board, input, currentPlayer)
          Board(19)(19) match {
            case 0 =>
              currentPlayer = alternateTurns(currentPlayer)
              updateStatusLabel("Valid", statusLabel, currentPlayer)
              centerPanel.contents.clear()
              centerPanel.contents += Drawer(Board)
              centerPanel.repaint()
            case 1 => updateStatusLabel("Invalid", statusLabel, currentPlayer)
            case _ =>
          }
          takeInput.text = ""
      }

      // Board reset button
      val resetButton = new Button("Reset Board")
      resetButton.reactions += {
        case ButtonClicked(`resetButton`) =>
          Board = Array.ofDim[Char](20, 20)
          currentPlayer = 1
          updateStatusLabel("reset", statusLabel, currentPlayer)
          centerPanel.contents.clear()
          centerPanel.contents += Drawer(Board)
          centerPanel.repaint()
          takeInput.text = ""
      }
      val flowButton = new FlowPanel(FlowPanel.Alignment.Center)(resetButton)

      // Back to main menu button
      val backToMain = new Button("Back to Main Menu")
      backToMain.reactions += {
        case ButtonClicked(`backToMain`) =>
          Board = Controller(Board, "reset", currentPlayer)
          close()
          Main.main(Array.empty)
      }
      val exitButton = new FlowPanel(FlowPanel.Alignment.Center)(backToMain)

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
      val leftPanel = new FlowPanel(FlowPanel.Alignment.Center)(inputPanel)

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

  // Updates the status label with the current player's turn and indicates invalid inputs
  def updateStatusLabel(validity: String, statusLabel: Label, currentPlayer: Integer): Unit = {
    validity match {
      case "Valid" => statusLabel.foreground = Color.BLACK; statusLabel.text = s"Player $currentPlayer's turn"
      case "Invalid" => statusLabel.foreground = Color.RED; statusLabel.text = s"Invalid Move! Player $currentPlayer's turn"
      case "reset" => statusLabel.foreground = Color.GREEN; statusLabel.text = s"Board Reset, Player $currentPlayer's turn"
      case _ =>
    }
  }

  // Switch to the next player's turn
  def alternateTurns(currentPlayer: Int): Int = {
    currentPlayer match {
      case 1 => 2
      case 2 => 1
      case _ => -1
    }
  }
}