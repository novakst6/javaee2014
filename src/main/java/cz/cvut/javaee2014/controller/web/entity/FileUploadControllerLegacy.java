///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package cz.cvut.javaee2014.controller.web.entity;

///**
import cz.cvut.javaee2014.model.entity.FileEntity;
import cz.cvut.javaee2014.service.repository.FileManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

// *
// * @author Toms
// */
@Controller
@RequestMapping(value = {"entity"})
public class FileUploadControllerLegacy {

    @Autowired
    private Environment env;

    @Autowired
    private FileManager fm;

    @RequestMapping(value = "/legacy/upload.htm", method = RequestMethod.GET)
    public String uploadGET(ModelMap m) {
        m.addAttribute("files", fm.findAll());
        return "entity/file/index";
    }

    @RequestMapping(value = "/legacy/upload.htm", method = RequestMethod.POST)
    public String uploadPOST(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {

        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;

        while (itr.hasNext()) {

            mpf = request.getFile(itr.next());
            System.out.println(mpf.getOriginalFilename() + " uploaded! ");

            InputStream fis = mpf.getInputStream();
            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
            fis.close();

            String rootFileSystemDirectory = env.getProperty("app.filesystem.root");
            String uploadDir = rootFileSystemDirectory + env.getProperty("app.filesystem.upload.dir");

            try {
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(uploadDir + mpf.getOriginalFilename()));
            } catch (IOException e) {
                //something happened - invalide upload
                e.printStackTrace();
            }

            FileEntity file = new FileEntity();
            file.setHash(md5);
            file.setMimeType(mpf.getContentType());
            file.setPath(uploadDir + mpf.getOriginalFilename());
            file.setSize(mpf.getSize());
            file.setName(mpf.getOriginalFilename());
            fm.add(file);
        }
        return "redirect:upload.htm";
    }

    @RequestMapping(value = {"/legacy/download.htm"}, method = RequestMethod.GET)
    public String downloadGET(HttpServletResponse response, @RequestParam("id") Long id) {
        FileEntity mimeFile = fm.findById(id);
        try {
            FileInputStream fis = new FileInputStream(mimeFile.getPath());
            response.setContentType(mimeFile.getMimeType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + mimeFile.getName() + "\"");
            FileCopyUtils.copy(fis, response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
