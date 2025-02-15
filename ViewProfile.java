
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sakshi
 */
public class ViewProfile extends javax.swing.JFrame {

    String email;

    /**
     * Creates new form ViewProfile
     */
    public ViewProfile(String email) {
        initComponents();
        this.email = email;

        setSize(800, 800);

        jp2.setPreferredSize(new Dimension(750, 800));

        new Thread(new job_profile()).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jp1 = new javax.swing.JPanel();
        lb_photo = new javax.swing.JLabel();
        lb_name = new javax.swing.JLabel();
        lb_phone = new javax.swing.JLabel();
        lb_email = new javax.swing.JLabel();
        jsp1 = new javax.swing.JScrollPane();
        jp2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jp1.setBackground(new java.awt.Color(204, 204, 0));
        jp1.setLayout(null);

        lb_photo.setText("photo");
        lb_photo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jp1.add(lb_photo);
        lb_photo.setBounds(30, 50, 90, 90);

        lb_name.setText("name");
        jp1.add(lb_name);
        lb_name.setBounds(130, 60, 190, 20);

        lb_phone.setText("phone");
        jp1.add(lb_phone);
        lb_phone.setBounds(130, 90, 160, 16);

        lb_email.setText("email");
        jp1.add(lb_email);
        lb_email.setBounds(130, 120, 160, 20);

        getContentPane().add(jp1);
        jp1.setBounds(0, 0, 960, 200);

        jp2.setBackground(new java.awt.Color(255, 255, 102));
        jp2.setForeground(new java.awt.Color(255, 255, 153));
        jp2.setLayout(null);
        jsp1.setViewportView(jp2);

        getContentPane().add(jsp1);
        jsp1.setBounds(0, 200, 910, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewProfile("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jp1;
    private javax.swing.JPanel jp2;
    private javax.swing.JScrollPane jsp1;
    private javax.swing.JLabel lb_email;
    private javax.swing.JLabel lb_name;
    private javax.swing.JLabel lb_phone;
    private javax.swing.JLabel lb_photo;
    // End of variables declaration//GEN-END:variables
 class job_profile implements Runnable {

        @Override
        public void run() {
            try {
                HttpResponse res = Unirest.get("http://"+GlobalData.host+"/fetchprofile").queryString("email", email).asString();
                if (res.getBody().equals("fail")) {
                    JOptionPane.showMessageDialog(rootPane, "fail");
                } else {
                    StringTokenizer st = new StringTokenizer(res.getBody().toString(), ";");
                    String name = st.nextToken();
                    String phone = st.nextToken();
                    String email = st.nextToken();
                    String photo = st.nextToken();
                    lb_email.setText(email);
                    lb_name.setText(name);
                    lb_phone.setText(phone);

                    System.out.println(photo);
                    BufferedImage myimage = ImageIO.read(new URL("http://"+GlobalData.host+"/GetSource/" + photo));
                    lb_photo.setIcon(new ImageIcon(myimage));
                    new Thread(new job_sub()).start();

                }
            } catch (UnirestException ex) {
                Logger.getLogger(ViewProfile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ViewProfile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ViewProfile.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    class job_sub implements Runnable {

        int x = 20, y = 20;

        @Override
        public void run() {
            try {

                HttpResponse res = Unirest.get("http://"+GlobalData.host+"/sub").queryString("email", email).asString();
                StringTokenizer st2 = new StringTokenizer(res.getBody().toString(), "~");
                int count = st2.countTokens();
                SingleCoursePanel array[] = new SingleCoursePanel[count];
                for (int i = 0; i < count; i++) {
                    String one = st2.nextToken();
                    System.out.println("one" + one);
                    StringTokenizer st3;
                    st3 = new StringTokenizer(one, ";");
                    System.out.println("st3" + st3);
                    String coursen = st3.nextToken();
                    System.out.println("coursen" + coursen);
                    String photo = st3.nextToken();
                    System.out.println("pic" + photo);
                    BufferedImage myimage = ImageIO.read(new URL("http://"+GlobalData.host+"/GetSource/" + photo));
                    array[i] = new SingleCoursePanel();
                    array[i].setBounds(x, y, 171, 150);
                    array[i].lb1.setIcon(new ImageIcon(myimage));
                    array[i].lb2.setText(coursen);
                    array[i].lb3.setText("");
                    jp2.add(array[i]);
                    jp2.repaint();

                    if (x > 500) {
                        y = y + 170;
                        x = 20;
                    } else {
                        x = x + 190;

                    }

                }

            } catch (UnirestException ex) {
                Logger.getLogger(ViewProfile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ViewProfile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ViewProfile.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
