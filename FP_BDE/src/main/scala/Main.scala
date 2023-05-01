import scala.swing._
import scala.swing.event._
import AbstractGameEngine._


// Define the main object for the Board Games application
object Main extends SimpleSwingApplication {
  // Set the UI scale to 1.0 for consistency
  System.setProperty("sun.java2d.uiScale", "1.0")

  // Define functions to get the drawer, controller, and board for a selected game
  def getDrawer(game: String): Array[Array[Char]] => FlowPanel = {
    game match {
      case "Tic Tac Toe" => TicTacToeDrawer.Drawer
      case _ => TicTacToeDrawer.Drawer
    }
  }
  def getController(game: String): (Array[Array[Char]], String, Int) => Array[Array[Char]] = {
    game match {
      case "Tic Tac Toe" => TicTacToeController.Controller
      case _ => TicTacToeController.Controller
    }
  }

  // Initialize variables for game selection
  private val buttons = Array.ofDim[Button](6, 1)
  private var selectedGame = ""

  // Define the main window
  def top = new MainFrame {
    title = "Board Games"

    // Create a panel containing buttons for game selection
    contents = new BorderPanel {
      val boardPanel = new GridPanel(6, 1) {
        for (i <- 0 until 6) {
          val button = new Button("")
          button.focusable = false
          button.preferredSize = new Dimension(300, 50)
          button.peer.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 30))
          button.name = s"$i"

          // Set the button text based on the index using pattern matching
          button.name match {
            case "0" => button.text = "Tic Tac Toe"
            case _ =>
          }

          // Listen for button clicks and set the selected game and board accordingly
          button.listenTo(button)
          button.reactions += {
            case ButtonClicked(_) =>
              button.name match {
                case "0" => selectedGame = button.text
                case _ => selectedGame = ""
              }
              close()
              val drawer = getDrawer(selectedGame)
              val controller = getController(selectedGame)
              playGame(drawer, controller)
          }
          buttons(i)(0) = button
          contents += button
        }
      }
      layout(boardPanel) = BorderPanel.Position.Center
    }
    pack()
    centerOnScreen()
  }
}