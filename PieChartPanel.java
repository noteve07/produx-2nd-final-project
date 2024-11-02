
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


class PieChartPanel extends JPanel {

    // Category data
    private String[] categories = {"Electronics", "Food", "Accessories", "Office Supplies"};
    private int[] counts = {6, 6, 2, 1};
    private Color[] colors = {new Color(72, 145, 220), new Color(232, 77, 98), new Color(255, 165, 0), new Color(109, 191, 115)};
    private String title = "Category Distribution";

    // Constructor to set up the panel
    public PieChartPanel() {
        setPreferredSize(new Dimension(250, 200));
        setBackground(new Color(178, 214, 203)); // Background color RGB(178, 214, 203)
        setBorder(BorderFactory.createLineBorder(new Color(130, 184, 167))); // Border color RGB(130, 184, 167)
    }

    // Method to draw the pie chart
    public void drawPieChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the width and height of the panel
        int width = getWidth();
        int height = getHeight();
        int minSize = Math.min(width, height);
        int cx = width / 2;
        int cy = height / 2;
        int radius = minSize / 3; // Adjusted radius for a smaller pie chart

        // Draw title
        g2d.setColor(new Color(84, 84, 84));
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics metrics = g2d.getFontMetrics();
        int titleWidth = metrics.stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, 15);

        // Draw pie slices
        double total = 0;
        for (int count : counts) {
            total += count;
        }

        double startAngle = 0;
        for (int i = 0; i < counts.length; i++) {
            double arcAngle = (counts[i] / total) * 360;
            g2d.setColor(colors[i]);
            g2d.fill(new Arc2D.Double(cx - radius, cy - radius, 2 * radius, 2 * radius, startAngle, arcAngle, Arc2D.PIE));

            // Calculate midpoint angle of the slice
            double midAngle = Math.toRadians(startAngle + arcAngle / 2);
            int labelRadius = radius + 20;
            int labelX = (int) (cx + labelRadius * Math.cos(midAngle));
            int labelY = (int) (cy + labelRadius * Math.sin(midAngle));

            // Draw percentage text
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            String percentageText = String.format("%.1f%%", (counts[i] / total) * 100);
            g2d.drawString(percentageText, labelX - g2d.getFontMetrics().stringWidth(percentageText) / 2, labelY + 5);

            // Draw category text
            String categoryText = categories[i];
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(categoryText, labelX - g2d.getFontMetrics().stringWidth(categoryText) / 2, labelY - 5);

            // Draw arrow-like line indicator
            int arrowLength = 15;
            int arrowX = (int) (cx + (radius + arrowLength) * Math.cos(midAngle));
            int arrowY = (int) (cy + (radius + arrowLength) * Math.sin(midAngle));
            g2d.drawLine(cx, cy, arrowX, arrowY);

            startAngle += arcAngle;
        }
    }

    // Override the paintComponent method to call drawPieChart
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPieChart(g);
    }
}
