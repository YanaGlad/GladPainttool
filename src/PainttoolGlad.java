import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PainttoolGlad {

    private int xPos, yPos, xFinal, yFinal, rez = 0;
    private boolean pressed = false;
    private Color mainColor;
    private final MyFrame myFrame;
    private final MyPanel panel;
    private final JButton colorButton;
    private final JColorChooser colorChooser;

    BufferedImage imag;

    private boolean loading = false;
    private String fileName;

    private PainttoolGlad() {
        myFrame = new MyFrame("PaintTool Glad");
        myFrame.setSize(1000, 700);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainColor = Color.black;

        JMenuBar menuBar = new JMenuBar();
        myFrame.setJMenuBar(menuBar);
        menuBar.setBounds(30, 0, 350, 30);

        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");

        JCheckBoxMenuItem line = new JCheckBoxMenuItem("Show tools");
        JCheckBoxMenuItem grid = new JCheckBoxMenuItem("Show options");
        JCheckBoxMenuItem navig = new JCheckBoxMenuItem("Navigation");
        JCheckBoxMenuItem full = new JCheckBoxMenuItem("Full Screen");

        viewMenu.add(line);
        viewMenu.add(grid);
        viewMenu.add(navig);
        viewMenu.add(full);


        JMenu exitMenu = new JMenu("Options");
        JMenuItem exit = new JMenuItem(new ExitAction());
        exit.setIcon(new ImageIcon("D://exit.png"));
        exitMenu.add(exit);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(exitMenu);


        Action loadAction = new AbstractAction("Load", new ImageIcon("res\\open.png")) {
            public void actionPerformed(ActionEvent event) {
                JFileChooser fileChooser = new JFileChooser();

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        fileName = fileChooser.getSelectedFile().getAbsolutePath();
                        File iF = new File(fileName);

                        fileChooser.addChoosableFileFilter(new TextFileFilter(".png"));
                        fileChooser.addChoosableFileFilter(new TextFileFilter(".jpg"));
                        
                        imag = ImageIO.read(iF);
                        loading = true;

                        if (imag.getWidth() > myFrame.getWidth())
                            myFrame.setSize(imag.getWidth() + 40, imag.getWidth() + 80);

                        panel.setSize(imag.getWidth(), imag.getWidth());
                        panel.repaint();

                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(myFrame, "This file does not exist");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(myFrame, "Input/output exception");
                    }
                }
            }
        };

        JMenuItem loadMenu = new JMenuItem(loadAction);
        fileMenu.add(loadMenu);

        Action saveAction = new AbstractAction("Save", new ImageIcon("res\\save.png")) {
            public void actionPerformed(ActionEvent event) {
                try {
                    JFileChooser jf = new JFileChooser();
                    TextFileFilter pngFilter = new TextFileFilter(".png");
                    TextFileFilter jpgFilter = new TextFileFilter(".jpg");

                    if (fileName == null) {
                        jf.addChoosableFileFilter(pngFilter);
                        jf.addChoosableFileFilter(jpgFilter);

                        int result = jf.showSaveDialog(null);

                        if (result == JFileChooser.APPROVE_OPTION)
                            fileName = jf.getSelectedFile().getAbsolutePath();

                    }
                    if (jf.getFileFilter() == pngFilter)
                        ImageIO.write(imag, "png", new File(fileName + ".png"));
                     else
                        ImageIO.write(imag, "jpeg", new File(fileName + ".jpg"));

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(myFrame, "Input/output exception");
                }
            }
        };

        JMenuItem saveMenu = new JMenuItem(saveAction);
        fileMenu.add(saveMenu);

        Action saveasAction = new AbstractAction("Save as...", new ImageIcon("res\\saveas.png")) {
            public void actionPerformed(ActionEvent event) {
                try {
                    JFileChooser fileChooser = new JFileChooser();

                    TextFileFilter pngFilter = new TextFileFilter(".png");
                    TextFileFilter jpgFilter = new TextFileFilter(".jpg");

                    fileChooser.addChoosableFileFilter(pngFilter);
                    fileChooser.addChoosableFileFilter(jpgFilter);

                    int result = fileChooser.showSaveDialog(null);

                    if (result == JFileChooser.APPROVE_OPTION)
                        fileName = fileChooser.getSelectedFile().getAbsolutePath();


                    if (fileChooser.getFileFilter() == pngFilter)
                        ImageIO.write(imag, "png", new File(fileName + ".png"));
                    else
                        ImageIO.write(imag, "jpeg", new File(fileName + ".jpg"));

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(myFrame, "Input/output exception");
                }
            }
        };

        JMenuItem saveasMenu = new JMenuItem(saveasAction);
        fileMenu.add(saveasMenu);

        panel = new MyPanel();
        panel.setBounds(50, 50, 260, 260);
        panel.setBackground(Color.white);
        panel.setOpaque(true);
        myFrame.add(panel);

        JToolBar toolBar = new JToolBar("Toolbar", JToolBar.VERTICAL);


        JButton penButton = new JButton(new ImageIcon("res\\pen.png"));
        penButton.addActionListener(event -> rez = 0);
        toolBar.add(penButton);

        JButton brushButton = new JButton(new ImageIcon("res\\brush.png"));
        brushButton.addActionListener(event -> rez = 1);
        toolBar.add(brushButton);


        JButton pencil = new JButton(new ImageIcon("res\\pencil.png"));
        pencil.addActionListener(e -> rez = 8);
        toolBar.add(pencil);

        JButton eraserButton = new JButton(new ImageIcon("res\\eraser.png"));
        eraserButton.addActionListener(event -> rez = 2);

        JButton textButton = new JButton(new ImageIcon("res\\text.png"));
        textButton.addActionListener(event -> rez = 3);

        JButton lineButton = new JButton(new ImageIcon("res\\line.png"));
        lineButton.addActionListener(event -> rez = 4);

        JButton elipsButton = new JButton(new ImageIcon("res\\elips.png"));
        elipsButton.addActionListener(event -> rez = 5);

        JButton rectbutton = new JButton(new ImageIcon("res\\rect.png"));
        rectbutton.addActionListener(event -> rez = 6);

        toolBar.setBounds(0, 0, 50, 400);
        myFrame.add(toolBar);

        JToolBar colorBar = new JToolBar("Colorbar", JToolBar.HORIZONTAL);
        colorBar.setBounds(650, 0, 300, 30);
        colorButton = new JButton();

        JToolBar textic = new JToolBar("", JToolBar.HORIZONTAL);
        textic.setBounds(310, 0, 55, 35);
        textButton.setBounds(310, 0, 15, 35);
        textic.add(textButton);
        myFrame.add(textic);

        colorButton.setBackground(mainColor);
        colorButton.setBounds(15, 5, 20, 20);
        colorButton.addActionListener(event -> {
            ColorDialog coldi = new ColorDialog(myFrame, "Choose color");

            coldi.setBounds(300, 300, 600, 300);
            coldi.setVisible(true);
            coldi.setLocationRelativeTo(null);
        });
        colorBar.add(colorButton);

        JToolBar penWidth = new JToolBar("Width", JToolBar.HORIZONTAL);
        penWidth.setBounds(510, 0, 130, 30);
        JMenu help = new JMenu("Width");
        JRadioButtonMenuItem first = new JRadioButtonMenuItem("", new ImageIcon("res\\first.png"));
        help.add(first);
        penWidth.add(help);

        JToolBar textStyle = new JToolBar("Text Style", JToolBar.HORIZONTAL);
        textStyle.setBounds(370, 0, 130, 30);
        JMenu style = new JMenu("Text Style");
        textStyle.add(style);

        myFrame.add(textStyle);
        JToolBar figures = new JToolBar("", JToolBar.HORIZONTAL);
        figures.setBounds(130, 0, 170, 40);
        lineButton.setBounds(110, 0, 20, 20);
        figures.add(lineButton);
        rectbutton.setBounds(200, 0, 20, 20);
        figures.add(rectbutton);

        elipsButton.setBounds(150, 0, 20, 20);
        figures.add(elipsButton);
        myFrame.add(figures);
        JToolBar ers = new JToolBar("", JToolBar.HORIZONTAL);
        ers.setBounds(70, 0, 60, 40);
        ers.add(eraserButton);
        myFrame.add(ers);

        JButton redButton = new JButton();
        redButton.setBackground(Color.red);
        redButton.setBounds(40, 5, 15, 15);

        redButton.addActionListener(event -> {
            mainColor = Color.red;
            colorButton.setBackground(mainColor);
        });
        colorBar.add(redButton);

        JButton orangeButton = new JButton();
        orangeButton.setBackground(Color.orange);
        orangeButton.setBounds(60, 5, 15, 15);

        orangeButton.addActionListener(event -> {
            mainColor = Color.orange;
            colorButton.setBackground(mainColor);
        });
        colorBar.add(orangeButton);

        JButton yellowButton = new JButton();
        yellowButton.setBackground(Color.yellow);
        yellowButton.setBounds(80, 5, 15, 15);

        yellowButton.addActionListener(event -> {
            mainColor = Color.yellow;
            colorButton.setBackground(mainColor);
        });
        colorBar.add(yellowButton);

        JButton greenButton = new JButton();
        greenButton.setBackground(Color.green);
        greenButton.setBounds(100, 5, 15, 15);

        greenButton.addActionListener(event -> {
            mainColor = Color.green;
            colorButton.setBackground(mainColor);
        });
        colorBar.add(greenButton);

        JButton blueButton = new JButton();
        blueButton.setBackground(Color.blue);
        blueButton.setBounds(120, 5, 15, 15);

        blueButton.addActionListener(event -> {
            mainColor = Color.blue;
            colorButton.setBackground(mainColor);
        });
        colorBar.add(blueButton);

        JButton cyanButton = new JButton();
        cyanButton.setBackground(Color.cyan);
        cyanButton.setBounds(140, 5, 15, 15);

        cyanButton.addActionListener(event -> {
            mainColor = Color.cyan;
            colorButton.setBackground(mainColor);
        });
        colorBar.add(cyanButton);

        JButton magentaButton = new JButton();
        magentaButton.setBackground(Color.magenta);
        magentaButton.setBounds(160, 5, 15, 15);
        magentaButton.addActionListener(event -> {
            mainColor = Color.magenta;
            colorButton.setBackground(mainColor);
        });
        colorBar.add(magentaButton);

        JButton whiteButton = new JButton();
        whiteButton.setBackground(Color.white);
        whiteButton.setBounds(180, 5, 15, 15);
        whiteButton.addActionListener(event -> {
            mainColor = Color.white;
            colorButton.setBackground(mainColor);
        });
        colorBar.add(whiteButton);

        JButton blackButton = new JButton();
        blackButton.setBackground(Color.black);
        blackButton.setBounds(200, 5, 15, 15);
        blackButton.addActionListener(event -> {
            mainColor = Color.black;
            colorButton.setBackground(mainColor);
        });

        colorBar.add(blackButton);
        colorBar.setLayout(null);
        myFrame.add(colorBar);
        myFrame.add(penWidth);

        colorChooser = new JColorChooser(mainColor);

        colorChooser.getSelectionModel().addChangeListener(e -> {
            mainColor = colorChooser.getColor();
            colorButton.setBackground(mainColor);
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (pressed) {
                    Graphics g = imag.getGraphics();
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(mainColor);
                    switch (rez) {
                        case 0 -> {
                            g2.drawLine(xPos, yPos, e.getX(), e.getY());
                            if (first.isSelected()) {
                                g2.setStroke(new BasicStroke(7.0f));
                                g2.drawLine(xPos, yPos, e.getX(), e.getY());
                            }
                        }
                        case 1 -> {
                            g2.setStroke(new BasicStroke(3.0f));
                            g2.drawLine(xPos, yPos, e.getX(), e.getY());
                            if (first.isSelected()) {
                                g2.setStroke(new BasicStroke(10.0f));
                                g2.drawLine(xPos, yPos, e.getX(), e.getY());
                            }
                        }
                        case 2 -> {
                            g2.setStroke(new BasicStroke(3.0f));
                            g2.setColor(Color.WHITE);
                            g2.drawLine(xPos, yPos, e.getX(), e.getY());
                            if (first.isSelected()) {
                                g2.setStroke(new BasicStroke(10.0f));
                                g2.setColor(Color.WHITE);
                                g2.drawLine(xPos, yPos, e.getX(), e.getY());

                            }
                        }
                        case 8 -> {
                            g2.setStroke(new BasicStroke(3.5f));
                            g2.drawLine(xPos, yPos, xPos + 1, yPos + 1);
                            if (first.isSelected()) {
                                g2.setStroke(new BasicStroke(9.5f));
                                g2.drawLine(xPos, yPos, xPos + 3, yPos + 3);
                            }
                        }
                    }
                    xPos = e.getX();
                    yPos = e.getY();
                }
                panel.repaint();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                Graphics g = imag.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(mainColor);

                switch (rez) {
                    case 0 -> {
                        if (first.isSelected()) g2.setStroke(new BasicStroke(7.0f));
                    }

                    case 1 -> {
                        g2.setStroke(new BasicStroke(3.0f));
                        if (first.isSelected()) g2.setStroke(new BasicStroke(10.0f));
                    }

                    case 2 -> {
                        g2.setStroke(new BasicStroke(3.0f));
                        g2.setColor(Color.WHITE);
                        if (first.isSelected()) {
                            g2.setStroke(new BasicStroke(10.0f));
                            g2.setColor(Color.WHITE);
                        }
                    }

                    case 3 -> panel.requestFocus();

                    case 8 -> {
                        g2.setStroke(new BasicStroke(3.5f));
                        if (first.isSelected()) g2.setStroke(new BasicStroke(9.5f));
                    }
                }

                g2.drawLine(xPos, yPos, xPos + 1, yPos + 1);

                xPos = e.getX();
                yPos = e.getY();

                pressed = true;
                panel.repaint();
            }

            public void mousePressed(MouseEvent e) {
                xPos = e.getX();
                yPos = e.getY();
                xFinal = e.getX();
                yFinal = e.getY();
                pressed = true;
            }

            public void mouseReleased(MouseEvent e) {

                Graphics g = imag.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(mainColor);
                int x1 = xFinal, x2 = xPos, y1 = yFinal, y2 = yPos;
                if (xFinal > xPos) {
                    x2 = xFinal;
                    x1 = xPos;
                }
                if (yFinal > yPos) {
                    y2 = yFinal;
                    y1 = yPos;
                }
                switch (rez) {
                    case 4 -> g.drawLine(xFinal, yFinal, e.getX(), e.getY());
                    case 5 -> g.drawOval(x1, y1, (x2 - x1), (y2 - y1));
                    case 6 -> g.drawRect(x1, y1, (x2 - x1), (y2 - y1));
                }

                xFinal = 0;
                yFinal = 0;
                pressed = false;
                panel.repaint();
            }
        });
        panel.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                panel.requestFocus();
            }

            public void keyTyped(KeyEvent e) {
                if (rez == 3) {
                    Graphics g = imag.getGraphics();
                    Graphics2D g2 = (Graphics2D) g;

                    g2.setColor(mainColor);
                    g2.setStroke(new BasicStroke(2.0f));

                    String str = "";
                    str += e.getKeyChar();
                    g2.setFont(new Font("Arial", Font.PLAIN, 15));
                    g2.drawString(str, xPos, yPos);
                    xPos += 10;

                    panel.requestFocus();
                    panel.repaint();
                }
            }
        });

        myFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {

                if (!loading) {
                    panel.setSize(myFrame.getWidth() - 40, myFrame.getHeight() - 80);
                    BufferedImage tempImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D d2 = tempImage.createGraphics();
                    d2.setColor(Color.white);
                    d2.fillRect(0, 0, panel.getWidth(), panel.getHeight());
                    tempImage.setData(imag.getRaster());
                    imag = tempImage;
                    panel.repaint();
                }
                loading = false;
            }
        });
        myFrame.setLayout(null);
        myFrame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PainttoolGlad::new);
    }

    class ColorDialog extends JDialog {
        public ColorDialog(JFrame owner, String title) {
            super(owner, title, true);
            add(colorChooser);
            setSize(200, 200);
        }
    }

    static class MyFrame extends JFrame {
        public void paint(Graphics g) {
            super.paint(g);
        }

        public MyFrame(String title) {
            super(title);
        }
    }

    class MyPanel extends JPanel {
        public MyPanel() {
        }

        public void paintComponent(Graphics g) {
            if (imag == null) {
                imag = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = imag.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            super.paintComponent(g);
            g.drawImage(imag, 0, 0, this);
        }
    }


}