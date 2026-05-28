import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// 1. Changed to extends JPanel
public class chooseMap extends JPanel {

    private MainDisplayPanel mainDisplay;
    private MainGame mainGame;

    // 2. Pass MainGame into the constructor
    public chooseMap(MainGame mainGame) {
        this.mainGame = mainGame;
        setLayout(new BorderLayout());

        // Initialize the big main display
        mainDisplay = new MainDisplayPanel();
        add(mainDisplay, BorderLayout.CENTER);

        // Initialize the thumbnail container
        JPanel thumbnailContainer = new JPanel();
        thumbnailContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));
        thumbnailContainer.setBackground(Color.DARK_GRAY);

        // 3. Updated paths to use your relative assets folder!
        MapThumbnail map1 = new MapThumbnail("Senate Floor", "assets/4.png");
        MapThumbnail map2 = new MapThumbnail("Malacañang", "assets/5.png");
        MapThumbnail map3 = new MapThumbnail("ICC Exterior", "assets/6.png");
        MapThumbnail map4 = new MapThumbnail("Divisoria", "assets/7.png");

        thumbnailContainer.add(map1);
        thumbnailContainer.add(map2);
        thumbnailContainer.add(map3);
        thumbnailContainer.add(map4);

        add(thumbnailContainer, BorderLayout.SOUTH);
        
        // Set initial map
        mainDisplay.updateDisplay("Senate Floor", "assets/4.png");
    }

    private static void drawProportionalImage(Graphics2D g2d, BufferedImage image, int panelWidth, int panelHeight) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        double scaleX = (double) panelWidth / imgWidth;
        double scaleY = (double) panelHeight / imgHeight;
        double scale = Math.max(scaleX, scaleY); 
        int scaledWidth = (int) (imgWidth * scale);
        int scaledHeight = (int) (imgHeight * scale);
        int x = (panelWidth - scaledWidth) / 2;
        int y = 0; 
        g2d.drawImage(image, x, y, scaledWidth, scaledHeight, null);
    }

    class MainDisplayPanel extends JPanel {
        private String currentMapName = "";
        private BufferedImage currentMapImage;

        public MainDisplayPanel() {
            setPreferredSize(new Dimension(850, 480));
        }

        public void updateDisplay(String name, String imagePath) {
            this.currentMapName = name;
            try {
                this.currentMapImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                System.out.println("Could not load display image: " + imagePath);
                this.currentMapImage = null; 
            }
            repaint(); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            if (currentMapImage != null) {
                drawProportionalImage(g2d, currentMapImage, getWidth(), getHeight());
            } else {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, getHeight() - 60, getWidth(), 60);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 36));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth("Selected Area: " + currentMapName);
            g2d.drawString("Selected Area: " + currentMapName, (getWidth() - textWidth) / 2, getHeight() - 20);
        }
    }

    class MapThumbnail extends JPanel {
        private String mapName;
        private String imagePath;
        private BufferedImage thumbImage;
        private boolean isHovered = false;

        public MapThumbnail(String mapName, String imagePath) {
            this.mapName = mapName;
            this.imagePath = imagePath;
            
            try {
                this.thumbImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                System.out.println("Could not load thumbnail image: " + imagePath);
            }
            
            setPreferredSize(new Dimension(180, 120)); 
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    mainDisplay.updateDisplay(mapName, imagePath);
                    repaint(); 
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    repaint(); 
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // 4. TRIGGER THE NEXT SCREEN!
                    mainGame.goToCharacterSelection(imagePath);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            if (thumbImage != null) {
                drawProportionalImage(g2d, thumbImage, getWidth(), getHeight());
            } else {
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, getHeight() - 25, getWidth(), 25);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
            g2d.drawString(mapName, 5, getHeight() - 8);

            if (isHovered) {
                g2d.setColor(new Color(255, 215, 64)); 
                g2d.setStroke(new BasicStroke(5));
                g2d.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
            } else {
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
            }
        }
    }
}