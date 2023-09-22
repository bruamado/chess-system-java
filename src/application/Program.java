package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		int lang;
		do {	
		System.out.print("Enter language 0=English / 1=Portuguese-BR (0/1): ");
		lang = sc.nextInt();
		} while (lang != 0 && lang != 1);
		
		if (lang == 1) {
			Locale.setDefault(new Locale("pt", "BR"));
		}
		else {			
			Locale.setDefault(new Locale("en", "US"));
		}
		ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", Locale.getDefault());
		System.out.println(resourceBundle.getString("languageSetMsg"));
		System.out.print(resourceBundle.getString("pressEnter"));
		sc.nextLine();
		sc.nextLine();
		System.out.println();
		
		
		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print(resourceBundle.getString("source"));
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print(resourceBundle.getString("target"));
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if (chessMatch.getPromoted() != null) {
					System.out.print(resourceBundle.getString("enterPromotion"));
					String type = sc.nextLine().toUpperCase();
					while (!type.equals(resourceBundle.getString("bishopPieceName")) && !type.equals(resourceBundle.getString("knightPieceName")) && !type.equals(resourceBundle.getString("rookPieceName")) && !type.equals(resourceBundle.getString("queenPieceName"))){
						System.out.print(resourceBundle.getString("invalidPromotion"));
						type = sc.nextLine().toUpperCase();
					}
					chessMatch.replacePromotedPiece(type);
				}
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}

}