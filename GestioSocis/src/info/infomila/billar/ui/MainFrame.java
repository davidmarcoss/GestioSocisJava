package info.infomila.billar.ui;

import info.infomila.billar.ipersistence.BillarException;
import info.infomila.billar.ipersistence.BillarFactory;
import info.infomila.billar.ipersistence.IBillar;
import info.infomila.billar.models.EstadisticaModalitat;
import info.infomila.billar.models.Soci;
import info.infomila.billar.models.SociException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends javax.swing.JFrame
{

    private String className;
    private String hibernateConfig;
    private IBillar billar = null;
    private List<Soci> socis;
    private Soci soci = null;
    private int modeSeleccionat = 0;

    public MainFrame()
    {
        initComponents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Controlar la sortida del programa
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                int i = JOptionPane.showConfirmDialog(rootPane,
                        "Estás segur que vols sortir?",
                        "Sortir",
                        JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    try {
                        billar.close();
                        dispose();
                    } catch (BillarException ex) {
                        System.out.println(ex);
                    }
                }
            }
        });

        ObtenirPropietats();

        GetConnexio();
    }

    private void ObtenirPropietats()
    {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            int result = JOptionPane.showConfirmDialog(rootPane,
                    "No s'ha pogut llegir el fitxer de propietats, l'aplicació s'abortarà",
                    "Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            System.exit(0);
        }

        className = p.getProperty("className");
        if (className == null || className.length() == 0) {
            int result = JOptionPane.showConfirmDialog(rootPane,
                    "No s'ha pogut llegir la propietat className del fitxer de propietats, l'aplicació s'abortarà",
                    "Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            System.exit(0);
        }
        
        hibernateConfig = p.getProperty("hibernateConfig");
        if (hibernateConfig == null || hibernateConfig.length() == 0) {
            int result = JOptionPane.showConfirmDialog(rootPane,
                    "No s'ha pogut llegir la propietat hibernateConfig del fitxer de propietats, l'aplicació s'abortarà",
                    "Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            System.exit(0);
        }
    }

    private void GetConnexio()
    {
        try {
            billar = BillarFactory.getInstance(className, hibernateConfig);
            populateTaula(modeSeleccionat);
        } catch (BillarException ex) {
            try {
                if (billar != null) {
                    billar.close();
                }
                int result = JOptionPane.showConfirmDialog(rootPane,
                        "No s'ha pogut establir connexió amb el origen de dades, l'aplicació s'abortarà",
                        "Error",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                System.exit(0);
            } catch (BillarException ex1) {
                System.exit(0);
            }
        }
    }

    private void populateTaula(int mode) throws BillarException
    {
        socis = billar.getSocis(mode);

        DefaultTableModel model = (DefaultTableModel) TaulaSocis.getModel();
        model.setRowCount(0);

        socis.forEach((Soci s) -> {
            String cognom = s.getCognom1() != null ? s.getCognom1() : "";
            String cognom2 = s.getCognom2() != null ? s.getCognom2() : "";
            model.addRow(new Object[]{
                s.getNif(),
                s.getNom() + " " + cognom + " " + cognom2,
                s.getDataAltaString(),
                s.isActiu()
            });
        });
    }

    private void resetForm()
    {
        LabelFoto.setIcon(null);
        InputNIF.setText("");
        InputNom.setText("");
        InputCognom.setText("");
        InputCognom2.setText("");
        InputPassword.setText("");
        CheckBoxActiu.setSelected(false);
    }

    private void populateForm()
    {
        InputNIF.setText(soci.getNif());
        InputNom.setText(soci.getNom());
        InputCognom.setText(soci.getCognom1());
        InputCognom2.setText(soci.getCognom2());
        CheckBoxActiu.setSelected(soci.isActiu());
        if (soci.getFoto() != null) {
            BlobToLabel(soci.getFoto());
        } else {
            LabelFoto.setIcon(null);
        }
        ComboBoxModalitats.removeAllItems();
        Iterator<EstadisticaModalitat> it = soci.iteEstadistiques();
        while (it.hasNext()) {
            ComboBoxModalitats.addItem(it.next().toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        InputNIF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        InputNom = new javax.swing.JTextField();
        InputCognom = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        InputCognom2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        InputPassword = new javax.swing.JPasswordField();
        LabelFoto = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ComboBoxModalitats = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        InputCoeficient = new javax.swing.JTextField();
        BtnCrear = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();
        BtnCambiarFoto = new javax.swing.JButton();
        BtnEliminarFoto = new javax.swing.JButton();
        InputCaramboles = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        InputEntrades = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TaulaSocis = new javax.swing.JTable();
        CheckBoxActiu = new javax.swing.JCheckBox();
        ComboBoxFiltres = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Gestió de Socis");

        jLabel2.setText("NIF");

        jLabel3.setText("Nom");

        jLabel4.setText("Cognom");

        jLabel5.setText("Segon Cognom");

        jLabel6.setText("Password");

        LabelFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel8.setText("Modalitats");

        ComboBoxModalitats.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ComboBoxModalitatsActionPerformed(evt);
            }
        });

        jLabel9.setText("Coeficient");

        InputCoeficient.setEditable(false);

        BtnCrear.setText("Crear");
        BtnCrear.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                BtnCrearMouseClicked(evt);
            }
        });

        BtnGuardar.setText("Guardar");
        BtnGuardar.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                BtnGuardarMouseClicked(evt);
            }
        });

        BtnCancelar.setText("Cancelar");
        BtnCancelar.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                BtnCancelarMouseClicked(evt);
            }
        });

        BtnCambiarFoto.setText("Cambiar");
        BtnCambiarFoto.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                BtnCambiarFotoMouseClicked(evt);
            }
        });

        BtnEliminarFoto.setText("Eliminar");
        BtnEliminarFoto.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                BtnEliminarFotoMouseClicked(evt);
            }
        });

        InputCaramboles.setEditable(false);

        jLabel10.setText("Caramboles Temporada");

        InputEntrades.setEditable(false);

        jLabel11.setText("Entrades Temporada");

        TaulaSocis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "NIF", "Nom", "Data alta", "Actiu"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        TaulaSocis.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                TaulaSocisMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TaulaSocis);

        CheckBoxActiu.setText("Actiu");

        ComboBoxFiltres.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TOTS", "Actius", "No actius" }));
        ComboBoxFiltres.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ComboBoxFiltresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnCrear)
                            .addComponent(ComboBoxFiltres, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BtnCambiarFoto)
                                    .addComponent(BtnEliminarFoto)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(InputPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(InputNom, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(InputCognom, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(InputCognom2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(InputNIF, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ComboBoxModalitats, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(BtnGuardar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(BtnCancelar)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CheckBoxActiu)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(InputCoeficient, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(InputCaramboles, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(InputEntrades, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboBoxFiltres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addComponent(BtnCambiarFoto)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(BtnEliminarFoto))
                                .addComponent(LabelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(InputNIF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(InputNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(InputCognom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(InputCognom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(InputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(ComboBoxModalitats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(InputCoeficient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(InputEntrades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(InputCaramboles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnCancelar)
                    .addComponent(CheckBoxActiu)
                    .addComponent(BtnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TaulaSocisMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_TaulaSocisMouseClicked
    {//GEN-HEADEREND:event_TaulaSocisMouseClicked
        int rowIndex = TaulaSocis.getSelectedRow();
        if (rowIndex >= 0) {
            soci = socis.get(rowIndex);
            populateForm();
        }
    }//GEN-LAST:event_TaulaSocisMouseClicked

    private void BtnGuardarMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_BtnGuardarMouseClicked
    {//GEN-HEADEREND:event_BtnGuardarMouseClicked
        String nif = InputNIF.getText();
        String nom = InputNom.getText();
        String cognom = InputCognom.getText();
        String cognom2 = InputCognom2.getText();
        boolean actiu = CheckBoxActiu.isSelected();
        Icon icon = LabelFoto.getIcon();
        Blob foto = null;
        if (icon != null) foto = ImageToBlob(iconToImage(icon));
        String passwordmd5 = InputPassword.getText();
        if (passwordmd5 != null && !"".equals(passwordmd5)) {
            MessageDigest dg;

            try {
                dg = MessageDigest.getInstance("MD5");
                dg.update(passwordmd5.getBytes());

                BigInteger hash = new BigInteger(1, dg.digest());

                passwordmd5 = hash.toString(16);
                while (passwordmd5.length() < 32) {
                    passwordmd5 = "0" + passwordmd5;
                }
            } catch (NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

        try {
            if (soci == null) {
                Soci newSoci = new Soci(nif, nom, cognom, cognom2, passwordmd5, foto, actiu);
                soci = newSoci;
                try {
                    billar.updateSoci(soci);
                    billar.commit();
                    populateTaula(modeSeleccionat);
                    JOptionPane.showMessageDialog(rootPane, "Soci inserit correctament.");
                } catch (BillarException ex) {
                    try {
                        billar.rollback();
                        populateTaula(modeSeleccionat);
                        JOptionPane.showMessageDialog(rootPane, "Ja hi ha un soci amb aquest DNI");
                    } catch (BillarException ex1) {
                        JOptionPane.showMessageDialog(null, ex1);
                    }
                }
            } else if (soci != null) {
                try {
                    String lastDNI = InputNIF.getText();
                    soci.setFoto(foto);
                    soci.setNif(InputNIF.getText());
                    soci.setNom(InputNom.getText());
                    soci.setCognom1(InputCognom.getText());
                    soci.setCognom2(InputCognom2.getText());
                    soci.setActiu(CheckBoxActiu.isSelected());
                    if (passwordmd5 != null && !"".equals(passwordmd5)) {
                        soci.setPasswordHash(passwordmd5);
                    }

                    try {
                        billar.updateSoci(soci);
                        billar.commit();
                        populateTaula(modeSeleccionat);
                        JOptionPane.showMessageDialog(rootPane, "Soci modificat correctament.");
                    } catch (BillarException ex) {
                        try {
                            billar.rollback();
                            soci.setNif(lastDNI);
                            populateTaula(modeSeleccionat);
                            JOptionPane.showMessageDialog(rootPane, "Ja hi ha un soci amb aquest DNI");
                        } catch (BillarException ex1) {
                            JOptionPane.showMessageDialog(rootPane, "Error al fer el Rollback, abortant aplicació...");
                            dispose();
                        }
                    }
                } catch (SociException sociException) {
                    JOptionPane.showMessageDialog(null, sociException.getMessage());
                }
            }
        } catch (Exception ex ) {
            
        }

        InputPassword.setText("");
    }//GEN-LAST:event_BtnGuardarMouseClicked

    private void BtnCrearMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_BtnCrearMouseClicked
    {//GEN-HEADEREND:event_BtnCrearMouseClicked
        soci = null;
        resetForm();
        TaulaSocis.clearSelection();
    }//GEN-LAST:event_BtnCrearMouseClicked

    private void ComboBoxModalitatsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ComboBoxModalitatsActionPerformed
    {//GEN-HEADEREND:event_ComboBoxModalitatsActionPerformed
        int row = ComboBoxModalitats.getSelectedIndex();
        if (row >= 0 && soci != null) {
            InputCoeficient.setText(soci.getEstadisticaByIndex(row).getCoeficientBase() + "");
            InputCaramboles.setText(soci.getEstadisticaByIndex(row).getCarambolesTemporadaActual() + "");
            InputEntrades.setText(soci.getEstadisticaByIndex(row).getEntradesTemporadaActual() + "");
        } else {
            InputCoeficient.setText("");
            InputCaramboles.setText("");
            InputEntrades.setText("");
        }
    }//GEN-LAST:event_ComboBoxModalitatsActionPerformed

    private void BtnCancelarMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_BtnCancelarMouseClicked
    {//GEN-HEADEREND:event_BtnCancelarMouseClicked
        int result = JOptionPane.showConfirmDialog(rootPane,
                "Estàs segur que vols cancelar la edició del soci?",
                "Cancelar edició",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            if (soci != null) {
                populateForm();
            } else {
                resetForm();
            }
        }
    }//GEN-LAST:event_BtnCancelarMouseClicked

    private void BtnCambiarFotoMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_BtnCambiarFotoMouseClicked
    {//GEN-HEADEREND:event_BtnCambiarFotoMouseClicked
        JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            MostrarImatgeLabel(file);
        }
    }//GEN-LAST:event_BtnCambiarFotoMouseClicked

    private void BtnEliminarFotoMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_BtnEliminarFotoMouseClicked
    {//GEN-HEADEREND:event_BtnEliminarFotoMouseClicked
        if (LabelFoto.getIcon() != null) {
            LabelFoto.setIcon(null);
        }
    }//GEN-LAST:event_BtnEliminarFotoMouseClicked

    private void ComboBoxFiltresActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ComboBoxFiltresActionPerformed
    {//GEN-HEADEREND:event_ComboBoxFiltresActionPerformed
        int row = ComboBoxFiltres.getSelectedIndex();
        modeSeleccionat = row;
        try {
            populateTaula(modeSeleccionat);
        } catch (BillarException ex) {
            int result = JOptionPane.showConfirmDialog(rootPane,
                    "No s'ha pogut obtenir la llista de socis",
                    "Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_ComboBoxFiltresActionPerformed

    public void MostrarImatgeLabel(File file)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(e);
        }
        if (img != null) {
            Image dimg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            LabelFoto.setIcon(new ImageIcon(dimg));
        }
    }

    public void BlobToLabel(Blob blob)
    {
        try {
            InputStream in;

            in = blob.getBinaryStream();
            BufferedImage img = ImageIO.read(in);
            if (img != null) {
                Image dimg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                LabelFoto.setIcon(new ImageIcon(dimg));
            }
        } catch (SQLException | IOException ex) {
            System.out.println("Error populate imatge");
        }
    }

    public Blob ImageToBlob(Image img)
    {
        SerialBlob blob = null;

        try {
            BufferedImage o = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            Graphics2D bGr = o.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(o, "jpg", b);
            b.flush();
            byte[] byts = b.toByteArray();
            b.close();
            blob = new SerialBlob(byts);
        } catch (IOException | SQLException ex) {
            System.out.println("");
        }

        return blob;
    }

    private Image iconToImage(Icon icon)
    {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            icon.paintIcon(null, image.getGraphics(), 0, 0);
            return image;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCambiarFoto;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnCrear;
    private javax.swing.JButton BtnEliminarFoto;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JCheckBox CheckBoxActiu;
    private javax.swing.JComboBox<String> ComboBoxFiltres;
    private javax.swing.JComboBox<String> ComboBoxModalitats;
    private javax.swing.JTextField InputCaramboles;
    private javax.swing.JTextField InputCoeficient;
    private javax.swing.JTextField InputCognom;
    private javax.swing.JTextField InputCognom2;
    private javax.swing.JTextField InputEntrades;
    private javax.swing.JTextField InputNIF;
    private javax.swing.JTextField InputNom;
    private javax.swing.JPasswordField InputPassword;
    private javax.swing.JLabel LabelFoto;
    private javax.swing.JTable TaulaSocis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
