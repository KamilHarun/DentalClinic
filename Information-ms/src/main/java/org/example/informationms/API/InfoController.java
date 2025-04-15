package org.example.informationms.API;

import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.informationms.Enum.InfoType;
import org.example.informationms.Model.Info;
import org.example.informationms.Service.InfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    @GetMapping("{type}")
    public ResponseEntity<Info> getContentByType(@PathVariable InfoType type) {
        return new ResponseEntity<>(infoService.getContentByType(type),OK);
    }

    @PutMapping("/{type}")
    public ResponseEntity<Info> updateContent(
            @PathVariable InfoType type,
            @RequestBody String content) {
        return ResponseEntity.ok(infoService.updateContent(type, content));
    }
}

