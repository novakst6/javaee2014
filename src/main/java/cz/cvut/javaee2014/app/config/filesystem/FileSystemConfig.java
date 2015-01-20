/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.app.config.filesystem;

/**
 *
 * @author Toms
 */
public class FileSystemConfig {
    
    public FileSystemConfig(){}
    
    private String rootDirectory;
    private String uploadDirectory;
    private String webDirectory;

    /**
     * @return the rootDirectory
     */
    public String getRootDirectory() {
        return rootDirectory;
    }

    /**
     * @param rootDirectory the rootDirectory to set
     */
    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    /**
     * @return the uploadDirectory
     */
    public String getUploadDirectory() {
        return uploadDirectory;
    }

    /**
     * @param uploadDirectory the uploadDirectory to set
     */
    public void setUploadDirectory(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    /**
     * @return the webDirectory
     */
    public String getWebDirectory() {
        return webDirectory;
    }

    /**
     * @param webDirectory the webDirectory to set
     */
    public void setWebDirectory(String webDirectory) {
        this.webDirectory = webDirectory;
    }
    
}
