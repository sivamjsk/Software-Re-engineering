package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;


/**
 * The Class SidebarElement.
 */
public class SidebarElement extends GameElements{
    
    /** The card selected. */
    private static int cardSelected=-1;
    
    /** The timeout time. */
    private int timeoutTime;
    
    /** The is disabled. */
    private boolean isDisabled=false;
    
    /** The selected border. */
    private static ImageView selectedBorder;
    
    /** The all elements. */
    private static HashMap<Integer,SidebarElement> allElements;
    
    /** The cost. */
    private final int cost;
    
    /**
     * Instantiates a new sidebar element.
     *
     * @param x the x
     * @param y the y
     * @param path the path
     * @param width the width
     * @param height the height
     * @param cost the cost
     */
    public SidebarElement(int x,int y,String path, int width,int height,int cost){
        super(x,y,path,width,height);
        this.cost=cost;
//        super.makeImage();
    }

    /**
     * Gets the cost.
     *
     * @return the cost
     */
    public int getCost(){
        return this.cost;
    }

    public static void setSidebarElements(HashMap<Integer,SidebarElement> allElements,SidebarElement element, Pane pane, int timeout, int cardOrder) {
        element.makeImage(pane);
        element.timeoutTime = timeout;
        allElements.put(cardOrder, element);
        element.img.setOnMouseClicked(e -> {
            if (!element.isDisabled) {
                setCardSelected(cardOrder);
                Shovel.getInstance().disable();
            }
        });
    }


    /**
     * Gets the side bar elements.
     *
     * @param level the level
     * @param pane the pane
     * 
     */

    public static void getSideBarElements(int level,Pane pane){
        String path;
        int x;
        int y;
        int width=97;
        int height=58;
        allElements=new HashMap<Integer, SidebarElement>();
        path="file:assets/sunflowerCard.png";
        x=24;
        y=79;
        if(level>=1){
            path="/assets/sunflowerCard.png";
            x=24;
            y=79;
            SidebarElement sunflowerCard = new SidebarElement(x, y, path, width, height, 50);
            setSidebarElements(allElements, sunflowerCard, pane, 5000,1);

        }
        if(level>=1){
            path="/assets/peashooterCard.png";
            x=22;
            y=147;
            SidebarElement peashooterCard=new SidebarElement(x,y,path,width,height,100);
            setSidebarElements(allElements, peashooterCard, pane, 6000,2);
        }
        if (level>=2){
            path="/assets/wallnutCard.png";
            x=22;
            y=217;
            SidebarElement wallnutCard=new SidebarElement(x,y,path,width,height,50);
            setSidebarElements(allElements, wallnutCard, pane, 7000,3);
        }
        if (level>=3) {
            path="/assets/cherrybombCard.png";
            x=22;
            y=284;
            SidebarElement cherrybombCard=new SidebarElement(x,y,path,width,height,150);
            setSidebarElements(allElements, cherrybombCard, pane, 15000,4);
        }
        if(level>=4) {
            path="/assets/repeaterCard.png";
            x=23;
            y=352;
            SidebarElement repeaterCard=new SidebarElement(x,y,path,width,height,200);
            setSidebarElements(allElements, repeaterCard, pane, 10000,5);
        }
        if (level>=5) {
            path="/assets/jalapenoCard.png";
            x=24;
            y=420;
            SidebarElement jalapenoCard=new SidebarElement(x,y,path,width,height,125);
            setSidebarElements(allElements, jalapenoCard, pane, 12000,6);

        }
        String border_path = SidebarElement.class.getResource("/assets/selectedCardBorder.png").toString();
        selectedBorder = new ImageView(new Image(border_path,110.0,72.0,false,false));
        pane.getChildren().add(selectedBorder);
        selectedBorder.setVisible(false);
        selectedBorder.setDisable(true);
    }

    /**
     * Gets the card selected.
     *
     * @return the card selected
     */
    public static int getCardSelected() {
        return cardSelected;
    }
    
    /**
     * Sets the card selected.
     *
     * @param i the new card selected
     */
    private static void setCardSelected(int i){
        cardSelected=i;
        selectedBorder.setVisible(true);
        selectedBorder.setX(allElements.get(cardSelected).getX()-5);
        selectedBorder.setY(allElements.get(cardSelected).getY()-5);
    }
    
    /**
     * Setter method to print the card selected to null.
     */
    public static void setCardSelectedToNull(){
        cardSelected=-1;
        selectedBorder.setVisible(false);
    }

    /**
     * Gets the element.
     *
     * @param x the x
     * @return the element
     */
    public static SidebarElement getElement(int x){
        if (allElements.containsKey(x)) { return allElements.get(x);}
        else {return null;}
    }

    /**
     * Sets the disabled on.
     *
     * @param pane the new disabled on
     */
    public void setDisabledOn(Pane pane){
        this.isDisabled=true;
        ImageView im =new ImageView(new Image(getClass().getResource("/assets/lock.png").toString(),50.0,50.0,false,false));
        im.setX(this.getX()+20);
        im.setY(this.getY());
        pane.getChildren().add(im);
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(this.timeoutTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.isDisabled=false;
            im.setVisible(false);
            im.setDisable(true);
        });
        t.start();

    }
}
