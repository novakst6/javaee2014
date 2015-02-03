/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.service.batch;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

/**
 *
 * @author Toms
 */
public class ImageResizer {
        
    public static void resizeToFile(File from, File to, int width, int height){
        
        Logger.getRootLogger().info("ImageResizer from: "+from.getAbsolutePath());
        Logger.getRootLogger().info("ImageResizer to: "+to.getAbsolutePath());
        
        try{
            
            // přípona
            String extension = "";
            int i = from.getName().lastIndexOf('.');
            if (i > 0) {
                extension = from.getName().substring(i+1);
            }  
            
            // formát
            String format = "jpg";
            if(extension.equalsIgnoreCase("png")){format = "png";}
            if(extension.equalsIgnoreCase("gif")){format = "gif";}
            
            // typ RGB / ARGB nebo tak
            BufferedImage originalImage = ImageIO.read(from);
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
	
            // velikost
            int oW = originalImage.getWidth(); int nW = oW;
            int oH = originalImage.getHeight(); int nH = oH;
            
            // zmenšování
            if(oW > width){
                nW = width;
                nH = (nW * oH) / oW;
            }
            if(oH > height){
                nH = height;
                nW = (nH * oW) / oH;
            }
            
            // zvětšování
            if(oW < width){
                nW = width;
                nH = (nW * oH) / oW;
            }
            if(oH < height){
                nH = height;
                nW = (nH * oW) / oH;
            }
            
            // komprese a zapsání výsledku
            BufferedImage resizeImageJpg = resizeImage(originalImage, type, nW, nH);
            ImageIO.write(resizeImageJpg, format, to); 
        
        }catch(IOException e){
            Logger.getRootLogger().info("Error while image resizing: "+e.getMessage());
	}        
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height){
 
	BufferedImage resizedImage = new BufferedImage(width, height, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, width, height, null);
	g.dispose();	
	g.setComposite(AlphaComposite.Src);
 
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.setRenderingHint(RenderingHints.KEY_RENDERING,
	RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	RenderingHints.VALUE_ANTIALIAS_ON);
 
	return resizedImage;
    }	
}
