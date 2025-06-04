package com.sjh.upload.image;
import java.io.File;
import java.util.UUID;
import com.sjh.utils.ResultUtils;
import com.sjh.utils.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 沈俊豪
 * 
 * **/
@RestController
@RequestMapping("/api/upload")
public class ImageUploadController {
    //图片上传的路径
    @Value("${web.uploadpath}")
    private String webUploadpath;
    @RequestMapping("/uploadImage")
    public ResultVo uploadImage(@RequestParam("file") MultipartFile file){
        String Url = "";
        String fileName = file.getOriginalFilename();
        //获取文件扩展名
        String fileExtenionName = fileName.substring(fileName.indexOf("."));
        //新的文件名
        String newName = UUID.randomUUID().toString()+fileExtenionName;
        String path = webUploadpath;
        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.mkdirs();
            //设置权限
            fileDir.setWritable(true);
        }
        File targetFile = new File(path,newName);
        try{
            file.transferTo(targetFile);
                    Url = "/" + targetFile.getName();
        }catch (Exception e){
            return  null;
        }
        System.out.println(Url);
        return ResultUtils.success("成功", "/images" + Url);
    }
}