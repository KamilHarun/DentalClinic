package org.example.informationms.Service;

import org.example.informationms.Enum.InfoType;
import org.example.informationms.Model.Info;

public interface InfoService {
    Info getContentByType(InfoType type);

    Info updateContent(InfoType type, String content);
}
