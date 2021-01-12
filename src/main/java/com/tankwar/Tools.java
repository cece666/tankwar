package com.tankwar;

import javax.swing.*;
import java.awt.*;

public class Tools {
    public static Image getImage(String name){
        return new ImageIcon("assets/images/"+ name).getImage();
    }
}
