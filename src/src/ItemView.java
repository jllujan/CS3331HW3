package src;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;


import javax.imageio.ImageIO;
import javax.swing.*;


/** A special panel to display the detail of an item. */

@SuppressWarnings("serial")
public class ItemView extends JPanel implements ListCellRenderer<Item> {



    private String itemName = "LED Monitor";
    private String URL = "http://www.bestbuy.com/site/samsun-ue90-series-28-led-4k-uhd-moniotr-black/5484022.p?skuId=5484022";
    private double maxPrice = 369.99;
    private double minPrice = 61.67;
    private double itemChange;
    private String itemDate = "08/25/2018";

    private Item item = new Item(itemName,URL,maxPrice,minPrice,itemDate);

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Item> list, Item value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        this.item = value;
        Color background;
        Color foreground;

        if(isSelected){
            background = Color.YELLOW;
            foreground = Color. BLACK;
        }else{
            background = Color.WHITE;
            foreground = Color. BLACK;

        };

        setBackground(background);
        setForeground(foreground);

        return this;
    }

    /** Interface to notify a click on the view page icon. */
    public interface ClickListener {

        /** Callback to be invoked when the view page icon is clicked. */
        void clicked();
    }

    /** Directory for image files: src/image in Eclipse. */
    private final static String IMAGE_DIR = "/image/";

    /** View-page clicking listener. */
    private ClickListener listener;

    public ItemView(Item item) {
        this.item = item;
    }


    /** Create a new instance. */
    public ItemView() {
        setPreferredSize(new Dimension(100, 160));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isViewPageClicked(e.getX(), e.getY()) && listener != null) {
                    listener.clicked();
                }
            }
        });
    }

    /** Set the view-page click listener. */
    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    /** Overridden here to display the details of the item. */
    @Override
    public void paintComponent(Graphics g) {
        int height = 25;
        int width = 25;
        super.paintComponent(g);
        g.drawImage(getImage("URL.png"), 20, 5, height,width,null);
        int x = 20, y = 50;
        g.drawString("Name: " + item.getItemName(), x, y);
        y += 20;
        g.drawString("URL: " + item.getURL(), x, y);
        y += 20;
        g.drawString("Price: " + item.getItemPrice(), x, y);
        y += 20;
        if(item.getItemChange() > 0.00) {
            g.setColor(Color.GREEN);
            g.drawString("Change: " + item.getItemChange(), x, y);
        } else{
            g.setColor(Color.RED);
            g.drawString("Change: " + item.getItemChange(), x, y);
        }
        g.setColor(Color.BLACK);
        y += 20;
        g.drawString("Added: " + item.getItemDate(), x, y);
    }

    /** Return true if the given screen coordinate is inside the viewPage icon. */
    private boolean isViewPageClicked(int x, int y) {
        //--
        //-- WRITE YOUR CODE HERE
        //--
        return new Rectangle(20, 5, 25, 25).contains(x,  y);

    }

    /** Return the image stored in the given file. */
    public Image getImage(String file) {
        try {
            URL url = new URL(getClass().getResource(IMAGE_DIR), file);
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

