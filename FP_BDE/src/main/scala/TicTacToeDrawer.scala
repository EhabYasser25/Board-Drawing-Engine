import java.awt.Color
import javax.swing.{BorderFactory, ImageIcon}
import scala.swing._

object TicTacToeDrawer {

  // Drawer function
  def Drawer(newBoard: Array[Array[Char]]): FlowPanel = {
    // Icon set
    val xIcon = new ImageIcon("C:/Users/moham/Desktop/Board-Drawing-Engine/icons set/x.png")
    val oIcon = new ImageIcon("C:/Users/moham/Desktop/Board-Drawing-Engine/icons set/o.png")

    // An array of labels to be displayed inside the grid panel
    val boardLabels = Array.ofDim[Label](3, 3)

    // The game board
    val boardPanel = new GridPanel(3, 3) {
      val labels = for {
        i <- 0 until 3
        j <- 0 until 3
      } yield {
        val label = new Label("")
        label.border = BorderFactory.createMatteBorder(1, 1, 0, 0, Color.BLACK)
        label.preferredSize = new Dimension(100, 100)
        label.name = s"$i,$j"
        boardLabels(i)(j) = label
        (i, j, newBoard(i)(j))
      }

      labels.foreach { case (i, j, value) =>
        value.toString.toLowerCase match {
          case "x" => boardLabels(i)(j).icon = xIcon
          case "o" => boardLabels(i)(j).icon = oIcon
          case _ =>
        }
        contents += boardLabels(i)(j)
      }
    }

    // The horizontal letters panel
    val lettersPanel = new GridPanel(1, 3) {
      val labels = (0 until 3).map { i =>
        val letter = i match {
          case 0 => "a"
          case 1 => "b"
          case 2 => "c"
          case _ => ""
        }
        val label = new Label(letter)
        label.border = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK)
        label.preferredSize = new Dimension(100, 15)
        label
      }
      contents ++= labels
    }

    // The vertical numbers panel
    val numbersPanel = new GridPanel(3, 1) {
      for (i <- 1 until 4) {
        val label = new Label(s"$i")
        label.preferredSize = new Dimension(10, 100)
        contents += label
      }
    }

    // The center panel containing both the board panel and the letters panel
    val centerPanel = new BoxPanel(Orientation.Vertical) {
      contents += boardPanel
      contents += lettersPanel
    }
    new FlowPanel(FlowPanel.Alignment.Center)(numbersPanel, centerPanel)
  }
}
