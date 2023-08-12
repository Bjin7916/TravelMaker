package com.app.travelmaker.controller.information;


import com.app.travelmaker.domain.cs.request.CustomServiceDTO;
import com.app.travelmaker.service.cs.CustomSerivceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/informations")
public class InformationApiController {

    private final CustomSerivceService customSerivceService;


    @PostMapping("")
    public void write(@RequestPart(required = true, value = "customServiceDTO") CustomServiceDTO customServiceDTO){
        customSerivceService.register(customServiceDTO);
    }

}
