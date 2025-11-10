package com.back.domain.wiseSaying.controller;

import com.back.AppTestRunner;
//import com.back.global.app.AppConfig;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
    @Test
    @DisplayName("등록")
    void t3() {
        final String addOut = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        assertThat(addOut)
                .contains("명언 :")
                .contains("작가 :")
                .contains("번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void t4() {
        final String listOut = AppTestRunner.run("""
                목록
                종료
                """);

        assertThat(listOut)
                .contains("번호 / 작가 / 명언\n------------------------")
                .contains("/ 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제")
    void t5() {
//        목록에서 id를 얻어와서 삭제 수행
        final String listOut = AppTestRunner.run("""
                목록
                종료
                """);

        String str = listOut.split("------------------------")[1];
        int delNum = 0;
        for(int i = 0 ; i < str.length() ; i++){
            if(str.charAt(i) == '\n'){
                continue;
            }
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                delNum *= 10;
                delNum += str.charAt(i) - '0';
            }
            else{
                break;
            }
        }

        final String delOut = AppTestRunner.run("""
                삭제?id=%d
                종료
                """.formatted(delNum));
        assertThat(delOut)
                .contains("%d번 명언이 삭제되었습니다.".formatted(delNum));


        final String delOut2 = AppTestRunner.run("""
                삭제?id=%d
                종료
                """.formatted(10001));
        assertThat(delOut2)
                .contains("%d번 명언은 존재하지 않습니다.".formatted(10001));
    }

    @Test
    @DisplayName("수정")
    void t6() {
        final String updateOut = AppTestRunner.run("""
                수정?id=1
                update test content
                update test author
                종료
                """);

        assertThat(updateOut)
                .contains("명언(기존) : ")
                .contains("명언 : ")
                .contains("작가(기존) : ")
                .contains("작가 : ");
    }

    @Test
    @DisplayName("빌드")
    void t7() {
        final String updateOut = AppTestRunner.run("""
                빌드
                종료
                """);

        assertThat(updateOut)
                .contains("data.json 파일의 내용이 갱신되었습니다.");
    }
}