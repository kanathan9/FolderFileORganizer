import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

public class FolderFilesOrganizer {

//    static Scanner sc = new Scanner(System.in);
    private static final StringBuilder command = new StringBuilder();
    private static final StringBuilder workingFile = new StringBuilder();
    private static final StringBuilder fileToSearch = new StringBuilder();
    private static final StringBuilder fileType = new StringBuilder();
    private static Boolean group = Boolean.FALSE;
    private static int nameLength;
    private static int numberOfFiles;

    private static final String[] videoFiles = {"VIDEOS",".mp4",".avi",".mkv"};
    private static final String[] documentFiles = {"DOCUMENTS",".txt",".pdf",".doc",".docx"};
    private static final String[] imagesFiles = {"IMAGES",".png",".jpg",".jpeg",".gif"};
    private static final String[] musicFiles = {"MUSIC",".aif",".cda",".mid",".midi",".mpa",".ogg",".wav",".wma",".wpl",".mp3"};
    private static final String[] execFiles = {"EXECUTABLE",".exe"};
    private static final String[] zipRarFiles = {"COMPRESSED",".zip",".rar",".7z",".arj",".deb",".pkg",".rpm",".tar.gz",".z"};
    private static final String[] discAndMedia = {"DISC-MEDIA",".bin",".dmg",".iso",".toast",".vcd"};
    private static final String[] database = {"DATABASE",".csv",".dat",".db",".dbf",".log",".mdb",".sav",".sql",".tar",".xml"};

    private static final String[][] extensions = {videoFiles,documentFiles,imagesFiles,musicFiles,execFiles,zipRarFiles,discAndMedia,database};

    private static List<File> foldersToDelete = new ArrayList<>();
    private static List<String> foldersToKeep = new ArrayList<>();
    private static HashMap<String,Integer> subFoldMap = new HashMap<>();

    public static void main(String[] args){
        initial();
        int argsLen = args.length;
        switch(argsLen){
            case 0:
                System.out.println("Missing arguments!!!!");
                return;
            case 1:
                command.append(args[0]);
                workingFile.append("./");
                break;
            case 2:
                command.append(args[0]);
                workingFile.append(args[1]);
                break;
            case 3:
                command.append(args[0]);
                workingFile.append(args[1]);
                if(args[0].compareToIgnoreCase("ORDER") == 0){
                    group= (args[2].contains("y") || args[2].contains("Y"))? Boolean.TRUE:Boolean.FALSE;
                }
                else if(args[0].compareToIgnoreCase("SEARCH") == 0){
                    fileToSearch.append(args[2]);
                }
                break;
            case 4:
                command.append(args[0]);
                workingFile.append(args[1]);
                if(args[0].compareToIgnoreCase("ORDER") == 0){
                    group= (args[2].contains("y") || args[2].contains("Y"))? Boolean.TRUE:Boolean.FALSE;
                }
                else if(args[0].compareToIgnoreCase("SEARCH") == 0){
                    fileToSearch.append(args[2]);
                }
                fileType.append(args[3]);
                break;
        }
        runCommand();
    }

    private static void initial(){
        System.out.println("Welcome on FolderFilesOrganizer. We will help you put you files in order, and find them back easily.");
        System.out.println("To organize a directory: ORDER [DIR_NAME] [GROUP_BY_NAME?]");
        System.out.println("Notes: DIR_NAME should be absolute path. GROUP_BY_NAME is yes or no.");
        System.out.println("To search a file: SEARCH [DIR_NAME] [FILE_NAME] [FILE_TYPE?");
        System.out.println("Notes: FILE_TYPE values will be available after running ORDER command.");
        System.out.println(" ");
        try(InputStream in = new FileInputStream("FFO.properties")){
            Properties prop = new Properties();
            prop.load(in);
            nameLength = Integer.valueOf(prop.getProperty("nameLength"));
            numberOfFiles = Integer.valueOf(prop.getProperty("numberOfFiles"));
        }catch(IOException e){

        }
    }

    private static void runCommand(){
        try {
            if(command.toString().compareToIgnoreCase("ORDER")==0){
                orderFolder(new File(workingFile.toString()));
            }
            else if(command.toString().compareToIgnoreCase("SEARCH")==0){
                int countResult = searchFile(new File(workingFile.toString()),0);
                System.out.println(countResult+" File(s) Found!");
            }else{
                System.out.println("Unknown Command: "+ command.toString());
            }
        } catch (Exception e) {
            System.out.println("An exception occured on ORDER command");
            e.printStackTrace();
        }
    }

    private static void orderFolder(File folder) throws Exception{
        Files.walk(folder.toPath()).forEach(f->orderByExtension(f.toFile()));
        for(File folderToDelete:foldersToDelete){
            if(foldersToKeep.indexOf(folderToDelete.toString()) < 0) {
                if (!folderToDelete.delete()) System.out.println("Could not delete " + folderToDelete.getName());
            }
        }
        if(group) Files.walk(folder.toPath()).forEach(f->{if(f.toFile().isFile()) orderByName(f.toFile());});
    }

