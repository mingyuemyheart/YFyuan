package com.moft.xfapply.utils;

public class MimeTypeUtil {

    private static final String[][] MIME_MapTable={ 
            {".3gp",    "video/3gpp"}, 
            {".apk",    "application/vnd.android.package-archive"}, 
            {".asf",    "video/x-ms-asf"}, 
            {".avi",    "video/x-msvideo"}, 
            {".bin",    "application/octet-stream"}, 
            {".bmp",    "image/bmp"}, 
            {".c",  "text/plain"}, 
            {".class",  "application/octet-stream"}, 
            {".conf",   "text/plain"}, 
            {".cpp",    "text/plain"}, 
            {".doc",    "application/msword"}, 
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, 
            {".xls",    "application/vnd.ms-excel"},  
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, 
            {".exe",    "application/octet-stream"}, 
            {".gif",    "image/gif"}, 
            {".gtar",   "application/x-gtar"}, 
            {".gz", "application/x-gzip"}, 
            {".h",  "text/plain"}, 
            {".htm",    "text/html"}, 
            {".html",   "text/html"}, 
            {".jar",    "application/java-archive"}, 
            {".java",   "text/plain"}, 
            {".jpeg",   "image/jpeg"}, 
            {".jpg",    "image/jpeg"}, 
            {".js", "application/x-javascript"}, 
            {".log",    "text/plain"}, 
            {".m3u",    "audio/x-mpegurl"}, 
            {".m4a",    "audio/mp4a-latm"}, 
            {".m4b",    "audio/mp4a-latm"}, 
            {".m4p",    "audio/mp4a-latm"}, 
            {".m4u",    "video/vnd.mpegurl"}, 
            {".m4v",    "video/x-m4v"},  
            {".mov",    "video/quicktime"}, 
            {".mp2",    "audio/x-mpeg"}, 
            {".mp3",    "audio/x-mpeg"}, 
            {".mp4",    "video/mp4"}, 
            {".mpc",    "application/vnd.mpohun.certificate"},        
            {".mpe",    "video/mpeg"},   
            {".mpeg",   "video/mpeg"},   
            {".mpg",    "video/mpeg"},   
            {".mpg4",   "video/mp4"},    
            {".mpga",   "audio/mpeg"}, 
            {".msg",    "application/vnd.ms-outlook"}, 
            {".ogg",    "audio/ogg"}, 
            {".pdf",    "application/pdf"}, 
            {".png",    "image/png"}, 
            {".pps",    "application/vnd.ms-powerpoint"}, 
            {".ppt",    "application/vnd.ms-powerpoint"}, 
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"}, 
            {".prop",   "text/plain"}, 
            {".rc", "text/plain"}, 
            {".rmvb",   "audio/x-pn-realaudio"}, 
            {".rtf",    "application/rtf"}, 
            {".sh", "text/plain"}, 
            {".tar",    "application/x-tar"},    
            {".tgz",    "application/x-compressed"},  
            {".txt",    "text/plain"}, 
            {".wav",    "audio/x-wav"}, 
            {".wma",    "audio/x-ms-wma"}, 
            {".wmv",    "audio/x-ms-wmv"}, 
            {".wps",    "application/vnd.ms-works"}, 
            {".xml",    "text/plain"}, 
            {".z",  "application/x-compress"}, 
            {".zip",    "application/x-zip-compressed"}, 
            {"",        "*/*"}   
        }; 
    
    public static String getMIMEType(String suffix) {
        String mime = null;
        
        suffix = FileUtil.getFileSuffix(suffix);
        
        for(int i=0;i<MIME_MapTable.length;i++){ 
            if(suffix.equals(MIME_MapTable[i][0])) 
                mime = MIME_MapTable[i][1]; 
        }        
        return mime; 
    }
    
    public static boolean isImage(String fNameOrPath){
        String suffix = FileUtil.getFileSuffixNoDot(fNameOrPath);
        
        return "bmp".equals(suffix) || "gif".equals(suffix) || "jpeg".equals(suffix) || "jpg".equals(suffix) || "png".equals(suffix);
    }
    
    public static boolean isVideo(String fNameOrPath){
        String suffix = FileUtil.getFileSuffixNoDot(fNameOrPath);
        
        return "3gp".equals(suffix) || "asf".equals(suffix) || "avi".equals(suffix) 
                || "m4u".equals(suffix) || "m4v".equals(suffix) || "mov".equals(suffix)
                || "mp4".equals(suffix) || "mpe".equals(suffix) || "mpeg".equals(suffix)
                || "mpg".equals(suffix) || "mpg4".equals(suffix);
    }
    
    public static boolean isDoc(String fNameOrPath){
        String suffix = FileUtil.getFileSuffixNoDot(fNameOrPath);
        
        return "doc".equals(suffix) || "docx".equals(suffix);
    }
    
    public static boolean isExcel(String fNameOrPath){
        String suffix = FileUtil.getFileSuffixNoDot(fNameOrPath);
        
        return "xls".equals(suffix) || "xlsx".equals(suffix);
    }
    
    public static boolean isPpt(String fNameOrPath){
        String suffix = FileUtil.getFileSuffixNoDot(fNameOrPath);
        
        return "ppt".equals(suffix) || "pptx".equals(suffix);
    }
    
    public static boolean isPdf(String fNameOrPath){
        String suffix = FileUtil.getFileSuffixNoDot(fNameOrPath);
        
        return "pdf".equals(suffix);
    }
    
    public static boolean isTxt(String fNameOrPath){
        String suffix = FileUtil.getFileSuffixNoDot(fNameOrPath);
        
        return "txt".equals(suffix);
    }
}
