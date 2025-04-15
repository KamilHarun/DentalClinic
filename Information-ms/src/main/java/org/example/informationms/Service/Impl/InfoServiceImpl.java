package org.example.informationms.Service.Impl;

import com.example.commonms.Exception.ErrorMessage;
import com.example.commonms.Exception.InfoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.informationms.Enum.InfoType;
import org.example.informationms.Model.Info;
import org.example.informationms.Repo.InfoRepo;
import org.example.informationms.Service.InfoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final InfoRepo infoRepo;

    @Override
    public Info getContentByType(InfoType type) {
        return infoRepo.getInfoByType(type)
                .orElseThrow(() -> new InfoNotFoundException(ErrorMessage.INFORMATION_NOT_FOUND_EXCEPTION));
    }

    @Override
    public Info updateContent(InfoType type, String content) {
        Info info = infoRepo.findByType(type).orElse(new Info());
        info.setType(type);
        info.setContent(content);
        return infoRepo.save(info);
    }
}

