package com.photonstudio.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PicUploadResult;
import com.photonstudio.pojo.FileUploadProperteis;

@Component
public class FileUtil {

    private static FileUploadProperteis fileUploadProperteis;

    @Autowired
    public FileUtil(FileUploadProperteis fileUploadProperteis) {
        FileUtil.fileUploadProperteis = fileUploadProperteis;
    }

    static public PicUploadResult base64Upload(String dBname, String base64Str, String type,String imgType) {
        PicUploadResult result = new PicUploadResult();
        if (base64Str == null || base64Str.equals("")) {
            //不能为空
            result.setStatus(50008);
            return result;
        }
        //文件存储
        String datePath = fileUploadProperteis.getUploadFolder()
                + dBname + "/" + type;
        File dirFile = new File(datePath);
        if (!dirFile.exists()) {
            //如果文件不存在,则创建文件夹
            dirFile.mkdirs();
        }
        //为文件生成唯一名称 UUID+后缀
        String uuid = UUID.randomUUID()
                .toString()
                .replace("-", "");
        //图片名称  abc.jpg
        String fileType = imgType;
        String realFileName = uuid +"."+ fileType;
        //将文件实现上传
        try {
            ImgFileUtil.base64StringToFile(base64Str, datePath + "/" + realFileName);
            String imgurl = dBname + "/" + type + "/" + realFileName;
            String url = fileUploadProperteis.getStaticAccessPath()
                    + imgurl;
            result.setUrl(url);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(50008);
            return result;
        }
    }

    static public PicUploadResult uploadPic(String dBname, MultipartFile file, String type) {
        PicUploadResult result = new PicUploadResult();
        if (file == null) {
            //不能为空
            result.setStatus(50008);
            return result;

        }

        //获取图片信息
        String fileName = file.getOriginalFilename();
        //将字符全部小写
        fileName = fileName.toLowerCase();
        //判断是否为图片类型
        if (!fileName.matches("^.+\\.(bmp|jpg|png|gif|jpeg)$")) {
            //表示不是图片类型
            result.setStatus(50008);
            return result;
        }
        /**
         * 判断文件是否为恶意程序
         */
        BufferedImage image;
        try {
            image = ImageIO.read(file.getInputStream());

            int height = image.getHeight();
            int width = image.getWidth();
            if (height == 0 || width == 0) {
                result.setStatus(50008);
                return result;
            }
            //文件存储
            String datePath = fileUploadProperteis.getUploadFolder()
                    + dBname + "/" + type;
            File dirFile = new File(datePath);
            if (!dirFile.exists()) {
                //如果文件不存在,则创建文件夹
                dirFile.mkdirs();
            }
            //为文件生成唯一名称 UUID+后缀
            String uuid = UUID.randomUUID()
                    .toString()
                    .replace("-", "");
            //图片名称  abc.jpg  截串的规则:包头不包尾
            String fileType =
                    fileName.substring(fileName.lastIndexOf("."));
            String realFileName = uuid + fileType;
            //将文件实现上传
            File realFile = new File(datePath + "/" + realFileName);
            file.transferTo(realFile);
            /**封装返回值数据*/
            result.setHeight(height + "");
            result.setWidth(width + "");
            String imgurl = dBname + "/" + type + "/" + realFileName;
            String url = fileUploadProperteis.getStaticAccessPath()
                    + imgurl;
            result.setUrl(url);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(50008);
            return result;
        }
    }

    static public boolean deleteObject(String dBname, String url) {
        if (StringUtils.isEmpty(url)) {
            return true;
        }
        try {
            //获取URL
            int index = fileUploadProperteis.getStaticAccessPath().length();
            String path = fileUploadProperteis.getUploadFolder() +
                    url.substring(index);
            File file = new File(path);
            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
            if (file.exists() && file.isFile()) {
                if (file.delete()) {

                    return true;
                } else {

                    return false;
                }
            } else {
                System.out.println("图片已经不存在");
                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }

    }


    static public String uploadFile(String dBname, MultipartFile file, String saveName, String type) {
        if (file == null) throw new IllegalArgumentException("上传文件不存在");
        try {
            String fileName = file.getOriginalFilename();
            //将字符全部小写
            fileName = fileName.toLowerCase();
            //文件存储
            String datePath = fileUploadProperteis.getUploadFolder()
                    + dBname + "/" + type;
            File dirFile = new File(datePath);
            if (!dirFile.exists()) {
                //如果文件不存在,则创建文件夹
                dirFile.mkdirs();
            }
            //为文件生成自定义名字+时间
            DateFormat dFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String uuid = saveName + "-" + dFormat.format(new Date());
            //图片名称  abc.jpg  截串的规则:包头不包尾
            String fileType =
                    fileName.substring(fileName.lastIndexOf("."));
            String realFileName = uuid + fileType;
            //将文件实现上传
            File realFile = new File(datePath + "/" + realFileName);
            file.transferTo(realFile);

            String imgurl = dBname + "/" + type + "/" + realFileName;
            String url = fileUploadProperteis.getStaticAccessPath()
                    + imgurl;

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("文件上传失败");
        }

    }
}
