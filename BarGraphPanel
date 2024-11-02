
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


class BarGraphPanel extends JPanel {

    // Daily profit data
    private double[] profits = {950.00, 1150.00, 800.00, 1050.00, 975.00, 1200.00, 1921.29};
    private String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private String title = "Daily Profit (last 7 days)";
    private int hoveredIndex = -1;

    // Constructor to set up the panel
    public BarGraphPanel() {
        setPreferredSize(new Dimension(250, 200));
        setBackground(new Color(178, 214, 203)); // Background color RGB(133, 151, 153)
        setBorder(new LineBorder(new Color(130, 184, 167)));

        // Add mouse listener for tooltips
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int width = getWidth();
                int barWidth = width / profits.length;
                int x = e.getX();
                hoveredIndex = x / barWidth;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoveredIndex = -1;
                repaint();
            }
        });
    }

    // Method to draw the bar graph
    public void drawGraph(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the width and height of the panel
        int width = getWidth();
        int height = getHeight();

        // Calculate the width of each bar
        int barWidth = width / profits.length;

        // Find the maximum profit for scaling the graph
        double maxProfit = 0;
        for (double profit : profits) {
            if (profit > maxProfit) {
                maxProfit = profit;
            }
        }

        // Draw the bars
        for (int i = 0; i < profits.length; i++) {
            int barHeight = (int) ((profits[i] / maxProfit) * (height - 100)); // Scale bar height
            int x = i * barWidth;
            int y = height - barHeight - 60;

            // Draw bars with light blue color
            g2d.setColor(new Color(72, 145, 220));
            g2d.fillRect(x + 10, y, barWidth - 20, barHeight); // Adjusted bar thickness

            // Draw profit value above each bar
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10)); // Smaller text
            g2d.drawString(String.format("%.2f", profits[i]), x + (barWidth / 2) - 12, y - 5);

            // Draw tooltip if hovered
            if (i == hoveredIndex) {
                String tooltip = String.format("Profit: %.2f", profits[i]);
                FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
                int tooltipWidth = metrics.stringWidth(tooltip) + 10;
                int tooltipHeight = metrics.getHeight() + 4;
                int tooltipX = x + (barWidth - tooltipWidth) / 2;
                int tooltipY = y - tooltipHeight - 5;

                g2d.setColor(new Color(255, 255, 200));
                g2d.fillRect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                g2d.drawString(tooltip, tooltipX + 5, tooltipY + metrics.getAscent());
            }
        }

        // Draw x-axis
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, height - 60, width, height - 60);

        // Draw labels for each day
        g2d.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller text
        for (int i = 0; i < days.length; i++) {
            g2d.drawString(days[i], (i * barWidth) + (barWidth / 2) - 10, height - 25);
        }

        // Draw title
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(new Color(84, 84, 84));
        g2d.drawString(title, (width - g2d.getFontMetrics().stringWidth(title)) / 2, 20);

        // Draw the line graph on top of the bars
        g2d.setColor(new Color(255, 165, 0)); // Orange color
        g2d.setStroke(new BasicStroke(3)); // Thicker line
        for (int i = 0; i < profits.length - 1; i++) {
            int x1 = (i * barWidth) + (barWidth / 2);
            int y1 = height - (int) ((profits[i] / maxProfit) * (height - 100)) - 60;
            int x2 = ((i + 1) * barWidth) + (barWidth / 2);
            int y2 = height - (int) ((profits[i + 1] / maxProfit) * (height - 100)) - 60;
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    // Override the paintComponent method to call drawGraph
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraph(g);
    }
}
