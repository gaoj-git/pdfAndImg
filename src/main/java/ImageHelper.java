import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ImageHelper {



    private static double scale = 0.5d;

    private static double imageQuality = 0.5d;

    public static void optimizeImage(String source, String target) {
        try {
            Thumbnails.of(source) //原图片
                    .scale(scale) //分辨率比例
                    .outputQuality(imageQuality) //图片质量
                    .outputFormat("JPEG") //目标文件格式
                    .toFile(target); //目标图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//
        File file = new File("G:\\pdffile\\covert\\study-7.jpg");
        file.setLastModified(1657026335001l);
//        File[] files = file.listFiles();
//        System.out.printf("1");
//        List<File> files = getFileSort("G:\\\\pdffile\\\\pdf\\\\");
//        System.out.printf("f" +
//                "");
//        for (File f:
//                files) {
//            optimizeImage(f.getPath(),"G:\\pdffile\\covert\\"+f.getName());
//        }

        //optimizeImage("g://study-0.jpg","g://test.jpg");

    }


    /**
     * 获取目录下所有文件(按时间排序)
     *
     * @param path
     * @return
     */
    public static List getFileSort(String path) {

        List list = getFiles(path, new ArrayList<File>());
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return 1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return -1;
                    }

                }
            });

        }

        return list;
    }

    /**
     *
     * 获取目录下所有文件
     *
     * @param realpath
     * @param files
     * @return
     */
    public static List getFiles(String realpath, List files) {

        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files);
                } else {
                    files.add(file);
                }
            }
        }
        return files;
    }
}

