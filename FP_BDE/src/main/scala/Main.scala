import javax.swing.ImageIcon
import scala.swing._
import scala.swing.event._
import java.awt.Image
import scala.swing.event.EditDone
import AbstractGameEngine._

object Main {
//    def top = new MainFrame {
//      val originalX = new ImageIcon("C:/Users/moham/Desktop/Board-Drawing-Engine/icons set/x.png").getImage
//      val scaledX = originalX.getScaledInstance(50, 50, Image.SCALE_SMOOTH)
//      val originalO = new ImageIcon("C:/Users/moham/Desktop/Board-Drawing-Engine/icons set/o.png").getImage
//      val scaledO = originalO.getScaledInstance(50, 50, Image.SCALE_SMOOTH)
//      title = "Tic Tac Toe"
//
//      val textField = new TextField(20)
//      textField.listenTo(textField)
//      val label = new Label {
//        preferredSize = new Dimension(100, 100)
//        text = "hello"
//      }
//      textField.reactions += {
//        case EditDone(textField) =>
//          val text = textField.text.toLowerCase() // get the text from the text field
//          if(text.equals("x")){
//            label.icon = new ImageIcon(scaledX)
//          }else if(text.equals("o")){
//            label.icon = new ImageIcon(scaledO)
//          }else{}
//          textField.text = ""
//        case _ =>
//      }
//
//      contents = new BoxPanel(Orientation.Vertical) {
//        contents += textField
//        contents += label
//      }
//      pack() // resize the window to fit the components
//    }
  def main(args: Array[String]): Unit = {
    val TicTacToeBoard: Board = Board(Vector(Vector(0,0,0), Vector(0,0,0), Vector(0,0,0)))
    playGame(TicTacToeDrawer.Drawer, TicTacToeController.Controller,
      Board(Vector(Vector(0,0,0), Vector(0,0,0), Vector(0,0,0))))
  }
}