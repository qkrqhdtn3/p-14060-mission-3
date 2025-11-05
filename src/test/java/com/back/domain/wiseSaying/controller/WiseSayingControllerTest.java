package com.back.domain.wiseSaying.controller;

import com.back.AppTestRunner;
import com.back.global.app.AppConfig;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
    @Test
    @DisplayName("등록")
    void t3() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);
//        debug
//        System.out.println(out);
        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void t4() {
        final String out = AppTestRunner.run("""
                목록
                종료
                """);
//        debug
//        System.out.println(out);
        assertThat(out)
                .contains("번호 / 작가 / 명언\n------------------------")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제")
    void t5() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                목록
                종료
                """);
//        debug
        System.out.println(out);
        // (미구현) 목록에서 id를 얻어와서 삭제 수행
        final String out = AppTestRunner.run("""
                삭제?id=???
                종료
                """);
        assertThat(out)
                .contains("번호 / 작가 / 명언\n------------------------");
    }
    // ...
}