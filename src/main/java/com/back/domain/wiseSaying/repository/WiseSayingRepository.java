package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private final List<WiseSaying> wiseSayings = new ArrayList<>();
    private final String lastIdFilePath = "db/wiseSaying/lastId.txt";
    private final String wiseSayingDirPath = "db/wiseSaying/";

//    initData
    public WiseSayingRepository(){
        File lastIdFile = new File(lastIdFilePath);
        File wiseSayingDir = new File(wiseSayingDirPath);
        if(!wiseSayingDir.exists()){
            wiseSayingDir.mkdirs();
        }
        if(!lastIdFile.exists()){
            try {
                lastIdFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(lastIdFilePath);
                fileOutputStream.write("0".getBytes());
                fileOutputStream.close();
            } catch(Exception e){
                System.out.println(e);
            }
        }

        loadWiseSayingsOnStart();
    }

    public void loadWiseSayingsOnStart(){
        File file = new File(wiseSayingDirPath);
        if(file.isDirectory()){
            String[] fileNameList = file.list();
            String target;
            int targetInd;
            String targetId;

            target = ".json";
            for(int i = 0 ; i < fileNameList.length; i++){
                targetInd = fileNameList[i].indexOf(target);
                if(targetInd == -1){
                    continue;
                }
                if(!fileNameList[i].equals("data.json")){
                    targetId = fileNameList[i].substring(0, targetInd);
                    wiseSayings.add(jsonStringToWiseSaying(Integer.parseInt(targetId)));
                }
            }
        }
    }
    public WiseSaying jsonStringToWiseSaying(int id){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("db/wiseSaying/" + id + ".json"));
            StringBuilder sb = new StringBuilder();
            String s;
            WiseSaying wiseSaying = new WiseSaying(id, null, null);
            while((s = bufferedReader.readLine()) != null){
                sb.append(s);
            }

            bufferedReader.close();
            s = sb.toString();

            String target;
            int targetInd;
            String result;

            target = "\"id\"";
            targetInd = s.indexOf(target) + 6;
            result = s.substring(targetInd, s.substring(targetInd).indexOf(",") + targetInd);
            wiseSaying.setId(Integer.parseInt(result));

            target = "\"content\"";
            targetInd = s.indexOf(target) + 12;
            result = s.substring(targetInd, s.substring(targetInd).indexOf("\",") + targetInd);
            wiseSaying.setContent(result);

            target = "\"author\"";
            targetInd = s.indexOf(target) + 11;
            result = s.substring(targetInd, s.substring(targetInd).indexOf("\"") + targetInd);
            wiseSaying.setAuthor(result);

            return wiseSaying;
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    public int getLastId() {
        try {
            FileInputStream fileInputStream = new FileInputStream(lastIdFilePath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            int i = 0;
            String s = "";
            while((i = bufferedInputStream.read()) != -1){
                s += (char) i;
            }
            fileInputStream.close();
            return Integer.parseInt(s);
        } catch(Exception e) {
            System.out.println(e);
            return -1;
        }
    }

    public void setLastId(int lastId) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(lastIdFilePath);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            byte b[] = Integer.toString(lastId).getBytes();
            fileOutputStream.write(b);
            fileOutputStream.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void saveFile(WiseSaying wiseSaying) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(wiseSayingDirPath + Integer.toString(wiseSaying.getId()) + ".json");
            byte b[] = wiseSaying.toJsonString().getBytes();
            fileOutputStream.write(b);
            fileOutputStream.close();
        } catch(Exception e){
            System.out.println(e);
        }
        wiseSayings.add(wiseSaying);
    }

    public WiseSaying findById(int id){
        return wiseSayings.stream().filter(wiseSaying -> wiseSaying.getId() == id).findFirst().orElse(null);
    }

    public List<WiseSaying> findAll() {
        wiseSayings.sort((a, b) -> b.getId() - a.getId());
        return wiseSayings;
    }

    public boolean deleteFile(int id) {
        File file = new File(wiseSayingDirPath + id + ".json");
        WiseSaying wiseSaying = findById(id);
        if(wiseSaying == null) return false;
        if(file.exists()){
            if(!file.delete()){
                System.out.println("deleteFileError");
            }
        }
        if(id == getLastId()){
            setLastId(id - 1);
        }
        wiseSayings.remove(wiseSaying);
        return true;
    }

    public void updateFile(WiseSaying wiseSaying) {
        deleteFile(wiseSaying.getId());
        saveFile(wiseSaying);
    }

    public void build() {
        saveJsonDataFile();
    }

    private void saveJsonDataFile() {
        StringBuffer sb = new StringBuffer("[\n");
        for(WiseSaying wiseSaying : wiseSayings){
            sb.append("  {\n");
            sb.append("    \"id\": " + wiseSaying.getId() + ",\n");
            sb.append("    \"content\": \"" + wiseSaying.getContent() + "\",\n");
            sb.append("    \"author\": \"" + wiseSaying.getAuthor() + "\"\n");
            sb.append("  },\n");
        }
        if(sb.charAt(sb.length() - 2) == ','){
            sb.deleteCharAt(sb.length() - 2);
        }
        sb.append("]");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("db/wiseSaying/data.json");
            byte b[] = sb.toString().getBytes();
            fileOutputStream.write(b);
            fileOutputStream.close();
        } catch(Exception e){
            System.out.println(e);
        }
    }
}
