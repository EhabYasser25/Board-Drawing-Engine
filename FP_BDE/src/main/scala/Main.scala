import javax.swing.ImageIcon
import scala.swing._
import scala.swing.event._
import java.awt.Image
import scala.swing.event.EditDone
import AbstractGameEngine._

object Main {
  System.setProperty("sun.java2d.uiScale", "1.0")
//    def top = new MainFrame {
//      val X = new ImageIcon("C:/Users/moham/Desktop/Board-Drawing-Engine/icons set/x.png").getImage
//      val O = new ImageIcon("C:/Users/moham/Desktop/Board-Drawing-Engine/icons set/o.png").getImage
//      title = "Tic Tac Toe"
//
//      val textField = new TextField(20)
//      textField.listenTo(textField)
//      val label = new Label {
//        preferredSize = new Dimension(300, 300)
//      }
//      textField.reactions += {
//        case EditDone(textField) =>
//          val text = textField.text.toLowerCase() // get the text from the text field
//          if(text.equals("x")){
//            label.icon = new ImageIcon(X)
//          }else if(text.equals("o")){
//            label.icon = new ImageIcon(O)
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
    val TicTacToeBoard = Array.ofDim[Char](3, 3)
    playGame("TicTacToe", TicTacToeController.Controller,
      TicTacToeBoard)
  }
}