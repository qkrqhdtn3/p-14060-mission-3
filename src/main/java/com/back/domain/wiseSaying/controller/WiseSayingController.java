package com.back.domain.wiseSaying.controller;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.io.File;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WiseSayingController {
    private final WiseSayingService wiseSayingService = new WiseSayingService();
    private final Scanner scanner = new Scanner(System.in);
    private final String lastIdFilePath = "db/wiseSaying/lastId.txt";
    private final String wiseSayingDirPath = "db/wiseSaying/";

    public void handleCommand(String command) {
        if (command.startsWith("등록")) {
            System.out.print("명언 : ");
//            debug
            System.out.println(command);
            String content = scanner.nextLine();
            System.out.print("작가 : ");
            String author = scanner.nextLine();
            WiseSaying wiseSaying = wiseSayingService.add(content, author);
            System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
        } else if (command.startsWith("목록")) {
            System.out.println(" 번호 / 작가 / 명언\n------------------------");
            List<WiseSaying> wiseSayings = wiseSayingService.list();
            for(WiseSaying wiseSaying : wiseSayings){
                System.out.printf("%d / %s / %s\n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
            }
        } else if (command.startsWith("삭제")) {
            int id = Integer.parseInt(command.substring(6, command.length()));
            if(wiseSayingService.delete(id)){
                System.out.println(id + "번 명언이 삭제되었습니다.");
            }
            else{
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        } else if (command.startsWith("수정")) {
            int id = Integer.parseInt(command.substring(6, command.length()));
            WiseSaying wiseSaying = wiseSayingService.findById(id);
            if(wiseSaying == null){
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
            else{
                System.out.println("명언(기존) : " + wiseSaying.getContent());
                System.out.print("명언 : ");
                String content = scanner.nextLine();
                System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                System.out.print("작가 : ");
                String author = scanner.nextLine();
                wiseSayingService.update(new WiseSaying(id, content, author));
            }
        } else if (command.startsWith("빌드")) {
            wiseSayingService.build();
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }
    }
}
