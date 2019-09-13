
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class Viewcourses extends javax.swing.JFrame {

    mytablemodel tm;
    ArrayList<course> al;

    public Viewcourses() {
        initComponents();
        setSize(800, 800);
        setVisible(true);
        tm = new mytablemodel();
        jt.setModel(tm);
        ResultSet rs = JdbcCommon.executeQuery("select * from course");
        al = new ArrayList<>();
        new Thread(new job()).start();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jt = new javax.swing.JTable();
        bt_delete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jt);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 320, 230);

        bt_delete.setText("Delete ");
        bt_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_deleteActionPerformed(evt);
            }
        });
        getContentPane().add(bt_delete);
        bt_delete.setBounds(67, 260, 160, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_deleteActionPerformed
        int r = jt.getSelectedRow();
        if (r == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please Select Something");
        } else {
            int k = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete?");
            if (k == JOptionPane.YES_OPTION) {
                try {
                    String cname = al.get(r).name;
                    HttpResponse res = Unirest.get("http://"+GlobalData.host+"/deletecourse").queryString("coursed", cname).asString();
                    if (res.getBody().equals("success")) {
                        new Thread(new job()).start();

                    } else {

                        JOptionPane.showMessageDialog(rootPane, "Failed");

                    }

                } catch (UnirestException ex) {
                    ex.printStackTrace();
                }

            } else {

            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_bt_deleteActionPerformed

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
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Viewcourses().setVisible(true);
            }
        });
    }

    class mytablemodel extends AbstractTableModel {

        String names[] = {"names", "description", "category", "amount"};

        public String getColumnName(int column) {
            return names[column];

        }

        @Override
        public int getRowCount() {
            return al.size();

        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int row, int col) {
            course c = al.get(row);
            if (col == 0) {
                return c.name;
            } else if (col == 1) {
                return c.description;
            } else if (col == 2) {
                return c.category;
            } else {
                return c.amount;
            }

        }

    }

    class job implements Runnable {

        @Override
        public void run() {
            try {
                HttpResponse<String> httpresponse;

                httpresponse = Unirest.get("http://"+GlobalData.host+"/viewcourses").queryString("", "").asString();

                StringTokenizer st = new StringTokenizer(httpresponse.getBody(), "~");
                al.clear();
                while (st.hasMoreTokens()) {
                    String course = st.nextToken();
                    StringTokenizer st2 = new StringTokenizer(course, ";");
                    String name = st2.nextToken();
                    String des = st2.nextToken();
                    String cat = st2.nextToken();
                    String amount = st2.nextToken();
                    al.add(new course(name, des, cat, amount));
                }
          tm.fireTableDataChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_delete;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jt;
    // End of variables declaration//GEN-END:variables
}
