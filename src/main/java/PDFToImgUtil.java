import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.lowagie.text.pdf.PdfReader;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * @author 漫路h
 * Created by 2020-08-24
 */

public class PDFToImgUtil {
    /**
     * main方法
     */
    public static void main(String[] args) {
        pdfToImage("d:\\20220706_120449.pdf", "G:\\pdffile\\1", 150);
    }

    /**
     * PDF文件转PNG图片，全部页数
     *
     * @param PdfFilePath  pdf完整路径
     * @param dstImgFolder 图片存放的文件夹
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     * @return 返回转换后图片集合list
     */
    public static List<File> pdfToImage(String PdfFilePath, String dstImgFolder, int dpi) {
        UUID uuid = UUID.randomUUID();
        String uuId = uuid.toString();
        System.out.println(uuId);
        File file = new File(PdfFilePath);
        //定义集合保存返回图片数据
        List<File> fileList = new ArrayList<File>();
        @SuppressWarnings("resource")//抑制警告
        PDDocument pdDocument = new PDDocument();
        try {
            //String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
            String imgFolderPath = null;
            if (dstImgFolder.equals("")) {
                imgFolderPath = dstImgFolder + File.separator + uuId;// 获取图片存放的文件夹路径
            } else {
                imgFolderPath = dstImgFolder + File.separator + uuId;
            }
            if (createDirectory(imgFolderPath)) {
                pdDocument = PDDocument.load(file);
                PDFRenderer renderer = new PDFRenderer(pdDocument);
                /* dpi越大转换后越清晰，相对转换速度越慢 */
                PdfReader reader = new PdfReader(PdfFilePath);
                int pages = reader.getNumberOfPages();
                System.out.println("pdf总共多少页-----" + pages);
                StringBuffer imgFilePath = null;
                for (int i = 0; i < pages; i++) {
                    String imgFilePathPrefix = imgFolderPath + File.separator + "study";
                    System.out.println("imgFilePathPrefix=====" + imgFilePathPrefix);
                    imgFilePath = new StringBuffer();
                    imgFilePath.append(imgFilePathPrefix);
                    imgFilePath.append("-");
                    imgFilePath.append(String.valueOf(i));
                    imgFilePath.append(".jpg");
                    File dstFile = new File(imgFilePath.toString());
                    BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                    ImageIO.write(image, "png", dstFile);
                    fileList.add(dstFile);
                }
                System.out.println("PDF文档转PNG图片成功！");
                return fileList;
            } else {
                System.out.println("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //创建文件夹
    private static boolean createDirectory(String folder) {
        File dir = new File(folder);
        if (dir.exists()) {
            return true;
        } else {
            return dir.mkdirs();
        }
    }
    //删除文件夹
    //param folderPath 文件夹完整绝对路径
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除指定文件夹下所有文件
    //param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();

            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
}
