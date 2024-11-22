
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {

    public void save(String title, String content) throws SQLException {
        String query = "INSERT INTO notes (title, content) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.executeUpdate();
        }
    }

    public void update(int id, String title, String content) throws SQLException {
        String query = "UPDATE notes SET title = ?, content = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM notes WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<String> getTitles() throws SQLException {
        List<String> titles = new ArrayList<>();
        String query = "SELECT title FROM notes";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                titles.add(rs.getString("title"));
            }
        }
        return titles;
    }

    public String[] getNoteByTitle(String title) throws SQLException {
        String query = "SELECT id, title, content FROM notes WHERE title = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new String[]{
                        String.valueOf(rs.getInt("id")), // Mengambil ID
                        rs.getString("title"), // Mengambil judul
                        rs.getString("content") // Mengambil isi
                    };
                }
            }
        }
        return null;
    }

}
