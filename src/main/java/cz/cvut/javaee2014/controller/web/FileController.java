/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.controller.web;

import cz.cvut.javaee2014.app.config.ApplicationConfig;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Toms
 */
@Controller
@PropertySource("classpath:config/app.properties")
public class FileController {
    
    //<editor-fold defaultstate="collapsed" desc="Konfigurace adresářů">
    
    /**
     * Cesta ke koř. adresáři aplikace - obsahuje web, uploady i logy
     */
    @Value("${filesys.root}")
    private String rootDirectory;
    
    @Value("${filesys.upload}")
    private String uploadDirectory;
    
    @Value("${filesys.web}")
    private String webDirectory;
    
    public String getUploadDirectory(){
        return rootDirectory + uploadDirectory;
    }
    
    public String getWebDirectory(){
        return rootDirectory + webDirectory;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Stažení souboru">
    
    @RequestMapping(value = "/files/**", method = RequestMethod.GET)
    @ResponseBody
    public void getFile(HttpServletRequest request, HttpServletResponse response) {

        // budeme se pokoušet nahrát soubor z FS nebo je dotaz blbost
        boolean tryToServeFile = true;
        
        // soubour a rel cesta
        File theFile = null;    
        String relativeFilePath = ""; //relativní cesta k souboru, od ..files/

        // --- získáme abs. cestu
        do{
            
            // záskáme relativní adresu
            relativeFilePath = request.getRequestURI().split("/files/")[1];
            if(relativeFilePath == null){ tryToServeFile = false; break; }
            if(relativeFilePath.equals("")){ tryToServeFile = false; break; }

            // získáme abs. a dresu a odkaz
            theFile = new File(rootDirectory+"/"+relativeFilePath);
            if(!theFile.exists()){ tryToServeFile = false; break; }
        }while(false);
        
        // --- napíšeme not found
        if(!tryToServeFile){
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                //response.getOutputStream().write(((String)"[NOT_FOUND]").getBytes());
                //response.getOutputStream().flush();
                Logger.getLogger(this.getClass().getName()).log(Level.ERROR," FAILED Serving file: "+request.getRequestURI());
            } catch (Exception e) {}
            return;
        }
        
        // --- JS mime type pro Chrome browser
        String extension = "";
        int dotIndex = theFile.getName().lastIndexOf(".");  // ok, soubor existuje
        if (dotIndex >= 0) {
            extension = theFile.getName().substring( dotIndex + 1);
        }
        if(extension.equals("js")){
            response.setHeader("Content-Type", "application/x-javascript");
        }

        // --- poslat soubor nebo not found
        try {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO," Serving file: "+theFile.getAbsolutePath());
            
            // streamy
            InputStream is = new FileInputStream(theFile);
            ServletOutputStream os = response.getOutputStream();
            
            // odeslání souboru
            byte[] buffer = new byte[1024];
            int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1){
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                is.close();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.ERROR," FAILED Reading file: "+theFile.getAbsolutePath());
        }
    }
    
    //</editor-fold>

    
    @RequestMapping(value = {"uploadx.htm"},method = {RequestMethod.GET})
    public void uploadPage(HttpServletResponse response){
        response.setHeader("Content-Type", "text/html");
        response.setHeader("success", "yes");
        
        // odeslání textu
        try {
            PrintWriter writer;
            writer = response.getWriter();
            writer.write(
                "<html>\n" +                    
                "<body>\n" +
                "	<form method=\"POST\" enctype=\"multipart/form-data\"\n" +
                "		action=\"upload\">\n" +
                "		File to upload: <input type=\"file\" name=\"file\"><br /> Name: <input\n" +
                "			type=\"text\" name=\"name\"><br /> <br /> <input type=\"submit\"\n" +
                "			value=\"Upload\"> Press here to upload the file!\n" +
                "	</form>\n" +
                "<br /><a href=\"upload\">Test upload link</a>\n"+
                "</body>\n" +
                "</html>");
            writer.close();
            
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.WARN, null, ex);
        }        
    }
    
    /*@RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }*/

    /**
     * Příchozí file upload - uložíme soubor
     * @param name
     * @param file
     * @return 
     */
    @RequestMapping(value="/uploadx", method = {RequestMethod.POST})
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
        
        if (!file.isEmpty()) {
            try {
                
                // nový soubor
                String newFilename = file.getOriginalFilename(); // používá původní název souboru vč. přípony
                //String newFilename = name + "-uploaded"; // používá údaj z formuláře
                File outFile = new File(getUploadDirectory() + "/" + newFilename);  
                
                // vytvoříme adresáře
                if(!outFile.getParentFile().exists()){
                    outFile.getParentFile().mkdirs();
                }
                
                // kopírování dat
                byte[] bytes = file.getBytes();                
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(outFile));
                stream.write(bytes);
                stream.close();
                
                // ok
                return "You successfully uploaded " + name + " into " + name + "-uploaded !\n" +
                       "Original filename: " + file.getOriginalFilename() + "\n" + 
                       "Uploaded file path: " + outFile.getAbsolutePath() + "\n";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

}