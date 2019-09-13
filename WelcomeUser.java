
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sakshi
 */
public class WelcomeUser extends javax.swing.JFrame {

    String email;
    int width;

    /**
     * Creates new form WelcomeUser
     */
    public WelcomeUser(String email) {
        initComponents();
        width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setSize(width, height);
        this.email = email;
        lb1.setText("Welcome "+email);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final PanelSlider42<JPanel> slider = new PanelSlider42<JPanel>(jPanel2);
                JPanel basePanel = slider.getBasePanel();
                String images[] = {"src" + File.separator + "SliderPics" + File.separator + "VOD1.jpg", "src" + File.separator + "SliderPics" + File.separator + "VOD2.png", "src" + File.separator + "SliderPics" + File.separator + "VOD3.gif"};

                for (int i = 0; i < images.length; i++) {
                    try {
                        JLabel lb = new JLabel();

                        BufferedImage image = ImageIO.read(new File(images[i]));
                        BufferedImage img = resize(image, width, 270);
                        lb.setIcon(new ImageIcon(img));
                        slider.addComponent(lb);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                slider.test();
                jPanel2.add(basePanel);

            }
        }).start();
        new Thread(new job_welcome()).start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lb1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        p3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(204, 204, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(null);

        lb1.setFont(new java.awt.Font("Krinkes Regular PERSONAL USE", 3, 48)); // NOI18N
        lb1.setText("Welcome");
        jPanel1.add(lb1);
        lb1.setBounds(10, -20, 500, 140);

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setFont(new java.awt.Font("Krinkes Regular PERSONAL USE", 3, 36)); // NOI18N
        jButton1.setText("Change Password");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(760, 10, 260, 70);

        jButton2.setBackground(new java.awt.Color(204, 204, 255));
        jButton2.setFont(new java.awt.Font("Krinkes Regular PERSONAL USE", 3, 36)); // NOI18N
        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(1260, 10, 130, 70);

        jButton3.setBackground(new java.awt.Color(204, 204, 255));
        jButton3.setFont(new java.awt.Font("Krinkes Regular PERSONAL USE", 0, 36)); // NOI18N
        jButton3.setText("View Profile");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(1030, 10, 210, 70);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1490, 90);

        jPanel2.setBackground(new java.awt.Color(102, 102, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(null);
        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 90, 1490, 210);

        p3.setBackground(new java.awt.Color(255, 204, 0));
        p3.setLayout(null);
        jScrollPane1.setViewportView(p3);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 300, 1440, 520);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
        Login obj = new Login();
        obj.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
ChangePass obj=new ChangePass(email);
obj.setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
ViewProfile obj=new ViewProfile(email);  
obj.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(WelcomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WelcomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WelcomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WelcomeUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //   new WelcomeUser("").setVisible(true);
            }
        });
    }

    class job_welcome implements Runnable {

        @Override
        public void run() {
            jScrollPane1.setPreferredSize(new Dimension(700, 500));
            p3.setPreferredSize(new Dimension(700, 380 * GlobalData.category.length));

            SingleCategoryPanel sp[] = new SingleCategoryPanel[GlobalData.category.length];
            int y = 10;
            for (int i = 0; i < GlobalData.category.length; i++) {
                sp[i] = new SingleCategoryPanel();
                sp[i].setBounds(10, y, width - 50, 270);
                sp[i].lb1.setText("Course Under " + GlobalData.category[i]+":");
                p3.add(sp[i]);
                y = y + 310;
                repaint();
                try {

                    HttpResponse<String> res = Unirest.get("http://"+GlobalData.host+"/fetchcategory").queryString("category", GlobalData.category[i]).asString();
                    StringTokenizer st = new StringTokenizer(res.getBody(), "~");
                    int count = st.countTokens();
                    SingleCoursePanel sp2[] = new SingleCoursePanel[count];
                    int x = 10;
                    for (int j = 0; j < count; j++) {
                        String test = st.nextToken();
                        StringTokenizer st2 = new StringTokenizer(test, ";");
                        String photo = st2.nextToken();
                        String coursename = st2.nextToken();
                        int amount = Integer.parseInt(st2.nextToken());
                        sp2[j] = new SingleCoursePanel();
                        sp2[j].addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                FullCourseView obj = new FullCourseView(coursename, email);
                                obj.setVisible(true);

                            }

                        }
                        );

                        BufferedImage myimage = ImageIO.read(new URL("http://"+GlobalData.host+"/GetSource/" + photo));
                        BufferedImage newimage = resize(myimage, sp2[j].lb1.getWidth(), sp2[j].lb1.getHeight());
                        sp2[j].lb1.setIcon(new ImageIcon(newimage));
                        sp2[j].lb2.setText("Coursename: " + coursename);
                        sp2[j].lb3.setText("Price: " + "Rs" + amount + "");
                        sp2[j].setBounds(x, 60, 147, 185);
                        sp[i].add(sp2[j]);
                        x = x + 200;
                        repaint();

                    }

                } catch (UnirestException ex) {
                    Logger.getLogger(WelcomeUser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(WelcomeUser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(WelcomeUser.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb1;
    private javax.swing.JPanel p3;
    // End of variables declaration//GEN-END:variables

    BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }

}
