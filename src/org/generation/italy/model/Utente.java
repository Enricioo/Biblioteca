package org.generation.italy.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Utente {

	public int id;
	public String nome, cognome, email;
	public static String sql, url = "jdbc:mysql://localhost:3306/biblioteca";

	// Metodo per inserimento utenti
	public static void inserisciUtente(Utente utente) {
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			sql = "INSERT INTO utenti (nome, cognome, email) VALUES (?, ?, ?)";
			int righeInserite = 0;
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, utente.nome);
				ps.setString(2, utente.cognome);
				ps.setString(3, utente.email);
				righeInserite = ps.executeUpdate();
			}
		} catch (Exception e) {
			System.err.println("Si è verificato un'errore: " + e.getMessage());
		}

	}
	
	// Metodo per stampa utenti
	public static void stampaUtenti() {
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			sql = "SELECT * FROM utenti";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						System.out.println("ID: " + rs.getInt("id") + ", Nome: " + rs.getString("nome") + ", Cognome: "
								+ rs.getString("cognome") + ", Email: " + rs.getString("email"));
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Si è verificato un errore: " + e.getMessage());
		}
	}

	// Autenticazione utente da sistemare (non funziona)
	public static boolean authUtente() {
		boolean auth = false;
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			String sql = "SELECT * FROM utenti WHERE nome = ? AND cognome = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, "nome");
				ps.setString(2, "cognome");
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
					return auth;
					}
				} catch (Exception e) {
					System.err.println("Si è verificato un errore: " + e.getMessage());
				}
				return auth;
			}

		} catch (Exception e) {
			System.err.println("Si è verificato un errore: " + e.getMessage());
			return false;
		}
	}
	
	public static void profiloUtente(int id) {
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			String sql = "SELECT * FROM utenti WHERE id = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						System.out.println("ID: " + rs.getInt("id"));
						System.out.println("Nome: " + rs.getString("nome"));
						System.out.println("Cognome: " + rs.getString("cognome"));
						System.out.println("Email: " + rs.getString("email"));
					} else {
						System.out.println("Utente non trovato.");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Si è verificato un'errore: " + e.getMessage());
		}
	}
	
}
