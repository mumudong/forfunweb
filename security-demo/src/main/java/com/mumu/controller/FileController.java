package com.mumu.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {
    private String folder = "";

//    @PostMapping
//    public FileInfo upload(MultipartFile file)throws IOException {
//        System.out.println("接收文件名：" + file.getName());
//        File localFile = new File(folder,file.getName());
//        file.transferTo(localFile);
//        return new FileInfo(localFile.getAbsolutePath());
//    }

    @PostMapping("/uploadFiles")
    @ResponseBody
    public String uploadFiles(HttpServletRequest request) throws Exception {
        File savePath = new File(request.getSession().getServletContext().getRealPath("/upload/"));
        System.out.println("目录位置：" + savePath.getAbsolutePath());
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multiRequest.getFiles("file");
        Collection<Part> parts = multiRequest.getParts();

        for(Part part:parts) {
            System.out.println(part.getName() + "--" + part.getContentType() );
        }
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    File saveFile = new File(savePath, file.getOriginalFilename());
                    stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    if (stream != null) {
                        stream.close();
                        stream = null;
                    }
                    return "第 " + i + " 个文件上传有错误" + e.getMessage();
                }
            } else {
                return "第 " + i + " 个文件为空";
            }
        }
        return "所有文件上传成功";
    }

    @PostMapping("/uploadFiles2")
    public Map<String,Object> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String upload_file_path="D:\\upload\\" ;
        System.out.println(upload_file_path);
        // 设置工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置文件存储位置
        factory.setRepository(Paths.get(upload_file_path).toFile());
        // 设置大小，如果文件小于设置大小的话，放入内存中，如果大于的话则放入磁盘中,单位是byte
        factory.setSizeThreshold(1024 * 1024);

        ServletFileUpload upload = new ServletFileUpload(factory);
        // 这里就是中文文件名处理的代码，其实只有一行
        upload.setHeaderEncoding("utf-8");
        String fileName = null;
        List<FileItem> list = upload.parseRequest(request);
        for (FileItem item : list) {
            if (item.isFormField()) {
                String name = item.getFieldName();
                String value = item.getString("utf-8");
                System.out.println(name);
                System.out.println(value);
                request.setAttribute(name, value);
            } else {
                String name = item.getFieldName();
                String value = item.getName();
                System.out.println(name);
                System.out.println(value);

                fileName = Paths.get(value).getFileName().toString();
                request.setAttribute(name, fileName);
                if(!Paths.get(upload_file_path).toFile().exists()){
                    Paths.get(upload_file_path).toFile().mkdirs();
                }
                // 写文件到path目录，文件名问filename
                item.write(new File(upload_file_path, fileName));
            }
        }
        Map<String,Object> result = new HashMap<>();
        result.put("status","success");
        result.put("fileName",fileName);
        return result;
    }

    @GetMapping
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException,FileNotFoundException{
        try(
            InputStream inputStream = new FileInputStream(new File(folder,id + ".txt"));
            OutputStream outputStream = response.getOutputStream();
         ){
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition","attachment;filename=test.txt");

            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        }
    }
}
