
# Aplikasi Catatan Harian



# Deskripsi
Aplikasi Catatan Harian adalah aplikasi sederhana untuk membuat, membaca, memperbarui, dan menghapus (CRUD) catatan harian. Pengguna dapat menambahkan catatan baru dengan judul dan isi, melihat daftar catatan yang sudah dibuat, serta mengedit atau menghapus catatan yang diinginkan.

Aplikasi ini dirancang untuk membantu pengguna mencatat informasi penting secara terstruktur, dengan fitur tambahan seperti pencarian catatan berdasarkan judul dan kemampuan mencetak catatan ke dalam format PDF.
# Features

- CRUD Catatan: Tambahkan, lihat, edit, dan hapus catatan.
- Pencarian: Cari catatan berdasarkan judul menggunakan kata kunci.
- Penyimpanan Lokal: Semua catatan disimpan di database MySQL.
- Cetak PDF: Kemampuan mencetak catatan yang sudah dibuat ke dalam file PDF.
- Antarmuka Pengguna: GUI yang sederhana dan mudah digunakan, dibuat menggunakan Java Swing.

# Teknologi yang digunakan
- Java Swing: Untuk antarmuka pengguna.
- MySQL: Untuk menyimpan data catatan.
- iText Library: Untuk mencetak laporan catatan ke dalam PDF.


# Komponen GUI

Aplikasi ini menggunakan beberapa komponen GUI utama, di antaranya:

- JFrame
- JPanel
- JLabel
- JTextField
- JButton
- JComboBox
- JTable
- JScrollPane


# Dokumentasi Code

## AplikasiCatatanHarianForm.java

