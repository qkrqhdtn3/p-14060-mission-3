package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    private final String lastIdFilePath = "db/wiseSaying/lastId.txt";
    private final String wiseSayingDirPath = "db/wiseSaying/";

    public WiseSaying add(String content, String author){
        int lastId = wiseSayingRepository.getLastId() + 1;
        wiseSayingRepository.setLastId(lastId);
        wiseSayingRepository.saveFile(new WiseSaying(lastId, content, author));
        return null;
    }

    public List<WiseSaying> list() {
        return wiseSayingRepository.findAll();
    }

    public boolean delete(int id) {
        return wiseSayingRepository.deleteFile(id);
    }

    public void update(WiseSaying wiseSaying) {
        wiseSayingRepository.updateFile(wiseSaying);
    }

    public void build() {
        wiseSayingRepository.build();
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }
}
