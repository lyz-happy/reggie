package com.itheima.controller;

import com.itheima.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author lyz
 * @create 2024-06-18-9:35
 * 文件上传与下载
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String path;

    // 文件上传
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {        // 形参名字与前端传送过来的形参名相同即可接收
        // file为一临时文件，需要转存到指定位置
        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        // 取出后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 为了避免重名导致覆盖问题，使用UUID生成文件名
        String fileName = UUID.randomUUID().toString() + suffix;
        file.transferTo(new File(path + fileName));
        // 将文件名返回，前端需要接收此数据
        return R.success(fileName);
    }

    // 文件下载，前端上传成功之后，自动发送请求http://localhost:8080/common/download?name=afe0a7c4-fc83-4ea8-a0f5-89c3f6f2571f.png进行文件回显
    @GetMapping("/download")
    public R<String> download(String name, HttpServletResponse response) {
        try {
            // 输入流，读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(path + name));
            // 输出流，通过输出流将文件写回到浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            // 关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.success("下载成功"); 
    }

}