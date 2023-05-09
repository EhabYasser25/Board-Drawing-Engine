package Engines

def chess_controller(game_board: Array[Array[String]], input: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {
  val inputPattern = """([1-8])([a-h]) ([1-8])([a-h])( )*""".r
  if (!input.matches(inputPattern.regex)) return (game_board, false)                          // simple validation of the input

  val pos: Array[Int]  = Array(input(0).asDigit - 1, input(1) - 'a', input(3).asDigit - 1, input(4) - 'a')
  val diff: Array[Int] = Array(pos(2) - pos(0), pos(3) - pos(1));
  /*
   * validating input ...
   * good_flag is true when !badCond1 && !badCond2 etc ... = !(badCond1 || badCond2 || etc ...)
   */

  // phase 1 of validation
  val good_flag: Boolean =
    !(
    pos.exists(_.toDouble.isNaN) || (pos(0) == pos(2) && pos(1) == pos(3))             // input is incorrect or no changes were made
    || (game_board(pos(0))(pos(1)).substring(0, 5) == game_board(pos(2))(pos(3)).substring(0, 5)
      && game_board(pos(2))(pos(3)).takeRight(6) != "Square")                         // Attacking themselves
    || game_board(pos(0))(pos(1)).takeRight(6) == "Square"                            // moving a square not a piece
    || (game_board(pos(0))(pos(1)).substring(0, 5) == "Black" && player1Turn)
    || (game_board(pos(0))(pos(1)).substring(0, 5) == "White" && !player1Turn)
  )
  && (    // phase 2 of validation
    game_board(pos(0))(pos(1)) match
      case kingPiece if kingPiece.contains("King") =>
        diff.forall(_ <= 1);
      case queenPiece if queenPiece.contains("Queen") =>
        (Math.abs(diff(0)) == Math.abs(diff(1)) || pos(0) == pos(2) || pos(1) == pos(3)) &&
        (
          if (Math.abs(diff(0)) == Math.abs(diff(1)))
            (1 until Math.abs(diff(0))).forall(m => game_board(pos(0) + (m * diff(0) / (if(Math.abs(diff(0)) != 0) Math.abs(diff(0)) else 1) ))(pos(1) + (m * diff(1) / (if(Math.abs(diff(1)) != 0) Math.abs(diff(0)) else 1) )).takeRight(6) == "Square")
          else
            if(pos(0) == pos(2))
              (1 until Math.abs(diff(1))).forall(m => game_board(pos(0))(pos(1) + (m * diff(1) / (if(Math.abs(diff(1)) != 0) Math.abs(diff(0)) else 1) )).takeRight(6) == "Square")
            else
              (1 until Math.abs(diff(0))).forall(m => game_board(pos(0) + (m * diff(1) / (if(Math.abs(diff(1)) != 0) Math.abs(diff(0)) else 1) ))(pos(1)).takeRight(6) == "Square")
        )
      case rookPiece if rookPiece.contains("Rook") =>
        (pos(0) == pos(2) || pos(1) == pos(3)) &&
        (
          if (pos(0) == pos(2))
            (1 until Math.abs(diff(1))).forall(m => game_board(pos(0))(pos(1) + (m * diff(1) / (if(Math.abs(diff(1)) != 0) Math.abs(diff(0)) else 1) )).takeRight(6) == "Square")
          else
            (1 until Math.abs(diff(0))).forall(m => game_board(pos(0) + (m * diff(1) / (if(Math.abs(diff(1)) != 0) Math.abs(diff(0)) else 1) ))(pos(1)).takeRight(6) == "Square")
        )
      case bishopPiece if bishopPiece.contains("Bishop") =>
        (Math.abs(diff(0)) == Math.abs(diff(1))) &&
          (1 until Math.abs(diff(0))).forall(m => game_board(pos(0) + (m * diff(0) / (if(Math.abs(diff(0)) != 0) Math.abs(diff(0)) else 1) ))(pos(1) + (m * diff(1) / (if(Math.abs(diff(1)) != 0) Math.abs(diff(0)) else 1) )).takeRight(6) == "Square")
      case knightPiece if knightPiece.contains("Knight") =>
        Math.abs(diff(0)) + Math.abs(diff(1)) == 3
      case pawnPiece if pawnPiece.contains("Pawn") =>
        (if (pawnPiece.contains("White")) diff(0) == -1 else diff(0) == 1)
        && (if (game_board(pos(2))(pos(3)).takeRight(6) != "Square") Math.abs(diff(1)) == 1 else Math.abs(diff(1)) == 0)
      case _ =>
        println("Chess Controller - Phase 2 Of Validation - Error")
        false
  )
  (if(good_flag) applyMove(pos)(game_board) else game_board, good_flag)
}

def applyMove(pos: Array[Int]): Array[Array[String]] => Array[Array[String]] =
  (board: Array[Array[String]]) =>
    board.updated(pos(2), board(pos(2)).updated(pos(3), board(pos(0))(pos(1))))
      .updated(pos(0), board(pos(0)).updated(pos(1), if( ((pos(0) + pos(1)) % 2) == 0) "White Square" else "Black Square"))

def chess_drawer(game_board: Array[Array[String]]): Unit = println(
  (0 to 7).map { i =>
    val row = (0 to 7).map { j =>
      toUnicode(game_board(i)(j))
    }.mkString(" ")
    s"${i + 1} $row\n"
  }.mkString("  a b c d e f g h\n", "", "")
)

def chess_initializer(): Array[Array[String]] = Array(
  Array("Black Rook", "Black Knight", "Black Bishop", "Black Queen", "Black King", "Black Bishop", "Black Knight", "Black Rook"),
  Array("Black Pawn", "Black Pawn", "Black Pawn", "Black Pawn", "Black Pawn", "Black Pawn", "Black Pawn", "Black Pawn"),
  Array("White Square", "Black Square", "White Square", "Black Square", "White Square", "Black Square", "White Square", "Black Square"),
  Array("Black Square", "White Square", "Black Square", "White Square", "Black Square", "White Square", "Black Square", "White Square"),
  Array("White Square", "Black Square", "White Square", "Black Square", "White Square", "Black Square", "White Square", "Black Square"),
  Array("Black Square", "White Square", "Black Square", "White Square", "Black Square", "White Square", "Black Square", "White Square"),
  Array("White Pawn", "White Pawn", "White Pawn", "White Pawn", "White Pawn", "White Pawn", "White Pawn", "White Pawn"),
  Array("White Rook", "White Knight", "White Bishop", "White Queen", "White King", "White Bishop", "White Knight", "White Rook"),
);

def toUnicode(pieceName: String): String = pieceName match
  case "White King" => "\u265A"
  case "White Queen" => "\u265B"
  case "White Rook" => "\u265C"
  case "White Bishop" => "\u265D"
  case "White Knight" => "\u265E"
  case "White Pawn" => "\u265F"
  case "White Square" => "\u25A0"
  case "Black King" => "\u2654"
  case "Black Queen" => "\u2655"
  case "Black Rook" => "\u2656"
  case "Black Bishop" => "\u2657"
  case "Black Knight" => "\u2658"
  case "Black Pawn" => "\u2659"
  case "Black Square" => "\u25A1"
  case _ => "unknown piece" // for unknown piece names
