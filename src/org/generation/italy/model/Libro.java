package org.generation.italy.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Libro {
	public int id, giacenza, id_autore, id_genere;
	public String nome, codice, nomeAuto, cognomeAuto, nomeGen;
	public LocalDate anno;
	public static String sql, url = "jdbc:mysql://localhost:3306/biblioteca";

//metodo per inserire libro
	public static void InserisciLibro(Libro lib) {
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			sql = "INSERT INTO libri(anno,codice,nome,giacenza,id_autore,id_genere) VALUES (?,?,?,?,?,?)";
			int righeInserite = 0;
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setDate(1, Date.valueOf(lib.anno));
				ps.setString(2, lib.codice);
				ps.setString(3, lib.nome);
				ps.setInt(4, lib.giacenza);
				ps.setInt(5, lib.id_autore);
				ps.setInt(6, lib.id_genere);
				righeInserite = ps.executeUpdate();
			}
		} catch (Exception e) {
			System.err.println("Si è verificato un errore: " + e.getMessage());
		}
	}

//metodo per ricercare l'autore
	public static void ricercaAutori(String nome) {
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			sql = "SELECT * FROM autori WHERE nome = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, nome);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						System.out.println(
								rs.getString("nome") + " " + rs.getString("cognome") + " id: " + rs.getInt("id"));
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Si è verificato un errore: " + e.getMessage());
		}
	}

	// metodo per ricercare il genere
	public static void ricercaGenere(String nome) {
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			sql = "SELECT * FROM generi WHERE nome = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, nome);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						System.out.println(rs.getString("nome") + " " + " id: " + rs.getInt("id"));
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Si è verificato un errore: " + e.getMessage());
		}
	}

	// metodo per stampare i libri
	public static void stampaLibri() {
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			sql = "SELECT * FROM libri INNER JOIN autori ON libri.id_autore = autori.id INNER JOIN generi ON libri.id_genere = generi.id";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						System.out.println("Titolo: " + rs.getString("nome") + ", Autore: "
								+ rs.getString("autori.nome") + " " + rs.getString("autori.cognome")
								+ ", Data di uscita: " + rs.getDate("anno") + ", Genere: " + rs.getString("generi.nome")
								+ ", Codice ISBN: " + rs.getString("codice") + ", id: " + rs.getInt("id"));
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Si è verificato un errore: " + e.getMessage());
		}
	}

	// metodo per eliminare il libro
	public static void eliminazioneLibro(int id) {
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			sql = "DELETE FROM libri WHERE id = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, id);
				int righeEliminate = ps.executeUpdate();
				System.out.println("libro eliminato con successo");

			}
		} catch (Exception e) {
			System.err.println("Si è verificato un errore: " + e.getMessage());
		}
	}

	// metodo per ricercare il libro
	public static void ricercaLibro(String nome) {
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			sql = "SELECT * FROM libri INNER JOIN autori ON libri.id_autore = autori.id INNER JOIN generi ON libri.id_genere = generi.id WHERE libri.nome  = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, nome);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						System.out.println("Titolo: " + rs.getString("nome") + ", Autore: "
								+ rs.getString("autori.nome") + " " + rs.getString("autori.cognome")
								+ ", Data di uscita: " + rs.getDate("anno") + ", Genere: " + rs.getString("generi.nome")
								+ ", Codice ISBN: " + rs.getString("codice") + ", id: " + rs.getInt("id"));
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Si è verificato un errore: " + e.getMessage());
		}
	}

	// metodo per modifica libro
	public static void modificaLibro(Libro lib, int id) {
		try (Connection conn = DriverManager.getConnection(url, "root", "")) {
			sql = "UPDATE libri SET anno=?,codice=?,nome=?,giacenza=?,id_autore=?,id_genere=? WHERE id=?";
			int righeInserite = 0;
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setDate(1, Date.valueOf(lib.anno));
				ps.setString(2, lib.codice);
				ps.setString(3, lib.nome);
				ps.setInt(4, lib.giacenza);
				ps.setInt(5, lib.id_autore);
				ps.setInt(6, lib.id_genere);
				ps.setInt(7, id);
				righeInserite = ps.executeUpdate();
				System.out.println("il libro è stato modificato");
			}
		} catch (Exception e) {
			System.err.println("Si è verificato un errore: " + e.getMessage());
		}
	}

}