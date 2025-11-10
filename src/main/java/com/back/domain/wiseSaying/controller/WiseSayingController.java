package com.back.domain.wiseSaying.controller;

import com.back.Rq;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private final WiseSayingService wiseSayingService = new WiseSayingService();
    private final Scanner scanner;

    public WiseSayingController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void handleCommand(String command) {
        Rq rq = new Rq(command);
        if (rq.getActionName().equals("등록")) {
            actionWrite(rq);
        } else if (rq.getActionName().equals("목록")) {
            actionList(rq);
        } else if (rq.getActionName().equals("삭제")) {
            actionDelete(rq);
        } else if (rq.getActionName().equals("수정")) {
            actionUpdate(rq);
        } else if (rq.getActionName().equals("빌드")) {
            wiseSayingService.build();
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }
    }

    public void actionWrite(Rq rq) {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        WiseSaying wiseSaying = wiseSayingService.add(content, author);
        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId()));
    }

    public void actionList(Rq rq) {
        List<WiseSaying> wiseSayings = wiseSayingService.list();
        System.out.println(makePage(rq, wiseSayings));
    }

    private void actionDelete(Rq rq) {
        int id = rq.getParamAsInt("id", -1);
        if (wiseSayingService.delete(id)) {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        } else {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }

    private void actionUpdate(Rq rq) {
        int id = rq.getParamAsInt("id", -1);
        WiseSaying wiseSaying = wiseSayingService.findById(id);
        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        } else {
//            텍스트 블록의 후행 공백은 \s로 표시할 수 있습니다.
            System.out.print("""
                    명언(기존) : %s
                    명언 :\s""".formatted(wiseSaying.getContent()));
            String content = scanner.nextLine();
            System.out.print("""
                    작가(기존) : %s
                    작가 :\s""".formatted(wiseSaying.getAuthor()));
            String author = scanner.nextLine();
            wiseSayingService.update(new WiseSaying(id, content, author));
            System.out.println("%d번 명언이 수정되었습니다.".formatted(id));
        }
    }

    public String makePage(Rq rq, List<WiseSaying> wiseSayings) {
        StringBuilder pageContent = new StringBuilder();
        pageContent.append("""
                번호 / 작가 / 명언
                ------------------------
                """);
        int startIndex = 0;
        int endIndex = wiseSayings.size();
        for (int i = startIndex; i < endIndex; i++) {
            WiseSaying wiseSaying = wiseSayings.get(i);
            pageContent.append("%d / %s / %s\n".formatted(wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent()));
        }
        return pageContent.toString();
    }
}