```java


import javax.swing.DefaultListModel; // Untuk DefaultListModel
import javax.swing.JOptionPane; // Untuk JOptionPane
import java.util.List; // Untuk List
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.List;

public class AplikasiCatatanHarianForm extends javax.swing.JFrame {

    /**
     * Creates new form AplikasiCatatanHarianForm
     */
    public AplikasiCatatanHarianForm() {
        initComponents();
        loadTitles();

        listTitles.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listTitlesValueChanged(evt);
            }
        });

    }

    private void searchNotesByTitle(String keyword) {
        try {
            DefaultListModel<String> model = new DefaultListModel<>();
            NoteDAO dao = new NoteDAO();

            // Ambil semua judul dari database
            List<String> titles = dao.getTitles();

            // Filter judul berdasarkan keyword
            for (String title : titles) {
                if (title.toLowerCase().contains(keyword.toLowerCase())) { // Pencarian case-insensitive
                    model.addElement(title);
                }
            }

            // Tampilkan hasil pencarian di JList
            listTitles.setModel(model);

            if (model.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada hasil pencarian untuk: " + keyword);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat mencari: " + e.getMessage());
        }
    }

    private void exportToPDF() {
        try {
            // Buat dokumen PDF
            Document document = new Document();

            // Tentukan lokasi penyimpanan file PDF
            String filePath = "CatatanHarian.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            // Buka dokumen untuk menulis
            document.open();

            // Tambahkan judul ke PDF
            document.add(new Paragraph("Catatan Harian"));
            document.add(new Paragraph("\n"));

            // Ambil data dari database
            NoteDAO dao = new NoteDAO();
            List<String> titles = dao.getTitles();

            for (String title : titles) {
                // Ambil data catatan berdasarkan judul
                String[] noteData = dao.getNoteByTitle(title);
                if (noteData != null) {
                    document.add(new Paragraph("Judul: " + noteData[1]));  // Judul
                    document.add(new Paragraph("Isi: " + noteData[2]));    // Isi
                    document.add(new Paragraph("\n"));
                }
            }

            // Tutup dokumen
            document.close();

            JOptionPane.showMessageDialog(this, "PDF berhasil disimpan di: " + filePath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat mencetak PDF: " + e.getMessage());
        }
    }

    private void listTitlesValueChanged(javax.swing.event.ListSelectionEvent evt) {
        String selectedTitle = listTitles.getSelectedValue();
        if (selectedTitle != null && !evt.getValueIsAdjusting()) {
            try {
                NoteDAO dao = new NoteDAO();
                String[] noteData = dao.getNoteByTitle(selectedTitle);

                if (noteData != null) {
                    // Mengisi field dengan data yang diambil
                    txtTitle.setText(noteData[1]); // Judul
                    txtContent.setText(noteData[2]); // Isi
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void loadTitles() {
        try {
            DefaultListModel<String> model = new DefaultListModel<>();
            NoteDAO dao = new NoteDAO();
            List<String> titles = dao.getTitles();
            for (String title : titles) {
                model.addElement(title);
            }
            listTitles.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtTitle.setText("");
        txtContent.setText("");
        listTitles.clearSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtContent = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        listTitles = new javax.swing.JList<>();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnExportPDF = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aplikasi Catatan Harian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 36))); // NOI18N

        jLabel1.setText("Judul:");

        jLabel2.setText("Isi Catatan:");

        txtContent.setColumns(20);
        txtContent.setRows(5);
        jScrollPane1.setViewportView(txtContent);

        jScrollPane2.setViewportView(listTitles);

        btnSave.setText("Simpan");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setText("Edit");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Hapus");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnExportPDF.setText("Eksport Data");
        btnExportPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportPDFActionPerformed(evt);
            }
        });

        btnSearch.setText("Cari Judul");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnReset.setText("Reset List Judul");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(90, 90, 90)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear)
                        .addGap(26, 26, 26)
                        .addComponent(btnExportPDF)
                        .addGap(18, 18, 18)
                        .addComponent(btnReset)
                        .addGap(0, 31, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                            .addComponent(txtTitle))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnClear)
                    .addComponent(btnExportPDF)
                    .addComponent(btnReset))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {                                        
        try {
            String title = txtTitle.getText();
            String content = txtContent.getText();
            NoteDAO dao = new NoteDAO();
            dao.save(title, content);
            JOptionPane.showMessageDialog(this, "Catatan berhasil disimpan!");
            loadTitles();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }                                       

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {                                          
        String selectedTitle = listTitles.getSelectedValue();
        if (selectedTitle != null) {
            try {
                NoteDAO dao = new NoteDAO();
                String[] noteData = dao.getNoteByTitle(selectedTitle);

                // Pastikan noteData tidak null
                if (noteData != null) {
                    int id = Integer.parseInt(noteData[0]); // Ambil ID
                    String title = txtTitle.getText();
                    String content = txtContent.getText();
                    dao.update(id, title, content);
                    JOptionPane.showMessageDialog(this, "Catatan berhasil diupdate!");
                    loadTitles();
                    clearFields();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }                                         

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {                                          
        String selectedTitle = listTitles.getSelectedValue();
        if (selectedTitle != null) {
            try {
                NoteDAO dao = new NoteDAO();
                String[] noteData = dao.getNoteByTitle(selectedTitle);

                // Pastikan noteData tidak null
                if (noteData != null) {
                    int id = Integer.parseInt(noteData[0]); // Ambil ID
                    dao.delete(id);
                    JOptionPane.showMessageDialog(this, "Catatan berhasil dihapus!");
                    loadTitles();
                    clearFields();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }                                         

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {                                         
        clearFields();
    }                                        

    private void btnExportPDFActionPerformed(java.awt.event.ActionEvent evt) {                                             
        exportToPDF();
    }                                            

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {                                          
        String keyword = txtSearch.getText();
        if (!keyword.isEmpty()) {
            searchNotesByTitle(keyword);
        } else {
            JOptionPane.showMessageDialog(this, "Masukkan kata kunci untuk pencarian.");
        }
    }                                         

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {                                         
        loadTitles(); // Memuat ulang semua judul
        txtSearch.setText("");
    }                                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AplikasiCatatanHarianForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AplikasiCatatanHarianForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AplikasiCatatanHarianForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AplikasiCatatanHarianForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AplikasiCatatanHarianForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExportPDF;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listTitles;
    private javax.swing.JTextArea txtContent;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration                   
}


```

## DBConnection.java

```java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/catatan_harian";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


```

## NoteDAO.java

```java


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


```



## Authors
Almas Syauqannanda

2210010479 5B Reg Pagi Banjarmasin



