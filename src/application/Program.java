package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
		System.out.print("Enter language\n0=English / 1=Portuguese-BR (0/1): ");
		lang = sc.nextInt();
		} while (lang != 0 && lang != 1);
		
		if (lang == 1) {
			//Locale.setDefault(new Locale("pt", "BR"));
			Translation.setLanguage(Languages.PT);
		}
		else {			
			//Locale.setDefault(new Locale("en", "US"));
			Translation.setLanguage(Languages.EN);
		}
		System.out.println(Translation.get("languageSetMsg"));
		System.out.print(Translation.get("pressEnter"));
		sc.nextLine();
		sc.nextLine();
		System.out.println();
		
		
		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print(Translation.get("source"));
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print(Translation.get("target"));
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if (chessMatch.getPromoted() != null) {
					System.out.print(Translation.get("enterPromotion"));
					String type = sc.nextLine().toUpperCase();
					while (!type.equals(Translation.get("bishopPieceName")) && !type.equals(Translation.get("knightPieceName")) && !type.equals(Translation.get("rookPieceName")) && !type.equals(Translation.get("queenPieceName"))){
						System.out.print(Translation.get("invalidPromotion"));
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