    private static void orderByExtension(File file){
        if(file.isFile()) {
            boolean extensionKnown = false;
            for (String[] types : extensions) {
                for (String extension : Arrays.copyOfRange(types, 1, types.length)) {
                    if (file.getPath().contains(extension)) {
                        extensionKnown = true;
                        //Create a folder to hold the file
                        File newPath = new File(workingFile.toString() + File.separatorChar + types[0]);
                        if (!newPath.exists()) newPath.mkdir();
                        foldersToKeep.add(newPath.toString());

                        //Copy the File in the folder and delete old file
                        File newFile = new File(newPath.toString() + File.separatorChar + file.getName());
                        try {
                            Files.copy(file.toPath(), newFile.toPath());
                            if(!file.delete()) System.out.println("Could not delete "+file.getName());
                        } catch (SecurityException e) {
                            System.out.println("You don't have read/write access to process file: " + file.getName());
                        } catch (IOException e) {
                            System.out.println("Could not copy or delete file: " + file.getName());
                        }
                    }
                }
            }
            if (!extensionKnown) {
                //Create a folder to hold the file
                File newPath = new File(workingFile.toString() + File.separatorChar + "OTHERS");
                if (!newPath.exists()) newPath.mkdir();
                foldersToKeep.add(newPath.toString());

                //Copy the File in the folder and delete old file
                File newFile = new File(newPath.toString() + File.separatorChar + file.getName());
                try {
                    Files.copy(file.toPath(), newFile.toPath());
                    if(!file.delete()) System.out.println("Could not delete "+file.getName());
                } catch (SecurityException e) {
                    System.out.println("You don't have read/write access to process file: " + file.getName());
                } catch (IOException e) {
                    System.out.println("Could not copy or delete file: " + file.getName());
                    e.printStackTrace();
                }

            }

            //Prepare to order by name
            if(group){
                int i=0;
                for(char c:file.getName().toCharArray()){
                    if(Character.isLetter(c)){
                        i++;
                        continue;
                    }
                    if(i<nameLength){ i=nameLength; }
                    break;
                }
                String sub = file.getName().substring(0,i);
                int numberOfOccurence= (subFoldMap.containsKey(sub))? subFoldMap.get(sub).intValue()+1 : 1;
                subFoldMap.put(sub, Integer.valueOf(numberOfOccurence));
            }
        }
        else if(file.isDirectory()){
            foldersToDelete.add(file);
        }

    }

    private static void orderByName(File file){
        int i=0;
        for(char c:file.getName().toCharArray()){
            if(Character.isLetter(c)){
                i++;
                continue;
            }
            if(i<nameLength){ i=nameLength; }
            break;
        }
        String sub = file.getName().substring(0,i);
        if(subFoldMap.get(sub).intValue() >= numberOfFiles){
           //Create subFolder
           File subFolder = new File(file.getParentFile().toString() + File.separatorChar + sub);
           subFolder.mkdir();

            //Copy the File in the folder and delete old file
            File newFile = new File(subFolder.toString() + File.separatorChar + file.getName());
            try {
                Files.copy(file.toPath(), newFile.toPath());
                if(!file.delete()) System.out.println("2Could not delete "+file.getName());
            } catch (SecurityException e) {
                System.out.println("2You don't have read/write access to process file: " + file.getName());
            } catch (IOException e) {
                System.out.println("2Could not copy or delete file: " + file.getName());
            }
        }
    }

    private static String buildRegex(){
        StringBuilder regex = new StringBuilder(".*"+fileToSearch+".*(");

        switch(fileType.toString()){
            case "vid":
                for(int i=1;i<videoFiles.length;i++){
                    if(i<videoFiles.length-1){
                        regex.append(videoFiles[i]+'|');
                    }else{
                        regex.append(videoFiles[i]+"$)");
                    }
                }
                break;
            case "img":
                for(int i=1;i<imagesFiles.length;i++){
                    if(i<imagesFiles.length-1){
                        regex.append(imagesFiles[i]+'|');
                    }else{
                        regex.append(imagesFiles[i]+"$)");
                    }
                }
                break;
        }
        return regex.toString();
    }

    private static int searchFile(File mainFile, int res){
        int resultCount = res;
        Pattern myPattern = Pattern.compile(buildRegex(), Pattern.CASE_INSENSITIVE);
        File[] foundFiles = new File(mainFile.toString()).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return myPattern.matcher(name).matches();
            }
        });
        if(foundFiles.length > 0){
            for(File fil:foundFiles){
                if(fil.isFile()){
                    System.out.println(fil.getAbsolutePath());
                    resultCount++;
                }
            }
        }

        File[] subFolders = new File(mainFile.toString()).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        if (subFolders.length > 0) {
            for(File subFold:subFolders){
                resultCount = searchFile(subFold,resultCount);
            }
        }
        return resultCount;
    }
}
